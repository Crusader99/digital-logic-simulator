package de.crusader.controls.workpanel;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import lombok.Getter;

public class ObjectList extends CopyOnWriteArrayList<IWorkObject> {
	private static final long serialVersionUID = -4045451146137947631L;
	// Amount of saved history data
	private static final int ITEMS = 0x20;

	private ObjectList previous = null;
	@Getter
	private ZoomManagement zoom;

	/**
	 * Called when creating a new instance.
	 */
	public ObjectList(ZoomManagement zoom) {
		super();
		this.zoom = zoom;
	}

	/**
	 * @param previous - list of objects
	 */
	public ObjectList(ObjectList previous) {
		super();
		this.zoom = previous.getZoom().clone();
		CloneSystem system = new CloneSystem(previous);
		super.addAll(system.cloneAll());
		setPrevious(previous);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.CopyOnWriteArrayList#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> collection) {
		int notFound = 0;
		for (Object e : collection) {
			if (remove(e) == false) {
				notFound++;
			}
		}
		return notFound == 0;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.CopyOnWriteArrayList#remove(int)
	 */
	@Override
	public IWorkObject remove(int index) {
		IWorkObject removed = super.remove(index);
		if (removed != null) {
			removed.getElement().close();
		}
		return removed;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.CopyOnWriteArrayList#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object obj) {
		if (obj instanceof IWorkObject) {
			IWorkObject wo = (IWorkObject) obj;
			wo.getElement().close();
		}
		return super.remove(obj);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.CopyOnWriteArrayList#clear()
	 */
	@Override
	public void clear() {
		for (IWorkObject obj : this) {
			obj.getElement().close();
		}
		super.clear();
	}

	/*
	 * Returns the previous ObjectList
	 */
	public ObjectList getPrevious() {
		return previous;
	}

	/*
	 * Sets the previous ObjectList
	 */
	public void setPrevious(ObjectList list) {
		previous = list;
		previous.cleanup(0);
	}

	/*
	 * Deep search for previous ObjectList
	 */
	public ObjectList getByIndex(int index) {
		if (index <= 0 || previous == null) {
			return this;
		}
		return previous.getByIndex(index - 1);
	}

	/*
	 * Cleanup of previous ObjectLists
	 */
	private void cleanup(int index) {
		index++;
		if (previous == null) {
			return;
		}
		if (index > ITEMS) {
			previous = null;
		} else {
			previous.cleanup(index);
		}
	}
}
