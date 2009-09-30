/**
 * 
 */
package model.module.kinds;

import model.module.Module;

/**
 * A Module that can be renamed at runtime
 * @author Jonathan Lovelace
 */
public interface RenameableModule extends Module {
	/**
	 * @param name the module's new name
	 */
	void setName(final String name);
}
