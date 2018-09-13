package de.crusader.analytics;

import java.awt.Dimension;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import de.crusader.controls.modulebox.ModuleBox;
import de.crusader.controls.workpanel.WorkPanel;
import de.crusader.screens.Management;
import de.crusader.screens.WindowManager;
import de.crusader.screens.api.MainScreen;
import de.crusader.screens.controls.tabcontrol.GuiTabControl;
import de.crusader.screens.enums.EnumScreenState;
import de.crusader.screens.interfaces.handler.IWindowCloseHandler;
import lombok.Getter;

public class PerformanceAnalytics implements IWindowCloseHandler {
	public final static float MAX_FPS = 100f;
	private static GuiTabControl control;
	protected static Map<Class<?>, StatisticsScreen> stats = new ConcurrentHashMap<>();
	private static MainScreen mainScreen = null;
	@Getter
	private static Timer timer = null;

	/*
	 * Creates a new window with performance statistics
	 */
	public static synchronized void start() {
		if (mainScreen != null) {
			return;
		}

		// Configure window
		PerformanceAnalytics instance = new PerformanceAnalytics();
		WindowManager window = new WindowManager();
		window.setTitle(Management.getWindowTitle() + " - Performance Analyse");
		window.setIcon(Management.getWindowIcon());
		window.setScreenState(EnumScreenState.MAXIMIZED);
		window.setDefaultSize(new Dimension(800, 300));
		window.setWindowCloseHandler(instance);
		window.getSubScreens().add(control = new StatisticsTabControl());

		// Add tabs
		stats.put(WorkPanel.class,
				new StatisticsScreen("Durchschnittliche Repaint-Zeit in ns f\u00FCr den Arbeitsbereich", false));
		stats.put(ModuleBox.class,
				new StatisticsScreen("Durchschnittliche Repaint-Zeit in ns f\u00FCr die Auswahlbox", false));
		stats.put(All.class, new StatisticsScreen("Fames/Sekunde des kompletten GUI", true));
		for (Entry<Class<?>, StatisticsScreen> e : stats.entrySet()) {
			control.openTab(new StatisticsTab(e.getKey().getSimpleName(), e.getValue()));
		}

		// Show GUI
		mainScreen = window.createAndShow();

		if (timer == null) {
			// This timer updates the values
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					synchronized (PerformanceAnalytics.getTimer()) {
						for (Entry<Class<?>, StatisticsScreen> e : stats.entrySet()) {
							e.getValue().setCurrentValue(e.getValue().getCurrentFrameUpdates().getAndSet(0) / MAX_FPS);
						}
					}
				}
			}, 0L, 1_000L);
		}
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.crusader.screens.interfaces.handler.IWindowCloseHandler#onCloseWindow()
	 */
	@Override
	public void onCloseWindow() {
		MainScreen mainScreen = PerformanceAnalytics.mainScreen;
		if (mainScreen == null) {
			return;
		}
		mainScreen.remove();
		PerformanceAnalytics.mainScreen = null;
	}

	/*
	 * This class represents performance of the complete system
	 */
	public static class All {
	}
}
