package de.crusader.logic.elements.converter;

import java.util.HashMap;
import java.util.Map;

import de.crusader.logic.elements.IElement;
import de.crusader.logic.system.IInterface;

public abstract class IConverterElement extends IElement {
	private static final long serialVersionUID = 1416479300928050539L;
	/**
	 * First integer: input value; Second integer: output value
	 */
	private Map<Integer, Integer> function = new HashMap<>();

	public IConverterElement(IInterface input, IInterface output) {
		super(input, output);
	}

	/**
	 * Init the function
	 */
	protected void init(int input, int output) {
		function.put(input, output);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#update()
	 */
	@Override
	public void update() {
		int in = getInput().getBinaryValues(false);
		Integer out = function.get(in);
		if (out == null) { // throw exception
			throw new NullPointerException("No output value found for 0b" + Integer.toBinaryString(in));
		}
		getOutput().updateBinaryValues(out);
	}
}
