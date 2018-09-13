package de.crusader.logic.elements.logicgate;

import de.crusader.logic.system.IDynamicInterface;
import de.crusader.logic.system.IFixedInterface;
import de.crusader.logic.system.IPort;
import de.crusader.operators.Operators;

public class LogicGateOR extends ILogicGateElement {
	private static final long serialVersionUID = -1737186319882766329L;

	/**
	 * Called when creating a new instance.
	 */
	public LogicGateOR() {
		// Initialize input and output ports
		super(Operators.OR, new IDynamicInterface(2), new IFixedInterface(new IPort()));
	}

}