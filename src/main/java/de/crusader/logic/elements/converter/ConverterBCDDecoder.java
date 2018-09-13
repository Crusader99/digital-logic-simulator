package de.crusader.logic.elements.converter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import de.crusader.logic.system.IFixedInterface;
import de.crusader.logic.system.IPort;
import de.crusader.painter.Painter;
import de.crusader.painter.util.EnumCenteredType;

public class ConverterBCDDecoder extends IConverterElement {
	private static final long serialVersionUID = -1053590042310329539L;

	/**
	 * Called when creating a new instance.
	 * 
	 * @see https://de.wikipedia.org/wiki/Segmentanzeige and
	 *      http://www.rodrigo-groener.de/7SegmentAnzeige/
	 */
	public ConverterBCDDecoder() {
		// Initialize input and output ports
		super(new IFixedInterface(new IPort[4]), new IFixedInterface(new IPort[7]));

		// Initialize the function
		// First parameter is input; second is output
		init(0x0, 0b1111110);
		init(0x1, 0b0110000);
		init(0x2, 0b1101101);
		init(0x3, 0b1111001);
		init(0x4, 0b0110011);
		init(0x5, 0b1011011);
		init(0x6, 0b1011111);
		init(0x7, 0b1110000);
		init(0x8, 0b1111111);
		init(0x9, 0b1111011);
		init(0xA, 0b1110111);
		init(0xB, 0b0011111);
		init(0xC, 0b1001110);
		init(0xD, 0b0111101);
		init(0xE, 0b1001111);
		init(0xF, 0b1000111);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#getName()
	 */
	@Override
	public String getName() {
		return "BCD-Decoder";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.circuit.api.IPainter#draw(de.crusader.painter.Painter,
	 *      java.awt.Rectangle)
	 */
	@Override
	public void draw(Painter p, Rectangle rec) {
		// Draw background
		p.createRectangle().color(Color.CYAN).rectangle(rec).draw();

		// draw diagonal line
		int border = (rec.width + rec.height) >> 5;
		p.createLine().position(rec.x + border, rec.y + (rec.height >> 1))
				.position2(rec.x + rec.width - border, rec.y + border).draw();

		Dimension middle = new Dimension(rec.width >> 1, rec.height >> 2);
		Rectangle textArea1 = new Rectangle(rec.x + border, rec.y + border, middle.width, middle.height);
		Rectangle textArea2 = new Rectangle(rec.x + middle.width, rec.y + middle.height, middle.width - border,
				middle.height - border);

		// Draw some text
		p.createString().text("BCD").rectangle(textArea1).filled(true).centered(EnumCenteredType.BOTH).draw();
		p.createString().text("7-Seg").rectangle(textArea2).filled(true).centered(EnumCenteredType.BOTH).draw();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#getDefaultSize()
	 */
	@Override
	public Dimension getDefaultSize() {
		return new Dimension(20, 30);
	}
}
