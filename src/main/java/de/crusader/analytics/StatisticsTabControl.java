package de.crusader.analytics;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import de.crusader.painter.Painter;
import de.crusader.screens.Management;
import de.crusader.screens.controls.tabcontrol.Cancelable;
import de.crusader.screens.controls.tabcontrol.GuiTabControl;
import de.crusader.screens.controls.tabcontrol.ITabEntry;
import de.crusader.screens.controls.tooltip.GuiTooltip;

public class StatisticsTabControl extends GuiTabControl {

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.controls.tabcontrol.ITabControlEvents#onCloseTab(de.crusader.screens.controls.tabcontrol.ITabEntry,
	 *      de.crusader.screens.controls.tabcontrol.Cancelable)
	 */
	@Override
	public void onCloseTab(ITabEntry t, Cancelable c) {
		// Cancel action and do nothing
		c.setCancelled(true);
		return;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.controls.tabcontrol.ITabControlEvents#onOpenKontextMenu(java.awt.Point)
	 */
	@Override
	public void onOpenKontextMenu(Point arg0) {
		// Do nothing
		return;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.controls.tabcontrol.ITabControlEvents#onPaintEmptyPage(de.crusader.painter.Painter,
	 *      java.awt.Rectangle)
	 */
	@Override
	public void onPaintEmptyPage(Painter arg0, Rectangle arg1) {
		// Do nothing
		return;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.controls.tabcontrol.ITabControlEvents#onSelectedTabChanged(de.crusader.screens.controls.tabcontrol.ITabEntry)
	 */
	@Override
	public void onSelectedTabChanged(ITabEntry arg0) {
		// Do nothing
		return;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.controls.tabcontrol.ITabControlEvents#onTooltipCloseRequest(de.crusader.screens.controls.tabcontrol.ITabEntry)
	 */
	@Override
	public String onTooltipCloseRequest(ITabEntry arg0) {
		// Do nothing
		return null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.controls.tabcontrol.ITabControlEvents#onTooltipUpdate(java.awt.Rectangle,
	 *      java.lang.String)
	 */
	@Override
	public GuiTooltip onTooltipUpdate(Rectangle arg0, String arg1) {
		// Do nothing
		return null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.colors.IFocusColor#getFocusColor()
	 */
	@Override
	public Color getFocusColor() {
		return Management.getColors().getFocus();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.colors.IBackgroundColor#getBackgroundColor()
	 */
	@Override
	public Color getBackgroundColor() {
		return Management.getColors().getBackground();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.interfaces.handler.IUpdateSizeHandler#onUpdateSize()
	 */
	@Override
	public Rectangle onUpdateSize() {
		return Management.getDocker().dock(getParent());
	}

	
	/**
	 * (non-Javadoc)
	 * @see de.crusader.screens.controls.tabcontrol.GuiTabControl#onPaint(de.crusader.painter.Painter)
	 */
	@Override
	public void onPaint(Painter p) {
		ITabEntry selected = getSelectedTab();
		for(ITabEntry entry : getOpenedTabs()){
			if(selected == entry){
				continue;
			}
			// Update statistics in background
			StatisticsTab tab = (StatisticsTab) entry;
			tab.getScreen().drawStats(null);
		}
		super.onPaint(p);
	}
}
