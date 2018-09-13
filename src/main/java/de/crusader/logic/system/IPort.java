package de.crusader.logic.system;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import de.crusader.circuit.api.IPainter;
import de.crusader.funktion.api.IValue;
import de.crusader.helpers.MathUtils;
import de.crusader.logic.connections.Link;
import de.crusader.painter.DrawString;
import de.crusader.painter.Painter;
import de.crusader.painter.util.EnumCenteredType;
import de.crusader.screens.Management;
import lombok.Getter;
import lombok.Setter;

public class IPort implements IPainter, Serializable {
	private static final long serialVersionUID = 8141804791152010115L;

	// The owner-interface of this port
	@Setter
	@Getter
	private IInterface owner;

	// The current value of this port (0/1)
	@Getter
	private IValue value = IValue.create(false);

	// The name of this port, for example "J", "K", used by some flip-flops
	@Getter
	private String name = "?";

	// This atomic boolean is true if there was a flank since last update
	@Getter
	private AtomicBoolean flank = new AtomicBoolean(false);

	// Some properties for this port
	private boolean showName = false, invertSymbol = false, flankSymbol = false;

	// List of connections to other ports
	private List<IPort> connectedPorts = new CopyOnWriteArrayList<>();

	/**
	 * Shows a specific name for this port
	 */
	public IPort name(String name) {
		this.name = name;
		this.showName = true;
		return this;
	}

	/**
	 * Enables the invert symbol for the painter
	 */
	public IPort invertSymbol() {
		this.invertSymbol = true;
		return this;
	}

	/**
	 * Enables the flank symbol for the painter
	 */
	public IPort flankSymbol() {
		this.flankSymbol = true;
		return this;
	}

	/**
	 * @return - Returns whether this is an input or output port
	 */
	public EnumInterfaceType getType() {
		return owner.getType();
	}

	/**
	 * @return - Returns all ports connected to this ports
	 */
	public Iterable<IPort> getConnectedPorts() {
		return new ArrayList<>(this.connectedPorts);
	}

	/**
	 * @return - Returns the count of connected ports
	 */
	public int getCountOfConnectedPorts() {
		return this.connectedPorts.size();
	}

	/**
	 * @return - Return the position of this port
	 */
	public Point getPosition() {
		IInterface owner = getOwner();
		List<IPort> ports = (List<IPort>) owner.listPorts();
		return new Point(owner.getPositionX(), owner.getYByIndex(ports.indexOf(this)));
	}

	/**
	 * @return - Return the position of this port
	 */
	public Point getPositionOfLinkStart() {
		Point p = getPosition();
		Rectangle rec = getOwner().getOwner().getWorkObject().getRectangle();
		int size = MathUtils.bounds(rec.height / getOwner().getCurrentPortCount(), 10, rec.height >> 1);
		int radius = 0;
		if (invertSymbol) {
			radius = Math.max((size >> 2) - (size >> 4), 2);
		}
		int dir = getType().getDirectionToElement().getX();
		p.x += dir * -(radius << 1);
		return p;
	}

	/**
	 * @return - Returns the index of this port on it's interface
	 */
	public int getIndex() {
		IInterface owner = getOwner();
		List<IPort> ports = (List<IPort>) owner.listPorts();
		return ports.indexOf(this);
	}

	/**
	 * @return - Returns true if this port has an invert-symbol
	 */
	public boolean hasInvertSymbol() {
		return this.invertSymbol;
	}

	/**
	 * Call to connect this port to another port
	 */
	public void connectTo(IPort port) throws Exception {
		if (port == null) {
			// Throw exception if port does not exist
			throw new NullPointerException();
		} else if (port == this) {
			// If this is the same port...
			throw new UnsupportedOperationException();
		}
		connect(this, port);
		connect(port, this);
	}

	/**
	 * Method to connect one port to another
	 */
	private final static void connect(IPort link, IPort target) {
		EnumInterfaceType linkType = link.getOwner().getType();
		EnumInterfaceType targetType = target.getOwner().getType();
		if (linkType == targetType) {
			// For example if someone tries to connect an input to another input
			throw new UnsupportedOperationException();
		}
		while (target.connectedPorts.size() >= targetType.getMaxConnectionsToSamePort()) {
			target.connectedPorts.remove(0);
		}
		target.connectedPorts.add(link);
	}

	/**
	 * Disconnects all connections to other ports
	 */
	public void disconnect() {
		for (IPort port : new ArrayList<>(this.connectedPorts)) {
			port.connectedPorts.remove(this);
		}
		this.connectedPorts.clear();
	}

	/**
	 * Disconnect from a specific port
	 */
	public void disconnect(IPort port) {
		this.connectedPorts.remove(port);
		port.connectedPorts.remove(this);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.circuit.api.IPainter#draw(de.crusader.painter.Painter,
	 *      java.awt.Rectangle)
	 */
	@Override
	public void draw(Painter p, Rectangle rec) {
		p.createRectangle().color(Color.BLACK).rectangle(rec).filled(false).draw();

		Point point = getPosition();
		int size = MathUtils.bounds(rec.height / getOwner().getCurrentPortCount(), 10, rec.height >> 1);
		int direction = getType().getDirectionToElement().getX();

		int radius = 0;
		if (invertSymbol) {
			// Draw a circle, called "invert-symbol"
			radius = Management.getWorkPanel().getZoom().getZoomFactor();
			p.createCircle().color(Color.RED).filled(false)
					.position(point.x - radius - direction * radius, point.y - radius)
					.size((radius << 1) - 1, (radius << 1) - 1).draw();
		} else {
			int length = Management.getWorkPanel().getZoom().getZoomFactor() << 1;

			// Draw start for possible link to another port
			p.createRectangle().color(Management.getColors().getFocus()).position(point).width(-direction * length)
					.height(2).draw();
		}

		if (flankSymbol) {
			// Calculate the size of the flank symbol
			radius = Math.max((size >> 2) - (size >> 4), 2);

			// Draw the flank symbol
			p.createPolygon().add(point.x, point.y - radius).add(point.x + (radius << 1), point.y)
					.add(point.x, point.y + radius).filled(false).color(Color.BLACK).draw();
		}

		if (showName) {
			// Calculate text area
			Rectangle textArea = new Rectangle(point.x, point.y - (size >> 1), rec.width >> 1, size);
			DrawString drawStr = p.createString().text(this.name).rectangle(textArea).filled(true)
					.centered(EnumCenteredType.Y);

			// Move x position if this text is on the output-side
			if (getType() == EnumInterfaceType.OUTPUT) {
				textArea.x -= drawStr.getWidth();
			}

			// Add a little border
			textArea.x += direction * (size >> 3);

			// Change x position if there is a flank symbol
			if (flankSymbol) {
				textArea.x += radius << 1;
			}

			// Draw the text
			drawStr.rectangle(textArea).draw();
		}

		// Draw the link-connection to the connected ports
		if (getType() == EnumInterfaceType.INPUT) {
			for (IPort port : getConnectedPorts()) {
				Link link = new Link(this, port);
				link.draw(p, rec);
			}
		}
	}
}
