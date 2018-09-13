package de.crusader.circuit.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.crusader.screens.Management;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfigDetails implements Serializable {
	/*
	 * This are some settings which will be saved in configuration
	 */

	private static final long serialVersionUID = -5321383772925231103L;
	private int buildVersion = Management.getBuildVersion();
	private List<String> creators = new ArrayList<>(Arrays.asList(System.getProperty("user.name")));
	private long timestampCreated = System.currentTimeMillis();
	private long timestampEdited = timestampCreated;
	private int zoom = 2;
}
