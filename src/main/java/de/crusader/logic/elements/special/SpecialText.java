package de.crusader.logic.elements.special;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import de.crusader.controls.workpanel.WorkPanel;
import de.crusader.logic.system.IFixedInterface;
import de.crusader.logic.system.IPort;
import de.crusader.painter.Painter;
import de.crusader.painter.util.EnumCenteredType;
import de.crusader.screens.Management;
import de.crusader.screens.WindowManager;
import de.crusader.screens.enums.EnumDialogType;

public class SpecialText extends ISpecialElement {
	private static final long serialVersionUID = -2949503998384088423L;

	/*
	 * The text content of this text element
	 */
	private String textContent;

	/*
	 * Time stamp of the last click
	 */
	private transient long lastClick = 0;

	/**
	 * Called when creating a new instance.
	 */
	public SpecialText() {
		// Initialize input and output ports
		super(new IFixedInterface(new IPort[0]), new IFixedInterface(new IPort[0]));
		// Set the currently displayed text
		this.textContent = getName();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#getName()
	 */
	@Override
	public String getName() {
		return "Textfield";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#update()
	 */
	@Override
	public void update() {
		// Do nothing
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.circuit.api.IPainter#draw(de.crusader.painter.Painter,
	 *      java.awt.Rectangle)
	 */
	@Override
	public void draw(Painter p, Rectangle rec) {
		// Draw the text in the text area
		p.createString().text(textContent).color(Color.BLACK).rectangle(rec).filled(true)
				.centered(EnumCenteredType.BOTH).draw();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#click()
	 */
	@Override
	public void click() {
		// Wait for click cool down
		long now = System.currentTimeMillis();
		if (now - lastClick < 1000) {
			return;
		}
		lastClick = now;

		// Show input dialog for the new text
		String result = WindowManager.getDialogs().showInputBox("Textfeld bearbeiten", "Bitte gebe den Text hier ein:",
				EnumDialogType.QUESTION, textContent);

		// Normalize text result
		if (result == null) {
			return;
		} else {
			result = result.trim();
		}

		WorkPanel panel = Management.getWorkPanel();
		if (result.isEmpty()) {
			// Remove if the new text is empty
			panel.getObjects().remove(getWorkObject());
		} else {
			// Update the content
			setTextContent(result);
		}

		// Show that there are unsaved changes
		panel.markUnsavedChanges();
	}

	/**
	 * Sets the text content and updates the cache
	 */
	public void setTextContent(String txt) {
		this.textContent = txt;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#getDefaultSize()
	 */
	@Override
	public Dimension getDefaultSize() {
		return new Dimension(this.textContent.length() * Management.getChars().getWidth(),
				Management.getChars().getHeight());
	}
}