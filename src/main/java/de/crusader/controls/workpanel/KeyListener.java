package de.crusader.controls.workpanel;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import de.crusader.controls.mainmenu.options.file.New;
import de.crusader.controls.mainmenu.options.file.Open;
import de.crusader.controls.mainmenu.options.file.Save;
import de.crusader.controls.mainmenu.options.file.SaveAs;
import de.crusader.controls.mainmenu.options.tabs.CloseAll;
import de.crusader.controls.mainmenu.options.tabs.CloseFile;
import de.crusader.helpers.MathUtils;

public class KeyListener {
	private final WorkPanel panel;

	/**
	 * Called when creating a new instance.
	 */
	protected KeyListener(WorkPanel panel) {
		this.panel = panel;
	}

	/*
	 * This method handles the key commands
	 */
	public void onKeyDown(int keyCode, char typed) {
		if (!panel.getMainScreen().hasFocus()) {
			// Prevent some bugs
			return;
		}
		if (panel.isKeyDown(KeyEvent.VK_CONTROL)) {
			if (keyCode == KeyEvent.VK_Z) {
				if (panel.isKeyDown(KeyEvent.VK_SHIFT)) {
					// Repeat last operation
					panel.getHistory().next();
				} else {
					// Undo last operation
					panel.getHistory().previous();
				}
			} else if (keyCode == KeyEvent.VK_Y) {
				// Repeat last operation
				panel.getHistory().next();
			} else if (keyCode == KeyEvent.VK_A) {
				// Select all objects in the current tab
				panel.selectAll();
			} else if (keyCode == KeyEvent.VK_C) {
				// Copy selected objects to clip-board
				panel.copy();
			} else if (keyCode == KeyEvent.VK_X) {
				// Cut selected objects to clip-board
				panel.cut();
			} else if (keyCode == KeyEvent.VK_V) {
				// Paste objects from clip-board
				panel.paste();
			} else if (keyCode == KeyEvent.VK_S) {
				if (panel.isKeyDown(KeyEvent.VK_SHIFT)) {
					// Save current file as...
					new SaveAs().onClick();
				} else {
					// Save current file
					new Save().onClick();
				}
			} else if (keyCode == KeyEvent.VK_O) {
				// Open file
				new Open().onClick();
			} else if (keyCode == KeyEvent.VK_W) {
				if (panel.isKeyDown(KeyEvent.VK_SHIFT)) {
					// Close all tabs
					new CloseAll().onClick();
				} else {
					// Close current tab
					new CloseFile().onClick();
				}
			} else if (keyCode == KeyEvent.VK_PLUS) {
				// Zoom in
				panel.getZoom().zoomIn();
			} else if (keyCode == KeyEvent.VK_MINUS) {
				// Zoom out
				panel.getZoom().zoomOut();
			} else if (keyCode == KeyEvent.VK_N) {
				// New tab
				new New().onClick();
			}
		}
		if (keyCode == KeyEvent.VK_DELETE || (panel.isKeyDown(KeyEvent.VK_CONTROL) && keyCode == KeyEvent.VK_D)) {
			// Delete selected elements
			for (IWorkObject obj : panel.getSelectedObjects()) {
				panel.getObjects().remove(obj);
			}
			panel.markUnsavedChanges();
		} else if (keyCode == KeyEvent.VK_RIGHT && panel.drag == null) {
			// Make selected elements go right
			for (IWorkObject obj : panel.getSelectedObjects()) {
				Rectangle rec = obj.getRectangle();
				rec.x = MathUtils.bounds(rec.x + 1, 0,
						panel.getRectangle().width - obj.getSize().width - panel.getScrollX());
			}
			panel.markUnsavedChanges();
		} else if (keyCode == KeyEvent.VK_LEFT && panel.drag == null) {
			// Make selected elements go left
			for (IWorkObject obj : panel.getSelectedObjects()) {
				Rectangle rec = obj.getRectangle();
				rec.x = MathUtils.bounds(rec.x - 1, 0,
						panel.getRectangle().width - obj.getSize().width - panel.getScrollX());
			}
			panel.markUnsavedChanges();
		} else if (keyCode == KeyEvent.VK_DOWN && panel.drag == null) {
			// Make selected elements go down
			for (IWorkObject obj : panel.getSelectedObjects()) {
				Rectangle rec = obj.getRectangle();
				rec.y = MathUtils.bounds(rec.y + 1, 0,
						panel.getRectangle().height - obj.getSize().height - panel.getScrollY());
			}
			panel.markUnsavedChanges();
		} else if (keyCode == KeyEvent.VK_UP && panel.drag == null) {
			// Make selected elements go up
			for (IWorkObject obj : panel.getSelectedObjects()) {
				Rectangle rec = obj.getRectangle();
				rec.y = MathUtils.bounds(rec.y - 1, 0,
						panel.getRectangle().height - obj.getSize().height - panel.getScrollY());
			}
			panel.markUnsavedChanges();
		}
	}
}
