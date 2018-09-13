package de.crusader.controls.mainmenu.options.tabs;

import de.crusader.controls.contextmenu.Contextitem;
import de.crusader.controls.tabcontrol.TabControl;
import de.crusader.screens.Management;
import de.crusader.screens.controls.tabcontrol.ITabEntry;

public class CloseAll extends Contextitem {
	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.IClickable#onClick()
	 */
	@Override
	public void onClick() {
		TabControl tabs = Management.getTabControl();
		// Close all tabs
		for (ITabEntry e : tabs.getOpenedTabs()) {
			tabs.closeTab(e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.IDisplayName#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return "Alle verwerfen";

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.controls.kontextmenu.IKontextItem#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		// Check if there is a tab opened
		return Management.getWorkPanel() != null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.controls.kontextmenu.IKontextItem#getShortCut()
	 */
	@Override
	public String getShortCut() {
		return "Strg+Shift+W";
	}
}
