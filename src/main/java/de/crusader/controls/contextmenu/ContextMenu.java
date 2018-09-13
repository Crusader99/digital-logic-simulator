package de.crusader.controls.contextmenu;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import de.crusader.screens.Management;
import de.crusader.screens.controls.kontextmenu.GuiKontextMenu;
import de.crusader.screens.interfaces.IScreen;

public class ContextMenu extends GuiKontextMenu {
	/**
	 * Called when creating a new instance.
	 */
	public ContextMenu(IScreen parent, Rectangle rec) {
		super(parent, rec);
	}

	/**
	 * Called when creating a new instance.
	 */
	public ContextMenu(IScreen parent, Point point) {
		super(parent, point);
	}

	/**
	 * Called when creating a new instance.
	 */
	public ContextMenu(IScreen parent, int x, int y) {
		super(parent, x, y);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.colors.IBackgroundColor#getBackgroundColor()
	 */
	@Override
	public Color getBackgroundColor() {
		return Management.getColors().getBackground();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.colors.IFocusColor#getFocusColor()
	 */
	@Override
	public Color getFocusColor() {
		return Management.getColors().getDarkFocus();
	}
}
