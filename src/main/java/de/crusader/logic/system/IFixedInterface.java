package de.crusader.logic.system;

import java.util.Arrays;

public class IFixedInterface extends IInterface {
	private static final long serialVersionUID = -8859673050363256738L;
	// Fixed array of the ports of this interface
	private IPort[] ports;

	/**
	 * Called when creating a new instance.
	 */
	public IFixedInterface(IPort... ports) {
		for (int i = 0; i < ports.length; i++) {
			if (ports[i] == null) {
				ports[i] = new IPort();
			}
		}
		this.ports = ports;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.system.IInterface#getMinimumPortCount()
	 */
	@Override
	public int getMinimumPortCount() {
		return ports.length;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.system.IInterface#getMaximumPortCount()
	 */
	@Override
	public int getMaximumPortCount() {
		return ports.length;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.system.IInterface#getCurrentPortCount()
	 */
	@Override
	public int getCurrentPortCount() {
		return ports.length;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.system.IInterface#listPorts()
	 */
	@Override
	public Iterable<IPort> listPorts() {
		return Arrays.asList(ports.clone());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.system.IInterface#getPort(int)
	 */
	@Override
	public IPort getPort(int index) {
		if (index < 0) {
			return null;
		} else if (index >= ports.length) {
			return null;
		}
		return ports[index];
	}
}
