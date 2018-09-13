package de.crusader.controls.workpanel;

import java.util.List;

import de.crusader.circuit.config.ConfigDetails;
import de.crusader.screens.Management;

public class ObjectsHistory {
	private ObjectList currentObjects;
	// Current history position index
	private int index = 0;
	// Time-stamp of creation
	private long created;
	// Name of creators
	private List<String> creators;

	/**
	 * Called when creating a new instance.
	 */
	public ObjectsHistory(ZoomManagement zoom) {
		this.currentObjects = new ObjectList(zoom);
		ConfigDetails details = new ConfigDetails();
		created = details.getTimestampCreated();
		creators = details.getCreators();
	}

	/*
	 * Returns the currently valid saved objects
	 */
	public ObjectList getCurrentObjects() {
		return currentObjects.getByIndex(index);
	}

	/**
	 * @return boolean - Returns true if there are things that can be undone
	 * @see this.previous();
	 */
	public boolean hasPrevious() {
		return getCurrentObjects().getPrevious() != null;
	}

	/*
	 * This is an easy undo function
	 */
	public void previous() {
		if (getCurrentObjects().getPrevious() == null) {
			return;
		}
		if (!hasNext()) {
			index++;
		}
		index++;
	}

	/**
	 * @return boolean - Returns true if there are things that can be redone
	 * @see this.next();
	 */
	public boolean hasNext() {
		return index > 0;
	}

	/*
	 * This is an easy re-do function
	 */
	public void next() {
		index = Math.max(0, index - (index <= 2 ? 2 : 1));
	}

	/*
	 * Saves the current changes in the history
	 */
	public void makeHistory() {
		ObjectList previous = getCurrentObjects();
		index = 0;
		currentObjects = new ObjectList(previous);
	}

	/*
	 * Create a new ConfigDetails object for the configuration
	 */
	public ConfigDetails getConfigDetails() {
		return new ConfigDetails(Management.getBuildVersion(), creators, created, System.currentTimeMillis(),
				getCurrentObjects().getZoom().getZoomFactor());
	}

	/*
	 * Set some information about the circuit for the configuration
	 */
	public void setConfigDetails(ConfigDetails details) {
		this.created = details.getTimestampCreated();
		this.creators = details.getCreators();
		String user = System.getProperty("user.name");
		if (user == null) {
			user = "?";
		}
		if (!creators.contains(user)) {
			creators.add(user);
		}
		getCurrentObjects().getZoom().setZoomFactor(details.getZoom());
		makeHistory();
	}

	/*
	 * Clears the history-data
	 */
	public void clear() {
		ZoomManagement zoom = getCurrentObjects().getZoom();
		this.currentObjects = new ObjectList(zoom);
		this.index = 0;
	}
}
