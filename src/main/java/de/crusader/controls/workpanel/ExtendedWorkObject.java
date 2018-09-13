package de.crusader.controls.workpanel;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import de.crusader.controls.modulebox.TreeEntry;
import de.crusader.logic.elements.IElement;
import de.crusader.logic.system.IPort;
import de.crusader.painter.Painter;
import de.crusader.screens.Management;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class ExtendedWorkObject extends TreeEntry implements IWorkObject {
	private static final long serialVersionUID = -8263813770922583543L;

	/*
	 * Fields for dragging this object
	 */
	private transient boolean selected = false;
	private Point dragPosition = null;
	private Point scrollPosition = new Point();

	/*
	 * The current mouse state for this object
	 */
	private transient boolean mouseHover = false;
	private transient boolean hoverInputArea = false;

	private final IElement element;
	private Rectangle rectangle = new Rectangle();

	/**
	 * Called when creating a new instance.
	 */
	public ExtendedWorkObject(IElement element) {
		this.element = element;
		this.element.setWorkObject(this);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.controls.workpanel.IWorkObject#isValid()
	 */
	public boolean isValid() {
		// Check each input port
		for (IPort in : getElement().getInput().listPorts()) {
			// An input-port can only have one input-signal
			if (in.getCountOfConnectedPorts() != 1) {
				// Invalid if there are more or less than one connection
				return false;
			}
		}
		return true;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.circuit.api.IPainter#draw(de.crusader.painter.Painter,
	 *      java.awt.Rectangle)
	 */
	public void draw(Painter p, Rectangle rec) {
		getElement().draw(p, rec);
		for (IPort port : getElement().listAllPorts()) {
			port.draw(p, rec);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.circuit.api.IClickable#onClick()
	 */
	@Override
	public void onClick() {
		// Link click-event to element
		getElement().click();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.funktion.api.IDisplayName#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return element.getName();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.controls.workpanel.ExtendedWorkObject#setHoverInputArea(
	 *      boolean)
	 */
	@Override
	public void setHoverInputArea(boolean hover) {
		// Do nothing
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.controls.workpanel.IWorkObject#findPort(java.awt.Point)
	 */
	@Override
	public IPort findPort(Point point) {
		IPort port = getElement().getInput().findPort(point);
		if (port != null) {
			return port;
		}
		return getElement().getOutput().findPort(point);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.circuit.api.IRectangle#getRectangle()
	 */
	@Override
	public Rectangle getRectangle() {
		int zoom = Management.getWorkPanel().getZoom().getZoomFactor();
		Dimension defaultSize = getElement().getDefaultSize();
		rectangle.setSize(defaultSize.width * zoom, defaultSize.height * zoom);
		return rectangle;
	}

	/**
	 * @return - Returns the name of the element.
	 */
	@Override
	public String toString() {
		return getElement().getName();
	}
}
