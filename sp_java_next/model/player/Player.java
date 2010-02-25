package model.player;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import model.module.Advance;
import model.module.Module;

/**
 * A player in the game -- local or remote, human or AI.
 * 
 * TODO: Should this be abstract (if so, what should be abstract)? Removed the
 * abstract keyword to make PMD errors go away.
 * 
 * @author Jonathan Lovelace
 */
public class Player implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4976698342006367713L;

	/**
	 * Each player has a module in the game representing him, her, or it.
	 */
	private Module associatedModule;

	/**
	 * Advances this player is unable to research.
	 */
	private final Set<Advance> forbiddenAdvances; // NOPMD by kingjon on
	// 2/10/08 10:49 PM

	/**
	 * Advances this player has.
	 */
	private final Set<Advance> advances; // NOPMD by kingjon on 2/10/08 10:49
	// PM

	/**
	 * The player's "name" or handle.
	 */
	private String name;

	/**
	 * Constructor. TODO: Figure out what belongs here (remember, this is an
	 * abstract class)
	 * 
	 * @param assocModule
	 *            The module that represents the player in the game-world.
	 * 
	 */
	public Player(final Module assocModule) {
		associatedModule = assocModule;
		forbiddenAdvances = new HashSet<Advance>();
		advances = new HashSet<Advance>();
	}

	/**
	 * Discover an advance. TODO: Add dependency checking (delegate to Advance
	 * itself?)
	 * 
	 * @param advance
	 *            The advance to discover
	 */
	public void discoverAdvance(final Advance advance) {
		if (forbiddenAdvances.contains(advance)) {
			throw new IllegalArgumentException(
					"Player tried to discover a forbidden advance");
		}
		advances.add(advance);
	}

	/**
	 * Forbid the player from discovering an advance.
	 * 
	 * @param advance
	 *            the advance
	 */
	public void forbid(final Advance advance) {
		forbiddenAdvances.add(advance);
	}

	/**
	 * @param getter
	 *            becomes the set of the advances the player knows
	 */
	public void getAdvances(final Set<Advance> getter) {
		getter.addAll(advances);
		getter.retainAll(advances);
	}
	/**
	 * @return the set of advances the player knows
	 */
	public Set<Advance> getAdvances() {
		return new HashSet<Advance>(advances);
	}
	/**
	 * @return the associatedModule
	 */
	public Module getAssociatedModule() {
		return associatedModule;
	}

	/**
	 * The advances a player cannot discover. (Changed from returning the set to
	 * copying it into the given set.)
	 * 
	 * @param getter
	 *            a set to change to be identical with the set of forbidden
	 *            advances
	 */
	public void getForbiddenAdvances(final Set<Advance> getter) {
		getter.addAll(forbiddenAdvances);
		getter.retainAll(forbiddenAdvances);
	}

	/**
	 * @return the player's chosen "name" or handle.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param assocModule
	 *            the module that represents the player in the game-world
	 */
	public void setAssociatedModule(final Module assocModule) {
		associatedModule = assocModule;
	}

	/**
	 * Set a player's chosen "name" or "handle".
	 * 
	 * @param newName
	 *            The player's chosen name
	 * 
	 * TODO: Implement (see Yudexen?)
	 */
	protected void setName(final String newName) {
		name = newName;
	}
}
