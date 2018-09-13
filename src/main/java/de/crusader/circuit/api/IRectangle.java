package de.crusader.circuit.api;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public interface IRectangle extends IPosition {
	/*
	 * Sets the rectangle of the object
	 */
	void setRectangle(Rectangle rec);

	/**
	 * @return Rectangle - Rectangle of the object
	 */
	Rectangle getRectangle();

	/**
	 * @return int - X-Position
	 */
	default int getX() {
		return getRectangle().x;
	}

	/**
	 * @return int - Y-Position
	 */
	default int getY() {
		return getRectangle().y;
	}

	/**
	 * @return int - Width-Position
	 */
	default int getWidth() {
		return getRectangle().width;
	}

	/**
	 * @return int - height-Position
	 */
	default int getHeight() {
		return getRectangle().height;
	}

	/**
	 * @return Dimension - Size of the object
	 */
	default Dimension getSize() {
		return getRectangle().getSize();
	}

	/**
	 * @return Point - Position of the object
	 */
	default Point getPosition() {
		return getRectangle().getLocation();
	}
}
