package model.building;

import java.util.EnumSet;
import java.util.Set;

import model.module.SPModule;
import model.unit.UnitAction;

/**
 * A simple building (i.e. structure) class
 * 
 * @author Jonathan Lovelace
 * 
 */
public class SimpleBuilding implements SPModule {
	/**
	 * Which player owns this building.
	 */
	private int owner;

	/**
	 * Constructor.
	 * 
	 * @param bOwner
	 *            which player owns this building
	 */
	public SimpleBuilding(final int bOwner) {
		owner = bOwner;
	}

	/**
	 * @return which player owns this building
	 */
	@Override
	public int getOwner() {
		return owner;
	}

	/**
	 * @param bOwner
	 *            the building's new owner
	 */
	public void setOwner(final int bOwner) {
		owner = bOwner;
	}

	/**
	 * @return the actions this building supports: none
	 */
	@Override
	public Set<UnitAction> supportedActions() {
		return EnumSet.noneOf(UnitAction.class);
	}
}
