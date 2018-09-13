package de.crusader.logic.elements.logicgate;

import de.crusader.logic.system.IFixedInterface;
import de.crusader.logic.system.IPort;
import de.crusader.operators.Operators;

public class LogicGateNOT extends ILogicGateElement {
	private static final long serialVersionUID = 2588061053457088524L;

	/**
	 * Called when creating a new instance.
	 */
	public LogicGateNOT() {
		// Initialize input and output ports
		super(Operators.NOT, new IFixedInterface(new IPort[1]), new IFixedInterface(new IPort().invertSymbol()));
	}
}