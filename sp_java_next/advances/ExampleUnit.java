package advances;

import java.io.Serializable;

import model.location.Location;
import model.location.NullLocation;
import model.location.Tile;
import model.main.Game;
import model.main.UuidManager;
import model.module.Module;
import model.module.Statistics;
import model.module.UnableToMoveException;
import model.module.Statistics.Stats;
import model.module.kinds.MobileModule;
import model.module.kinds.RenameableModule;
import model.module.kinds.RootModule;
import model.module.kinds.Weapon;
import pathfinding.AStarPathFinder;
import pathfinding.Path;

/**
 * A sample unit.
 * 
 * @author kingjon
 */
public class ExampleUnit implements Module, Serializable, MobileModule,
		RenameableModule {
	/**
	 * An ExampleUnit's starting movement points
	 */
	private static final int EXAMPLE_UNIT_MP = 5;

	/**
	 * An ExampleUnit's starting hit points
	 */
	private static final int EXAMPLE_UNIT_HP = 20;

	/**
	 */
	private static final long serialVersionUID = -1201325245093673269L;

	/**
	 * Where I am.
	 */
	private Location location;
	/**
	 * The module's name
	 */
	private String name;
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
		statistics.getStats().put(Stats.MAX_HP, EXAMPLE_UNIT_HP);
		statistics.getStats().put(Stats.HP, EXAMPLE_UNIT_HP);
		statistics.getStats().put(Stats.MAX_MP, EXAMPLE_UNIT_MP);
		statistics.getStats().put(Stats.MP, EXAMPLE_UNIT_MP);
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
		// FIXME: Should subtract an appropriate cost from MP.
	}

	/**
	 * Die.
	 */
	@Override
	public void die() {
		location.remove(this);
	}
	/**
	 * @return the ExampleUnit's name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @param _name
	 *            the ExampleUnit's new name
	 */
	public void setName(final String _name) {
		name = _name;
	}

	/**
	 * FIXME: Implement using MP.
	 * 
	 * @param loc
	 *            a location to pretend to move to
	 * @return whether it's possible to move there
	 */
	@Override
	public boolean checkMove(final Location loc) {
		return loc.checkAdd(this)
				&& location instanceof Tile
				&& loc instanceof Tile
				&& (!new AStarPathFinder(Game.getGame().getMap(), statistics
						.getStats().get(Stats.MP).intValue(), true).findPath(
						this, ((Tile) location).getLocation().getX(),
						((Tile) location).getLocation().getY(),
						((Tile) loc).getLocation().getX(),
						((Tile) loc).getLocation().getY()).equals(new Path()));
	}

	/**
	 * FIXME: Implement!
	 * 
	 * @param loc
	 *            a location
	 * @return the cost of visiting that location
	 */
	@Override
	public double getCost(final Location loc) {
		// TODO Auto-generated method stub
		return 1;
	}
}
