package model.main;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.location.SPMap;
import model.module.RootModule;
import model.player.Side;

import org.xml.sax.SAXException;

/**
 * The main object of the model (or is it the controller?) For now, just a place
 * to put design TODO items.
 * 
 * TODO: We need some way of implementing normative advances (which cannot have
 * an incremental cost).
 * 
 * TODO: Ponder how to make the model suitable for play-by-email, RTS, _and_
 * FPS/space-shooter style gameplay. (Ask Paul.)
 * 
 * TODO: Should this extend HashSet<Side>?
 * 
 * @author Jonathan Lovelace
 * 
 */
public final class Game implements Serializable {
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -3912749657208931450L;
	/**
	 * Logger
	 */
	private static final Logger LOGGER = Logger.getLogger(Game.class.getName());
	/**
	 * The game object
	 */
	private static Game theGame;
	static {
		theGame = new Game();
	}

	/**
	 * @return The game object
	 */
	public static Game getGame() {
		return theGame;
	}

	/**
	 * All the teams in the game.
	 */
	private final Set<Side> sides; // NOPMD by kingjon on 5/19/08 4:29 PM
	/**
	 * The main map the game takes place on. Long-range TODO: Is this really the
	 * best way to do this (given that various levels needed)?
	 */
	private SPMap map; // NOPMD by kingjon on 5/19/08 4:29 PM
	/**
	 * Constructor
	 */
	private Game() {
		sides = new HashSet<Side>();
		map = new SPMap();
		RootModule.getRootModule();
	}

	/**
	 * Add a team to the game
	 * 
	 * @param side
	 *            The team to add
	 */
	public void addSide(final Side side) {
		sides.add(side);
	}

	/**
	 * @return the map
	 */
	public SPMap getMap() {
		return map;
	}

	/**
	 * Accessor.
	 * 
	 * @return the teams in the game
	 */
	public Set<Side> getSides() {
		return Collections.unmodifiableSet(sides);
	}
	/**
	 * Get the map from file
	 * @param filename The filename of the XML map
	 */
	public void createMap(final String filename) {
		try {
			map = new MapXMLReader(filename).getMap();
		} catch (SAXException e) {
			LOGGER.log(Level.SEVERE,"Parsing problem",e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,"Parsing I/O problem",e);
		}
	}
}
