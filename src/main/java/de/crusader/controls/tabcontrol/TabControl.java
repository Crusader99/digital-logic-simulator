package de.crusader.controls.tabcontrol;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import de.crusader.controls.contextmenu.ContextMenu;
import de.crusader.controls.mainmenu.options.Separator;
import de.crusader.controls.mainmenu.options.file.New;
import de.crusader.controls.mainmenu.options.file.Save;
import de.crusader.controls.mainmenu.options.tabs.CloseAll;
import de.crusader.controls.mainmenu.options.tabs.CloseFile;
import de.crusader.controls.mainmenu.options.tabs.CloseLeft;
import de.crusader.controls.mainmenu.options.tabs.CloseOthers;
import de.crusader.controls.mainmenu.options.tabs.CloseRight;
import de.crusader.controls.modulebox.ModuleBox;
import de.crusader.controls.toolbar.Toolbar;
import de.crusader.controls.tooltip.Tooltip;
import de.crusader.controls.workpanel.WorkPanel;
import de.crusader.painter.Painter;
import de.crusader.screens.Management;
import de.crusader.screens.WindowManager;
import de.crusader.screens.controls.tabcontrol.Cancelable;
import de.crusader.screens.controls.tabcontrol.GuiTabControl;
import de.crusader.screens.controls.tabcontrol.ITabEntry;
import de.crusader.screens.controls.tooltip.GuiTooltip;
import de.crusader.screens.dialog.IButton;
import de.crusader.screens.enums.EnumDialogType;

public class TabControl extends GuiTabControl {

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.controls.tabcontrol.ITabControlEvents#onCloseTab(de.crusader.screens.controls.tabcontrol.ITabEntry,
	 *      de.crusader.screens.controls.tabcontrol.Cancelable)
	 */
	@Override
	public void onCloseTab(ITabEntry tab, Cancelable cancel) {
		if (!(tab instanceof WorkPanel)) {
			return;
		}
		WorkPanel panel = (WorkPanel) tab;
		if (!panel.hasUnsavedChanges()) {
			return;
		}

		// Show save dialog
		WindowManager.getDialogs().showMsgBox("Speichern?",
				"M\u00F6chtest du die Schaltung \"" + tab.getName() + "\" speichern?", EnumDialogType.QUESTION,
				new IButton() {
					@Override
					public String getDisplayName() {
						return "Ja, speichern";
					}

					@Override
					public void onClick() {
						new Save().onClick();
					}
				}, new IButton() {

					@Override
					public String getDisplayName() {
						return "Verwerfen";
					}

					@Override
					public void onClick() {
						// Do nothing
					}
				}, new IButton() {

					@Override
					public String getDisplayName() {
						return "Abbrechen";
					}

					@Override
					public void onClick() {
						cancel.setCancelled(true);
					}
				});

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.controls.tabcontrol.ITabControlEvents#onOpenKontextMenu(java.awt.Point)
	 */
	@Override
	public void onOpenKontextMenu(Point mouse) {
		ContextMenu context = new ContextMenu(getParent(), mouse);

		// All this items are show in the context menu
		context.getItems().add(new New());
		context.getItems().add(new Separator());
		context.getItems().add(new CloseFile());
		context.getItems().add(new CloseOthers());
		context.getItems().add(new Separator());
		context.getItems().add(new CloseLeft());
		context.getItems().add(new CloseRight());
		context.getItems().add(new Separator());
		context.getItems().add(new CloseAll());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.controls.tabcontrol.ITabControlEvents#onSelectedTabChanged(de.crusader.screens.controls.tabcontrol.ITabEntry)
	 */
	@Override
	public void onSelectedTabChanged(ITabEntry newTab) {
		if (newTab == null && this.getOpenedTabs().size() <= 0) {
			Management.createNewTab();
		}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.handler.IUpdateSizeHandler#onUpdateSize()
	 */
	@Override
	public Rectangle onUpdateSize() {
		Rectangle rec = getParent().getRectangle();
		return Management.getDocker().dock(getParent(), Toolbar.class, ModuleBox.class, null, null, rec.width,
				rec.height);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.controls.tabcontrol.ITabControlEvents#onTooltipCloseRequest(de.crusader.screens.controls.tabcontrol.ITabEntry)
	 */
	@Override
	public String onTooltipCloseRequest(ITabEntry entry) {
		return "Schaltung schlie\u00DFen";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.controls.tabcontrol.ITabControlEvents#onTooltipUpdate(java.awt.Rectangle,
	 *      java.lang.String)
	 */
	@Override
	public GuiTooltip onTooltipUpdate(Rectangle rec, String displayText) {
		return new Tooltip(rec, displayText);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.controls.tabcontrol.ITabControlEvents#onPaintEmptyPage(de.crusader.painter.Painter,
	 *      java.awt.Rectangle)
	 */
	@Override
	public void onPaintEmptyPage(Painter painter, Rectangle rec) {
		// Do nothing
	}

}