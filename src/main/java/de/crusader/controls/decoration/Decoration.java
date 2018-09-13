package de.crusader.controls.decoration;

import java.awt.Color;
import java.awt.image.BufferedImage;

import de.crusader.screens.Management;
import de.crusader.screens.controls.decoration.GuiDecoration;

public class Decoration extends GuiDecoration {

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
	 * @see de.crusader.screens.controls.decoration.GuiDecoration#loadControlImages()
	 */
	@Override
	public BufferedImage loadControlImages() {
		return Management.getTextures().find("lookandfeel");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.controls.decoration.GuiDecoration#loadIconImage()
	 */
	@Override
	public BufferedImage loadIconImage() {
		return Management.getTextures().find("logo");
	}

}