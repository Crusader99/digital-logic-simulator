package de.crusader.operators.api;

import java.io.Serializable;

import de.crusader.funktion.api.IExecutable;
import lombok.NonNull;

public interface IOperator extends IExecutable, Serializable {
	/*
	 * Examples: "NOT", "AND", "XNOR"
	 */
	@NonNull
	String getName();

	/*
	 * Example: "&"
	 */
	String getDisplaySymbol();

}
