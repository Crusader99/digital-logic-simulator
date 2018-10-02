package de.crusader.textures;

import static de.crusader.helpers.FileUtils.removeExtension;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;

import de.crusader.helpers.streams.NonClosableInputStream;
import de.crusader.services.CrashReporter;
import lombok.Getter;

public class TextureManager {
	/*
	 * Cache for all loaded textures
	 */
	@Getter
	private Map<String, BufferedImage> cache = new HashMap<>();

	/**
	 * Called when creating a new instance.
	 */
	public TextureManager() {
		// Read the zip file with all png-images
		this.readZip(getClass().getResourceAsStream("/textures.zip"));
	}

	/**
	 * Reads all *.png images from a .zip file and write them in the cache
	 */
	private void readZip(InputStream in) {
		if (in == null) {
			throw new NullPointerException();
		}

		// Open file
		try (ZipInputStream zip = new ZipInputStream(new BufferedInputStream(in))) {

			// Get all items
			ZipEntry entry;
			while ((entry = zip.getNextEntry()) != null) {
				if (entry.isDirectory()) {
					continue;
				}

				// Check if file is a png
				if (!entry.getName().toLowerCase().endsWith(".png")) {
					continue;
				}

				// Read image and write to cache
				try (InputStream stream = new NonClosableInputStream(zip)) {
					readImage(entry.getName(), stream);
				}
			}
		} catch (Exception e) {
			// Report error
			CrashReporter.getInstance().reportCrash(new IOException("Error while loading textures", e));
		}
	}

	/**
	 * Reads the InputStream an put the image in the cache
	 */
	private void readImage(String name, InputStream stream) {
		try {
			// Read image
			BufferedImage img = ImageIO.read(stream);

			// Calculate name for cache
			String cacheName = new String();
			for (char chr : removeExtension(name).toLowerCase().toCharArray()) {
				if (chr < 'a' || chr > 'z') {
					continue;
				}
				cacheName += chr;
			}

			// Save image reference in cache
			cache.put(cacheName, img);
		} catch (Exception ex) {
			// Report error
			CrashReporter.getInstance().reportCrash(new IOException("Exception while reading image from stream", ex));
		}
	}

	/**
	 * Searches for a named image.
	 * 
	 * @param String - Name of the image
	 * 
	 * @return BufferedImage or null if the image does not exist
	 */
	public BufferedImage find(String name) {
		// Get image from cache
		return cache.get(name.toLowerCase());
	}
}
