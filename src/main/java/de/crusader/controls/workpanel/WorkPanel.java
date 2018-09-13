package de.crusader.controls.workpanel;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.crusader.analytics.Analytics;
import de.crusader.circuit.clipboard.ClipboardSystem;
import de.crusader.controls.contextmenu.ContextMenu;
import de.crusader.controls.mainmenu.options.Separator;
import de.crusader.controls.mainmenu.options.edit.Copy;
import de.crusader.controls.mainmenu.options.edit.Cut;
import de.crusader.controls.mainmenu.options.edit.Delete;
import de.crusader.controls.mainmenu.options.edit.Paste;
import de.crusader.controls.mainmenu.options.edit.Redo;
import de.crusader.controls.mainmenu.options.edit.SelectAll;
import de.crusader.controls.mainmenu.options.edit.Undo;
import de.crusader.controls.mainmenu.options.file.Save;
import de.crusader.controls.mainmenu.options.view.ZoomIn;
import de.crusader.controls.mainmenu.options.view.ZoomOut;
import de.crusader.controls.scrollbar.HorizontalScrollbar;
import de.crusader.controls.scrollbar.VerticalScrollbar;
import de.crusader.controls.tooltip.Tooltip;
import de.crusader.logic.system.IPort;
import de.crusader.painter.Painter;
import de.crusader.screens.Management;
import de.crusader.screens.api.ControlScreen;
import de.crusader.screens.controls.scrollbar.IScrollPositionAdapter;
import de.crusader.screens.controls.scrollbar.IScrollbar;
import de.crusader.screens.controls.tabcontrol.ITabEntry;
import de.crusader.screens.enums.EnumMouseButton;
import de.crusader.screens.interfaces.IScreen;
import de.crusader.screens.interfaces.IScreenEvents;
import lombok.Getter;
import lombok.Setter;

public class WorkPanel extends ControlScreen implements IScreenEvents, ITabEntry {
	// Time of animations
	private static final int ANIMATION_TIME = 300;

	/**
	 * History for undo and redo
	 */
	@Getter
	private ObjectsHistory history = new ObjectsHistory(new ZoomManagement(this, 4, 8));

	/**
	 * Scrollbars
	 */
	public IScrollbar scrollbarVertical;
	public IScrollbar scrollbarHorizontal;
	private int[] pageSize = new int[2];

	/**
	 * Listeners
	 */
	private KeyListener keyListener = new KeyListener(this);
	private PortPopupListener portPopupListener = new PortPopupListener(this);

	/**
	 * Drag elements
	 */
	protected EnumDragType drag = null;
	protected Point lastClickPos = null;

	/**
	 * Tooltip
	 */
	protected Tooltip currentTooltip = null;

	/**
	 * Settings of this WorkPanel
	 */
	@Getter
	@Setter
	private boolean autoDock = true;

	/*
	 * True if option for cross-cursor disabled
	 */
	@Getter
	@Setter
	private boolean defaultCursor = true;

	/**
	 * True, if there are unsaved changes
	 */
	@Getter
	private boolean unsavedChanges = false;

	/**
	 * The path of the current file. This field can also be null!
	 */
	@Getter
	@Setter
	private File currentFile = null;

	/**
	 * Variables for the TabControl
	 */
	@Getter
	@Setter
	private int tabPositionX = 0;

	// Time stamp in ms
	@Getter
	@Setter
	private long lastHoverCloseButton;

	/**
	 * Initialize this WorkPanel
	 */
	public WorkPanel() {
		// Add the scroll bars
		getSubScreens().add(scrollbarHorizontal = new HorizontalScrollbar(new IScrollPositionAdapter() {
			@Override
			public int getFullPageSize() {
				return pageSize[0] + (pageSize[0] >> 2);
			}
		}));
		getSubScreens().add(scrollbarVertical = new VerticalScrollbar(new IScrollPositionAdapter() {
			@Override
			public int getFullPageSize() {
				return pageSize[1] + (pageSize[1] >> 2);
			}
		}));
		focus();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.api.Screen#onPaint(de.crusader.painter.Painter)
	 */
	@Override
	public void onPaint(Painter painter) {
		try (Analytics a = new Analytics(getClass())) {
			// Fill the background with a gray color
			painter.createRectangle().color(Color.GRAY).size(getRectangle().getSize()).filled(true).draw();

			// Outsourced methods for painting
			PanelPainter pp = new PanelPainter(this);
			pp.draw(painter);

			if (drag == null) {
				pageSize[0] = pp.getMaxX();
				pageSize[1] = pp.getMaxY();
			}
			// Set the cursor.type
			setCursor(pp.getCursor());

			// Add the tool tip to sub screens
			if (pp.getTooltip() != null) {
				getMainScreen().getSubScreens().add(currentTooltip = pp.getTooltip());
			}
			super.onPaint(painter);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.api.Screen#onMouseMove()
	 */
	@Override
	public void onMouseMove() {
		keepConsistandUpdating(ANIMATION_TIME << 1);
		Point mouse = currentMousePosition();
		IScreen scroll = getHoverScollbar(mouse);
		if (scroll != null) {
			// If mouse is hover a scroll bar
			scroll.onMouseMove();
			return;
		}
		super.onMouseMove();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.api.Screen#onMouseDown(de.crusader.screens.api.utils.
	 *      EnumMouseButton, int)
	 */
	@Override
	public void onMouseDown(EnumMouseButton button, int clicks) {
		if (drag != null) {
			return;
		}

		// Fix defect mouse devices
		if (clicks % 2 == 0) {
			return;
		}

		Point mouse = currentMousePosition();

		IScreen scroll = getHoverScollbar(mouse);
		if (scroll != null) {
			// If mouse is hover a scroll bar
			scroll.onMouseDown(button, clicks);
			return;
		}

		// Update last mouse click position
		lastClickPos = mouse;

		IWorkObject hoverObj = getMouseHoverObject();
		if (hoverObj == null) {
			// Select none if mouse-click turns in the void
			selectNone();
			return;
		}

		// Remove this object from the selected objects when pressing STRG/CTRL
		// and clicking on a already selected object.
		if (!hoverObj.isSelected()) {
			boolean ctrlDown = isKeyDown(KeyEvent.VK_CONTROL);
			if (!ctrlDown) {
				selectNone();
			}
			hoverObj.setSelected(true);
		}

		// Update the positions of the selected objects
		for (IWorkObject obj : getSelectedObjects()) {
			Point drag;
			obj.setDragPosition(drag = new Point());
			drag.x = lastClickPos.x - obj.getRectangle().x;
			drag.y = lastClickPos.y - obj.getRectangle().y;
		}

		super.onMouseDown(button, clicks);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.api.Screen#onMouseRelease(de.crusader.screens.api.
	 *      utils.EnumMouseButton, int)
	 */
	@Override
	public void onMouseRelease(EnumMouseButton button, int clicks) {
		Point mouse = currentMousePosition();

		// Reset status for the scroll bars
		for (IScreen scroll : getScollbars()) {
			scroll.onMouseRelease(button, clicks);
		}

		if (drag == null && lastClickPos != null && getSelectedObjects().isEmpty()) {
			// Selection-area
			Rectangle rec = new Rectangle(mouse.x, mouse.y, lastClickPos.x - mouse.x, lastClickPos.y - mouse.y);

			// Prevent negative sizes
			if (rec.width < 0) {
				rec.width = -rec.width;
				rec.x -= rec.width;
			}
			if (rec.height < 0) {
				rec.height = -rec.height;
				rec.y -= rec.height;
			}

			// Prevent empty sizes
			rec.width = Math.max(1, rec.width);
			rec.height = Math.max(1, rec.height);

			// Remove current selection-settings
			selectNone();
			// Mark the objects as selected in the chosen area
			for (IWorkObject obj : getObjects()) {
				if (rec.intersects(obj.getRectangle())) {
					obj.setSelected(true);
				}
			}
		} else if (drag == null && clicks % 2 != 0 && button == EnumMouseButton.LEFT_BUTTON) {
			IWorkObject hover = getMouseHoverObject();
			if (hover != null) {
				// On left mouse click
				hover.onClick();
			}
		} else if (drag == EnumDragType.MOVE_OBJECTS) {
			// Move objects, do auto-docking and save changed in history
			AutoDockSystem.autoDock(null, getObjects());
			markUnsavedChanges();
		} else if (drag == EnumDragType.CREATE_LINK) {
			IPort from = drag.getCurrentPort();
			IPort to = getPortAtMousePosition(mouse);

			boolean changeSaves = false;
			if (from != null && to != null) {
				try {
					// Tries to create a new link to another port
					from.connectTo(to);
					changeSaves = true;
				} catch (Exception ex) {
					// Do nothing
				}
			}

			// Call event to listener
			portPopupListener.onCreateLinkFinished();

			if (changeSaves) {
				markUnsavedChanges();
			}
		}
		resetDrag();
		lastClickPos = null;
		if (button == EnumMouseButton.RIGHT_BUTTON) {
			// open the context menu when pressing right mouse button
			openContextMenu();
		}
		super.onMouseRelease(button, clicks);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.api.Screen#onMouseDrag(de.crusader.screens.api.utils.
	 *      EnumMouseButton, int)
	 */
	@Override
	public void onMouseDrag(EnumMouseButton button, int clicks) {
		Point mouse = currentMousePosition();
		IScreen scroll = getHoverScollbar(mouse);
		if (scroll != null) {
			// If cursor hover scroll bar
			scroll.onMouseDrag(button, clicks);
			return;
		}

		if (lastClickPos == null) {
			super.onMouseDrag(button, clicks);
			return;
		}

		// Get currently selected objects
		List<IWorkObject> selected = getSelectedObjects();
		if (selected.isEmpty()) {
			if (drag != null) {
				if (drag == EnumDragType.CREATE_LINK) {
					portPopupListener.onMouseDragWhileCreateLink(mouse, drag.getCurrentPort());
				}
				return;
			}
			try {
				// Maybe a new link will be created
				onMouseDragLink(button, clicks);
			} catch (Exception ex) {
				super.onMouseDrag(button, clicks);
				resetDrag();
			}
			return;
		}
		if (drag == null) {
			if (mouse.distanceSq(lastMousePosition()) < 2) {
				return;
			}
			drag = EnumDragType.MOVE_OBJECTS;
			// Called when need to copy selected objects
			if (isKeyDown(KeyEvent.VK_CONTROL)) {
				List<IWorkObject> copy = new ArrayList<>();
				for (IWorkObject obj : selected) {
					obj.setSelected(false);
					copy.add(obj);
				}
				copy = new CloneSystem(copy).cloneAndRemoveUnusedConnections();
				for (IWorkObject obj : copy) {
					obj.setSelected(true);
				}
				getObjects().addAll(copy);
				return;
			}
		}

		// Move selected elements to mouse position
		for (IWorkObject obj : selected) {
			Rectangle rec = obj.getRectangle();
			rec.x = mouse.x - obj.getDragPosition().x;
			rec.y = mouse.y - obj.getDragPosition().y;
		}

		// Render the page
		new PanelPainter(this).renderPage();
		keepConsistandUpdating(200);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.api.Screen#onMouseWheel(int)
	 */
	@Override
	public void onMouseWheel(int rotation) {
		if (isKeyDown(KeyEvent.VK_CONTROL)) {
			// Change zoom factor
			if (rotation < 0) {
				getZoom().zoomIn();
			} else {
				getZoom().zoomOut();
			}
		} else {
			// Perform scroll
			scrollbarVertical.onMouseWheel(rotation);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.api.Screen#onResize()
	 */
	@Override
	public void onResize() {
		setRectangle(Management.getDocker().dock(getParent()));
		super.onResize();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.api.Screen#onKeyDown(int, char)
	 */
	@Override
	public void onKeyDown(int keyCode, char typed) {
		keyListener.onKeyDown(keyCode, typed);
		super.onKeyDown(keyCode, typed);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.api.Screen#onMouseLeftScreen()
	 */
	@Override
	public void onMouseLeftScreen() {
		super.onMouseLeftScreen();
	}

	/**
	 * Called when creating a new link between objects
	 */
	private void onMouseDragLink(EnumMouseButton button, int clicks) throws Exception {
		IPort port = getPortAtMousePosition(lastClickPos);
		if (port == null) {
			throw new NullPointerException();
		}
		drag = EnumDragType.CREATE_LINK;
		drag.setCurrentPort(port);
	}

	/**
	 * Called when finished creating new link
	 */
	private void resetDrag() {
		EnumDragType d = drag;
		if (d != null) {
			d.setCurrentPort(null);
		}
		drag = null;
	}

	/**
	 * @return a IInterfaceIO if there is one
	 */
	protected IPort getPortAtMousePosition(Point position) {
		IPort port = null;
		for (IWorkObject obj : getObjects()) {
			IPort p = obj.findPort(position);
			if (p != null) {
				port = p;
				// No return to prevent bugs
			}
		}
		return port;
	}

	/**
	 * Cut-Command for the KontextMenu
	 */
	public void cut() {
		List<IWorkObject> selected = getSelectedObjects();
		ClipboardSystem.getInstance().copyToClipboard(this, selected);
		getObjects().removeAll(selected);
		markUnsavedChanges();
	}

	/**
	 * Copy-Command for the KontextMenu
	 */
	public void copy() {
		List<IWorkObject> selected = getSelectedObjects();
		ClipboardSystem.getInstance().copyToClipboard(this, selected);
	}

	/**
	 * Paste-Command for the KontextMenu
	 */
	public void paste() {
		ClipboardSystem.getInstance().pasteFromClipboard(this);
		markUnsavedChanges();
	}

	/**
	 * DeleteSelected-Command for the KontextMenu
	 */
	public void deleteSelected() {
		for (IWorkObject obj : getSelectedObjects()) {
			getObjects().remove(obj);
		}
		markUnsavedChanges();
	}

	/**
	 * SelectAll-Command for the KontextMenu
	 */
	public void selectAll() {
		for (IWorkObject obj : getObjects()) {
			obj.setSelected(true);
		}
	}

	/**
	 * SelectNone-Command for the KontextMenu
	 */
	public void selectNone() {
		for (IWorkObject item : getObjects()) {
			item.setSelected(false);
		}
	}

	/**
	 * ToggleAutoDock-Command for the ToolMenu
	 */
	public void toggleAutoDock() {
		autoDock = !autoDock;
	}

	/**
	 * ToggleDefaultCursor-Command for the ToolMenu
	 */
	public void toggleDefaultCursor() {
		defaultCursor = !defaultCursor;
	}

	/**
	 * @return the scroll position in x direction
	 */
	protected int getScrollX() {
		return scrollbarHorizontal.getRenderScrollPosition();
	}

	/**
	 * @return the scroll position in y direction
	 */
	protected int getScrollY() {
		return scrollbarVertical.getRenderScrollPosition();
	}

	/**
	 * @return ArrayList with the currently selected objects
	 */
	public List<IWorkObject> getSelectedObjects() {
		List<IWorkObject> list = new ArrayList<>();
		for (IWorkObject item : getObjects()) {
			if (item.isSelected()) {
				// Add item to list
				list.add(item);
			}
		}
		return list;
	}

	/**
	 * @return the current object under mouse
	 */
	public IWorkObject getMouseHoverObject() {
		IWorkObject hover = null;
		for (IWorkObject item : getObjects()) {
			if (item.isMouseHover()) {
				hover = item;
				// Not directly returning for correct sorting if clicking on a
				// object which is hover another
			}
		}
		return hover;
	}

	/**
	 * @return a list of all objects used in this WorkPanel
	 */
	public List<IWorkObject> getObjects() {
		return history.getCurrentObjects();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.controls.tabcontrol.ITabEntry#markUnsavedChanges()
	 */
	@Override
	public void setUnsavedChanges(boolean changes) {
		if (changes) {
			history.makeHistory();
		}
		unsavedChanges = changes;
	}

	/**
	 * @return ZoomManagement class for all zoom settings
	 */
	public ZoomManagement getZoom() {
		return history.getCurrentObjects().getZoom();
	}

	/**
	 * @return - Returns a scroll bar if there is one under the point
	 */
	protected IScreen getHoverScollbar(Point p) {
		if (lastClickPos != null) {
			p = lastClickPos;
		}
		for (IScreen s : getScollbars()) {
			if (s.getRectangle().contains(p)) {
				return s;
			}
		}
		// Return null if nothing found
		return null;
	}

	/**
	 * @return - List of all scroll bars for this work pannel
	 */
	protected IScreen[] getScollbars() {
		return new IScreen[] { scrollbarVertical, scrollbarHorizontal };
	}

	/**
	 * Opens the left click kontext menu
	 */
	private void openContextMenu() {
		update();
		Management.getScheduler().execute(() -> {
			// Fixes some screen bugs
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			IScreen main = getMainScreen();
			Point mouse = main.currentMousePosition();
			mouse.translate(1, 1);
			ContextMenu menu = new ContextMenu(main, mouse);

			// The options in the context-menu
			menu.getItems().add(new Delete());
			menu.getItems().add(new SelectAll());
			menu.getItems().add(new Separator());
			menu.getItems().add(new Cut());
			menu.getItems().add(new Copy());
			menu.getItems().add(new Paste());
			menu.getItems().add(new Separator());
			menu.getItems().add(new Undo());
			menu.getItems().add(new Redo());
			menu.getItems().add(new Save());
			menu.getItems().add(new Separator());
			menu.getItems().add(new ZoomIn());
			menu.getItems().add(new ZoomOut());
		});
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.controls.tabcontrol.ITabEntry#getName()
	 */
	@Override
	public String getName() {
		if (currentFile == null) {
			// returns the default name
			return "Neue Schaltung.digi";
		}
		return currentFile.getName();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.controls.tabcontrol.ITabEntry#getScreen()
	 */
	@Override
	public IScreen getScreen() {
		return this;
	}
}