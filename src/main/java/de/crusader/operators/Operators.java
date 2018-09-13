package de.crusader.operators;

import de.crusader.operators.api.IBasicOperator;
import de.crusader.operators.api.IBasicOppositeOperator;
import de.crusader.operators.api.IOperator;

public class Operators {
	// Constants to access to binary operators
	public static final IBasicOperator NOT = new OperatorNOT();
	public static final IBasicOppositeOperator AND = new OperatorAND();
	public static final IBasicOppositeOperator OR = new OperatorOR();
	public static final IOperator NAND = new OperatorNAND();
	public static final IOperator NOR = new OperatorNOR();
	public static final IOperator XOR = new OperatorXOR();
	public static final IOperator XNOR = new OperatorXNOR();

	/**
	 * @return - Returns an array of all operators
	 */
	public static final IOperator[] list() {
		return new IOperator[] { NOT, AND, OR, NAND, NOR, XOR, XNOR };
	}
}
