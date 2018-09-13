package de.crusader.operators;

import de.crusader.funktion.api.IValue;
import de.crusader.operators.api.IBasicOppositeOperator;
import de.crusader.operators.api.IMultipleInputOperator;

public class OperatorAND implements IBasicOppositeOperator, IMultipleInputOperator {
	private static final long serialVersionUID = 1399011282887968609L;

	// No access for other packages. Use Operators.*
	protected OperatorAND() {
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.operators.api.IBasicOperator#getDisplayChar()
	 */
	@Override
	public char getDisplayChar() {
		return '&';
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.operators.api.IOperator#getName()
	 */
	@Override
	public String getName() {
		return "AND";
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
		for (IValue v : inputs) {
			if (!v.booleanValue()) {
				return IValue.create(false);
			}
		}
		return IValue.create(true);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.operators.api.IBasicOppositeOperator#getOpposite()
	 */
	@Override
	public IBasicOppositeOperator getOpposite() {
		return (IBasicOppositeOperator) Operators.OR;
	}

}
