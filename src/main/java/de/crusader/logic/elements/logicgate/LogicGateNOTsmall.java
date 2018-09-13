package de.crusader.logic.elements.logicgate;

import java.awt.Dimension;

public class LogicGateNOTsmall extends LogicGateNOT {
	private static final long serialVersionUID = -2648948010011091498L;

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.logicgate.ILogicGateElement#getDefaultSize()
	 */
	public Dimension getDefaultSize() {
		return new Dimension(5, 5);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.logicgate.ILogicGateElement#getName()
	 */
	@Override
	public String getName() {
		return getOperator().getName() + "-Gatter (kleiner)";
	}
}