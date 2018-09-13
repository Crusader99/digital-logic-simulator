package de.crusader.controls.workpanel;

import java.awt.Point;

import de.crusader.logic.system.IDynamicInterface;
import de.crusader.logic.system.IInterface;
import de.crusader.logic.system.IPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PortPopupListener {
	// The owner of this object
	private final WorkPanel PANEL;

	// The temporary added pop-up port for new connections
	private IPort temporaryAddedPort = null;

	/*
	 * Called when the user tries to connect one element to another element
	 */
	public synchronized void onMouseDragWhileCreateLink(Point mouse, IPort fromPort) {
		if (fromPort == null) {
			// Return if this parameter is missing
			return;
		}

		IInterface iface;
		{ // Find underlying port
			IPort port = PANEL.getPortAtMousePosition(mouse);
			if (port == null) {
				// Return if the mouse is hover no port
				onCreateLinkFinished();
				return;
			}
			iface = port.getOwner();
		}

		{ // Check if a new port is required
			boolean newPortRequired = true;
			for (IPort p : iface.listPorts()) {
				if (p.getCountOfConnectedPorts() <= 0) {
					newPortRequired = false;
					break;
				}
			}
			if (!newPortRequired) {
				// Cancel action
				return;
			}
		}

		// Check if there are more ports than allowed
		if (iface.getMaximumPortCount() <= iface.getCurrentPortCount()) {
			return;
		}

		if (iface.getType() == fromPort.getType()) {
			// Cannot create a link between same interface types
			return;
		}

		if (iface instanceof IDynamicInterface) {
			// Create new port
			IDynamicInterface dyn = (IDynamicInterface) iface;
			IPort p = new IPort();
			p.setOwner(dyn);

			// Add new port to interface
			dyn.getPorts().add(p);
			this.temporaryAddedPort = p;
		}
	}

	/*
	 * Called when the link creation is finished
	 */
	public synchronized void onCreateLinkFinished() {
		IPort port = this.temporaryAddedPort;
		if (port == null) {
			// Return if there is not port currently
			return;
		}

		this.temporaryAddedPort = null;
		IInterface iface = port.getOwner();
		if (!(iface instanceof IDynamicInterface)) {
			return;
		}

		if (port.getCountOfConnectedPorts() > 0) {
			// Return if there are already connections to other ports
			return;
		}

		// Remove port from interface
		IDynamicInterface dyn = (IDynamicInterface) iface;
		dyn.getPorts().remove(port);
	}
}
