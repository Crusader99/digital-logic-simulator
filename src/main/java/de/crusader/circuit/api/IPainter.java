package de.crusader.circuit.api;

import java.awt.Rectangle;

import de.crusader.painter.Painter;

public interface IPainter {

	/*
	 * Draws the "image" of this operator
	 */
	void draw(Painter p, Rectangle rec);

}
