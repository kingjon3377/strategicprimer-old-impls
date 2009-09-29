package advances;

import java.io.Serializable;

import model.location.Location;
import model.location.NullLocation;
import model.main.UuidManager;
import model.module.MobileModule;
import model.module.Module;
import model.module.RootModule;
import model.module.Statistics;
import model.module.UnableToMoveException;
import model.module.Weapon;
import model.module.Statistics.Stats;

/**
 * A sample unit.
 * 
 * @author kingjon
 */
public class ExampleUnit implements Module, Serializable, MobileModule {

	/**
	 */
	private static final long serialVersionUID = -1201325245093673269L;

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
	 * UUID
	 */
	protected final long uuid = UuidManager.UUID_MANAGER.getNewUuid();
	
	/**
	 * Constructor.
	 */
	public ExampleUnit() {
		statistics = new Statistics();
		statistics.getStats().put(Stats.MAX_HP, 20);
		statistics.getStats().put(Stats.HP, 20);
		location = NullLocation.getNullLocation();
		parent = RootModule.getRootModule();
	}

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
	 * Accessor
	 * 
	 * @return the module that contains me
	 */
	@Override
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
	 * Take an attack from the given module.
	 * @param attacker a module attacking the unit
	 */
	@Override
	public void takeAttack(final Weapon attacker) {
		statistics.getStats().put(
				Statistics.Stats.HP,
				statistics.getStats().get(Statistics.Stats.HP).intValue()
						- attacker.predictDamage(this));
		if (statistics.getStats().get(Statistics.Stats.HP).intValue() <= 0) {
			die();
		}
	}

	/**
	 * @return the unit's moduleID. Should be overridden by subclasses
	 */
	@Override
	public int getModuleID() {
		return 3;
	}
	/**
	 * @return the unit's UUID
	 */
	@Override
	public final long getUuid() {
		return uuid; 
	}
	/**
	 * Move to a new location
	 * @param loc the new location
	 * @throws UnableToMoveException when unable to move
	 */
	@Override
	public void move(final Location loc) throws UnableToMoveException {
		location.remove(this);
		try {
			loc.add(this);
		} catch (IllegalStateException except) {
			location.add(this);
			throw new UnableToMoveException("Location already occupied"); // NOPMD
		}
		setLocation(loc);
	}
	/**
	 * Die.
	 */
	@Override
	public void die() {
		location.remove(this);
	}
}
