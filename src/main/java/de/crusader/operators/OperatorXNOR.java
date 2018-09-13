package de.crusader.operators;

import java.io.Serializable;

import de.crusader.funktion.api.IValue;
import de.crusader.operators.api.IMultipleInputOperator;
import de.crusader.operators.api.IOperator;

public class OperatorXNOR implements IOperator, IMultipleInputOperator, Serializable {
	private static final long serialVersionUID = -5904049932817987465L;

	// No access for other packages. Use Operators.*
	protected OperatorXNOR() {
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.operators.api.IOperator#getName()
	 */
	@Override
	public String getName() {
		return "XNOR";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.operators.api.IOperator#getDisplaySymbol()
	 */
	@Override
	public String getDisplaySymbol() {
		return "=1";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.funktion.api.IExecutable#execute(de.crusader.funktion.api.IValue[])
	 */
	@Override
	public IValue execute(IValue... inputs) {
		// Perform operation...
		return Operators.XOR.execute(inputs).invert();
	}

}
