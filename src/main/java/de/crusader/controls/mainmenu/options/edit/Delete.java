package de.crusader.controls.mainmenu.options.edit;

import de.crusader.controls.contextmenu.Contextitem;
import de.crusader.controls.workpanel.WorkPanel;
import de.crusader.screens.Management;

public class Delete extends Contextitem {
	/**
	 * (non-Javadoc)
	 *
	 * @see de.crusader.screens.interfaces.IClickable#onClick()
	 */
	@Override
	public void onClick() {
		Management.getWorkPanel().deleteSelected();
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.crusader.screens.interfaces.IDisplayName#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return "Ausgew\u00E4hltes l\u00F6schen";
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
		return !panel.getSelectedObjects().isEmpty();
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.crusader.screens.controls.kontextmenu.IKontextItem#getShortCut()
	 */
	@Override
	public String getShortCut() {
		return "Entf.";
	}
}
