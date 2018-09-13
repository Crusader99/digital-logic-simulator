package de.crusader.controls.mainmenu.options.edit;

import de.crusader.controls.contextmenu.Contextitem;
import de.crusader.controls.workpanel.WorkPanel;
import de.crusader.screens.Management;

public class Undo extends Contextitem {
	/**
	 * (non-Javadoc)
	 *
	 * @see de.crusader.screens.interfaces.IClickable#onClick()
	 */
	@Override
	public void onClick() {
		WorkPanel panel = Management.getWorkPanel();
		panel.getHistory().previous();
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.crusader.screens.interfaces.IDisplayName#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return "R\u00FCckg\u00E4ngig";
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.crusader.screens.controls.kontextmenu.IKontextItem#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		WorkPanel panel = Management.getWorkPanel();
		if (panel == null) {
			return false;
		}
		return panel.getHistory().hasPrevious();
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.crusader.screens.controls.kontextmenu.IKontextItem#getShortCut()
	 */
	@Override
	public String getShortCut() {
		return "Strg+Z";
	}
}
