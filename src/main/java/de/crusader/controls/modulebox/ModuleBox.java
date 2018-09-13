package de.crusader.controls.modulebox;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.crusader.analytics.Analytics;
import de.crusader.circuit.modules.Modules;
import de.crusader.controls.scrollbar.VerticalScrollbar;
import de.crusader.controls.toolbar.Toolbar;
import de.crusader.controls.workpanel.ExtendedWorkObject;
import de.crusader.controls.workpanel.IWorkObject;
import de.crusader.controls.workpanel.WorkPanel;
import de.crusader.helpers.MathUtils;
import de.crusader.painter.DrawString;
import de.crusader.painter.Painter;
import de.crusader.painter.util.EnumCenteredType;
import de.crusader.screens.Management;
import de.crusader.screens.api.ControlScreen;
import de.crusader.screens.controls.scrollbar.IScrollPositionAdapter;
import de.crusader.screens.enums.EnumMouseButton;
import de.crusader.screens.interfaces.IScreenEvents;
import lombok.Getter;

public class ModuleBox extends ControlScreen implements IScreenEvents, IScrollPositionAdapter {
	/**
	 * Target Y-Position. Can be null if disabled.
	 */
	public int[] scrollPositionTarget = null;
	public long scrollLastMouseWheelEvent = 0;

	/**
	 * Items of TreeEntrys
	 */
	private List<TreeEntry> entries = null;

	/**
	 * Scroll bar of this Element
	 */
	private VerticalScrollbar scrollbar;
	@Getter
	private int fullPageSize = 0;

	public ModuleBox() {
		getSubScreens().add(scrollbar = new VerticalScrollbar(this));
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.api.Screen#onPaint(de.crusader.painter.Painter)
	 */
	@Override
	public void onPaint(Painter p) {
		try (Analytics a = new Analytics(getClass())) {
			Color bg = new Color(64, 64, 64);
			p.createRectangle().color(bg).size(getRectangle().getSize()).filled(true).draw();

			DrawString string = p.createString().color(Color.WHITE);

			int fullPageSize = 0;

			int top = 10 + getScrollPositionTop();
			int left = 10;
			if (entries == null) {
				loadEntrys();
			}
			for (TreeEntry item : entries) {
				String name = item.getDisplayName();
				Rectangle rec = new Rectangle(item.getRectangle());
				rec.x = left;
				rec.y = top;

				int width = scrollbar.getRectangle().x - 14;
				width = Math.max(width, rec.width + (name.length() + 4) * Management.getChars().getWidth());
				item.setLastPaintedRectangle(new Rectangle(rec.x - 3, rec.y - 3, width, rec.height + 6));

				{
					if (item.getPositionY() == -1) {
						item.setPositionY(top - string.getHeight());
					}
					int animationDistance = Math.abs(item.getPositionY() - top);
					item.setPositionY(MathUtils.limit(item.getPositionY(), top, Math.max(3, animationDistance / 4)));
				}

				if (item.isMouseHover()) {
					p.createRectangle().color(Management.getColors().getBackground())
							.rectangle(item.getLastPaintedRectangle()).filled(true).draw();
				} else {
					p.createRectangle().color(bg).rectangle(item.getLastPaintedRectangle()).filled(true).draw();
				}

				{
					Rectangle rrr = new Rectangle(p.getX() + rec.x, p.getY() + rec.y, p.getWidth(), p.getHeight());
					Painter particalPainter = new Painter(p, (int) rrr.getX(), (int) rrr.getY(), (int) rrr.getWidth(),
							(int) rrr.getHeight());
					item.draw(particalPainter, new Rectangle(0, 0, rec.width, rec.height));
				}

				{
					int offX = rec.width + ((Management.getChars().getWidth()
							* Management.getWorkPanel().getZoom().getZoomFactor()) >> 1);
					string.rectangle(item.getLastPaintedRectangle()).centered(EnumCenteredType.Y).add(offX, 0)
							.text(name).draw();
				}
				if (item.isMouseHover()) {
					p.createRectangle().color(Management.getColors().getFocus())
							.rectangle(item.getLastPaintedRectangle()).filled(false).draw();
				}

				int height = rec.height + Management.getChars().getHeight();
				fullPageSize += height;
				top += height;
			}
			this.fullPageSize = fullPageSize;
			super.onPaint(p);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.api.Screen#onResize()
	 */
	@Override
	public void onResize() {
		Rectangle rec = getParent().getRectangle();
		int width = rec.width / 5;
		width = Math.min(width, 40 * Management.getChars().getWidth());
		width = Math.max(width, 20 * Management.getChars().getWidth());
		setRectangle(Management.getDocker().dock(getParent(), Toolbar.class, null, null, null, width, rec.height));
		super.onResize();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.api.Screen#onMouseDown(de.crusader.screens.api.utils.
	 *      EnumMouseButton, int)
	 */
	@Override
	public void onMouseDown(EnumMouseButton button, int clicks) {
		if (!isHoverSubScreen() && button == EnumMouseButton.LEFT_BUTTON && clicks % 2 == 1) {
			TreeEntry entry = getSelectedEntry();
			if (entry == null) {
				return;
			}
			WorkPanel panel = Management.getWorkPanel();
			if (panel != null) {
				try {
					IWorkObject obj = new ExtendedWorkObject(
							((IWorkObject) entry).getElement().getClass().newInstance());
					Rectangle rec = obj.getRectangle();
					rec.x *= Management.getWorkPanel().getZoom().getZoomFactor();
					rec.y *= Management.getWorkPanel().getZoom().getZoomFactor();
					rec.width *= Management.getWorkPanel().getZoom().getZoomFactor();
					rec.height *= Management.getWorkPanel().getZoom().getZoomFactor();
					panel.getObjects().add(obj);
					panel.keepConsistandUpdating(10);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			keepConsistandUpdating(2000);
		}
		super.onMouseDown(button, clicks);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.api.Screen#onMouseMove()
	 */
	@Override
	public void onMouseMove() {
		Point mouse = currentMousePosition();

		boolean updateRequired = false;
		for (TreeEntry item : entries) {

			// prevent mouse hover more than one item
			boolean mouseHover = !updateRequired && item.getLastPaintedRectangle().contains(mouse);
			if (mouseHover != item.isMouseHover()) {
				item.setMouseHover(mouseHover);
				updateRequired = true;
			}
		}

		if (updateRequired) {
			update();
		}

		super.onMouseMove();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.api.Screen#onMouseLeftScreen()
	 */
	@Override
	public void onMouseLeftScreen() {
		super.onMouseLeftScreen();

		// Set mouse is on no item
		for (TreeEntry item : entries) {
			item.setMouseHover(false);
		}
		update();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.api.Screen#onMouseWheel(int)
	 */
	@Override
	public void onMouseWheel(int rotation) {
		// Give scroll info to VerticalScrollbar
		getSubScreen(VerticalScrollbar.class).onMouseWheel(rotation);
	}

	/**
	 * Loads all entries and adds them in the cache
	 */
	private synchronized void loadEntrys() {
		List<TreeEntry> list = new CopyOnWriteArrayList<>();

		// add modules
		try {
			for (IWorkObject module : Modules.getList()) {
				module.getRectangle().width = 32;
				module.getRectangle().height = 32;
				list.add((TreeEntry) module);
			}
		} catch (Exception ex) {
			throw new RuntimeException("Error while initializing modules", ex);
		}

		if (entries != null) {
			for (TreeEntry e : entries) {
				if (!list.contains(e)) {
					e.setPositionY(-1);
				}
			}
		}
		entries = list;
	}

	/**
	 * @return the vertical scroll position
	 */
	public int getScrollPositionTop() {
		return getSubScreen(VerticalScrollbar.class).getRenderScrollPosition();
	}

	/**
	 * @return the currently selected entry
	 */
	public TreeEntry getSelectedEntry() {
		for (TreeEntry item : entries) {
			if (item.isMouseHover()) {
				return item;
			}
		}
		return null;
	}
}