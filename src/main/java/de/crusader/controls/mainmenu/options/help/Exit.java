package de.crusader.controls.mainmenu.options.help;

import de.crusader.controls.contextmenu.Contextitem;
import de.crusader.controls.tabcontrol.TabControl;
import de.crusader.screens.Management;
import de.crusader.screens.controls.tabcontrol.ITabEntry;

public class Exit extends Contextitem {
	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.IClickable#onClick()
	 */
	@Override
	public void onClick() {
		TabControl tabs = Management.getTabControl();
		for (ITabEntry entry : tabs.getOpenedTabs()) {
			if (!tabs.closeTab(entry)) {
				return;
			}
		}
		System.exit(0);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.IDisplayName#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return "Programm beenden";

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
		return "Alt+F4";
	}
}
