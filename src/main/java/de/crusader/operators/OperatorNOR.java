package de.crusader.operators;

import de.crusader.funktion.api.IValue;
import de.crusader.operators.api.IMultipleInputOperator;
import de.crusader.operators.api.IOperator;

public class OperatorNOR implements IOperator, IMultipleInputOperator {
	private static final long serialVersionUID = 6591543076741197720L;

	// No access for other packages. Use Operators.*
	protected OperatorNOR() {
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.operators.api.IOperator#getName()
	 */
	@Override
	public String getName() {
		return "NOR";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.operators.api.IOperator#getDisplaySymbol()
	 */
	@Override
	public String getDisplaySymbol() {
		// Get a special char (>=)
		char largerOrEqual = (char) 8805;
		return largerOrEqual + "1";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.funktion.api.IExecutable#execute(de.crusader.funktion.api.IValue[])
	 */
	@Override
	public IValue execute(IValue... inputs) {
		// Perform operation...
		return Operators.OR.execute(inputs).invert();
	}

}
