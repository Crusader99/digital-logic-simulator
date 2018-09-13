package de.crusader.logic.elements.logicgate;

import de.crusader.logic.system.IDynamicInterface;
import de.crusader.logic.system.IFixedInterface;
import de.crusader.logic.system.IPort;
import de.crusader.operators.Operators;

public class LogicGateAND extends ILogicGateElement {
	private static final long serialVersionUID = 5266430392615781393L;

	/**
	 * Called when creating a new instance.
	 */
	public LogicGateAND() {
		// Initialize input and output ports
		super(Operators.AND, new IDynamicInterface(2), new IFixedInterface(new IPort()));
	}

}
