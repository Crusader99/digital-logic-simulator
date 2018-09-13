package de.crusader.controls.mainmenu.options.edit;

import de.crusader.circuit.clipboard.ClipboardSystem;
import de.crusader.controls.contextmenu.Contextitem;
import de.crusader.controls.workpanel.WorkPanel;
import de.crusader.screens.Management;

public class Paste extends Contextitem {
	/**
	 * (non-Javadoc)
	 *
	 * @see de.crusader.screens.interfaces.IClickable#onClick()
	 */
	@Override
	public void onClick() {
		WorkPanel panel = Management.getWorkPanel();
		panel.paste();
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.crusader.screens.interfaces.IDisplayName#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return "Einf\u00FCgen";
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
		return ClipboardSystem.getInstance().hasObjectsInClipboard();
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.crusader.screens.controls.kontextmenu.IKontextItem#getShortCut()
	 */
	@Override
	public String getShortCut() {
		return "Strg+V";
	}
}
