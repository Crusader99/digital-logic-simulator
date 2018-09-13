package de.crusader.operators;

import de.crusader.funktion.api.IValue;
import de.crusader.operators.api.IMultipleInputOperator;
import de.crusader.operators.api.IOperator;

public class OperatorXOR implements IOperator, IMultipleInputOperator {
	private static final long serialVersionUID = 6365841170063923593L;

	// No access for other packages. Use Operators.*
	protected OperatorXOR() {
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.operators.api.IOperator#getName()
	 */
	@Override
	public String getName() {
		return "XOR";
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
		return Operators.AND.execute(Operators.OR.execute(inputs), Operators.AND.execute(inputs).invert());
	}

}
