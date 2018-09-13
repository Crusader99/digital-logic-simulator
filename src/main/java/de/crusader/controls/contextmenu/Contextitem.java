package de.crusader.controls.contextmenu;

import java.awt.image.BufferedImage;

import de.crusader.screens.Management;
import de.crusader.screens.controls.kontextmenu.ExtendedKontextItem;
import de.crusader.textures.TextureManager;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class Contextitem extends ExtendedKontextItem {

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.controls.kontextmenu.ExtendedKontextItem#getIcon()
	 */
	@Override
	public BufferedImage getIcon() {
		BufferedImage cached = super.getIcon();
		if (cached != null) {
			// Return image if found
			return cached;
		}

		TextureManager icons = Management.getTextures();
		if (icons == null) {
			// Return null if the textures are currently not loaded
			return null;
		}

		// Load image from cache
		super.setIcon(cached = icons.find(this.getClass().getSimpleName()));
		return cached;
	}

}
