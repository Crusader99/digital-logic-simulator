package de.crusader.screens;

import java.awt.image.BufferedImage;

import de.crusader.Main;
import de.crusader.asynchronous.Scheduler;
import de.crusader.controls.mainmenu.MainMenu;
import de.crusader.controls.modulebox.ModuleBox;
import de.crusader.controls.tabcontrol.TabControl;
import de.crusader.controls.toolbar.Toolbar;
import de.crusader.controls.workpanel.WorkPanel;
import de.crusader.painter.Painter;
import de.crusader.painter.util.IFontSettings;
import de.crusader.screens.api.Docker;
import de.crusader.screens.api.MainScreen;
import de.crusader.screens.controls.tabcontrol.ITabEntry;
import de.crusader.services.CrashReporter;
import de.crusader.services.SoftwareUpdater;
import de.crusader.textures.TextureManager;
import lombok.Getter;

public abstract class Management {
	private Management() {
		// You can not create a new instance of this class
		throw new UnsupportedOperationException();
	}

	/*
	 * The title of the window
	 */
	@Getter
	protected final static String windowTitle = "Digital Logic Simulator (DiLoSim)";

	/*
	 * The scheduler, used for delays, timers or async-tasks.
	 */
	@Getter
	protected final static Scheduler scheduler = WindowManager.getScheduler();

	/*
	 * The basic colors for the graphic components are defined here.
	 */
	@Getter
	protected final static Colors colors = new Colors();

	/*
	 * Size and font for the text are set in this field.
	 */
	@Getter
	protected final static IFontSettings chars = Painter.getFontSettings();

	/*
	 * Allows to resize the window of the program. The Docker will adjust the size
	 * of the graphic components.
	 */
	@Getter
	protected final static Docker docker = WindowManager.getDocker();

	/*
	 * This is the basic/root/main screen. All sub screens are added to this
	 * instance.
	 */
	@Getter
	private static MainScreen mainScreen;

	/*
	 * This is the tab-control for all opened circuits. It's a sub-screen of the
	 * main-screen.
	 */
	@Getter
	private static TabControl tabControl = null;

	/*
	 * Versions-number for this software.
	 */
	@Getter
	protected final static int buildVersion = 5;

	/*
	 * The TexttureManager contains all cached textures for this software.
	 */
	@Getter
	private final static TextureManager textures = new TextureManager();

	/*
	 * This crash reporter sends anonymous logs to the server
	 */
	@Getter
	private final static CrashReporter crashReporter = CrashReporter.init(Main.class);

	/*
	 * Update service to check for new updates
	 */
	@Getter
	private final static SoftwareUpdater updater = new SoftwareUpdater();

	/**
	 * @return - Returns the icon for the window.
	 */
	public final static BufferedImage getWindowIcon() {
		return textures.find("logo");
	}

	/**
	 * @return WorkPanel - Returns this work-panel for the currently selected
	 *         circuit-tab.
	 */
	public final static WorkPanel getWorkPanel() {
		TabControl tabs = getTabControl();
		if (tabs == null) {
			throw new NullPointerException("tabs");
		}
		ITabEntry tab = tabs.getSelectedTab();
		if (tab == null || !(tab instanceof WorkPanel)) {
			return null;
		}
		return (WorkPanel) tab;
	}

	/*
	 * Creates a new "work-panel"-tab and adds it to the tab-control.
	 */
	public final static WorkPanel createNewTab() {
		WorkPanel panel;
		getTabControl().openTab(panel = new WorkPanel());
		return panel;
	}

	/*
	 * This method is called on start to initialize the window and all sub screens.
	 */
	public final static void start() {
		// Set some window-properties
		WindowManager wm = new WindowManager();
		wm.setTitle(getWindowTitle());
		wm.setIcon(getWindowIcon());
		wm.setUndecorated(false);

		// Add event handlers for the window
		ScreenEventHandler handlers = new ScreenEventHandler();
		wm.setWindowCloseHandler(handlers);
		wm.setFileDropHandler(handlers);
		wm.setFrameUpdateHandler(handlers);

		// Create and show the screen
		MainScreen s = mainScreen = wm.createAndShow();

		// Add the sub screens
		s.getSubScreens().add(new ModuleBox());
		s.getSubScreens().add(new MainMenu());
		s.getSubScreens().add(new Toolbar());
		s.getSubScreens().add(tabControl = new TabControl());

		// Opens a single clean tab
		createNewTab();
	}
}
