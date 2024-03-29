package model.module;

import java.util.logging.Logger;

import model.module.features.Canal;
import model.module.features.Crater;
import model.module.features.OilWell;
import model.module.features.River;
import model.module.kinds.RootModule;
import model.module.resource.Oil;
import advances.ExampleUnit;
import advances.SimpleBuilding;
import advances.SimpleUnit;

/**
 * A class to create modules when from their moduleIDs.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class ModuleFactory {
	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(ModuleFactory.class
			.getName());

	/**
	 * Constructor.
	 */
	public ModuleFactory() {
		LOGGER.finest("ModuleFactory constructor");
	}

	/**
	 * ModuleID for an ExampleUnit.
	 */
	private static final int EXAMPLEUNIT_ID = 3;
	/**
	 * ModuleID for a SimpleBuilding.
	 */
	private static final int SIMPLEBLDG_ID = 4;
	/**
	 * ModuleID for a Crater landscape feature.
	 */
	private static final int CRATER_ID = 6;
	/**
	 * ModuleID for an OilWell.
	 */
	private static final int OIL_WELL_ID = 8;
	/**
	 * ModuleID for an Oil.
	 */
	private static final int OIL_ID = 9;
	/**
	 * ModuleID for a River.
	 */
	private static final int RIVER_ID = 10;
	/**
	 * ModuleID for a Canal.
	 */
	private static final int CANAL_ID = 11;

	/**
	 * Create a module from a moduleID.
	 * 
	 * FIXME: To reduce cyclomatic complexity, etc., use reflection:
	 * investigate.
	 * 
	 * @param moduleId
	 *            the moduleID.
	 * @return a module of that type
	 */
	public Module createModule(final int moduleId) {
		switch (moduleId) {
		case 0:
			return RootModule.ROOT_MODULE; // NOPMD
		case 2:
			return new SimpleUnit(); // NOPMD
		case EXAMPLEUNIT_ID:
			return new ExampleUnit(); // NOPMD
		case SIMPLEBLDG_ID:
			return new SimpleBuilding(); // NOPMD
		case CRATER_ID:
			return new Crater(); // NOPMD
		case OIL_WELL_ID:
			return new OilWell(); // NOPMD
		case OIL_ID:
			return new Oil(); // NOPMD
		case RIVER_ID:
			return new River(); // NOPMD
		case CANAL_ID:
			return new Canal(); // NOPMD
		default:
			throw new IllegalArgumentException("Unknown module type");
		}
	}
}
