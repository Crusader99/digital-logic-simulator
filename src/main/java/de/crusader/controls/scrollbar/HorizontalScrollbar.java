package de.crusader.controls.scrollbar;

import java.awt.Color;

import de.crusader.screens.Management;
import de.crusader.screens.controls.scrollbar.GuiHorizontalScrollbar;
import de.crusader.screens.controls.scrollbar.IScrollPositionAdapter;

public class HorizontalScrollbar extends GuiHorizontalScrollbar {

	/**
	 * Called when creating a new instance.
	 */
	public HorizontalScrollbar(IScrollPositionAdapter adapter) {
		super(adapter);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.colors.IFocusColor#getFocusColor()
	 */
	@Override
	public Color getFocusColor() {
		return Management.getColors().getFocus();
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
	 * @see de.crusader.screens.controls.scrollbar.ExtendedScrollbar#getScrollbarColor()
	 */
	@Override
	public Color getScrollbarColor() {
		return new Color(100, 100, 100);
	}

}