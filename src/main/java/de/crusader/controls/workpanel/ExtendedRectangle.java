package de.crusader.controls.workpanel;

import java.awt.Point;
import java.awt.Rectangle;

import de.crusader.circuit.api.IRectangle;

public abstract class ExtendedRectangle implements IRectangle {
	/*
	 * The cached rectangle
	 */
	protected Rectangle rectangle = new Rectangle(0, 0, 0x20, 0x20);

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.circuit.api.IRectangle#setRectangle(java.awt.Rectangle)
	 */
	@Override
	public void setRectangle(Rectangle rec) {
		throw new UnsupportedOperationException();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.circuit.api.IRectangle#getPosition()
	 */
	@Override
	public Point getPosition() {
		return getRectangle().getLocation();
	}
}
