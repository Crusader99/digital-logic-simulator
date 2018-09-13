package de.crusader.controls.workpanel;

import de.crusader.logic.system.IPort;
import lombok.Getter;
import lombok.Setter;

public enum EnumDragType {
	MOVE_OBJECTS, CREATE_LINK;

	/**
	 * Cache of the current port
	 */
	@Getter
	@Setter
	private IPort currentPort = null;
}
