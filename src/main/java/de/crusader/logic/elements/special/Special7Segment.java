package de.crusader.logic.elements.special;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import de.crusader.logic.system.IFixedInterface;
import de.crusader.logic.system.IPort;
import de.crusader.painter.Painter;

public class Special7Segment extends ISpecialElement {
	private static final long serialVersionUID = -1333029065857875662L;

	/**
	 * Called when creating a new instance.
	 */
	public Special7Segment() {
		// Initialize input and output ports
		super(new IFixedInterface(new IPort[7]), new IFixedInterface(new IPort[0]));
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#getName()
	 */
	@Override
	public String getName() {
		return "7-Segment Anzeige";
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
		// Draw background color
		p.createRectangle().color(Color.ORANGE).rectangle(rec).draw();

		// Rectangle for displaying values
		Rectangle seg = new Rectangle(rec);
		seg.width -= rec.width >> 1;
		seg.height = seg.width << 1;
		seg.x += rec.width >> 2;
		seg.y = rec.y + (rec.height >> 1) - (seg.height >> 1);

		// Width of a segment
		int width = Math.max(2, rec.width >> 4);

		// List of all segments
		List<Rectangle> lamps = new ArrayList<>();
		lamps.add(new Rectangle(seg.x, seg.y, seg.width, width));
		lamps.add(new Rectangle(seg.x + seg.width - width, seg.y, width, seg.height >> 1));
		lamps.add(new Rectangle(seg.x + seg.width - width, seg.y + (seg.height >> 1), width, seg.height >> 1));
		lamps.add(new Rectangle(seg.x, seg.y + seg.height - width, seg.width, width));
		lamps.add(new Rectangle(seg.x, seg.y + (seg.height >> 1), width, seg.height >> 1));
		lamps.add(new Rectangle(seg.x, seg.y, width, seg.height >> 1));
		lamps.add(new Rectangle(seg.x, seg.y + (seg.height >> 1) - (width >> 1), seg.width, width));

		// Draw all segments
		for (int i = 0; i < lamps.size(); i++) {
			Rectangle pos = lamps.get(i);
			float trans = 1f;
			if (!getInput().getPort(i).getValue().booleanValue()) {
				trans = 0.1f;
			}
			if (pos.width == width) {
				pos.y += width;
				pos.height -= width << 1;
			} else {
				pos.x += width;
				pos.width -= width << 1;
			}
			p.createRectangle().rectangle(pos).color(Color.RED.darker()).filled(true).transparency(trans).draw();
		}

		// Draw a black border
		p.createRectangle().color(Color.BLACK).rectangle(rec).filled(false).draw();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#getDefaultSize()
	 */
	public Dimension getDefaultSize() {
		return new Dimension(20, 30);
	}
}