package de.crusader.logic.elements.flipflop;

import de.crusader.funktion.api.IValue;
import de.crusader.logic.system.IPort;
import de.crusader.operators.Operators;

public class FlipflopRS extends IFlipflopElement {
	private static final long serialVersionUID = 3852040377228906088L;

	/**
	 * Called when creating a new instance.
	 */
	public FlipflopRS() {
		// Initialize input and output ports
		super(new IPort().name("S"), new IPort().name("R"));
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#getName()
	 */
	@Override
	public String getName() {
		return "RS-Flipflop";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#update()
	 */
	@Override
	public void update() {
		IValue[] in = getInput().getValues();
		IValue[] out = getOutput().getValues();

		out[1] = Operators.NOR.execute(in[0], out[0]);
		out[0] = Operators.NOR.execute(in[1], out[1]);

		// Update the output ports the new calculated values
		getOutput().updateValues(out);
	}

}
