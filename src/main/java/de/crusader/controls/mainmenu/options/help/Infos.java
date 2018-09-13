package de.crusader.controls.mainmenu.options.help;

import java.text.SimpleDateFormat;

import de.crusader.circuit.config.ConfigDetails;
import de.crusader.controls.contextmenu.Contextitem;
import de.crusader.controls.workpanel.WorkPanel;
import de.crusader.screens.Management;
import de.crusader.screens.WindowManager;
import de.crusader.screens.dialog.IButton;
import de.crusader.screens.enums.EnumDialogType;

public class Infos extends Contextitem {
	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.IClickable#onClick()
	 */
	@Override
	public void onClick() {
		StringBuilder builder = new StringBuilder();

		// Add basic information
		builder.append("----- Software -----\n");
		builder.append("Version: 1.0." + Management.getBuildVersion() + " (snapshot)\n");
		builder.append("Entwickler: Simon Forschner\n");
		builder.append("Designer: Fabian M\u00F6\u00DFner\n");
		builder.append("Webseite: https://provider.ddnss.de/software");
		builder.append("\n----- Schaltung -----\n");

		// Get some information...
		WorkPanel panel = Management.getWorkPanel();
		ConfigDetails details = panel.getHistory().getConfigDetails();
		SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy hh:mm");

		// Add information to builder
		builder.append("Dateiname: " + panel.getName() + "\n");
		builder.append("Erstellungsdatum: " + date.format(details.getTimestampCreated()) + "\n");
		builder.append("Zuletzt bearbeitet: " + date.format(details.getTimestampEdited()) + "\n");
		builder.append("Besitzer: ");

		// Add a list of all owners
		if (details.getCreators() == null) {
			builder.append("-");
		} else {
			int index = 0;
			for (String user : details.getCreators()) {
				if (index > 0) {
					builder.append(", ");
				}
				builder.append(user);
				index++;
			}
		}

		// Show a new dialog
		WindowManager.getDialogs().showMsgBox(getDisplayName(), builder.toString(), EnumDialogType.INFO, new IButton() {
			@Override
			public String getDisplayName() {
				return "OK";
			}

			@Override
			public void onClick() {
				// do nothing
			}
		});
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.IDisplayName#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return "Informationen";
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
