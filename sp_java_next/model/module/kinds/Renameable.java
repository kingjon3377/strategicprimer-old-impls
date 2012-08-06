package model.module.kinds;

/**
 * Something that can be renamed at runtime.
 * @author Jonathan Lovelace
 */
public interface Renameable {
	/**
	 * @param name the module's new name
	 */
	void setName(final String name);
}
