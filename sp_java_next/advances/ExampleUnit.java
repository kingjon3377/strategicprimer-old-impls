package advances;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.location.Location;
import model.location.NullLocation;
import model.location.TerrainType;
import model.location.Tile;
import model.main.Game;
import model.main.UuidManager;
import model.module.Module;
import model.module.Statistics;
import model.module.UnableToMoveException;
import model.module.Statistics.Stats;
import model.module.actions.Action;
import model.module.kinds.FunctionalModule;
import model.module.kinds.MobileModule;
import model.module.kinds.Renameable;
import model.module.kinds.RootModule;
import model.module.kinds.TransferableModule;
import model.module.kinds.Weapon;
import model.player.IPlayer;
import pathfinding.AStarPathFinder;
import pathfinding.Path;

/**
 * A sample unit.
 * 
 * @author Jonathan Lovelace
 */
public class ExampleUnit implements Module, Serializable, MobileModule,
		Renameable, TransferableModule, FunctionalModule {
	/**
	 * An ExampleUnit's starting movement points.
	 */
	private static final int EXAMPLE_UNIT_MP = 5;

	/**
	 * An ExampleUnit's starting hit points.
	 */
	private static final int EXAMPLE_UNIT_HP = 20;

	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = -1201325245093673269L;

	/**
	 * Where I am.
	 */
	private Location location;
	/**
	 * The module's name.
	 */
	private String name;
	/**
	 * My parent in the tree.
	 */
	private Module parent;

	/**
	 * My set of statistics.
	 */
	private Statistics statistics;
	/**
	 * UUID.
	 */
	private final long uuid = UuidManager.UUID_MANAGER.getNewUuid();

	/**
	 * Constructor.
	 */
	public ExampleUnit() {
		statistics = new Statistics();
		statistics.getStats().put(Stats.MAX_HP, EXAMPLE_UNIT_HP);
		statistics.getStats().put(Stats.HP, EXAMPLE_UNIT_HP);
		statistics.getStats().put(Stats.MAX_MP, EXAMPLE_UNIT_MP);
		statistics.getStats().put(Stats.MP, EXAMPLE_UNIT_MP);
		location = NullLocation.NULL_LOC;
		parent = RootModule.ROOT_MODULE;
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
	 * Accessor.
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
	 * @param newParent
	 *            My new parent in the tree.
	 */
	protected void setParent(final Module newParent) {
		parent = newParent;
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
	 * 
	 * @param attacker
	 *            a module attacking the unit
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
	 * The ModuleID of all instances of this class (not of subclasses).
	 */
	private static final int MODULE_ID = 3;
	/**
	 * @return the unit's moduleID. Should be overridden by subclasses
	 */
	@Override
	public int getModuleID() {
		return MODULE_ID;
	}

	/**
	 * @return the unit's UUID
	 */
	@Override
	public final long getUuid() {
		return uuid;
	}

	/**
	 * Move to a new location.
	 * 
	 * @param loc
	 *            the new location
	 * @throws UnableToMoveException
	 *             when unable to move
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
		getStatistics().getStats().put(
				Stats.MP,
				getStatistics().getStats().get(Stats.MP).doubleValue()
						- pathCost(new AStarPathFinder(Game.getGame().getMap(),
								statistics.getStats().get(Stats.MP).intValue(),
								true).findPath(this, ((Tile) location)
								.getLocation().getX(), ((Tile) location)
								.getLocation().getY(), ((Tile) loc)
								.getLocation().getX(), ((Tile) loc)
								.getLocation().getY())));
		System.out.println(getStatistics().getStats().get(Stats.MP) + " MP left");
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
	 * @param newName
	 *            the ExampleUnit's new name
	 */
	public void setName(final String newName) {
		name = newName;
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
				&& checkPath(new AStarPathFinder(Game.getGame().getMap(),
						statistics.getStats().get(Stats.MP).intValue(), true)
						.findPath(this, ((Tile) location).getLocation().getX(),
								((Tile) location).getLocation().getY(),
								((Tile) loc).getLocation().getX(), ((Tile) loc)
										.getLocation().getY()));
	}

	/**
	 * Is a path passable within our MP budget?
	 * 
	 * @param path
	 *            a path
	 * @return whether that path is passable within our budget
	 */
	private boolean checkPath(final Path path) {
		return !path.equals(new Path())
				&& pathCost(path) <= statistics.getStats().get(Stats.MP)
						.doubleValue();
	}

	/**
	 * @param path
	 *            a path
	 * @return the cost in MP for us to take that path
	 */
	private double pathCost(final Path path) {
		double cost = 0;
		for (int i = 1; i < path.getLength(); i++) {
			cost += getCost(Game.getGame().getMap().getTileAt(path.getStep(i)));
		}
		return cost;
	}

	/**
	 * TODO: Should take roads, etc., into account.
	 * 
	 * @param loc
	 *            a location
	 * @return the cost of visiting that location
	 */
	@Override
	public double getCost(final Location loc) {
		if (loc == null) {
			throw new IllegalStateException("Called getCost(null)");
		} else if (loc instanceof Tile) {
			return MOVE_COST_MAP.get(((Tile) loc).getTerrain());
		} else {
			throw new IllegalStateException(
					"Called getCost() on a non-Tile Location");
		}
	}

	/**
	 * The unit's owner.
	 */
	private IPlayer owner;

	/**
	 * @return the unit's owner
	 */
	@Override
	public IPlayer getOwner() {
		return owner;
	}

	/**
	 * @param newOwner
	 *            the unit's new owner
	 */
	@Override
	public void setOwner(final IPlayer newOwner) {
		owner = newOwner;
	}

	/**
	 * The set of supported actions.
	 */
	private static final Set<Action> ACTIONS = new HashSet<Action>();
	static {
		ACTIONS.add(new Action(2, "Heal", 0));
	}

	/**
	 * Do an action.
	 * 
	 * @param action
	 *            the action to do
	 * @param args
	 *            the arguments the action takes
	 */
	@Override
	public void action(final long action, final Module... args) {
		if (action == 2) {
			statistics.getStats().put(Stats.HP, EXAMPLE_UNIT_HP);
		} else {
			throw new IllegalStateException("Unsupported action");
		}
	}

	/**
	 * @return the actions this unit can do
	 */
	@Override
	public Set<Action> supportedActions() {
		return Collections.unmodifiableSet(ACTIONS);
	}
	/**
	 * Movement cost for forests.
	 */
	private static final double FOREST_COST = 4.0;
	/**
	 * Movement cost for jungle and mountain.
	 */
	private static final double JUNGLE_COST = 5.0;
	/**
	 * A mapping from terrain types to the cost to enter tiles of that type.
	 */
	private static final Map<TerrainType, Double> MOVE_COST_MAP = new EnumMap<TerrainType, Double>(
			TerrainType.class);
	static {
		MOVE_COST_MAP.put(TerrainType.Plains, 1.0);
		MOVE_COST_MAP.put(TerrainType.Desert, 2.0);
		MOVE_COST_MAP.put(TerrainType.Tundra, 2.0);
		MOVE_COST_MAP.put(TerrainType.TemperateForest, FOREST_COST);
		MOVE_COST_MAP.put(TerrainType.BorealForest, FOREST_COST);
		MOVE_COST_MAP.put(TerrainType.Jungle, JUNGLE_COST);
		MOVE_COST_MAP.put(TerrainType.Mountain, JUNGLE_COST);
		MOVE_COST_MAP.put(TerrainType.Ocean, Double.MAX_VALUE);
		MOVE_COST_MAP.put(TerrainType.NotVisible, Double.MAX_VALUE);
	}
}
