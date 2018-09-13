package de.crusader.logic.elements.logicgate;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import de.crusader.funktion.api.IValue;
import de.crusader.logic.elements.IElement;
import de.crusader.logic.system.IInterface;
import de.crusader.operators.api.IOperator;
import de.crusader.painter.Painter;
import de.crusader.painter.util.EnumCenteredType;
import lombok.Getter;

@Getter
public abstract class ILogicGateElement extends IElement {
	private static final long serialVersionUID = -1333029065857875662L;
	// The underlying operator of this logic gate
	private IOperator operator;

	/**
	 * Called when creating a new instance.
	 */
	public ILogicGateElement(IOperator operator, IInterface input, IInterface output) {
		super(input, output);
		this.operator = operator;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#getName()
	 */
	@Override
	public String getName() {
		return getOperator().getName() + "-Gatter";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#update()
	 */
	@Override
	public void update() {
		// Calculate the output value
		IValue result = getOperator().execute(getInput().getValues());
		// Updates the output value
		getOutput().updateValues(result);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.circuit.api.IPainter#draw(de.crusader.painter.Painter,
	 *      java.awt.Rectangle)
	 */
	@Override
	public void draw(Painter p, Rectangle rec) {
		// Draw background color
		p.createRectangle().filled(true).color(Color.PINK).rectangle(rec).draw();

		// Border for the text area
		int border = (rec.width + rec.height) >> 4;

		// Rectangle for the text
		Rectangle area = new Rectangle(rec.x + border, rec.y + border, rec.width - (border << 1),
				rec.height - (border << 1));

		// Draw the text-symbol
		p.createString().text(operator.getDisplaySymbol()).rectangle(area).centered(EnumCenteredType.BOTH).filled(true)
				.draw();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.logic.elements.IElement#getDefaultSize()
	 */
	public Dimension getDefaultSize() {
		return new Dimension(10, 10);
	}
}
