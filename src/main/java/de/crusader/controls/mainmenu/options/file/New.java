package de.crusader.controls.mainmenu.options.file;

import de.crusader.controls.contextmenu.Contextitem;
import de.crusader.screens.Management;
import de.crusader.screens.WindowManager;

public class New extends Contextitem {
	/**
	 * (non-Javadoc)
	 *
	 * @see de.crusader.screens.interfaces.IClickable#onClick()
	 */
	@Override
	public void onClick() {
		if (Management.getTabControl().getOpenedTabs().size() > 20) {
			WindowManager.getDialogs().showError("Zu viele offene Schaltungen",
					new IndexOutOfBoundsException("Limit: 20"));
			return;
		}
		Management.createNewTab();
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.crusader.screens.interfaces.IDisplayName#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return "Neue Schaltung";
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
		return "Strg+N";
	}
}
