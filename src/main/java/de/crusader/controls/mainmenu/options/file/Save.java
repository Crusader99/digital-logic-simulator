package de.crusader.controls.mainmenu.options.file;

import static de.crusader.helpers.StringUtils.removeExtension;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import de.crusader.circuit.config.ConfigSystem;
import de.crusader.controls.contextmenu.Contextitem;
import de.crusader.controls.workpanel.WorkPanel;
import de.crusader.screens.Management;
import de.crusader.screens.WindowManager;
import de.crusader.screens.dialog.IButton;
import de.crusader.screens.enums.EnumDialogType;

public class Save extends Contextitem {

	/**
	 * (non-Javadoc)
	 *
	 * @see de.crusader.screens.interfaces.IClickable#onClick()
	 */
	@Override
	public void onClick() {
		WorkPanel panel = Management.getWorkPanel();
		if (panel == null) {
			throw new NullPointerException("No open tab");
		}
		File file = panel.getCurrentFile();
		if (file == null) {
			saveAs();
			return;
		}
		save(panel, file);
	}

	/*
	 * Ask the user where to save
	 */
	public void saveAs() {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Schaltung speichern...");
		chooser.setMultiSelectionEnabled(false);
		chooser.setAutoscrolls(true);
		chooser.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return "Schaltung (*.digi)";
			}

			@Override
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".digi");
			}
		});
		if (chooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION || chooser.getSelectedFile() == null) {
			return;
		}
		String path = removeExtension(chooser.getSelectedFile().getPath());
		File f = new File(path + ".digi");
		if (f.exists()) {
			WindowManager.getDialogs().showMsgBox("Datei \u00FCberschreiben?",
					"M\u00F6chtest du die Datei \"" + f.getName() + "\" \u00FCberschreiben?", EnumDialogType.QUESTION,
					new IButton() {
						@Override
						public String getDisplayName() {
							return "Ja, \u00FCberschreiben";
						}

						@Override
						public void onClick() {
							WorkPanel panel = Management.getWorkPanel();
							panel.setCurrentFile(f);
							save(panel, f);
						}
					}, new IButton() {
						@Override
						public String getDisplayName() {
							return "Nein";
						}

						@Override
						public void onClick() {
							// do nothing
						}
					});
		} else {
			Management.getWorkPanel().setCurrentFile(f);
			onClick(); // save the selected file
		}
	}

	/*
	 * Save in an asynchronous task
	 */
	private void save(WorkPanel panel, File file) {
		Management.getScheduler().execute(() -> {
			try {
				ConfigSystem.saveFile(panel, file);
			} catch (Exception ex) {
				WindowManager.getDialogs().showError("Fehler beim Schreiben", ex);
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
		return "Speichern";

	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.crusader.screens.controls.kontextmenu.IKontextItem#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		WorkPanel panel = Management.getWorkPanel();
		return panel != null && panel.hasUnsavedChanges();
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.crusader.screens.controls.kontextmenu.IKontextItem#getShortCut()
	 */
	@Override
	public String getShortCut() {
		return "Strg+S";
	}
}
