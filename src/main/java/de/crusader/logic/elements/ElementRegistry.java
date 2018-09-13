package de.crusader.logic.elements;

import java.util.ArrayList;
import java.util.List;

import de.crusader.logic.elements.converter.ConverterBCDDecoder;
import de.crusader.logic.elements.flipflop.FlipflopD;
import de.crusader.logic.elements.flipflop.FlipflopEL;
import de.crusader.logic.elements.flipflop.FlipflopJK;
import de.crusader.logic.elements.flipflop.FlipflopJKwithRS;
import de.crusader.logic.elements.flipflop.FlipflopNRNS;
import de.crusader.logic.elements.flipflop.FlipflopRS;
import de.crusader.logic.elements.flipflop.FlipflopSL;
import de.crusader.logic.elements.flipflop.FlipflopT;
import de.crusader.logic.elements.logicgate.LogicGateAND;
import de.crusader.logic.elements.logicgate.LogicGateNAND;
import de.crusader.logic.elements.logicgate.LogicGateNOR;
import de.crusader.logic.elements.logicgate.LogicGateNOT;
import de.crusader.logic.elements.logicgate.LogicGateNOTsmall;
import de.crusader.logic.elements.logicgate.LogicGateOR;
import de.crusader.logic.elements.logicgate.LogicGateXNOR;
import de.crusader.logic.elements.logicgate.LogicGateXOR;
import de.crusader.logic.elements.special.Special7Segment;
import de.crusader.logic.elements.special.SpecialLamp;
import de.crusader.logic.elements.special.SpecialSwitch;
import de.crusader.logic.elements.special.SpecialText;

public class ElementRegistry {
	// This list of all elements is used to show them on the module-box
	public static final List<Class<? extends IElement>> ELEMENTS = new ArrayList<>();

	static {
		// Special
		ELEMENTS.add(SpecialText.class);
		ELEMENTS.add(SpecialSwitch.class);
		ELEMENTS.add(SpecialLamp.class);
		ELEMENTS.add(Special7Segment.class);

		// Logic Gates
		ELEMENTS.add(LogicGateAND.class);
		ELEMENTS.add(LogicGateNAND.class);
		ELEMENTS.add(LogicGateOR.class);
		ELEMENTS.add(LogicGateNOR.class);
		ELEMENTS.add(LogicGateXOR.class);
		ELEMENTS.add(LogicGateXNOR.class);
		ELEMENTS.add(LogicGateNOT.class);
		ELEMENTS.add(LogicGateNOTsmall.class);

		// Flip Flops
		ELEMENTS.add(FlipflopRS.class);
		ELEMENTS.add(FlipflopNRNS.class);
		ELEMENTS.add(FlipflopSL.class);
		ELEMENTS.add(FlipflopEL.class);
		ELEMENTS.add(FlipflopT.class);
		ELEMENTS.add(FlipflopD.class);
		ELEMENTS.add(FlipflopJK.class);
//		ELEMENTS.add(FlipflopJK_MS.class); Disabled because not finished yet
		ELEMENTS.add(FlipflopJKwithRS.class);

		// Converters
		ELEMENTS.add(ConverterBCDDecoder.class);
	}

}
