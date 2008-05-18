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
	 * Constructor.
	 */
	public ExampleUnit() {
		statistics = new Statistics();
		location = NullLocation.getNullLocation();
		parent = RootModule.getRootModule();
		topLevel = true;
	}
	
	/**
	 * Whether the unit is a member (direct or indirect) of a top-level unit
	 * (false if it is, true if this is this is the uppermost
	 * possibly-"top-level" module in this branch of the tree).
	 */
	private boolean topLevel;
	/**
	 */
	private static final long serialVersionUID = -1201325245093673269L;

	/**
	 * Where I am.
	 */
	private Location location;

	/**
	 * Accessor.
	 * 
	 * @return my location
	 */
	@Override
	public Location getLocation() {
		return location;
	}

	/**
	 * My parent in the tree.
	 */
	protected Module parent;

	/**
	 * Accessor
	 * 
	 * @return the module that contains me
	 */
	@Override
	public Module getParent() {
		return parent;
	}

	/**
	 * @param _parent
	 *            My new parent in the tree.
	 */
	protected void setParent(final Module _parent) {
		parent = _parent;
	}

	/**
	 * This sample unit can move, so
	 * 
	 * @return true
	 */
	@Override
	public boolean isMobile() {
		return true;
	}

	/**
	 * @return Whether the unit is a member (direct or indirect) of a top-level
	 *         unit (false if it is, true if this is this is the uppermost
	 *         possibly-"top-level" module in this branch of the tree).
	 * 
	 */
	@Override
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
	protected void setLocation(final Location loc) {
		location = loc;
	}

	/**
	 * My set of statistics.
	 */
	private Statistics statistics;

	/**
	 * @return my set of statistics
	 */
	public Statistics getStatistics() {
		return statistics;
	}

	/**
	 * @param stats
	 *            a new set of statistics
	 */
	protected void setStatistics(final Statistics stats) {
		statistics = stats;
	}

	/**
	 * Take an attack from the given module.
	 */
	@Override
	public void takeAttack(final Weapon attacker) {
		statistics.getStats().put(
				Statistics.Stats.HP,
				statistics.getStats().get(Statistics.Stats.HP).intValue()
						- attacker.projectedDamage(this));
	}

	/**
	 * Do upkeep-related things
	 */
	@Override
	public void upkeep(final long interval) {
		if (interval < 0) {
			throw new IllegalArgumentException(
					"Negative time elapsed doesn't make sense");
		}
		// TODO: Finish implementing.
	}

	/**
	 * @param _topLevel
	 *            whether there is not a possibly-"top-level" module above me in
	 *            the tree
	 */
	protected void setTopLevel(final boolean _topLevel) {
		topLevel = _topLevel;
	}

}
