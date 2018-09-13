package de.crusader.circuit.clipboard;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import de.crusader.controls.workpanel.CloneSystem;
import de.crusader.controls.workpanel.IWorkObject;
import de.crusader.controls.workpanel.WorkPanel;
import lombok.Getter;

public class ClipboardSystem {
	@Getter
	private final static ClipboardSystem instance = new ClipboardSystem();

	// Cache of the objects in clip board
	private List<IWorkObject> clipboard = null;

	/*
	 * Copy selected objects to "fake" clip board
	 */
	public void copyToClipboard(WorkPanel panel, List<IWorkObject> objs) {
		clipboard = new CloneSystem(objs).cloneAndRemoveUnusedConnections();
		for (IWorkObject obj : clipboard) {
			Point pos = obj.getPosition();
			Point drag = new Point(panel.currentMousePosition());
			drag.x -= pos.x;
			drag.y -= pos.y;
			obj.setDragPosition(drag);
			obj.setSelected(true);
		}
	}

	/*
	 * Paste objects from "fake" clip board to the given WorkPanel parameter
	 */
	public void pasteFromClipboard(WorkPanel panel) {
		if (clipboard == null) {
			return;
		}
		List<IWorkObject> clip = new CloneSystem(clipboard).cloneAndRemoveUnusedConnections();
		if (clip == null) {
			return;
		}
		Point mouse = panel.currentMousePosition();
		for (IWorkObject obj : clip) {
			Rectangle rec = obj.getRectangle();
			Point drag = obj.getDragPosition();
			rec.x = mouse.x - drag.x;
			rec.y = mouse.y - drag.y;
		}
		panel.selectNone();
		panel.getObjects().addAll(clip);
	}

	/*
	 * Returns true if objects are cached in "fake" clip board
	 */
	public boolean hasObjectsInClipboard() {
		return clipboard != null;
	}
}
