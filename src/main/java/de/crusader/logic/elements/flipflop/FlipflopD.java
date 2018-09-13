package de.crusader.logic.elements.flipflop;

import de.crusader.funktion.api.IValue;
import de.crusader.logic.system.IPort;

public class FlipflopD extends IFlipflopElement {
	private static final long serialVersionUID = 3852040377228906088L;

	/**
	 * Called when creating a new instance.
	 */
	public FlipflopD() {
		// Initialize input and output ports
		super(new IPort().name("1D"), new IPort().name("C1").flankSymbol());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#getName()
	 */
	@Override
	public String getName() {
		return "D-Flipflop";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#update()
	 */
	@Override
	public void update() {
		// Get input values
		IValue[] in = getInput().getValues();
		
		// Assign value to name
		IValue d = in[0];
		IValue c = in[1];

		// Only activate on positive flank
		if (!c.booleanValue() || !getInput().hasFlank(c)) {
			return;
		}

		// Update output values: Q, !Q
		getOutput().updateValues(d, d.invert());
	}

}
