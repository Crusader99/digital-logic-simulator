package de.crusader.logic.system;

import java.awt.Point;
import java.io.Closeable;
import java.io.Serializable;

import de.crusader.controls.workpanel.IWorkObject;
import de.crusader.funktion.api.IValue;
import de.crusader.logic.elements.IElement;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public abstract class IInterface implements Serializable, Closeable {
	private static final long serialVersionUID = 3744658299549177082L;
	// Owner for this interface
	private IElement owner;

	/*
	 * Sets the owner for this interface.
	 */
	public void setOwner(IElement owner) {
		this.owner = owner;
		for (IPort port : listPorts()) {
			port.setOwner(this);
		}
	}

	/**
	 * @return - Returns the values for all ports of this interface.
	 */
	public IValue[] getValues() {
		IValue[] values = new IValue[getCurrentPortCount()];
		for (int i = 0; i < values.length; i++) {
			IPort port = getPort(i);
			if (port == null) {
				values[i] = IValue.create(false);
			} else {
				values[i] = port.getValue();
			}
		}
		return values;
	}

	/**
	 * @return - Returns the binary value for all ports of this interface.
	 */
	public int getBinaryValues() {
		return getBinaryValues(true);
	}

	/**
	 * @return - Returns the binary value for all ports of this interface.
	 * 
	 * @see https://www.itwissen.info/Big-Endian-Format-big-endian.html
	 */
	public int getBinaryValues(boolean bigEndian) {
		int temp = 0x0;
		int amount = getCurrentPortCount();
		for (int i = 0; i < amount; i++) {
			// Search for port
			IPort port = getPort(i);
			if (port == null) {
				break;
			}

			// Ignore if value is not "1"
			if (!port.getValue().booleanValue()) {
				continue;
			}

			// Calculate the index of the bit
			int bit;
			if (bigEndian) {
				bit = amount - i - 1;
			} else {
				bit = i;
			}

			temp |= (0x1 << bit);
		}
		return temp;
	}

	/**
	 * @return - Returns the string value for all ports of this interface.
	 */
	public String getStringValues() {
		String values = new String();
		for (IValue val : getValues()) {
			values += val.charValue();
		}
		return values;
	}

	/*
	 * Sets the new values for the ports of this interface.
	 */
	public void updateBinaryValues(int temp) {
		int amount = getCurrentPortCount();
		for (int i = 0; i < amount; i++) {
			// Calculate the bit index
			int bit = amount - i - 1;

			// Get the value at the bit index
			int val = (temp >> bit) & 0x1;

			// Search port...
			IPort port = getPort(i);
			if (port == null) {
				// Return if not found
				break;
			}

			// Update the value of this port
			port.getValue().set(IValue.create(val));
		}
	}

	/*
	 * Sets the new values for the ports of this interface.
	 */
	public void updateValues(IValue... val) {
		for (int i = 0; i < val.length; i++) {
			IPort port = getPort(i);
			if (port == null) {
				break;
			}
			port.getValue().set(val[i]);
		}
	}

	/**
	 * @return - Returns whether this is an in- or output interface.
	 */
	public EnumInterfaceType getType() {
		if (owner.getInput() == this) {
			return EnumInterfaceType.INPUT;
		} else if (owner.getOutput() == this) {
			return EnumInterfaceType.OUTPUT;
		} else {
			throw new RuntimeException("Unknown interface type!");
		}
	}

	/*
	 * Calculates the total y position for a port by the given index.
	 */
	public int getYByIndex(int index) {
		IWorkObject obj = getOwner().getWorkObject();
		float ratio = (index + 1f) / (getCurrentPortCount() + 1);
		return obj.getY() + (int) (obj.getHeight() * ratio);
	}

	/*
	 * Calculates the index for a port by the given y position.
	 */
	public int getIndexByY(int y) {
		IWorkObject obj = getOwner().getWorkObject();
		int pos = y - obj.getY();
		if (pos < 0) {
			return -1;
		}
		int index = pos * getCurrentPortCount() / obj.getHeight();
		if (index < 0 || index >= getCurrentPortCount()) {
			return -1;
		}
		return index;
	}

	/*
	 * Returns the total y position for this interface.
	 */
	public int getPositionX() {
		IWorkObject obj = getOwner().getWorkObject();
		if (getType() == EnumInterfaceType.INPUT) {
			return obj.getX();
		} else {
			return obj.getX() + obj.getWidth();
		}
	}

	/**
	 * Tries to find a matching port by the given point.
	 * 
	 * @return - Returns null if there was no port found!
	 */
	public IPort findPort(Point point) {
		IWorkObject obj = getOwner().getWorkObject();
		int toleranceX = obj.getWidth() >> 2;
		if (Math.abs(getPositionX() - point.x) > toleranceX) {
			return null;
		}
		int index = getIndexByY(point.y);
		if (index == -1) {
			return null;
		}
		return getPort(index);
	}

	/**
	 * @return - Returns true if the given value had a flank since the last
	 *         update was called.
	 */
	public boolean hasFlank(IValue val) {
		int hash = System.identityHashCode(val);
		for (IPort port : listPorts()) {
			if (System.identityHashCode(port.getValue()) == hash) {
				return port.getFlank().get();
			}
		}
		return false;
	}

	/*
	 * Count of minimum required ports
	 */
	public abstract int getMinimumPortCount();

	/*
	 * Count of possible maximum ports
	 */
	public abstract int getMaximumPortCount();

	/*
	 * Count of the current ports
	 */
	public abstract int getCurrentPortCount();

	/*
	 * Allows to list the ports
	 */
	public abstract Iterable<IPort> listPorts();

	/*
	 * Returns the port at the given index
	 */
	public abstract IPort getPort(int index);

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() {
		for (IPort port : listPorts()) {
			port.disconnect();
		}
	}
}
