package de.crusader.operators;

import de.crusader.funktion.api.IValue;
import de.crusader.operators.api.IBasicOppositeOperator;
import de.crusader.operators.api.IMultipleInputOperator;

public class OperatorOR implements IBasicOppositeOperator, IMultipleInputOperator {
	private static final long serialVersionUID = 1739796608719636585L;

	// No access for other packages. Use Operators.*
	protected OperatorOR() {
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.operators.api.IBasicOperator#getDisplayChar()
	 */
	@Override
	public char getDisplayChar() {
		return '|';
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.operators.api.IOperator#getName()
	 */
	@Override
	public String getName() {
		return "OR";
	}

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
		// Check all input values
		for (IValue v : inputs) {
			if (v.booleanValue()) {
				// Return true, if one of the inputs is true
				return IValue.create(true);
			}
		}
		// Otherwise return false
		return IValue.create(false);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.operators.api.IBasicOppositeOperator#getOpposite()
	 */
	@Override
	public IBasicOppositeOperator getOpposite() {
		return (IBasicOppositeOperator) Operators.AND;
	}

}
