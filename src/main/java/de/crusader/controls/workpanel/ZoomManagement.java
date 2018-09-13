package de.crusader.controls.workpanel;

import java.awt.Rectangle;

import de.crusader.helpers.MathUtils;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ZoomManagement implements Cloneable {
	// The owner of this object
	private WorkPanel owner;

	// Current zoom factor
	private int factor;

	// Max zoom factor
	private int max;

	/**
	 * @return int - Returns the current zoom factor
	 */
	public int getZoomFactor() {
		return factor;
	}

	/*
	 * Directly set zoom factor without updating
	 */
	public void setZoomFactor(int factor) {
		this.factor = MathUtils.bounds(factor, 1, max);
	}

	/*
	 * Changes the zoom factor and updates the objects to the new size
	 */
	public void zoomIn() {
		int old = factor;
		factor = MathUtils.bounds(factor + 1, 1, max);
		update(old, factor);
	}

	/*
	 * Checks if the maximum zoom is reached
	 */
	public boolean canZoomIn() {
		return factor != MathUtils.bounds(factor + 1, 1, max);
	}

	/*
	 * Changes the zoom factor and updates the objects to the new size
	 */
	public void zoomOut() {
		int old = factor;
		factor = MathUtils.bounds(factor - 1, 1, max);
		update(old, factor);
	}

	/*
	 * Checks if the minimum zoom is reached
	 */
	public boolean canZoomOut() {
		return factor != MathUtils.bounds(factor - 1, 1, max);
	}

	/*
	 * Updates the objects to the new size
	 */
	private void update(int oldFactor, int newFactor) {
		for (IWorkObject obj : owner.getObjects()) {
			Rectangle rec = obj.getRectangle();
			rec.x /= oldFactor;
			rec.y /= oldFactor;
			rec.width /= oldFactor;
			rec.height /= oldFactor;
			rec.x *= newFactor;
			rec.y *= newFactor;
			rec.width *= newFactor;
			rec.height *= newFactor;
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public ZoomManagement clone() {
		return new ZoomManagement(owner, factor, max);
	}
}
