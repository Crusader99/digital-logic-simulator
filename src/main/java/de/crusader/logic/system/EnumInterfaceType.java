package de.crusader.logic.system;

import de.crusader.helpers.EnumDirection;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumInterfaceType {
	INPUT(1, EnumDirection.RIGHT), OUTPUT(20, EnumDirection.LEFT);

	// Limit for connections to same port
	private final int maxConnectionsToSamePort;

	// Direction which aims to the middle of the element
	private final EnumDirection directionToElement;
}
