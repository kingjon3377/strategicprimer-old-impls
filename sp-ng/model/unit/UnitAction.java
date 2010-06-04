package model.unit;

/**
 * Actions a unit can take. Eventually each unit or building will have an
 * EnumSet of the actions it supports.
 * 
 * @author Jonathan Lovelace
 * 
 */
public enum UnitAction {
	/**
	 * No action.
	 */
	Cancel,
	/**
	 * Harvest a resource.
	 */
	Harvest,
	/**
	 * Unload a unit's load.
	 */
	Unload,
	/**
	 * Movement.
	 */
	Move;
}
