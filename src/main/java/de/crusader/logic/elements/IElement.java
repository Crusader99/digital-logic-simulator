package de.crusader.logic.elements;

import java.awt.Dimension;
import java.io.Closeable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.crusader.circuit.api.IPainter;
import de.crusader.controls.workpanel.IWorkObject;
import de.crusader.logic.system.IInterface;
import de.crusader.logic.system.IPort;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class IElement implements IPainter, Serializable, Closeable {
	private static final long serialVersionUID = -3337926856918583740L;

	@Setter
	private IWorkObject workObject;

	// This interfaces contain the ports
	private IInterface input, output;

	/*
	 * Returns the name of this element, for example: "JK-Flipflop"
	 */
	public abstract String getName();

	/*
	 * Called to update the output port based on the input ports
	 */
	public abstract void update();

	/**
	 * @return - Returns the default size if this element. The zoom-factor can
	 *         change the final size
	 */
	public abstract Dimension getDefaultSize();

	/**
	 * Called when creating a new instance.
	 */
	public IElement(IInterface input, IInterface output) {
		this.input = input;
		this.input.setOwner(this);

		this.output = output;
		this.output.setOwner(this);
	}

	/*
	 * Lists all ports from in- and output interfaces.
	 */
	public Iterable<IPort> listAllPorts() {
		List<IPort> list = new ArrayList<>();
		list.addAll((List<IPort>) getInput().listPorts());
		list.addAll((List<IPort>) getOutput().listPorts());
		return list;
	}

	/*
	 * Called on right mouse click hover this element
	 */
	public void click() {
		// Do nothing, can be overwritten by subclasses
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() {
		this.input.close();
		this.output.close();
	}
}
