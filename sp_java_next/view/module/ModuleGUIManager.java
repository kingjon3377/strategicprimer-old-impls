package view.module;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import model.module.Module;
import model.module.kinds.Fortress;
import model.module.kinds.RootModule;
import utils.StringImage;

/**
 * Manages GUIs for particular modules.
 * 
 * @author Jonathan Lovelace
 * 
 */
public final class ModuleGUIManager {
	/**
	 * Do not instantiate.
	 */
	private ModuleGUIManager() {
		// Don't instantiate
	}

	/**
	 * A mapping from Module-IDs to Images.
	 */
	private static final Map<Integer, StringImage> MODULE_ID_MAP = new HashMap<Integer, StringImage>();
	/**
	 * A mapping from UUIDs to Images.
	 */
	private static final Map<Long, StringImage> UUID_MAP = new HashMap<Long, StringImage>();

	/**
	 * Get an Image for a Module. (The last part of the ternary operator returns
	 * null if the module isn't in either Map.)
	 * 
	 * @param module
	 *            the module to get an image for
	 * @return an image for that module
	 */
	public static Image getImage(final Module module) {
		return UUID_MAP.containsKey(Long.valueOf(module.getUuid())) ? UUID_MAP.get(
				Long.valueOf(module.getUuid())).getImage() : MODULE_ID_MAP.get(
				Integer.valueOf(module.getModuleID())).getImage();
	}

	/**
	 * Add an image for a Module.
	 * 
	 * @param module
	 *            the module
	 * @param filename
	 *            the image filename
	 */
	public static void addImage(final Module module, final String filename) {
		if (checkAddImageInput(module, filename)) {
			if (UUID_MAP.containsKey(Long.valueOf(module.getUuid()))
					&& !UUID_MAP.get(Long.valueOf(module.getUuid())).getString().equals(
							filename)) {
				throw new IllegalStateException("Already had an image for that module");
			} else if (MODULE_ID_MAP.containsKey(Integer.valueOf(module.getModuleID()))
					&& !UUID_MAP.containsKey(Long.valueOf(module.getUuid()))) {
				UUID_MAP.put(Long.valueOf(module.getUuid()), new StringImage(filename));
			} else if (!MODULE_ID_MAP.containsKey(Integer.valueOf(module.getModuleID()))) {
				MODULE_ID_MAP.put(Integer.valueOf(module.getModuleID()), new StringImage(
						filename));
			}
		}
	}

	/**
	 * Checks whether addImage() should go ahead.
	 * 
	 * @param module
	 *            The module
	 * @param filename
	 *            The image filename
	 * @return True if the module is non-null and the image isn't already
	 *         associated with this module
	 */
	private static boolean checkAddImageInput(final Module module, final String filename) {
		return module != null
				&& (!MODULE_ID_MAP.containsKey(Integer.valueOf(module.getModuleID())) || !MODULE_ID_MAP
						.get(Integer.valueOf(module.getModuleID())).getString().equals(
								filename))
				&& ((!UUID_MAP.containsKey(Long.valueOf(module.getUuid()))) || !UUID_MAP
						.get(Long.valueOf(module.getUuid())).getString().equals(filename));
	}

	/**
	 * @param module
	 *            the module
	 * @return the filename of its image. (For serialization purposes.)
	 */
	public static String getFilename(final Module module) {
		return UUID_MAP.containsKey(Long.valueOf(module.getUuid())) ? UUID_MAP.get(
				Long.valueOf(module.getUuid())).getString() : MODULE_ID_MAP
				.containsKey(Integer.valueOf(module.getModuleID())) ? MODULE_ID_MAP.get(
				Integer.valueOf(module.getModuleID())).getString() : "";
	}

	static {
		addImage(RootModule.ROOT_MODULE, "/clear.png");
		addImage(new Fortress(null, null, ""), "/fortress.png");
	}
}
