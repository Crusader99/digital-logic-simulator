package de.crusader.circuit.modules;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.crusader.controls.workpanel.ExtendedWorkObject;
import de.crusader.controls.workpanel.IWorkObject;
import de.crusader.logic.elements.ElementRegistry;
import de.crusader.logic.elements.IElement;
import lombok.Getter;

public class Modules {
	/*
	 * List of all module classes
	 */
	@Getter
	private static List<IWorkObject> list = new CopyOnWriteArrayList<>();

	// Called on initialize
	static {
		try {
			loadModules();

			// check for valid serial id
			List<Class<?>> classes = new ArrayList<>();
			for (IWorkObject obj : list) {
				Class<?> cls = obj.getClass();
				if (classes.contains(cls)) {
					continue;
				}
				classes.add(cls);
			}
			List<Long> usedIds = new ArrayList<>();
			for (Class<?> cls : classes) {
				long serialId = getSerialIdOfClass(cls);
				if (usedIds.contains(serialId)) {
					throw new UnsupportedOperationException("Same serialId is used several times");
				}
			}
		} catch (Throwable t) {
			new RuntimeException("Error while loading modules", t).printStackTrace();
		}
	}

	/*
	 * Load modules from element registry
	 */
	private static void loadModules() {
		for (Class<? extends IElement> ff : ElementRegistry.ELEMENTS) {
			try {
				list.add(new ExtendedWorkObject(ff.newInstance()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * Find a class by the serial unique id
	 */
	public static Class<?> getClassBySerialId(long uid) throws Exception {
		for (IWorkObject obj : list) {
			if (getSerialIdOfClass(obj.getClass()) == uid) {
				return obj.getClass();
			}
		}
		return null;
	}

	/*
	 * Reads the value of the static field "serialVersionUID"
	 */
	public static long getSerialIdOfClass(Class<?> cls) throws Exception {
		// Made for compatibility with ProGuard
		for (Field f : cls.getDeclaredFields()) {
			int modifier = f.getModifiers();
			if (f.getType().isAssignableFrom(long.class) && Modifier.isStatic(modifier)) {
				f.setAccessible(true);
				return f.getLong(null);
			}
		}

		// if check whether this fails
		try {
			Field f = cls.getDeclaredField("serialVersionUID");
			f.setAccessible(true);
			return f.getLong(null);
		} catch (NoSuchFieldException ex) {
			throw new IOException(cls.getSimpleName());
		}
	}
}
