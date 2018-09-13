package de.crusader.screens;

import java.io.File;

import de.crusader.analytics.Analytics;
import de.crusader.analytics.PerformanceAnalytics;
import de.crusader.controls.mainmenu.options.file.Open;
import de.crusader.controls.mainmenu.options.help.Exit;
import de.crusader.screens.interfaces.handler.IFileDropHandler;
import de.crusader.screens.interfaces.handler.IWindowCloseHandler;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ScreenEventHandler implements IFileDropHandler, IWindowCloseHandler, Runnable {
	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.handler.IWindowCloseHandler#onCloseWindow()
	 */
	@Override
	public void onCloseWindow() {
		// Open exit dialog if there are some unsaved changes
		new Exit().onClick();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.handler.IFileDropHandler#onDropFiles(java.io.File[])
	 */
	@Override
	public void onDropFiles(File... files) {
		// Load the new files via the open-menu
		new Open().load(files);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// Called when frames will be updates
		try (Analytics a = new Analytics(PerformanceAnalytics.All.class)) {
			// Do nothing
		}
	}

}