package de.crusader.controls.toolbar;

import java.awt.Color;
import java.awt.Rectangle;

import de.crusader.controls.mainmenu.MainMenu;
import de.crusader.controls.mainmenu.options.Separator;
import de.crusader.controls.mainmenu.options.edit.Copy;
import de.crusader.controls.mainmenu.options.edit.Cut;
import de.crusader.controls.mainmenu.options.edit.Delete;
import de.crusader.controls.mainmenu.options.edit.Paste;
import de.crusader.controls.mainmenu.options.edit.Redo;
import de.crusader.controls.mainmenu.options.edit.Undo;
import de.crusader.controls.mainmenu.options.file.New;
import de.crusader.controls.mainmenu.options.file.Open;
import de.crusader.controls.mainmenu.options.file.Save;
import de.crusader.controls.mainmenu.options.settings.CrossCursor;
import de.crusader.controls.mainmenu.options.settings.Magnet;
import de.crusader.controls.mainmenu.options.view.ZoomIn;
import de.crusader.controls.mainmenu.options.view.ZoomOut;
import de.crusader.controls.tooltip.Tooltip;
import de.crusader.screens.Management;
import de.crusader.screens.controls.kontextmenu.IKontextItem;
import de.crusader.screens.controls.toolbar.GuiToolbar;
import de.crusader.screens.controls.tooltip.GuiTooltip;

public class Toolbar extends GuiToolbar {
	/**
	 * Creates a new tool bar
	 */
	public Toolbar() {
		// Initialize items of this Toolbar
		getItems().add(new New());
		getItems().add(new Open());
		getItems().add(new Save());

		getItems().add(new Separator());

		getItems().add(new Undo());
		getItems().add(new Redo());

		getItems().add(new Separator());

		getItems().add(new Cut());
		getItems().add(new Copy());
		getItems().add(new Paste());
		getItems().add(new Delete());

		getItems().add(new Separator());

		getItems().add(new Magnet());
		getItems().add(new CrossCursor());
		getItems().add(new ZoomIn());
		getItems().add(new ZoomOut());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.handler.IUpdateSizeHandler#onUpdateSize()
	 */
	@Override
	public Rectangle onUpdateSize() {
		Rectangle rec = getParent().getRectangle();
		return Management.getDocker().dock(getParent(), MainMenu.class, null, null, null, rec.width, HEIGHT);
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
	 * @see de.crusader.screens.controls.toolbar.GuiToolbar#getDarkFocusColor()
	 */
	@Override
	public Color getDarkFocusColor() {
		return Management.getColors().getDarkFocus();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.controls.toolbar.GuiToolbar#isSeparator(de.crusader.screens.controls.kontextmenu.IKontextItem)
	 */
	@Override
	public boolean isSeparator(IKontextItem item) {
		return item instanceof Separator;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.controls.toolbar.GuiToolbar#onTooltipUpdate(java.awt.Rectangle,
	 *      java.lang.String)
	 */
	@Override
	public GuiTooltip onTooltipUpdate(Rectangle rec, String displayText) {
		return new Tooltip(rec, displayText);
	}

}