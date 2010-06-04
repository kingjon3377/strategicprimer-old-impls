package model.unit;

import java.util.EnumSet;
import java.util.Set;

/**
 * A unit to demonstrate interactions with terrain objects and buildings.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class Harvester extends SimpleUnit {
	/**
	 * Constructor; required because SimpleUnit doesn't have a no-arg
	 * constructor.
	 * 
	 * @param owner
	 *            the owner of this unit
	 */
	public Harvester(final int owner) {
		super(owner);
	}

	/**
	 * @return what actions this unit supports
	 */
	@Override
	public Set<UnitAction> supportedActions() {
		Set<UnitAction> tempSet = EnumSet.copyOf(super.supportedActions());
		tempSet.add(UnitAction.Harvest);
		tempSet.add(UnitAction.Unload);
		return tempSet;
	}

	/**
	 * How much of a resource (TODO: make some way to specify the resource) the
	 * unit is holding.
	 */
	private int burden;

	/**
	 * @return how much of a resource this unit is holding
	 */
	public int getBurden() {
		return burden;
	}

	/**
	 * @param newBurden
	 *            how much of a resource this unit will now be holding
	 */
	public void setBurden(final int newBurden) {
		if (newBurden < 0) {
			throw new IllegalArgumentException(
					"Harvester can't hold a negative amount");
		} else {
			burden = newBurden;
		}
	}
}
