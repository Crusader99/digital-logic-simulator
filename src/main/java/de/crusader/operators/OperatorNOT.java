package de.crusader.operators;

import de.crusader.funktion.api.IValue;
import de.crusader.operators.api.IBasicOperator;

public class OperatorNOT implements IBasicOperator {
	private static final long serialVersionUID = -7462563918640283706L;

	// No access for other packages. Use Operators.*
	protected OperatorNOT() {
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.operators.api.IBasicOperator#getDisplayChar()
	 */
	@Override
	public char getDisplayChar() {
		return '!';
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.operators.api.IOperator#getName()
	 */
	@Override
	public String getName() {
		return "NOT";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.operators.api.IOperator#getDisplaySymbol()
	 */
	@Override
	public String getDisplaySymbol() {
		return "1";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.funktion.api.IExecutable#execute(de.crusader.funktion.api.IValue[])
	 */
	@Override
	public IValue execute(IValue... inputs) {
		if (inputs.length > 1) {
			// NOT can only have one input
			throw new UnsupportedOperationException("only one input supported for " + getName());
		}
		// Perform operation...
		return inputs[0].invert();
	}

}
