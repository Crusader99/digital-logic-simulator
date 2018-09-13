package de.crusader.logic.elements.special;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import de.crusader.logic.system.IFixedInterface;
import de.crusader.logic.system.IPort;
import de.crusader.painter.Painter;

public class SpecialLamp extends ISpecialElement {
	private static final long serialVersionUID = -1333029065857875662L;
	// This value is cached for performance-reasons
	private static final double SIN_45 = Math.sin(Math.toRadians(45));

	/**
	 * Called when creating a new instance.
	 */
	public SpecialLamp() {
		// Initialize input and output ports
		super(new IFixedInterface(new IPort[1]), new IFixedInterface(new IPort[0]));
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#getName()
	 */
	@Override
	public String getName() {
		return "Lampe";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#update()
	 */
	@Override
	public void update() {
		// Do nothing
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.circuit.api.IPainter#draw(de.crusader.painter.Painter,
	 *      java.awt.Rectangle)
	 */
	@Override
	public void draw(Painter p, Rectangle rec) {
		// Draw the background color
		p.createRectangle().color(Color.ORANGE).rectangle(rec).draw();

		// Draw a bright or dark circle
		Point middle = new Point(rec.x + (rec.width >> 1), rec.y + (rec.height >> 1));
		int size = rec.width >> 1;
		boolean b = getInput().getValues()[0].booleanValue();
		p.createCircle().position(middle.x - (size >> 1), middle.y - (size >> 1)).size(size, size)
				.color(b ? Color.YELLOW : Color.BLUE.darker().darker()).filled(true).draw();
		p.createCircle().position(middle.x - (size >> 1), middle.y - (size >> 1)).size(size, size).color(Color.BLACK)
				.filled(false).draw();

		int edge = (int) (SIN_45 * (size >> 1));
		p.createLine().position(middle.x - edge, middle.y - edge).position2(middle.x + edge, middle.y + edge).draw();
		p.createLine().position(middle.x + edge, middle.y - edge).position2(middle.x - edge, middle.y + edge).draw();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#getDefaultSize()
	 */
	public Dimension getDefaultSize() {
		return new Dimension(10, 10);
	}
}