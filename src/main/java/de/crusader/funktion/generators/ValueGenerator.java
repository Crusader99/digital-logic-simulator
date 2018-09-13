package de.crusader.funktion.generators;

import java.io.Serializable;

import de.crusader.funktion.api.IValue;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ValueGenerator implements IValue, Serializable {
	private static final long serialVersionUID = 8356072367737595378L;

	// Current value as boolean
	private boolean bool = false;

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.funktion.api.IValue#stringValue()
	 */
	@Override
	public String stringValue() {
		return bool ? "1" : "0";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.funktion.api.IValue#charValue()
	 */
	@Override
	public char charValue() {
		return bool ? '1' : '0';
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.funktion.api.IValue#intValue()
	 */
	@Override
	public int intValue() {
		return bool ? 1 : 0;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.funktion.api.IValue#booleanValue()
	 */
	@Override
	public boolean booleanValue() {
		return bool;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.funktion.api.IValue#set(boolean)
	 */
	@Override
	public synchronized boolean set(boolean b) {
		boolean previous = bool;
		bool = b;

		// Returns true if the value has changed
		return previous != bool;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.funktion.api.IValue#invert()
	 */
	@Override
	public IValue invert() {
		IValue clone = clone();
		clone.set(!clone.booleanValue());
		return clone;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public IValue clone() {
		return IValue.create(this);
	}
}
