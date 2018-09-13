package de.crusader.controls.tooltip;

import java.awt.Color;
import java.awt.Rectangle;

import de.crusader.screens.Management;
import de.crusader.screens.controls.tooltip.GuiTooltip;

public class Tooltip extends GuiTooltip {

	/**
	 * Called when creating a new instance.
	 */
	public Tooltip(Rectangle rec, String tooltip) {
		super(rec, tooltip);
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
