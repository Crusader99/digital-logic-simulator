package de.crusader;

import java.awt.EventQueue;

import de.crusader.screens.Management;

public class Main {

	/*
	 * This is the main method
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			// Run initialization
			Management.start();
		});
	}

}
