package de.crusader.logic.connections;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.crusader.controls.workpanel.IWorkObject;
import de.crusader.helpers.EnumDirection;
import de.crusader.logic.system.EnumInterfaceType;
import de.crusader.logic.system.IInterface;
import de.crusader.logic.system.IPort;
import de.crusader.screens.Management;
import lombok.Getter;

public class LinkPathFinder implements Runnable {
	// Zoom factor * 4
	private final int DISTANCE = Management.getWorkPanel().getZoom().getZoomFactor() << 2;
	@Getter
	private Link link;
	// Cached settings for the ports
	@Getter
	private CachedPort from, to;
	// The way-points of this link
	@Getter
	private List<Point> path = new ArrayList<>();
	private EnumDirection lastDirection = EnumDirection.OTHER;

	/**
	 * Called when creating a new instance.
	 */
	public LinkPathFinder(Link link) {
		this.link = link;
		
		// Cache ports
		from = new CachedPort(link.getIn());
		to = new CachedPort(link.getOut());

		// "To" needs always to be higher than from
		if (from.getWorkObject().getY() > to.getWorkObject().getY()) {
			CachedPort cache = from;

			// toggle: from <-> to
			from = to;
			to = cache;
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// Build a path beginning at the first port
		goTo(from.getPortLocation());
		goTo(from.getStartLocation());

		if (findPort(EnumInterfaceType.INPUT)
				.getPortLocation().x < findPort(EnumInterfaceType.OUTPUT).getPortLocation().x + DISTANCE) {
			// Some crazy stuff to calculate the required path
			Point detour = new Point();
			detour.y = from.getWorkObject().getY() + from.getWorkObject().getHeight() + (DISTANCE >> 1);
			detour.x = to.getWorkObject().getX() + to.getWorkObject().getWidth() + DISTANCE;
			if (from.getWorkObject().getY() + from.getWorkObject().getHeight() + DISTANCE >= to.getWorkObject()
					.getY()) {
				int y1 = to.getWorkObject().getY() + to.getWorkObject().getHeight() + DISTANCE;
				int y2 = from.getWorkObject().getY() + from.getWorkObject().getHeight() + DISTANCE;
				detour.y = Math.max(y1, y2);
			}
			goTo(detour);

			if (to.getPort().getType() == EnumInterfaceType.INPUT) {
				detour = new Point(detour);
				detour.x = to.getWorkObject().getX() - DISTANCE;
				goTo(detour);
			}
		}

		// At the end go back target port
		goTo(to.getStartLocation());
		goTo(to.getPortLocation());

		if (from.getPort() == link.getIn()) {
			// Turn list around
			Collections.reverse(path);
		}
	}

	/**
	 * Point of the next path-location
	 */
	private void goTo(Point point) {
		if (path.isEmpty()) {
			// Directly add this point of it's the first point
			path.add(point);
			return;
		}

		// Find last added point
		Point last = path.get(path.size() - 1);

		// Convert from target-point to direction with a length
		if (last.x == point.x) {
			goTo(EnumDirection.DOWN, point.y - last.y);
		} else if (last.y == point.y) {
			goTo(EnumDirection.RIGHT, point.x - last.x);
		} else {
			goTo(EnumDirection.DOWN, point.y - last.y);
			goTo(EnumDirection.RIGHT, point.x - last.x);
		}
	}

	/**
	 * Calculates and adds a new point to the path
	 */
	private void goTo(EnumDirection direction, int length) {
		if (length == 0) {
			return;
		}

		// Find last added point
		Point last = path.get(path.size() - 1);
		Point point;

		if (direction == lastDirection) {
			// Same direction means the last point can be modified
			point = last;
		} else {
			// Add new point if required
			path.add(point = new Point(last));
		}

		// Add the new direction with the specific length
		point.x += direction.getX() * length;
		point.y += direction.getY() * length;

		// Set the last direction variable
		lastDirection = direction;
	}

	/*
	 * The calculates variables are cached for performance-reasons
	 */
	@Getter
	static class CachedPort {
		private IPort port;
		private IInterface iface;
		private IWorkObject workObject;
		private EnumDirection direction;
		private Point portLocation, startLocation;

		/**
		 * Called when creating a new instance.
		 */
		private CachedPort(IPort port) {
			this.port = port;
			this.iface = port.getOwner();
			this.workObject = iface.getOwner().getWorkObject();
			this.direction = port.getType().getDirectionToElement();
			this.portLocation = port.getPosition();
			this.startLocation = new Point(portLocation);
			int zoom = Management.getWorkPanel().getZoom().getZoomFactor() << 1;
			this.startLocation.translate(-direction.getX() * zoom, -direction.getY() * zoom);
		}
	}

	/**
	 * Searches for a specific port
	 * 
	 * @param type
	 *            of the port
	 * @return port or null if not found
	 */
	public CachedPort findPort(EnumInterfaceType type) {
		CachedPort[] cached = { from, to };
		for (CachedPort p : cached) {
			if (p.getPort().getType() == type) {
				return p;
			}
		}
		return null;
	}
}
