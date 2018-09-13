package de.crusader.logic.elements.logicgate;

import de.crusader.logic.system.IDynamicInterface;
import de.crusader.logic.system.IFixedInterface;
import de.crusader.logic.system.IPort;
import de.crusader.operators.Operators;

public class LogicGateXOR extends ILogicGateElement {
	private static final long serialVersionUID = 6298997673527514623L;

	/**
	 * Called when creating a new instance.
	 */
	public LogicGateXOR() {
		// Initialize input and output ports
		super(Operators.XOR, new IDynamicInterface(2), new IFixedInterface(new IPort()));
	}

}
