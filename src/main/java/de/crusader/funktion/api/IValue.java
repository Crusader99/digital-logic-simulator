package de.crusader.funktion.api;

import de.crusader.funktion.generators.ValueGenerator;
import lombok.NonNull;

public interface IValue {
	/*
	 * Creates a IValue by boolean
	 */
	static IValue create(boolean b) {
		return new ValueGenerator(b);
	}

	/*
	 * Creates a IValue by String ("1" = true; "0" = false)
	 */
	static IValue create(String s) {
		return new ValueGenerator("1".equals(s));
	}

	/*
	 * Creates a IValue by String ('1' = true; '0' = false)
	 */
	static IValue create(char c) {
		return new ValueGenerator(c == '1');
	}

	/*
	 * Creates a IValue by String (1 = true; 0 = false)
	 */
	static IValue create(int i) {
		return new ValueGenerator(i == 1);
	}

	/*
	 * Creates a clone of the current value
	 */
	static IValue create(IValue v) {
		return new ValueGenerator(v.booleanValue());
	}

	/**
	 * Converts the value to a char type
	 * 
	 * @return char - Returns the value which can be '0' or '1'
	 */
	char charValue();

	/**
	 * @return integer - Returns the value which can be 0 or 1
	 */
	int intValue();

	/**
	 * Converts the value to a boolean type
	 * 
	 * @return boolean - Returns the value which can be true or false
	 */
	boolean booleanValue();

	/**
	 * Converts the value to a string type
	 * 
	 * @return String - Returns the value which can be "0" or "1"
	 */
	@NonNull
	String stringValue();

	/**
	 * Sets the new value
	 * 
	 * @return - Returns true if the value has changed caused by this method
	 */
	default boolean set(int i) {
		return set(i == 1);
	}

	/**
	 * Sets the new value
	 * 
	 * @return - Returns true if the value has changed caused by this method
	 */
	default boolean set(char c) {
		return set(c == '1');
	}

	/**
	 * Sets the new value
	 * 
	 * @return - Returns true if the value has changed caused by this method
	 */
	default boolean set(@NonNull IValue v) {
		return set(v.booleanValue());
	}

	/**
	 * Sets the new value
	 * 
	 * @return - Returns true if the value has changed caused by this method
	 */
	boolean set(boolean b);

	/**
	 * Generates a copy and inverts the current value
	 */
	IValue invert();

	/**
	 * @return - Returns a copy of the current value
	 */
	IValue clone();
}
