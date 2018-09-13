package de.crusader.circuit.config;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Primitives {

	/*
	 * Write object to DataOutputStream
	 */
	public static void write(Class<?> type, Object obj, DataOutputStream out) throws IOException {
		if (type.isAssignableFrom(boolean.class)) {
			out.writeBoolean((boolean) obj);
		} else if (type.isAssignableFrom(byte.class)) {
			out.writeByte((byte) obj);
		} else if (type.isAssignableFrom(short.class)) {
			out.writeShort((short) obj);
		} else if (type.isAssignableFrom(char.class)) {
			out.writeChar((char) obj);
		} else if (type.isAssignableFrom(int.class)) {
			out.writeInt((int) obj);
		} else if (type.isAssignableFrom(long.class)) {
			out.writeLong((long) obj);
		} else if (type.isAssignableFrom(float.class)) {
			out.writeFloat((float) obj);
		} else if (type.isAssignableFrom(double.class)) {
			out.writeDouble((double) obj);
		} else {
			throw new UnsupportedOperationException(type.getSimpleName());
		}
	}

	/*
	 * Read object from DataInputStream
	 */
	public static Object read(Class<?> type, DataInputStream in) throws IOException {
		if (type.isAssignableFrom(boolean.class)) {
			return in.readBoolean();
		} else if (type.isAssignableFrom(byte.class)) {
			return in.readBoolean();
		} else if (type.isAssignableFrom(short.class)) {
			return in.readShort();
		} else if (type.isAssignableFrom(char.class)) {
			return in.readChar();
		} else if (type.isAssignableFrom(int.class)) {
			return in.readInt();
		} else if (type.isAssignableFrom(long.class)) {
			return in.readLong();
		} else if (type.isAssignableFrom(float.class)) {
			return in.readFloat();
		} else if (type.isAssignableFrom(double.class)) {
			return in.readDouble();
		} else {
			throw new UnsupportedOperationException(type.getSimpleName());
		}
	}

}
