package de.crusader.controls.workpanel;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import de.crusader.painter.Painter;
import de.crusader.painter.helpers.Drawable;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AutoDockSystem {
	// Maximum distance for enabling auto dock
	private static final int TOLERANCE = 5;
	// Defines whether the detected docking objects owns nearly the same x or y
	// positions
	private EnumDockType type;
	// Squared distance (for comparison we don't need Math.sqr(...))
	private double distanceSq;
	private Point[] points;
	// Differences for x and y
	private int diffX, diffY;

	/**
	 * Magic-function ;)
	 */
	public static List<Drawable<?>> autoDock(Painter p, List<IWorkObject> objects) {
		List<Drawable<?>> draw = new ArrayList<>();
		List<AutoDockSystem> detected = new ArrayList<>();

		// Loop all objects
		for (IWorkObject obj : objects) {
			// Ignore if not selected
			if (!obj.isSelected()) {
				continue;
			}

			// Loop all other objects
			for (IWorkObject compare : objects) {
				if (compare == obj || compare.isSelected()) {
					continue;
				}
				// Calculate only objects with same size
				if (obj.getWidth() != compare.getWidth() || obj.getHeight() != compare.getHeight()) {
					continue;
				}

				// Get calculate distance between objects
				double distanceSq = obj.getPosition().distanceSq(compare.getPosition());
				if (distanceSq < obj.getWidth() * obj.getHeight()) {
					continue;
				}

				EnumDockType type = null;
				Point[] points = null;
				int[] diff = null;

				// A lot of math....
				if (Math.abs(compare.getX() - obj.getX()) < TOLERANCE) {
					type = EnumDockType.X;
					points = new Point[4];
					points[0] = new Point(compare.getX(), obj.getY() + obj.getHeight() / 2);
					points[1] = new Point(compare.getX(), compare.getY() + compare.getHeight() / 2);
					points[2] = new Point(compare.getX() + compare.getWidth(), obj.getY() + obj.getHeight() / 2);
					points[3] = new Point(compare.getX() + compare.getWidth(),
							compare.getY() + compare.getHeight() / 2);
					diff = new int[] { obj.getRectangle().x - compare.getX(), 0 };
				} else if (Math.abs(compare.getY() - obj.getY()) < TOLERANCE) {
					type = EnumDockType.Y;
					points = new Point[4];
					points[0] = new Point(obj.getX() + obj.getWidth() / 2, compare.getY());
					points[1] = new Point(compare.getX() + compare.getWidth() / 2, compare.getY());
					points[2] = new Point(obj.getX() + obj.getWidth() / 2, compare.getY() + compare.getHeight());
					points[3] = new Point(compare.getX() + compare.getWidth() / 2,
							compare.getY() + compare.getHeight());
					diff = new int[] { 0, obj.getRectangle().y - compare.getY() };
				}

				AutoDockSystem[] edge = new AutoDockSystem[2];
				if (Math.abs(Math.abs(compare.getY() - obj.getY()) - compare.getHeight()
						- compare.getHeight() / 2) < TOLERANCE) {
					Point[] locations = new Point[4];

					int y = compare.getY() - obj.getY();
					int posY = compare.getY() + (y > 0 ? -1 : 3) * (compare.getHeight() / 2);
					locations[0] = new Point(compare.getX() - compare.getWidth() / 2, posY);
					locations[1] = new Point(compare.getX() + compare.getWidth() + compare.getWidth() / 2, posY);
					posY = compare.getY() + (y > 0 ? 0 : 2) * (compare.getHeight() / 2);
					locations[2] = new Point(locations[0].x, posY);
					locations[3] = new Point(locations[1].x, posY);
					posY = compare.getY() + (y > 0 ? -3 : 3) * (compare.getHeight() / 2);
					edge[0] = new AutoDockSystem(EnumDockType.Y, distanceSq, locations, 0, obj.getY() - posY);
				}
				if (Math.abs(Math.abs(compare.getX() - obj.getX()) - compare.getWidth()
						- compare.getWidth() / 2) < TOLERANCE) {
					Point[] locations = new Point[4];

					int x = compare.getX() - obj.getX();
					int posX = compare.getX() + (x > 0 ? -1 : 3) * (compare.getWidth() - compare.getWidth() / 2);
					locations[0] = new Point(posX, compare.getY() - compare.getHeight() / 2);
					locations[1] = new Point(posX, compare.getY() + compare.getHeight() + compare.getHeight() / 2);
					posX = compare.getX() + (x > 0 ? 0 : 2) * (compare.getWidth() - compare.getWidth() / 2);
					locations[2] = new Point(posX, locations[0].y);
					locations[3] = new Point(posX, locations[1].y);
					posX = compare.getX() + (x > 0 ? -3 : 3) * (compare.getWidth() - compare.getWidth() / 2);
					edge[1] = new AutoDockSystem(EnumDockType.X, distanceSq, locations, obj.getX() - posX, 0);
				}
				if (edge[0] != null && edge[1] != null) {
					Point difference = new Point(edge[0].diffX + edge[1].diffX, edge[0].diffY + edge[1].diffY);
					edge[0].points[2] = new Point(obj.getX() - difference.x + obj.getWidth() / 2,
							obj.getY() - difference.y + obj.getHeight() / 2);
					edge[0].points[3] = new Point(compare.getX() + compare.getWidth() / 2,
							compare.getY() + compare.getHeight() / 2);
					edge[1].points[2] = null;
					detected.add(edge[0]);
					detected.add(edge[1]);
				} else if (type == EnumDockType.X && edge[0] != null) {
					detected.add(edge[0]);
				} else if (type == EnumDockType.Y && edge[1] != null) {
					detected.add(edge[1]);
				}

				if (type != null) {
					detected.add(new AutoDockSystem(type, distanceSq, points, diff[0], diff[1]));
				}
			}
		}
		AutoDockSystem closestX = null;
		AutoDockSystem closestY = null;
		for (AutoDockSystem dock : detected) {
			if (p != null) {
				draw.add(p.createLine().position(dock.points[0]).position2(dock.points[1]).color(Color.RED));
				if (dock.points[2] != null) {
					draw.add(p.createLine().position(dock.points[2]).position2(dock.points[3]).color(Color.RED));
				}
			}
			if (dock.type == EnumDockType.X && (closestX == null || dock.distanceSq < closestX.distanceSq)) {
				closestX = dock;
			} else if (dock.type == EnumDockType.Y && (closestY == null || dock.distanceSq < closestY.distanceSq)) {
				closestY = dock;
			}
		}
		if (closestX != null) {
			for (IWorkObject obj : objects) {
				if (!obj.isSelected()) {
					continue;
				}
				obj.getRectangle().x -= closestX.diffX;
				obj.getRectangle().y -= closestX.diffY;
			}
		}
		if (closestY != null) {
			for (IWorkObject obj : objects) {
				if (!obj.isSelected()) {
					continue;
				}
				obj.getRectangle().x -= closestY.diffX;
				obj.getRectangle().y -= closestY.diffY;
			}
		}
		return draw;
	}

	// Switch between dock-type x and y
	private static enum EnumDockType {
		X, Y;
	}
}
