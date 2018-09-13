package de.crusader.logic.elements.flipflop;

import de.crusader.funktion.api.IValue;
import de.crusader.logic.system.IPort;

public class FlipflopT extends IFlipflopElement {
	private static final long serialVersionUID = 3852040377228906088L;

	/**
	 * Called when creating a new instance.
	 */
	public FlipflopT() {
		// Initialize input and output ports
		super(new IPort().name("T1").flankSymbol());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#getName()
	 */
	@Override
	public String getName() {
		return "T-Flipflop";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#update()
	 */
	@Override
	public void update() {
		IValue t = getInput().getValues()[0];
		if (!t.booleanValue()) {
			return;
		}

		// Only activate on positive flank
		if (!t.booleanValue() || !getInput().hasFlank(t)) {
			return;
		}

		// Toggle output values
		getOutput().updateBinaryValues(~getOutput().getBinaryValues());
	}

}
