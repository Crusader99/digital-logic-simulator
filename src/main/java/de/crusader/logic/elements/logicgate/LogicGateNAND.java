package de.crusader.logic.elements.logicgate;

import de.crusader.logic.system.IDynamicInterface;
import de.crusader.logic.system.IFixedInterface;
import de.crusader.logic.system.IPort;
import de.crusader.operators.Operators;

public class LogicGateNAND extends ILogicGateElement {
	private static final long serialVersionUID = -8461091269587580378L;

	/**
	 * Called when creating a new instance.
	 */
	public LogicGateNAND() {
		// Initialize input and output ports
		super(Operators.NAND, new IDynamicInterface(2), new IFixedInterface(new IPort().invertSymbol()));
	}

}
