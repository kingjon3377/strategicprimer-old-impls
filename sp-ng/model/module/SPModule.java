package model.module;

import java.util.Set;

import model.unit.UnitAction;

/**
 * A module: at present a unit or building.
 * 
 * @author Jonathan Lovelace
 * 
 */
public interface SPModule {
	/**
	 * So that this isn't an empty block.
	 * 
	 * @return what player owns the module
	 */
	int getOwner();

	/**
	 * Subclasses of implementors should use their parent's implementation and
	 * add any additional actions.
	 * 
	 * @return what actions this module supports
	 */
	Set<UnitAction> supportedActions();
}
