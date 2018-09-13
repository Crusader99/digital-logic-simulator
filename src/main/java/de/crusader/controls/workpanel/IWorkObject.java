package de.crusader.controls.workpanel;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

import de.crusader.circuit.api.IClickable;
import de.crusader.circuit.api.IPainter;
import de.crusader.circuit.api.IRectangle;
import de.crusader.controls.modulebox.ISelectable;
import de.crusader.logic.elements.IElement;
import de.crusader.logic.system.IPort;
import de.crusader.screens.interfaces.IDisplayName;

public interface IWorkObject extends ISelectable, IPainter, IRectangle, IClickable, IDisplayName, Serializable {

	// The working element for this object
	IElement getElement();

	// Returns a IPort for the given location
	IPort findPort(Point point);

	/*
	 * @return the current mouse state
	 */
	boolean isMouseHover();

	/*
	 * sets the current mouse state
	 */
	void setMouseHover(boolean hover);

	/*
	 * @return the current drag position (objectPos = mousePos - dragPos)
	 */
	Point getDragPosition();

	/*
	 * sets the current drag position (dragPos = mousePos - objectPos)
	 */
	void setDragPosition(Point drag);

	/*
	 * @return true if there are enough inputs
	 */
	boolean isValid();

	/*
	 * @return a rectangle hover the inputs
	 */
	default Rectangle getInputArea() {
		Rectangle me = getRectangle();
		return new Rectangle(me.x - (me.width >> 2), me.y, (me.width >> 2) + (me.width >> 3), me.height);
	}

	/*
	 * @return a rectangle hover the outputs
	 */
	default Rectangle getOutputArea() {
		Rectangle me = getRectangle();
		return new Rectangle(me.x + me.width, me.y, (me.width >> 2) + (me.width >> 3), me.height);
	}

	/*
	 * @return true if the cursor is hover the input area
	 */
	boolean isHoverInputArea();

	/*
	 * @param should be true if the cursor is hover the input area
	 */
	void setHoverInputArea(boolean hover);

	/*
	 * @return the current scroll position -> realObjectPosition =
	 * getRectanle().getLocation() - getScrollPosition()
	 */
	Point getScrollPosition();
}
