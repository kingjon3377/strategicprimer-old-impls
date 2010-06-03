package model.module;
/**
 * A module: at present a unit or building.
 * @author Jonathan Lovelace
 *
 */
public interface SPModule {
	/**
	 * So that this isn't an empty block.
	 * @return what player owns the module
	 */
	int getOwner();
}
