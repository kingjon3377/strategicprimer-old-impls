package view.map;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import model.module.Module;
import model.module.RootModule;

/**
 * Manages GUIs for particular modules
 * 
 * @author Jonathan Lovelace
 * 
 */
public final class ModuleGUIManager {
	/**
	 * Do not instantiate
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
	 * Get an Image for a Module
	 * 
	 * @param module
	 *            the module to get an image for
	 * @return an image for that module
	 */
	public static Image getImage(final Module module) {
		if (UUID_MAP.containsKey(Long.valueOf(module.getUuid()))) {
			return UUID_MAP.get(Long.valueOf(module.getUuid())).getImage();
		} else if (MODULE_ID_MAP.containsKey(Integer.valueOf(module
				.getModuleID()))) {
			return MODULE_ID_MAP.get(Integer.valueOf(module.getModuleID()))
					.getImage();
		} else {
			return null;
			// throw new
			// IllegalStateException("Default case not implemented yet");
		}
	}

	/**
	 * Add an image for a Module
	 * 
	 * @param module
	 *            the module
	 * @param filename
	 *            the image filename
	 */
	public static void addImage(final Module module, final String filename) {
		if (MODULE_ID_MAP.containsKey(Integer.valueOf(module.getModuleID()))) {
			if (MODULE_ID_MAP.get(Integer.valueOf(module.getModuleID()))
					.getString().equals(filename)
					|| UUID_MAP.containsKey(Long.valueOf(module.getUuid()))) {
				if (!UUID_MAP.get(Long.valueOf(module.getUuid())).getString()
						.equals(filename)) {
					throw new IllegalStateException(
							"Already had an image for that module");
				}
			} else {
				UUID_MAP.put(Long.valueOf(module.getUuid()), new StringImage(
						filename));
			}
		} else {
			MODULE_ID_MAP.put(Integer.valueOf(module.getModuleID()),
					new StringImage(filename));
		}
	}

	/**
	 * @param module
	 *            the module
	 * @return the filename of its image. (For serialization purposes.)
	 */
	public static String getFilename(final Module module) {
		if (UUID_MAP.containsKey(Long.valueOf(module.getUuid()))) {
			System.out.println("Returning GUI by moduleID");
			return UUID_MAP.get(Long.valueOf(module.getUuid())).getString();
		} else if (MODULE_ID_MAP.containsKey(Integer.valueOf(module
				.getModuleID()))) {
			System.out.println("Returning GUI by UUID");
			return MODULE_ID_MAP.get(Integer.valueOf(module.getModuleID()))
					.getString();
		} else {
			return "";
			// throw new
			// IllegalStateException("Default case not implemented yet");
		}
	}

	static {
		addImage(RootModule.getRootModule(), "/clear.png");
	}
}
