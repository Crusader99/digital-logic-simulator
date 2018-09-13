package de.crusader.logic.elements.flipflop;

import java.awt.Point;
import java.awt.Rectangle;

import de.crusader.logic.system.IPort;
import de.crusader.painter.Painter;

public class FlipflopJK_MS extends IFlipflopElement {
	private static final long serialVersionUID = 3852040377228906088L;

	/**
	 * Called when creating a new instance.
	 */
	public FlipflopJK_MS() {
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
		return "JK-MS-Flipflop";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.flipflop.IFlipflopElement#draw(de.crusader.painter.Painter,
	 *      java.awt.Rectangle)
	 */
	@Override
	public void draw(Painter p, Rectangle rec) {
		super.draw(p, rec);

		// Some settings for painting
		int border = (rec.width + rec.height) >> 4;
		int length = (rec.width + rec.height) >> 3;

		// Locations of the edges
		Point edge1 = new Point(rec.x + rec.width - border, rec.y + border);
		Point edge2 = new Point(edge1.x, rec.y + rec.height - border - length);

		// Vertical lines
		p.createLine().position(edge1).height(length).draw();
		p.createLine().position(edge2).height(length).draw();

		// Horizontal lines
		p.createLine().position(edge1).width(-length).draw();
		p.createLine().position(edge2).width(-length).draw();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#update()
	 */
	@Override
	public void update() {
		// TODO: Add update-function
	}

}
