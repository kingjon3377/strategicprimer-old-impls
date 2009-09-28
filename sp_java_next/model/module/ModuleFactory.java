package model.module;

import java.util.logging.Logger;

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
			return RootModule.getRootModule();
		case 2:
			return new SimpleUnit();
		default:
			throw new IllegalArgumentException("Unknown module type");
		}
	}
}
