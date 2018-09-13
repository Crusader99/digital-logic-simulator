package de.crusader.controls.mainmenu.options.tabs;

import de.crusader.controls.contextmenu.Contextitem;
import de.crusader.controls.tabcontrol.TabControl;
import de.crusader.screens.Management;
import de.crusader.screens.controls.tabcontrol.ITabEntry;

public class CloseOthers extends Contextitem {
	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.IClickable#onClick()
	 */
	@Override
	public void onClick() {
		ITabEntry current = Management.getWorkPanel();
		TabControl tabs = Management.getTabControl();
		// Close all tabs except for the currently shown
		for (ITabEntry e : tabs.getOpenedTabs()) {
			if (e.equals(current)) {
				// Ignore currently show tab
				continue;
			}
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
		return "Andere Tabs schlie\u00DFen";

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.controls.kontextmenu.IKontextItem#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return Management.getWorkPanel() != null && Management.getTabControl().getOpenedTabs().size() > 1;
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
}
