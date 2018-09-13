package de.crusader.logic.elements.special;

import de.crusader.logic.elements.IElement;
import de.crusader.logic.system.IInterface;

public abstract class ISpecialElement extends IElement {
	private static final long serialVersionUID = 4201636334078966539L;

	/**
	 * Called when creating a new instance.
	 */
	public ISpecialElement(IInterface input, IInterface output) {
		super(input, output);
	}

}
