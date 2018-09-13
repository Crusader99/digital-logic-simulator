package de.crusader.operators.api;

public interface IBasicOppositeOperator extends IBasicOperator, IMultipleInputOperator {
	/**
	 * @return - Returns the opposite of this operator
	 * 
	 * Example: AND -> OR
	 */
	IBasicOppositeOperator getOpposite();
}
