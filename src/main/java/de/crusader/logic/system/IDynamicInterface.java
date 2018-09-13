package de.crusader.logic.system;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.crusader.helpers.MathUtils;
import lombok.Getter;

public class IDynamicInterface extends IInterface {
	private static final long serialVersionUID = -849254994585439365L;

	// Dynamic list of the ports of this interface
	@Getter
	private List<IPort> ports;

	// Count of minimum required ports
	private int min;

	/**
	 * Called when creating a new instance.
	 */
	public IDynamicInterface(int minPorts) {
		this.min = minPorts;
		this.ports = new CopyOnWriteArrayList<>();
		for (int i = 0; i < minPorts; i++) {
			this.ports.add(new IPort());
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.system.IInterface#getMinimumPortCount()
	 */
	@Override
	public int getMinimumPortCount() {
		return min;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.system.IInterface#getMaximumPortCount()
	 */
	@Override
	public int getMaximumPortCount() {
		return Integer.MAX_VALUE;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.system.IInterface#getCurrentPortCount()
	 */
	@Override
	public int getCurrentPortCount() {
		return MathUtils.bounds(ports.size(), getMinimumPortCount(), getMaximumPortCount());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.system.IInterface#listPorts()
	 */
	@Override
	public Iterable<IPort> listPorts() {
		Object[] orginal = ports.toArray();
		IPort[] clone = new IPort[getCurrentPortCount()];
		System.arraycopy(orginal, 0, clone, 0, orginal.length);
		return Arrays.asList(clone);
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
		} else if (index >= ports.size()) {
			return null;
		}
		return ports.get(index);
	}
}
