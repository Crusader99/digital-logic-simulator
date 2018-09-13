package de.crusader.logic.elements.logicgate;

import de.crusader.logic.system.IDynamicInterface;
import de.crusader.logic.system.IFixedInterface;
import de.crusader.logic.system.IPort;
import de.crusader.operators.Operators;

public class LogicGateNOR extends ILogicGateElement {
	private static final long serialVersionUID = 7126110175823662618L;

	/**
	 * Called when creating a new instance.
	 */
	public LogicGateNOR() {
		// Initialize input and output ports
		super(Operators.NOR, new IDynamicInterface(2), new IFixedInterface(new IPort().invertSymbol()));
	}

}
