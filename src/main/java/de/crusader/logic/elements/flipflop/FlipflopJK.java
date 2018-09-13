package de.crusader.logic.elements.flipflop;

import de.crusader.funktion.api.IValue;
import de.crusader.logic.system.IPort;

public class FlipflopJK extends IFlipflopElement {
	private static final long serialVersionUID = 3852040377228906088L;

	/**
	 * Called when creating a new instance.
	 */
	public FlipflopJK() {
		// Initialize input and output ports
		super(new IPort().name("1J"), new IPort().name("C1").flankSymbol(), new IPort().name("1K"));
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#getName()
	 */
	@Override
	public String getName() {
		return "JK-Flipflop";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#update()
	 */
	@Override
	public void update() {
		IValue[] in = getInput().getValues();
		IValue j = in[0], c = in[1], k = in[2];

		// Only activate on positive flank
		if (!c.booleanValue() || !getInput().hasFlank(c)) {
			return;
		}

		if (j.intValue() == 1 && k.intValue() == 1) {
			// toggle
			getOutput().updateBinaryValues(~getOutput().getBinaryValues());
		} else if (j.intValue() == 1 && k.intValue() == 0) {
			// set
			getOutput().updateBinaryValues(0b10);
		} else if (j.intValue() == 0 && k.intValue() == 1) {
			// reset
			getOutput().updateBinaryValues(0b01);
		}
		// Else keep same output state
	}

}
