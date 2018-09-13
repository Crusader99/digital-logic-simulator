package de.crusader.operators;

import de.crusader.funktion.api.IValue;
import de.crusader.operators.api.IMultipleInputOperator;
import de.crusader.operators.api.IOperator;

public class OperatorNAND implements IOperator, IMultipleInputOperator {
	private static final long serialVersionUID = -3568351982412248036L;

	// No access for other packages. Use Operators.*
	protected OperatorNAND() {
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.operators.api.IOperator#getName()
	 */
	@Override
	public String getName() {
		return "NAND";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.operators.api.IOperator#getDisplaySymbol()
	 */
	@Override
	public String getDisplaySymbol() {
		return "&";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.funktion.api.IExecutable#execute(de.crusader.funktion.api.IValue[])
	 */
	@Override
	public IValue execute(IValue... inputs) {
		// Perform operation...
		return Operators.AND.execute(inputs).invert();
	}

}
