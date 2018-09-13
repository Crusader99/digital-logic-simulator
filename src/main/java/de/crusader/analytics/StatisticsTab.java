package de.crusader.analytics;

import de.crusader.screens.controls.tabcontrol.ExtendedTabEntry;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StatisticsTab extends ExtendedTabEntry {

	/*
	 * Attributes of a statistic tab
	 */

	private String name;
	private StatisticsScreen screen;

}
