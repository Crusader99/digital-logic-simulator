package de.crusader.circuit.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.crusader.circuit.modules.Modules;
import de.crusader.controls.workpanel.IWorkObject;
import de.crusader.controls.workpanel.ObjectList;
import de.crusader.controls.workpanel.ObjectsHistory;
import de.crusader.controls.workpanel.WorkPanel;
import de.crusader.funktion.generators.ValueGenerator;
import de.crusader.logic.system.IDynamicInterface;
import de.crusader.logic.system.IFixedInterface;
import de.crusader.logic.system.IPort;

public class ConfigSystem {
	private static List<Class<?>> classSet = new ArrayList<>();

	// Called on initialize
	static {
		classSet.add(IPort.class);
		classSet.add(IDynamicInterface.class);
		classSet.add(IFixedInterface.class);
		classSet.add(ValueGenerator.class);
		classSet.add(ConfigDetails.class);
	}

	/*
	 * Find a class by the serial unique id
	 */
	public static Class<?> getClassBySerialId(long uid) throws Exception {
		if (uid <= classSet.size() && uid >= 1) {
			return classSet.get((int) uid - 1);
		}
		return Modules.getClassBySerialId(uid);
	}

	/*
	 * Find a the serial unique id by class
	 */
	public static long getSerialIdOfClass(Class<?> cls) throws Exception {
		int index = classSet.indexOf(cls);
		if (index >= 0) {
			return index + 1;
		}
		return Modules.getSerialIdOfClass(cls);
	}

	/*
	 * Load the given WorkPanel from file
	 */
	public static void loadFile(WorkPanel panel, File file) throws IOException {
		if (panel == null) {
			throw new NullPointerException("panel");
		}
		if (file == null) {
			throw new NullPointerException("file");
		}
		try {
			@SuppressWarnings("unchecked")
			Collection<Object> list = (Collection<Object>) ObjectStreamHelper.read(file);
			ObjectsHistory history = panel.getHistory();
			history.clear();
			ObjectList objectList = history.getCurrentObjects();
			ConfigDetails details = null;
			for (Object obj : list) {
				if (obj instanceof ConfigDetails) {
					details = (ConfigDetails) obj;
					continue;
				}
				if (!(obj instanceof IWorkObject)) {
					continue;
				}
				objectList.add((IWorkObject) obj);
			}
			if (details == null) {
				throw new NullPointerException("No details found");
			} else {
				history.setConfigDetails(details);
				panel.setCurrentFile(file);
			}
		} catch (Throwable t) {
			throw new IOException("Unable to load file", t);
		}
	}

	/*
	 * Save the given WorkPanel to file
	 */
	public static void saveFile(WorkPanel panel, File file) throws IOException {
		if (panel == null) {
			throw new NullPointerException("panel");
		}
		if (file == null) {
			throw new NullPointerException("file");
		}
		panel.setCurrentFile(file);
		ConfigDetails details = panel.getHistory().getConfigDetails();
		details.setBuildVersion(details.getBuildVersion() + 1);
		details.setTimestampEdited(System.currentTimeMillis());
		String user = System.getProperty("user.name");
		if (!details.getCreators().contains(user)) {
			details.getCreators().add(user);
		}
		try {
			List<Object> objList = new ArrayList<>();
			objList.add(details);
			for (IWorkObject wo : panel.getHistory().getCurrentObjects()) {
				objList.add(wo);
			}
			ObjectStreamHelper.save(file, objList);
		} catch (Throwable t) {
			throw new IOException("Unable to save file", t);
		}
		panel.setUnsavedChanges(false);
	}
}
