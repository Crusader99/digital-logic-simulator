package de.crusader.controls.workpanel;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.crusader.logic.system.IInterface;
import de.crusader.logic.system.IPort;
import sun.reflect.ReflectionFactory;

@SuppressWarnings("restriction")
public class CloneSystem {
	// Original objects which have to be cloned
	private final List<IWorkObject> objects;
	// Cache for already cloned objects
	private final Map<Integer, Object> cache = new HashMap<>();

	public CloneSystem(List<IWorkObject> objects) {
		this.objects = objects;
	}

	/**
	 * Clones all work objects, given in the constructor
	 * 
	 * @return - Returns a list of all cloned work objects.
	 */
	public List<IWorkObject> cloneAll() {
		try {
			List<IWorkObject> generated = new ArrayList<>();
			for (IWorkObject previous : objects) {
				generated.add(clone(previous, previous.getClass()));
			}
			return generated;
		} catch (Exception ex) {
			// Throw new "hidden" exception
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Clones all work objects an removes links to unlisted work elements, given
	 * in the constructor. Used for Strg + C, Strg + X.
	 * 
	 * @return - Returns a list of all cloned work objects.
	 */
	public List<IWorkObject> cloneAndRemoveUnusedConnections() {
		// Clone all work-elements
		List<IWorkObject> generated = cloneAll();

		// For each work-element
		for (IWorkObject obj : generated) {
			// Check all ports from this work-element
			for (IPort port : obj.getElement().listAllPorts()) {
				// Find invalid connection from this port
				for (IPort target : port.getConnectedPorts()) {
					IInterface io = target.getOwner();
					if (!generated.contains(io.getOwner().getWorkObject())) {
						// Disconnect connection if it's invalid
						target.disconnect();
					}
				}
			}
		}
		return generated;
	}

	/**
	 * Very deep clone: clones object with all parent classes, linked
	 * attributes, arrays, ...
	 * 
	 * @see https://stackoverflow.com/questions/909843/how-to-get-the-unique-id-
	 *      of-an-object-which-overrides-hashcode
	 */
	private <K> K clone(K orginal, Class<? extends K> type) throws Exception {
		if (orginal == null || type.isPrimitive()) {
			return orginal;
		}

		// Check if clone already created
		int orginalHashCode = System.identityHashCode(orginal);
		Object copy = cache.get(orginalHashCode);

		if (copy == null) {
			if (orginal.getClass().isArray()) {
				// Get the length and component-type of the array
				int length = Array.getLength(orginal);
				Class<?> componentType = orginal.getClass().getComponentType();

				// Create new array with same length
				copy = Array.newInstance(componentType, length);

				// Cache the new array
				cache.put(orginalHashCode, copy);

				// Past clones from old instance
				for (int i = 0; i < length; i++) {
					Array.set(copy, i, clone(Array.get(orginal, i), componentType));
				}
			} else {
				// Create new instance an save in list
				cache.put(orginalHashCode, copy = createNewInstance(orginal.getClass()));

				// Update fields of the new instance
				for (Field f : getFields(copy.getClass())) {
					if (Modifier.isStatic(f.getModifiers())) {
						// Ignore static fields
						continue;
					}
					makeAccessible(f);
					f.set(copy, clone(f.get(orginal), f.getType()));
				}
			}
		}
		return type.cast(copy);
	}

	/**
	 * Creates a new instance of the given class without calling any
	 * constructor,
	 * 
	 * @see https://www.javaspecialists.eu/archive/Issue175.html
	 */
	public static <T> T createNewInstance(Class<T> cls) {
		try {
			Constructor<?> intConstr = getConstructor(cls);
			if (intConstr == null) {
				throw new UnsupportedOperationException();
			}
			return cls.cast(intConstr.newInstance());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to create object: " + cls.getSimpleName(), e);
		}
	}

	/**
	 * Tries to find the best constructor for an object. If some code in this
	 * method throws an exception try to fix it using the url at see.
	 * 
	 * @see https://stackoverflow.com/questions/860187/access-restriction-on-
	 *      class-due-to-restriction-on-required-library-rt-jar#2174607
	 * 
	 * @return - Returns the constructor or null, if there an error occurs
	 */
	private static Constructor<?> getConstructor(Class<?> cls) {
		try {
			return cls.getConstructor();
		} catch (Exception ex) {
			ReflectionFactory factory = ReflectionFactory.getReflectionFactory();
			Class<?> parent = cls.getSuperclass();
			Constructor<?> initConstr;
			if (parent == null) {
				initConstr = factory.newConstructorForSerialization(cls);
			} else {
				initConstr = factory.newConstructorForSerialization(cls, getConstructor(parent));
			}
			return initConstr;
		}
	}

	/*
	 * Get all declared fields (including from parent class)
	 */
	public static Iterable<Field> getFields(Class<?> startClass) {
		List<Field> currentClassFields = new ArrayList<>(Arrays.asList(startClass.getDeclaredFields()));

		Class<?> parentClass = startClass.getSuperclass();
		if (parentClass != null) {
			List<Field> parentClassFields = (List<Field>) getFields(parentClass);
			currentClassFields.addAll(parentClassFields);
		}

		return currentClassFields;
	}

	/**
	 * Use this method to set a new value to a field. Final or private keywords
	 * are ignored.
	 * 
	 * @see https://stackoverflow.com/questions/3301635/change-private-static-
	 *      final-field-using-java-reflection
	 */
	public static void makeAccessible(Field field) throws Exception {
		// Make private accessible
		field.setAccessible(true);

		// Access to final field
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
	}
}
