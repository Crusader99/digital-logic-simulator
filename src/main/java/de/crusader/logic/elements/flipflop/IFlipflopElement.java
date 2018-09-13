package de.crusader.logic.elements.flipflop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import de.crusader.logic.elements.IElement;
import de.crusader.logic.system.IFixedInterface;
import de.crusader.logic.system.IPort;
import de.crusader.painter.Painter;

public abstract class IFlipflopElement extends IElement {
	private static final long serialVersionUID = 7232414404546337305L;

	/**
	 * Called when creating a new instance.
	 */
	public IFlipflopElement(IPort... inputPorts) {
		this(inputPorts, new IPort[] { new IPort(), new IPort().invertSymbol() });
		getOutput().updateBinaryValues(0b01);
	}

	/**
	 * Called when creating a new instance.
	 */
	public IFlipflopElement(IPort[] inputPorts, IPort[] outputPorts) {
		super(new IFixedInterface(inputPorts), new IFixedInterface(outputPorts));
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.circuit.api.IPainter#draw(de.crusader.painter.Painter,
	 *      java.awt.Rectangle)
	 */
	@Override
	public void draw(Painter p, Rectangle rec) {
		// Draw background color
		p.createRectangle().filled(true).color(Color.GREEN).rectangle(rec).draw();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#getDefaultSize()
	 */
	@Override
	public Dimension getDefaultSize() {
		return new Dimension(10, 10);
	}
}
