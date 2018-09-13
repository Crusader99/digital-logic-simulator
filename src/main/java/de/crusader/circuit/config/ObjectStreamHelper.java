package de.crusader.circuit.config;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectStreamHelper {
	/*
	 * Reads an object from file
	 */
	public static Object read(File file) throws IOException, ClassNotFoundException {
		// Open file stream, buffer stream and use object stream to read object
		try (FileInputStream fis = new FileInputStream(file);
				BufferedInputStream bis = new BufferedInputStream(fis);
				ObjectInputStream ois = new ObjectInputStream(bis)) {
			// Read object from stream
			return ois.readObject();
		} // Automatically calls the close method
	}

	/*
	 * Write an object to file
	 */
	public static void save(File file, Object obj) throws IOException {
		// Open file stream, buffer stream and use object stream write object
		try (FileOutputStream fos = new FileOutputStream(file);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				ObjectOutputStream oos = new ObjectOutputStream(bos)) {
			// Write Object to stream
			oos.writeObject(obj);
		} // Automatically calls the close method
	}
}
