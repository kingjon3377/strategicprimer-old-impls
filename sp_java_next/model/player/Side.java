package model.player;

import java.util.HashSet;
import java.util.Set;

import model.module.Advance;
import model.module.Module;

/**
 * A team in the game.
 * 
 * TODO: Should this subclass Player?
 * 
 * TODO: Write fuller description.
 * 
 * @author Jonathan Lovelace
 */
public class Side {

	/**
	 * Each team's modules form a single-rooted tree.
	 */
	private Module moduleTree;

	/**
	 * The players on the team.
	 */
	private final Set<Player> players; // NOPMD by kingjon on 2/10/08 10:53 PM

	/**
	 * Advances this side is unable to research.
	 */
	private final Set<Advance> forbiddenAdvances; // NOPMD by kingjon on
	// 2/10/08 10:53 PM

	/**
	 * Advances every player on this side has without having to research them
	 * him-, her-, or itself.
	 */
	private final Set<Advance> universalAdvances; // NOPMD by kingjon on

	// 2/10/08 10:53 PM

	/**
	 * Constructor.
	 * 
	 * @param baseModule
	 *            The root of the module tree.
	 */
	public Side(final Module baseModule) {
		forbiddenAdvances = new HashSet<Advance>();
		universalAdvances = new HashSet<Advance>();
		players = new HashSet<Player>();
		moduleTree = baseModule;
	}

	/**
	 * Discover an advance (all players on this side gain it).
	 * 
	 * @param advance
	 *            The advance to discover
	 */
	public void discoverAdvance(final Advance advance) {
		if (forbiddenAdvances.contains(advance)) {
			throw new IllegalArgumentException(
					"Side tried to discover a forbidden advance");
		} else if (universalAdvances.contains(advance)) {
			return;
		} else {
			universalAdvances.add(advance);
			for (final Player p : players) {
				p.discoverAdvance(advance);
			}
			advance.normativeEffect(moduleTree);
		}
	}

	/**
	 * @param getter
	 *            a set that becomes the advances players on this side are
	 *            unable to discover
	 */
	public void getForbiddenAdvances(final Set<Advance> getter) {
		getter.addAll(forbiddenAdvances);
		getter.retainAll(forbiddenAdvances);
	}

	/**
	 * @return the root of the module tree
	 */
	public Module getModuleTree() {
		return moduleTree;
	}

	/**
	 * @param getter
	 *            a set to turn into the players on this side
	 */
	public void getPlayers(final Set<Player> getter) {
		getter.addAll(players);
		getter.retainAll(players);
	}

	/**
	 * @param getter
	 *            becomes the set of the advances all players on this side
	 *            automatically have
	 */
	public void getUniversalAdvances(final Set<Advance> getter) {
		getter.addAll(universalAdvances);
		getter.retainAll(universalAdvances);
	}

	/**
	 * @param theModuleTree
	 *            the root of the module tree TODO: Sanity checking here ...
	 */
	public void setModuleTree(final Module theModuleTree) {
		moduleTree = theModuleTree;
	}
}
