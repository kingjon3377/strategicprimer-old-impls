package sp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sp.model.astar.Tile;


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
public final class Game implements Serializable {
	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = -8833990787764269215L;

	/**
	 * The possible modes that the game can be in. FIXME: Model-view mixing!
	 * 
	 * @author Jonathan Lovelace
	 * 
	 */
	public static enum Mode {
		/**
		 * Building.
		 */
		BUILD_MODE,
		/**
		 * Getting information about things on the map.
		 */
		INFO_MODE,
		/**
		 * Ranged attacks.
		 */
		RANGED_ATTACK_MODE,
		/**
		 * Moving.
		 */
		MOVE_MODE,
		/**
		 * Melee attacks.
		 */
		ATTACK_MODE,
		/**
		 * No mode.
		 */
		NO_MODE
	}
	/**
	 * Encapsulating the various statistics used by the buildUnit method.
	 * 
	 * @author Jonathan Lovelace
	 * 
	 */
	public static final class Statistics {
		/**
		 * Maximum HP.
		 */
		private final int maxHP; // NOPMD by kingjon on 5/19/08 12:32 AM

		/**
		 * Maximum movement.
		 */
		private final int speed; // NOPMD by kingjon on 5/19/08 12:34 AM
		/**
		 * Damage reduction?
		 */
		private final int defense; // NOPMD by kingjon on 5/19/08 12:34 AM
		/**
		 * Ranged accuracy.
		 */
		private final double accuracy; // NOPMD by kingjon on 5/19/08 12:34 AM
		/**
		 * Attack power.
		 */
		private final int attack; // NOPMD by kingjon on 5/19/08 12:34 AM
		/**
		 * Ranged attack power.
		 */
		private final int ranged; // NOPMD by kingjon on 5/19/08 12:34 AM

		// ESCA-JAVA0138:
		/**
		 * Constructor.
		 * 
		 * @param _maxHP
		 *            maximum HP
		 * @param _speed
		 *            maximum movement
		 * @param _defense
		 *            damage reduction?
		 * @param _accuracy
		 *            ranged accuracy
		 * @param _attack
		 *            attack power
		 * @param _ranged
		 *            ranged attack power
		 */
		public Statistics(final int _maxHP, final int _speed, final int _defense,
				final double _accuracy, final int _attack, final int _ranged) {
			maxHP = _maxHP;
			speed = _speed;
			defense = _defense;
			accuracy = _accuracy;
			attack = _attack;
			ranged = _ranged;
		}

		/**
		 * @return the accuracy
		 */
		public double getAccuracy() {
			return accuracy;
		}

		/**
		 * @return the attack
		 */
		public int getAttack() {
			return attack;
		}

		/**
		 * @return the defense
		 */
		public int getDefense() {
			return defense;
		}

		/**
		 * @return the maxHP
		 */
		public int getMaxHP() {
			return maxHP;
		}

		/**
		 * @return the ranged
		 */
		public int getRanged() {
			return ranged;
		}

		/**
		 * @return the speed
		 */
		public int getSpeed() {
			return speed;
		}
	}

	/**
	 * Singleton instance.
	 */
	private static Game theGame;

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
	 * Singleton constructor.
	 * 
	 * @param _map
	 *            The map
	 * @param _modules
	 *            All the modules in the game
	 * @param players
	 *            How many players
	 * @return the singleton object
	 */
	public static Game getGame(final SPMap _map, final List<Module> _modules,
			final int players) {
		synchronized (Game.class) {
			if (theGame == null) {
				theGame = new Game(_map, _modules, players);
			}
		}
		return theGame;
	}

	/**
	 * The map --- a collection of all the tiles.
	 */
	private final SPMap map;

	/**
	 * Which mode the game is in. TODO: The model shouldn't care about this!
	 */
	private Mode mode;

	/**
	 * The first of two tiles set aside --- the source and destination for
	 * movement and attacks.
	 */
	private Tile tile1;

	/**
	 * The second of two tiles set aside --- the source and destination for
	 * movement and attacks.
	 */
	private Tile tile2;

	/**
	 * All the modules in the game (TODO: verify).
	 */
	private final List<IModule> modules;

	/**
	 * Whose turn it is.
	 */
	private int player;

	/**
	 * How many players there are.
	 */
	private final int numPlayers;

	/**
	 * How much of the single resource each player has. TODO: Make resources
	 * more complex.
	 */
	private final int[] playerResources;

	/**
	 * Constructor.
	 * 
	 * @param _map
	 *            The map
	 * @param _modules
	 *            All the modules on the map
	 * @param players
	 *            How many players there are
	 */
	private Game(final SPMap _map, final List<Module> _modules, final int players) {
		map = _map;
		mode = Mode.NO_MODE;
		modules = new ArrayList<IModule>(_modules);
		player = 1;
		numPlayers = players;
		playerResources = new int[players + 1];
		for (int i = 0; i < players; i++) {
			playerResources[i] = 0;
		}
	}

	/**
	 * TODO: We, or a helper object [but in the model not the view], should
	 * know what kinds of units there are and how to build them. This
	 * method might continue to exist, but as a private helper method
	 * called by the public method, and not just building *units*. That
	 * violates the module system.
	 * 
	 * @param location
	 *            Where the unit is to be (MUST be an empty tile)
	 * @param name
	 *            The name to give the unit.
	 * @param stats
	 *            Various statistics
	 * @param cost
	 *            The unit's cost
	 */
	public void buildUnit(final Tile location, final String name, final Statistics stats,
			final int cost) {
		// NOTICE: Only the current player can build; we removed the
		// sanity-check "who's building" parameter to simplify the method
		// signature.
		if (location != null && location.getModuleOnTile() == null && cost >= 0
				&& cost <= playerResources[player]) {
			final SimpleUnit unit = new SimpleUnit();
			unit.setAccuracy(stats.getAccuracy());
			unit.setMeleeAttack(stats.getAttack());
			unit.setDefense(stats.getDefense());
			unit.setMaxHP(stats.getMaxHP());
			unit.setHitPoints(stats.getMaxHP());
			unit.setLocation(location);
			unit.setName(name);
			unit.setOwner(player);
			unit.setRanged(stats.getRanged());
			unit.setSpeed(stats.getSpeed());
			location.setModuleOnTile(unit);
			modules.add(unit);
			playerResources[player] -= cost;
		}
	}

	/**
	 * Ends the turn -- refreshes all modules (hasAttacked and hasMoved
	 * become false), removes killed modules, and hands control to the next player.
	 * 
	 */
	public void endTurn() {
		final List<Integer> removableIndices = new ArrayList<Integer>();
		// Increment forward over all the modules, marking
		// them all as not having attacked or moved, noting
		// the indices of those flagged for removal, and
		// giving the player the resources of the tiles on
		// which his living units or buildings stand.
		// TODO: [Cross-posting from where the main change
		// will be implemented.] Change to use MP rather than
		// hasAttacked and hasMoved --- a unit should be able
		// to take a double move or a double attack action.
		// TODO: [Cross-posting from Tile.java, where the
		// first change will be implemented.] Tiles, not
		// units, should give the player resources, but each
		// player should come to own those tiles which his
		// units ended on or next to.
		for (int i = 0; i < modules.size(); i++) {
			if (modules.get(i) instanceof Weapon) {
				((Weapon) modules.get(i)).setHasAttacked(false);
			}
			((Module)modules.get(i)).setHasMoved(false);
			if (modules.get(i).isDeleted()) {
				removableIndices.add(i);
			} else if ((modules.get(i).getLocation() != null)
					&& (modules.get(i).getOwner() == player)) {
				setPlayerResources(((Tile)modules.get(i).getLocation()).getResourceOnTile()
						+ getPlayerResources(player), modules.get(i).getOwner());
			}
		}
		// Iterate backwards because removing early ones would
		// make our later indexes wrong
		for (int i = (removableIndices.size() - 1); i >= 0; i--) {
			modules.remove(i);
		}
		// Hand control over to the next player.
		player++;
		if (player > numPlayers) {
			player = 1;
		}
		mode = Mode.NO_MODE;
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
	public Mode getMode() {
		return mode;
	}

	/**
	 * @return how many players there are
	 */
	public int getNumPlayers() {
		return numPlayers;
	}

	/**
	 * @return the player whose turn it is
	 */
	public int getPlayer() {
		return player;
	}

	/**
	 * @param index
	 *            The player whose resources we want to look at
	 * @return That player's resource amount
	 */
	public int getPlayerResources(final int index) {
		return playerResources[index];
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
	 * TODO: With this now an enumerated type, the validity checking shouldn't
	 * be necessary. TODO: Model-view mixing!
	 * 
	 * @param _mode
	 *            The new mode.
	 */
	public void setMode(final Mode _mode) {
		if ((_mode == Mode.NO_MODE) || (_mode == Mode.ATTACK_MODE)
				|| (_mode == Mode.MOVE_MODE) || (_mode == Mode.RANGED_ATTACK_MODE)
				|| (_mode == Mode.INFO_MODE) || (_mode == Mode.BUILD_MODE)) {
			if (!mode.equals(_mode)) {
				tile1 = null; // NOPMD
				tile2 = null; // NOPMD
			}
			mode = _mode;
		} else {
			throw new IllegalArgumentException("Invalid mode");
		}
	}

	/**
	 * TODO: Design decision: Should drivers be able to arbitrarily set
	 * player resource levels?
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
		} else if (index < 1) {
			throw new IllegalArgumentException("Player number must be positive");
		} else if (index > numPlayers) {
			throw new IllegalArgumentException("Invalid player number");
		}
		playerResources[index] = amt;
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
	 * A wrapper around System.exit().
	 * 
	 * @param status
	 *            The exit code
	 */
	public static void quit(final int status) {
		System.exit(status); // NOPMD
	}
	/**
	 * @return all the modules in the game
	 */
	public List<IModule> getModules() {
		return modules;
	}
}
