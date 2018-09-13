package de.crusader.funktion.api;

public interface IExecutable {
	
	/**
	 * @return the new value changed by the current operator
	 */
	IValue execute(IValue... inputs);

}
