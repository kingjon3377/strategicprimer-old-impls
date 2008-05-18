package model.main;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import model.location.Map;
import model.player.Side;

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
	 * All the teams in the game.
	 */
	private final Set<Side> sides; 

	/**
	 * The main map the game takes place on. Long-range TODO: Is this really the
	 * best way to do this (given that various levels needed)?
	 */
	private final Map map;

	/**
	 * Constructor
	 */
	private Game() {
		sides = new HashSet<Side>();
		map = new Map();
	}

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
	 * Add a team to the game
	 * 
	 * @param side
	 *            The team to add
	 */
	public void addSide(final Side side) {
		sides.add(side);
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
	 * @return the map
	 */
	public Map getMap() {
		return map;
	}
	
	// ESCA-JAVA0009:
	/**
	 * Setter method to appease static analysis plugins.
	 * @param _sides ignored
	 */
	protected void setSides(final Set<Side> _sides) {
		sides.addAll(_sides);
	}
	
	// ESCA-JAVA0173:
	/**
	 * Setter method to appease static analysis plugins.
	 * @param _map ignored
	 */
	protected void setMap(@SuppressWarnings("unused")
	final Map _map) {
		throw new IllegalStateException("Cannot insert a new map at runtime");
	}
}
