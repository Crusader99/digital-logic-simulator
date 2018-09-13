package de.crusader.controls.workpanel;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import de.crusader.controls.tooltip.Tooltip;
import de.crusader.funktion.api.IValue;
import de.crusader.logic.connections.Link;
import de.crusader.logic.system.EnumInterfaceType;
import de.crusader.logic.system.IPort;
import de.crusader.painter.Painter;
import de.crusader.screens.Management;
import de.crusader.screens.enums.EnumCursor;
import lombok.Getter;

@Getter
public class PanelPainter {
	/**
	 * Variables for getting information about the panel and painter
	 */
	private WorkPanel panel;
	private Painter painter;

	/**
	 * Cursor-Informations
	 */
	private Point mouse;
	private EnumCursor cursor;
	private Tooltip tooltip = null;

	/**
	 * Details for the current page
	 */
	private int selectedObjects = 0;
	private int minX = 0, minY = 0, maxX = 0, maxY = 0;

	protected PanelPainter(WorkPanel panel) {
		this.panel = panel;
		this.cursor = panel.isDefaultCursor() ? EnumCursor.DEFAULT : EnumCursor.CROSSHAIR;
	}

	/**
	 * Draws all objects
	 */
	public void draw(Painter painter) {
		this.mouse = panel.currentMousePosition();
		this.painter = painter;

		if (panel.drag == EnumDragType.MOVE_OBJECTS && panel.isAutoDock()) {
			painter.createFinal().add(AutoDockSystem.autoDock(painter, panel.getObjects()));
		}

		// Draw each object
		for (IWorkObject obj : panel.getObjects()) {
			renderObject(obj);
			renderPage(obj);
			obj.getElement().update();
		}

		for (IWorkObject obj : panel.getObjects()) {
			updateFlags(obj);
		}

		// Do some crazy stuff ;)
		drawLinkCreationLine();
		drawDesignedLine();
		updatePage();
		checkHoverScrollbar();
		clearUnusedData();

		// Say painter to complete painting
		painter.doFinal();
	}

	/**
	 * Renders the page to get information about the page size
	 */
	public void renderPage() {
		for (IWorkObject obj : panel.getObjects()) {
			renderPage(obj);
		}
		updatePage();
	}

	/**
	 * Renders an object (private method)
	 */
	private void renderObject(IWorkObject obj) {
		checkMouseHover(obj);

		// Some variables...
		Rectangle rec = obj.getRectangle();
		Point oldScroll = obj.getScrollPosition();
		Point newScroll = new Point(panel.getScrollX(), panel.getScrollY());

		// Get correct location including scoll position
		rec.x -= oldScroll.x - newScroll.x;
		rec.y -= oldScroll.y - newScroll.y;

		((ExtendedWorkObject) obj).setScrollPosition(newScroll);

		// Draw rectangle around the object
		obj.draw(painter, new Rectangle(rec));
		if (obj.isSelected()) {
			// Mark object if selected
			int distance = 2;
			painter.createRectangle().color(Management.getColors().getFocus()).rectangle(rec.x - distance,
					rec.y - distance, rec.width + (distance << 1), rec.height + (distance << 1)).filled(false).draw();
			distance++;
			painter.createRectangle().color(Management.getColors().getFocus()).transparency(0.3f)
					.rectangle(rec.x - distance, rec.y - distance, rec.width + (distance << 1),
							rec.height + (distance << 1))
					.filled(false).draw();
			distance++;
			painter.createRectangle().color(Management.getColors().getFocus()).transparency(0.2f)
					.rectangle(rec.x - distance, rec.y - distance, rec.width + (distance << 1),
							rec.height + (distance << 1))
					.filled(false).draw();
		}

		renderTooltip(obj);
	}

	/*
	 * Updates the flags of an object
	 */
	private void updateFlags(IWorkObject obj) {
		for (IPort port : obj.getElement().getInput().listPorts()) {
			IValue val;
			if (port.getCountOfConnectedPorts() >= 1) {
				val = port.getConnectedPorts().iterator().next().getValue();
			} else {
				val = IValue.create(false);
			}
			port.getFlank().set(port.getValue().set(val));
		}
	}

	/**
	 * Renders the tooltip for an object (private method)
	 */
	private void renderTooltip(IWorkObject obj) {
		if (!obj.isMouseHover() || cursor == EnumCursor.MOVE) {
			return;
		}

		// Change displayed cursor type
		cursor = EnumCursor.HAND;

		// The state is displayed if the mouse is hover an object
		String state = obj.getDisplayName() + " | Ausgabe: ";
		if (obj.getElement().getOutput().getCurrentPortCount() > 0) {
			state += obj.getElement().getOutput().getStringValues() + " (bin)";
		} else if (obj.getElement().getInput().getCurrentPortCount() > 0) {
			state += obj.getElement().getInput().getStringValues() + " (bin)";
		} else {
			state = null;
		}
		if (state != null
				&& (panel.currentTooltip == null || !panel.currentTooltip.getTooltip().equals(state)
						|| !Management.getMainScreen().getSubScreens().contains(panel.currentTooltip))
				&& panel.lastClickPos == null) {
			Rectangle rec = obj.getRectangle();
			Point me = panel.getFullPosition();
			tooltip = new Tooltip(new Rectangle(rec.x + me.x, rec.y + me.y, rec.width, rec.height), state);
		}
	}

	/**
	 * Updates the size of the current page (private method)
	 */
	private void updatePage() {
		if (Math.min(minX, minY) < 0) {
			for (IWorkObject obj : panel.getObjects()) {
				Rectangle rec = obj.getRectangle();
				rec.x -= minX;
				rec.y -= minY;
			}
			maxX -= minX;
			maxY -= minY;
		}
	}

	/**
	 * Check mouse flags for an object (private method)
	 */
	private void checkMouseHover(IWorkObject obj) {
		obj.setMouseHover(obj.getRectangle().contains(mouse));
		obj.setHoverInputArea(panel.drag == EnumDragType.CREATE_LINK
				&& panel.drag.getCurrentPort().getType() == EnumInterfaceType.OUTPUT
				&& obj.getInputArea().contains(mouse));
		if (obj.isSelected() && panel.drag == EnumDragType.MOVE_OBJECTS) {
			selectedObjects++;
			cursor = EnumCursor.MOVE;
		}
	}

	/**
	 * Renders the page details for an object (private method)
	 */
	private void renderPage(IWorkObject obj) {
		Point loc = obj.getRectangle().getLocation();
		Point scroll = obj.getScrollPosition();
		loc.x -= scroll.x;
		loc.y -= scroll.y;

		minX = Math.min(minX, loc.x);
		minY = Math.min(minY, loc.y);

		maxX = Math.max(maxX, loc.x + obj.getWidth());
		maxY = Math.max(maxY, loc.y + obj.getHeight());
	}

	/**
	 * Draws the link from one object to the next in "link creating mode" (private
	 * method)
	 */
	private void drawLinkCreationLine() {
		if (panel.lastClickPos != null && selectedObjects == 0) {
			if (panel.drag == EnumDragType.CREATE_LINK) {
				IPort port = panel.drag.getCurrentPort();
				painter.createLine().position(port.getPosition()).position2(mouse)
						.color(port.getType() == EnumInterfaceType.INPUT ? Color.GREEN : Color.RED).draw();
			} else {
				painter.createRectangle().position(panel.lastClickPos).position2(mouse).color(Color.DARK_GRAY)
						.filled(false).draw();
			}
		}
	}

	/**
	 * Draws a line at the left side if the WorkPanel (looks better)
	 */
	private void drawDesignedLine() {
		painter.createRectangle().position(0, 0).size(2, painter.getHeight()).color(Management.getColors().getFocus())
				.draw();
	}

	/**
	 * Resets the cursor-type if the mouse is hover a scroll-bar-component
	 */
	private void checkHoverScrollbar() {
		if (panel.getHoverScollbar(mouse) != null) {
			cursor = EnumCursor.DEFAULT;
		}
	}

	/**
	 * Removes unused data
	 */
	private void clearUnusedData() {
		Link.resetRegisteredPointers();
	}
}
