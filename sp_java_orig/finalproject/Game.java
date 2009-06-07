package finalproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import finalproject.astar.Tile;

/**
 * Strategic Primer/Yudexen (the first is my name for the game this will
 * hopefully eventually become, which I began designing but had never created a
 * successful even partial implementation of until now; the second is the name
 * of the game into which I put quite a bit of effort in the Spring 2006
 * semester in CSX, and of which I am now the maintainer due to lack of
 * constinued interest from the rest of CSX -- consider it a fork of Strategic
 * Primer's design) is a strategy game (turn-based for now; likely RTS at some
 * time in the future). This project is a prototype of some parts of it.
 * 
 * The main class of the model. It has a map, references to two tiles to the
 * side (only two because I can't think of any action requiring more than two
 * arguments), and a list of modules (by design all the modules in the game, but
 * it can't enforce that); it knows its mode (which determines the game's
 * behavior, though drivers could choose to ignore it), what kinds of modes
 * there are, whose turn it is, how many players there are, and how many of each
 * resource (currently only one -- the resource handling in this implementation
 * is extremely primitive) each player has. It knows how to safely set the mode,
 * end the turn, and build a unit. While it provides accessor methods for all
 * its knowledge, mutators are available only for its two tiles set aside, the
 * mode, and the player resource counts.
 * 
 * @author Jonathan Lovelace
 * @assignment Final Project
 * @course CS108A
 * @semester FA06
 * 
 */
public final class Game {
	/**
	 * The map.
	 */
	private final SPMap map;
	/**
	 * The current mode.
	 */
	private int mode;
	/**
	 * The first selected tile.
	 */
	private Tile tile1;
	/**
	 * The second selected tile.
	 */
	private Tile tile2;
	/**
	 * All the modules in the game.
	 */
	private final List<SimpleModule> modules;
	/**
	 * Whose turn it is.
	 */
	private int player;
	/**
	 * How many players there are
	 */
	private final int numPlayers;
	/**
	 * @bug FIXME: The mode should be an enumerated type.
	 */
	/**
	 * The mode in which the player builds a new unit.
	 */
	public static final int BUILD_MODE = 5;
	/**
	 * A mode to give the player information about a tile or unit
	 */
	public static final int INFO_MODE = 4;
	/**
	 * A mode to make a ranged attack
	 */
	public static final int RANGED_ATK_MODE = 3;
	/**
	 * A mode to move a unit
	 */
	public static final int MOVE_MODE = 2;
	/**
	 * A mode to make a (melee) attack.
	 */
	public static final int ATTACK_MODE = 1;
	/**
	 * The none-of-the-above mode.
	 */
	public static final int NO_MODE = 0;
	/**
	 * How much of the resource each player has.
	 */
	private int[] playerResources;
	/**
	 * The singleton object
	 */
	private static Game theGame;

	/**
	 * @param _map
	 *            The map
	 * @param _modules
	 *            All the modules in the game
	 * @param players
	 *            How many players
	 * @return the singleton object
	 */
	public static Game getGame(final SPMap _map, final List<SimpleModule> _modules,
			final int players) {
		synchronized (Game.class) {
			if (theGame == null) {
				theGame = new Game(_map, _modules, players);
			}
		}
		return theGame;
	}

	/**
	 * @return the singleton object
	 * @throws IllegalStateException
	 *             when you call it before ever calling the full-parameters
	 *             version
	 */
	public static Game getGame() {
		if (theGame == null) {
			throw new IllegalStateException(
					"You must call the three-argument version of getGame() first");
		}
		return theGame;
	}
	/**
	 * The list of indices of modules that should be deleted.
	 */
	private final List<Integer> modulesToDelete;

	/**
	 * Constructor
	 * 
	 * @param _map
	 *            The map
	 * @param _modules
	 *            The modules on the map
	 * @param players
	 *            How many players there are
	 */
	private Game(final SPMap _map, final List<SimpleModule> _modules, final int players) {
		map = _map;
		mode = NO_MODE;
		modules = new ArrayList<SimpleModule>(_modules);
		player = 1;
		numPlayers = players;
		playerResources = new int[players + 1];
		for (int i = 0; i < players; i++) {
			playerResources[i] = 0;
		}
		modulesToDelete = new ArrayList<Integer>();
	}

	/**
	 * @return the map
	 */
	public SPMap getMap() {
		return map;
	}

	/**
	 * @return the current mode
	 */
	public int getMode() {
		return mode;
	}

	/**
	 * @return the first selected tile
	 */
	public Tile getTile1() {
		return tile1;
	}

	/**
	 * @return the second selected tile
	 */
	public Tile getTile2() {
		return tile2;
	}

	/**
	 * @return the player whose turn it is
	 */
	public int getPlayer() {
		return player;
	}

	/**
	 * @return how many players there are
	 */
	public int getNumPlayers() {
		return numPlayers;
	}

	/**
	 * @return the list of modules in the game
	 */
	public List<SimpleModule> getModules() {
		return Collections.unmodifiableList(modules);
	}

	/**
	 * Set a tile aside
	 * 
	 * @param tile
	 *            the first selected tile
	 */
	public void setTile1(final Tile tile) {
		tile1 = tile;
	}

	/**
	 * Set another tile aside
	 * 
	 * @param tile
	 *            the second selected tile
	 */
	public void setTile2(final Tile tile) {
		tile2 = tile;
	}

	/**
	 * Set the mode. FIXME: Should be an enumerated type.
	 * 
	 * @param _mode
	 *            The new mode.
	 */
	public void setMode(final int _mode) {
		if ((_mode == NO_MODE) || (_mode == ATTACK_MODE)
				|| (_mode == MOVE_MODE) || (_mode == RANGED_ATK_MODE)
				|| (_mode == INFO_MODE) || (_mode == BUILD_MODE)) {
			if (mode != _mode) {
				tile1 = tile2 = null;
			}
			mode = _mode;
		} else {
			throw new IllegalArgumentException("Invalid mode");
		}
	}

	/**
	 * Ends the turn -- refreshes all modules (hasAttacked and hasMoved become
	 * false), removes killed modules, and hands control to the next player.
	 * 
	 */
	public void endTurn() {
		modulesToDelete.clear();
		player++;
		if (player > numPlayers) {
			player = 1;
		}
		mode = NO_MODE;
		for (int index = 0; index < modules.size(); index++) {
			modules.get(index).setHasAttacked(false);
			modules.get(index).setHasMoved(false);
			if (modules.get(index).isDelete()) {
				modulesToDelete.add(index);
			} else if ((modules.get(index).getLocation() != null)
					&& (modules.get(index).getOwner() == player)) {
				setPlayerResources(modules.get(index).getLocation().getResourceOnTile()
						+ getPlayerResources(player), modules.get(index).getOwner());
			}
		}
		// Iterate backwards because removing early ones would make our later
		// indexes wrong
		for (int i = (modulesToDelete.size() - 1); i >= 0; i--) {
			modules.remove(i);
		}
	}

	/**
	 * @return a String representation of the Game object.
	 */
	@Override
	public String toString() {
		return "I am a Game object. The turn currently belongs to player "
				+ player + " of " + numPlayers + ". The mode is " + mode
				+ ". My object members:\ntile1: " + tile1 + "\ntile2: " + tile2
				+ "\nmodules: " + modules + "\nmap: " + map + "\n\n";
	}

	/**
	 * @param index
	 *            which player we're interested in
	 * @return That player's resource amount
	 */
	public int getPlayerResources(final int index) {
		return playerResources[index];
	}

	/**
	 * Set a player's resource amount
	 * 
	 * @param amt
	 *            The resource amount
	 * @param index
	 *            The player we're interested in
	 */
	public void setPlayerResources(final int amt, final int index) {
		if (amt < 0) {
			throw new IllegalArgumentException(
					"Player resource level must be nonnegative");
		}
		playerResources[index] = amt;
	}

	// ESCA-JAVA0138:
	/**
	 * Build a unit. FIXME: Make this private, with a delegate to specify
	 * specific units publicly available.
	 * 
	 * @param playerID
	 *            The player building (MUST be the current player
	 * @param location
	 *            Where the unit is to be
	 * @param name
	 *            The name of the unit
	 * @param maxHP
	 *            The unit's max HP
	 * @param hitPoints
	 *            The unit's current HP
	 * @param speed
	 *            The unit's speed
	 * @param defense
	 *            The unit's defense stat
	 * @param accuracy
	 *            The unit's accuracy
	 * @param attack
	 *            The unit's attack power
	 * @param ranged
	 *            The unit's ranged attack power
	 * @param cost
	 *            The unit's cost
	 * @bug FIXME: Enerjy says this has too many parameters, and I agree.
	 */
	public void buildUnit(final int playerID, final Tile location, // NOPMD
			final String name, final int maxHP, final int hitPoints, final int speed,
			final int defense, final double accuracy, final int attack,
			final int ranged, final int cost) {
		if (playerID == player && location != null
				&& location.getModuleOnTile() == null && cost >= 0
				&& cost <= playerResources[playerID]) {
			final SimpleUnit unit = new SimpleUnit();
			unit.setAccuracy(accuracy);
			unit.setAttack(attack);
			unit.setDefense(defense);
			unit.setMaxHP(maxHP);
			unit.setHP(hitPoints);
			unit.setLocation(location);
			unit.setName(name);
			unit.setOwner(playerID);
			unit.setRanged(ranged);
			unit.setSpeed(speed);
			location.setModuleOnTile(unit);
			modules.add(unit);
			playerResources[playerID] -= cost;
		}
	}
}
