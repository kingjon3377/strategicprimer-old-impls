package model.module;

import java.util.logging.Logger;

import advances.ExampleUnit;
import advances.SimpleBuilding;
import advances.SimpleUnit;

/**
 * A class to create modules when from their moduleIDs.
 * @author Jonathan Lovelace
 *
 */
public class ModuleFactory {
	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(ModuleFactory.class.getName());
	/**
	 * Constructor
	 */
	public ModuleFactory() {
		LOGGER.finest("ModuleFactory constructor");
	}
	/**
	 * Create a module from a moduleID.
	 * @param moduleId the moduleID.
	 * @return a module of that type
	 */
	public Module createModule(final int moduleId) {
		switch(moduleId) {
		case 0:
			return RootModule.getRootModule(); // NOPMD
		case 2:
			return new SimpleUnit(); // NOPMD
		case 3:
			return new ExampleUnit(); // NOPMD
		case 4:
			return new SimpleBuilding();
		default:
			throw new IllegalArgumentException("Unknown module type");
		}
	}
}
