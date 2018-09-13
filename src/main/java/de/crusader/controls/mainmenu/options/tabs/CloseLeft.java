package de.crusader.controls.mainmenu.options.tabs;

import de.crusader.controls.contextmenu.Contextitem;
import de.crusader.controls.tabcontrol.TabControl;
import de.crusader.screens.Management;
import de.crusader.screens.controls.tabcontrol.ITabEntry;

public class CloseLeft extends Contextitem {
	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.IClickable#onClick()
	 */
	@Override
	public void onClick() {
		ITabEntry current = Management.getWorkPanel();
		TabControl tabs = Management.getTabControl();

		// Close the tabs on the left
		for (ITabEntry e : tabs.getOpenedTabs()) {
			if (e.equals(current)) {
				// Stop if the tab is the currently shown tab
				break;
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
		return "Linke Tabs schlie\u00DFen";

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
