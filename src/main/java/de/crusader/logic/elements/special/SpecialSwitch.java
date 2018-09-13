package de.crusader.logic.elements.special;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import de.crusader.funktion.api.IValue;
import de.crusader.logic.system.IFixedInterface;
import de.crusader.logic.system.IPort;
import de.crusader.painter.Painter;

public class SpecialSwitch extends ISpecialElement {
	private static final long serialVersionUID = -1333029065857875662L;
	// The current value of this element
	private IValue currentValue = IValue.create(false);

	/**
	 * Called when creating a new instance.
	 */
	public SpecialSwitch() {
		// Initialize input and output ports
		super(new IFixedInterface(new IPort[0]), new IFixedInterface(new IPort[1]));
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#getName()
	 */
	@Override
	public String getName() {
		return "Schalter";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#click()
	 */
	@Override
	public void click() {
		// Toggle current output value
		currentValue.set(currentValue.invert());
		update();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#update()
	 */
	@Override
	public void update() {
		getOutput().updateValues(new IValue[] { currentValue });
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

		// Draw the switch symbol
		p.createRectangle().position(rec.x + (rec.height / 5), rec.y + 10).size(2, rec.height / 4).color(Color.BLACK)
				.draw();
		p.createCircle().position(rec.x + (rec.height / 10), rec.y + rec.height - (rec.height / 3))
				.size(rec.height / 4, rec.height / 4).color(Color.BLACK).filled(false).draw();
		Point pointerStart = new Point(rec.x + rec.width - (rec.height / 5), rec.y + rec.height / 2);
		if (getOutput().getValues()[0].booleanValue()) {
			p.createLine().position(pointerStart).position2(rec.x + (rec.width / 3), rec.y + (rec.height / 3))
					.color(Color.BLACK).draw();
		} else {
			p.createLine().position(pointerStart.x, pointerStart.y + rec.height / 16)
					.position2(rec.x + (rec.width / 3), rec.y + rec.height - rec.height / 3).color(Color.BLACK).draw();
		}
		p.createRectangle().position(pointerStart).size(rec.height / 8, rec.height / 8).color(Color.BLACK).filled(true)
				.draw();
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