package de.crusader.controls.mainmenu;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.List;

import de.crusader.controls.contextmenu.ContextMenu;
import de.crusader.controls.mainmenu.options.Separator;
import de.crusader.controls.mainmenu.options.edit.Copy;
import de.crusader.controls.mainmenu.options.edit.Cut;
import de.crusader.controls.mainmenu.options.edit.Delete;
import de.crusader.controls.mainmenu.options.edit.Paste;
import de.crusader.controls.mainmenu.options.edit.Redo;
import de.crusader.controls.mainmenu.options.edit.SelectAll;
import de.crusader.controls.mainmenu.options.edit.Undo;
import de.crusader.controls.mainmenu.options.file.New;
import de.crusader.controls.mainmenu.options.file.Open;
import de.crusader.controls.mainmenu.options.file.Save;
import de.crusader.controls.mainmenu.options.file.SaveAs;
import de.crusader.controls.mainmenu.options.help.Analytics;
import de.crusader.controls.mainmenu.options.help.CheckUpdates;
import de.crusader.controls.mainmenu.options.help.Exit;
import de.crusader.controls.mainmenu.options.help.Infos;
import de.crusader.controls.mainmenu.options.settings.CrossCursor;
import de.crusader.controls.mainmenu.options.settings.Magnet;
import de.crusader.controls.mainmenu.options.tabs.CloseAll;
import de.crusader.controls.mainmenu.options.tabs.CloseFile;
import de.crusader.controls.mainmenu.options.view.ZoomIn;
import de.crusader.controls.mainmenu.options.view.ZoomOut;
import de.crusader.screens.Management;
import de.crusader.screens.controls.mainmenu.ExtendedMenuItem;
import de.crusader.screens.controls.mainmenu.GuiMainMenu;
import de.crusader.screens.controls.mainmenu.IMenuItem;

public class MainMenu extends GuiMainMenu {
	/*
	 * Add all menu options
	 */
	public MainMenu() {
		List<IMenuItem> menuItems = getMenuItems();

		menuItems.add(new ExtendedMenuItem() { // file menu item

			@Override
			public void onClick() {
				Rectangle rec = MainMenu.this.getRectangle();
				ContextMenu context = new ContextMenu(getParent(), getRectangle().x, rec.y + rec.height);
				this.setMenu(context);
				context.setCloseListener(() -> this.setMenu(null));

				context.getItems().add(new New());
				context.getItems().add(new Open());
				context.getItems().add(new Separator());
				context.getItems().add(new Save());
				context.getItems().add(new SaveAs());
				context.getItems().add(new Separator());
				context.getItems().add(new CloseFile());
				context.getItems().add(new CloseAll());
				context.getItems().add(new Exit());
			}

			@Override
			public String getDisplayName() {
				return "Datei";
			}
		});
		menuItems.add(new ExtendedMenuItem() { // edit menu item

			@Override
			public void onClick() {
				Rectangle rec = MainMenu.this.getRectangle();
				ContextMenu context = new ContextMenu(getParent(), getRectangle().x, rec.y + rec.height);
				this.setMenu(context);
				context.setCloseListener(() -> this.setMenu(null));

				context.getItems().add(new Undo());
				context.getItems().add(new Redo());
				context.getItems().add(new Separator());
				context.getItems().add(new Cut());
				context.getItems().add(new Copy());
				context.getItems().add(new Paste());
				context.getItems().add(new Separator());
				context.getItems().add(new Delete());
				context.getItems().add(new SelectAll());
			}

			@Override
			public String getDisplayName() {
				return "Bearbeiten";
			}
		});
		menuItems.add(new ExtendedMenuItem() { // view menu item

			@Override
			public void onClick() {
				Rectangle rec = MainMenu.this.getRectangle();
				ContextMenu context = new ContextMenu(getParent(), getRectangle().x, rec.y + rec.height);
				this.setMenu(context);
				context.setCloseListener(() -> this.setMenu(null));

				context.getItems().add(new ZoomIn());
				context.getItems().add(new ZoomOut());
				context.getItems().add(new Separator());
				context.getItems().add(new Magnet());
				context.getItems().add(new CrossCursor());
			}

			@Override
			public String getDisplayName() {
				return "Ansicht";
			}
		});
		menuItems.add(new ExtendedMenuItem() { // help menu item

			@Override
			public void onClick() {
				Rectangle rec = MainMenu.this.getRectangle();
				ContextMenu context = new ContextMenu(getParent(), getRectangle().x, rec.y + rec.height);
				this.setMenu(context);
				context.setCloseListener(() -> this.setMenu(null));

				context.getItems().add(new Analytics());
				context.getItems().add(new Separator());
				context.getItems().add(new Infos());
				context.getItems().add(new CheckUpdates());
				context.getItems().add(new Separator());
				context.getItems().add(new Exit());
			}

			@Override
			public String getDisplayName() {
				return "Hilfe";
			}
		});
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.handler.IUpdateSizeHandler#onUpdateSize()
	 */
	@Override
	public Rectangle onUpdateSize() {
		Rectangle rec = getParent().getRectangle();
		return Management.getDocker().dock(getParent(), null, null, null, null, rec.width, HEIGHT);
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
	 * @see de.crusader.screens.controls.mainmenu.GuiMainMenu#getInfoText()
	 */
	@Override
	public String[] getInfoText() {
		return new String[] { "Software von Simon, Texturen von Fabi." };
	}

}
