package de.crusader.controls.mainmenu.options.help;

import de.crusader.controls.contextmenu.Contextitem;
import de.crusader.screens.Management;

public class CheckUpdates extends Contextitem {
	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.IClickable#onClick()
	 */
	@Override
	public void onClick() {
		Management.getUpdater().runInstallationGUI();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.IDisplayName#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return "Nach Updates suchen";
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
}
