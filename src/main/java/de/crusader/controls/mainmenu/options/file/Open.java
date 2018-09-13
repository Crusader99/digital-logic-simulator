package de.crusader.controls.mainmenu.options.file;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import de.crusader.circuit.config.ConfigSystem;
import de.crusader.controls.contextmenu.Contextitem;
import de.crusader.controls.workpanel.WorkPanel;
import de.crusader.screens.Management;
import de.crusader.screens.WindowManager;

public class Open extends Contextitem {
	/**
	 * (non-Javadoc)
	 *
	 * @see de.crusader.screens.interfaces.IClickable#onClick()
	 */
	@Override
	public void onClick() {
		Management.getScheduler().execute(() -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Schaltung \u00F6ffnen...");
			chooser.setMultiSelectionEnabled(true);
			chooser.setAutoscrolls(true);
			chooser.setFileFilter(getFileFilter());
			if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File[] files = chooser.getSelectedFiles();
			String currentFile = new String();
			try {
				for (File f : files) {
					currentFile = f.getName();
					WorkPanel panel = Management.createNewTab();
					ConfigSystem.loadFile(panel, f);
				}
			} catch (Exception ex) {
				WindowManager.getDialogs().showError("Fehler beim Lesen (" + currentFile + ")", ex);
			}
		});
	}

	/**
	 * Open all files from parameter in tab control
	 */
	public void load(File[] files) {
		String currentFile = new String();
		try {
			for (File f : files) {
				currentFile = f.getName();
				if (Management.getTabControl().getOpenedTabs().size() > 20) {
					WindowManager.getDialogs().showError("Zu viele offene Schaltungen",
							new IndexOutOfBoundsException("Limit: 20"));
					return;
				}
				WorkPanel panel = Management.createNewTab();
				ConfigSystem.loadFile(panel, f);
			}
		} catch (Exception ex) {
			WindowManager.getDialogs().showError("Fehler beim Lesen (" + currentFile + ")", ex);
		}
	}

	/*
	 * Returns a file filter that finds all files with '*.digi'
	 */
	private FileFilter getFileFilter() {
		return new FileFilter() {
			@Override
			public String getDescription() {
				return "Schaltung (*.digi)";
			}

			@Override
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".digi");
			}
		};
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.crusader.screens.interfaces.IDisplayName#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return "\u00F6ffnen";

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
		return "Strg+O";
	}
}
