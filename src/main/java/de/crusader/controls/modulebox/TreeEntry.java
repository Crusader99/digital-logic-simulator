package de.crusader.controls.modulebox;

import java.awt.Rectangle;

import de.crusader.circuit.api.IPainter;
import de.crusader.controls.workpanel.ExtendedRectangle;
import de.crusader.screens.interfaces.IDisplayName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class TreeEntry extends ExtendedRectangle implements IDisplayName, IPainter {
	/**
	 * Current vertical position
	 */
	private int positionY = 0;

	/**
	 * The last draw rectangle. Used to update/check whether the mouse is hover
	 * this element or not
	 */
	private Rectangle lastPaintedRectangle = new Rectangle();

	/*
	 * True if the mouse is hover this entry
	 */
	private boolean mouseHover = false;
}