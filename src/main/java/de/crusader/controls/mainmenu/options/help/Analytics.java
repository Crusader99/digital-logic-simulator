package de.crusader.controls.mainmenu.options.help;

import de.crusader.analytics.PerformanceAnalytics;
import de.crusader.controls.contextmenu.Contextitem;
import de.crusader.helpers.StringUtils;

public class Analytics extends Contextitem {
	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.IClickable#onClick()
	 */
	@Override
	public void onClick() {
		try {
			PerformanceAnalytics.start();
		}catch(Throwable t) {
			System.out.println(StringUtils.exceptionToFullString(t));
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.IDisplayName#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return "Performance-Analyse";

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
