package de.crusader.logic.elements.flipflop;

import de.crusader.funktion.api.IValue;
import de.crusader.logic.system.IPort;
import de.crusader.operators.Operators;

public class FlipflopEL extends IFlipflopElement {
	private static final long serialVersionUID = 3852040377228906088L;
	// Cache for the last state
	private IValue hiddenOutput = IValue.create(true);

	/**
	 * Called when creating a new instance.
	 */
	public FlipflopEL() {
		// Initialize input and output ports
		super(new IPort[] { new IPort().name("S1"), new IPort().name("R") }, new IPort[] { new IPort() });

		// Set name for outputs
		for (IPort out : getOutput().listPorts()) {
			out.name("1");
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#getName()
	 */
	@Override
	public String getName() {
		return "EL-Flipflop";
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

		in[0] = Operators.AND.execute(in[0], in[1].invert());

		hiddenOutput = Operators.NOR.execute(in[0], out[0]);
		out[0] = Operators.NOR.execute(in[1], hiddenOutput);

		// Update the output ports the new calculated values
		getOutput().updateValues(out);
	}

}
