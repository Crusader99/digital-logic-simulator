package de.crusader.logic.elements.logicgate;

import de.crusader.logic.system.IDynamicInterface;
import de.crusader.logic.system.IFixedInterface;
import de.crusader.logic.system.IPort;
import de.crusader.operators.Operators;

public class LogicGateXNOR extends ILogicGateElement {
	private static final long serialVersionUID = -7707491651603652578L;

	/**
	 * Called when creating a new instance.
	 */
	public LogicGateXNOR() {
		// Initialize input and output ports
		super(Operators.XNOR, new IDynamicInterface(2), new IFixedInterface(new IPort().invertSymbol()));
	}

}
