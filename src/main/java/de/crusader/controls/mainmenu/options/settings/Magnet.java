package de.crusader.controls.mainmenu.options.settings;

import de.crusader.controls.contextmenu.Contextitem;
import de.crusader.controls.workpanel.WorkPanel;
import de.crusader.screens.Management;

public class Magnet extends Contextitem {
	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.IClickable#onClick()
	 */
	@Override
	public void onClick() {
		WorkPanel panel = Management.getWorkPanel();
		panel.toggleAutoDock();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.IDisplayName#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return "Automatisches Andocken";

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.controls.kontextmenu.IKontextItem#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		WorkPanel panel = Management.getWorkPanel();
		return panel != null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.controls.kontextmenu.IKontextItem#getShortCut()
	 */
	@Override
	public String getShortCut() {
		// Display activated state
		return isActivated() ? "an" : "aus";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.controls.kontextmenu.ExtendedKontextItem#isActivated()
	 */
	@Override
	public boolean isActivated() {
		WorkPanel panel = Management.getWorkPanel();
		if (panel == null) {
			return false;
		}
		return panel.isAutoDock();
	}
}
