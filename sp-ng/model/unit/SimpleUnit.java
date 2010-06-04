package model.unit;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import model.module.SPModule;

/**
 * A simple unit class.
 * 
 * @author Jonathan Lovelace
 */
public class SimpleUnit implements SPModule {
	/**
	 * Which player owns this unit.
	 */
	private int owner;

	/**
	 * Constructor.
	 * 
	 * @param unitOwner
	 *            which player owns this unit
	 */
	public SimpleUnit(final int unitOwner) {
		owner = unitOwner;
	}

	/**
	 * @return which player owns this unit
	 */
	public int getOwner() {
		return owner;
	}

	/**
	 * @param unitOwner
	 *            the unit's new owner
	 */
	public void setOwner(final int unitOwner) {
		owner = unitOwner;
	}

	/**
	 * Mere SimpleUnits only support one action, namely movement.
	 */
	private static final Set<UnitAction> MOVE_ACTION;
	static {
		EnumSet<UnitAction> tempSet = EnumSet.noneOf(UnitAction.class);
		tempSet.add(UnitAction.Move);
		MOVE_ACTION = Collections.unmodifiableSet(tempSet);
	}

	/**
	 * @return what actions this unit supports.
	 */
	public Set<UnitAction> supportedActions() {
		// ESCA-JAVA0259: The collection we're returning is unmodifiable.
		return MOVE_ACTION;
	}
}
