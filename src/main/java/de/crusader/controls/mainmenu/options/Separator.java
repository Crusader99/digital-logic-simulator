package de.crusader.controls.mainmenu.options;

import java.awt.Color;
import java.awt.Rectangle;

import de.crusader.controls.contextmenu.Contextitem;
import de.crusader.painter.Painter;

public class Separator extends Contextitem {
	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.IClickable#onClick()
	 */
	@Override
	public void onClick() {
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.IDisplayName#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return new String();
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.crusader.screens.controls.kontextmenu.IKontextItem#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.crusader.screens.controls.kontextmenu.IKontextItem#getShortCut()
	 */
	@Override
	public String getShortCut() {
		return null;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.crusader.screens.controls.kontextmenu.ExtendedKontextItem#draw(de.crusader.painter.Painter,
	 *      java.awt.Rectangle, java.awt.Color)
	 */
	@Override
	public void draw(Painter painter, Rectangle border, Color focusColor) {
		Color c1 = new Color(34, 34, 34);
		Color c2 = new Color(70, 70, 72);
		painter.createRectangle().position(border.x + 5, border.y + 2 + (border.height >> 1)).size(border.width - 10, 2)
				.color(c1).draw();
		painter.createRectangle().position(border.x + 5, border.y + 4 + (border.height >> 1)).size(border.width - 8, 1)
				.color(c2).draw();
	}
}
