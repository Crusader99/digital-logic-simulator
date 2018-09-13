package de.crusader.logic.connections;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.crusader.analytics.Analytics;
import de.crusader.circuit.api.IPainter;
import de.crusader.helpers.EnumDirection;
import de.crusader.logic.system.IPort;
import de.crusader.painter.DrawRectangle;
import de.crusader.painter.Painter;
import de.crusader.screens.Management;
import lombok.Getter;

public class Link implements IPainter {
	/*
	 * This color is used to draw the connection/link between elements.
	 */
	private final static Color COLOR = Management.getColors().getFocus();

	/*
	 * Locations of the arrows. These arrows are drawn hover the connections/link to
	 * show their direction.
	 */
	private final static List<Point> REGISTERED_POINTERS = new CopyOnWriteArrayList<>();

	// The connected ports
	@Getter
	private IPort in, out;

	// The way-points of this link
	@Getter
	private LinkPathFinder pathFinder;

	/**
	 * Called when creating a new instance.
	 */
	public Link(IPort in, IPort out) {
		this.in = in;
		this.out = out;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.circuit.api.IPainter#draw(de.crusader.painter.Painter,
	 *      java.awt.Rectangle)
	 */
	@Override
	public void draw(Painter p, Rectangle rec) {
		this.pathFinder = new LinkPathFinder(this);
		this.pathFinder.run();

		try (Analytics a = new Analytics(getClass())) {
			drawPath(p);
		} catch (Exception ex) {
			ex.printStackTrace();
//			Management.getWorkPanel().getObjects().remove(this);
		}
	}

	/**
	 * Draws the path (already calculates) path between the ports
	 */
	private void drawPath(Painter p) {
		List<Point> path = pathFinder.getPath();
		for (int i = 0; i < path.size() - 1; i++) {
			Point last = path.get(i);
			Point current = path.get(i + 1);

			DrawRectangle drawWay = p.createRectangle().color(COLOR).position(last).position2(current);
			Rectangle rec = drawWay.getRectangle();

			if (rec.width <= rec.height) {
				drawWay.width(2);
			} else {
				drawWay.height(2);
			}
			p.createFinal().add(drawWay);

			drawArrow(p, last, current);

			if (i >= path.size() - 2) {
				continue;
			}
			if (i == 1 && pathFinder.getFrom().getPort().hasInvertSymbol()) {
				continue;
			}
			if (i == path.size() - 1 && pathFinder.getTo().getPort().hasInvertSymbol()) {
				continue;
			}
			drawCircle(p, current);
		}
	}

	/*
	 * Draws a circle to mark a connection between multiple links
	 */
	private void drawCircle(Painter p, Point location) {
		// Calculate some settings
		int radius = (int) (Management.getWorkPanel().getZoom().getZoomFactor() * 0.75f);
		Point point = new Point(location.x - radius, location.y - radius);

		// Draw the circle
		p.createFinal().add(p.createCircle().position(point).size(radius << 1, radius << 1).color(COLOR));

		// Register this point to block other point at same position
		REGISTERED_POINTERS.add(point);
	}

	// Draws the arrow which points to the target port
	private void drawArrow(Painter p, Point from, Point to) {
		// Calculate some settings
		int size = Management.getWorkPanel().getZoom().getZoomFactor();
		int maxDiffSq = (size << 3) * (size << 3);

		// Cancel if this is only a very short link
		if (from.distanceSq(to) < maxDiffSq) {
			return;
		}

		// Calculate the position
		Point point = new Point();
		point.x = (from.x + to.x) >> 1;
		point.y = (from.y + to.y) >> 1;

		// Check if there's already a point at this location
		for (Point registered : REGISTERED_POINTERS) {
			if (registered.distanceSq(point) < maxDiffSq) {
				return;
			}
		}

		// Find the direction by the vector
		EnumDirection dir = EnumDirection.find(to.x - from.x, to.y - from.y);

		// Return if no direction found
		if (dir == null || dir == EnumDirection.OTHER) {
			return;
		}

		// Register this point to block other points at same position
		REGISTERED_POINTERS.add(new Point(point));

		// Go to middle of the line
		point.x += dir.getX() * size;
		point.y += dir.getY() * size;

		// Draw lines of the way-pointer
		p.createFinal().add(p.createLine().position(point).setDirection(dir.rotate(90 + 45), size << 1).color(COLOR));
		p.createFinal().add(p.createLine().position(point).setDirection(dir.rotate(-90 - 45), size << 1).color(COLOR));
	}

	// Cleanup registered points
	public static void resetRegisteredPointers() {
		REGISTERED_POINTERS.clear();
	}
}
