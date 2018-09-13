package de.crusader.logic.elements.flipflop;

import java.awt.Dimension;

import de.crusader.funktion.api.IValue;
import de.crusader.logic.system.IPort;

public class FlipflopJKwithRS extends IFlipflopElement {
	private static final long serialVersionUID = 3314527482994032726L;

	/**
	 * Called when creating a new instance.
	 */
	public FlipflopJKwithRS() {
		// Initialize input and output ports
		super(new IPort().name("S"), new IPort().name("1J"), new IPort().name("C1").flankSymbol(),
				new IPort().name("1K"), new IPort().name("R"));
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#getName()
	 */
	@Override
	public String getName() {
		return "JK-Flipflop + Set/Reset";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#update()
	 */
	@Override
	public void update() {
		IValue[] in = getInput().getValues();
		IValue s = in[0], j = in[1], c = in[2], k = in[3], r = in[4];

		if (s.booleanValue()) { // Set is dominating
			// set
			getOutput().updateBinaryValues(0b10);

			// don't check for flank
			return;
		} else if (r.booleanValue()) { // If reset signal received
			// reset
			getOutput().updateBinaryValues(0b01);

			// don't check for flank
			return;
		}

		// Only activate the following code on positive flank
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
	
	/**
	 * (non-Javadoc)
	 * @see de.crusader.logic.elements.flipflop.IFlipflopElement#getDefaultSize()
	 */
	@Override
	public Dimension getDefaultSize() {
		return new Dimension(10, 20);
	}
}
