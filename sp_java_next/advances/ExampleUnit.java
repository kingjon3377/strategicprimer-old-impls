package advances;

import java.io.Serializable;

import model.location.Location;
import model.location.NullLocation;
import model.module.Module;
import model.module.RootModule;
import model.module.Statistics;
import model.module.Weapon;

/**
 * A sample unit.
 * 
 * @author kingjon
 */
public class ExampleUnit implements Module, Serializable {

	/**
	 */
	private static final long serialVersionUID = -1201325245093673269L;

	/**
	 * Whether the unit is a member (direct or indirect) of a top-level unit
	 * (false if it is, true if this is this is the uppermost
	 * possibly-"top-level" module in this branch of the tree).
	 */
	private boolean topLevel;
	/**
	 * Where I am.
	 */
	private Location location;

	/**
	 * My parent in the tree.
	 */
	protected Module parent;

	/**
	 * My set of statistics.
	 */
	private Statistics statistics;

	/**
	 * Constructor.
	 */
	public ExampleUnit() {
		statistics = new Statistics();
		location = NullLocation.getNullLocation();
		parent = RootModule.getRootModule();
		topLevel = true;
	}

	/**
	 * Accessor.
	 * 
	 * @return my location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Accessor
	 * 
	 * @return the module that contains me
	 */
	public Module getParent() {
		return parent;
	}

	/**
	 * @return my set of statistics
	 */
	public Statistics getStatistics() {
		return statistics;
	}

	/**
	 * This sample unit can move, so
	 * 
	 * @return true
	 */
	public boolean isMobile() {
		return true;
	}

	/**
	 * @return Whether the unit is a member (direct or indirect) of a top-level
	 *         unit (false if it is, true if this is this is the uppermost
	 *         possibly-"top-level" module in this branch of the tree).
	 * 
	 */
	public boolean isTopLevel() {
		return topLevel;
	}

	/**
	 * Set my location field. This is protected so that callers can be sure to
	 * do sanity checking first.
	 * 
	 * @param loc
	 *            my new location
	 */
	public void setLocation(final Location loc) {
		location = loc;
	}

	/**
	 * @param _parent
	 *            My new parent in the tree.
	 */
	protected void setParent(final Module _parent) {
		parent = _parent;
	}

	/**
	 * @param stats
	 *            a new set of statistics
	 */
	protected void setStatistics(final Statistics stats) {
		statistics = stats;
	}

	/**
	 * @param _topLevel
	 *            whether there is not a possibly-"top-level" module above me in
	 *            the tree
	 */
	protected void setTopLevel(final boolean _topLevel) {
		topLevel = _topLevel;
	}

	/**
	 * Take an attack from the given module.
	 * @param attacker a module attacking the unit
	 */
	public void takeAttack(final Weapon attacker) {
		statistics.getStats().put(
				Statistics.Stats.HP,
				statistics.getStats().get(Statistics.Stats.HP).intValue()
						- attacker.predictDamage(this));
	}

	/**
	 * Do upkeep-related things
	 * @param interval how long it's been since the last upkeep
	 */
	public void upkeep(final long interval) {
		if (interval < 0) {
			throw new IllegalArgumentException(
					"Negative time elapsed doesn't make sense");
		}
		// TODO: Finish implementing.
	}
	/**
	 * @return the unit's moduleID
	 */
	@Override
	public int getModuleID() {
		throw new IllegalStateException("Unimplemented");
	}
	/**
	 * @return the unit's UUID
	 */
	@Override
	public long getUuid() {
		throw new IllegalStateException("Unimplemented");
	}
}
