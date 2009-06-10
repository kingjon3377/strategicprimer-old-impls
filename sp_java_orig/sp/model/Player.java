package sp.model;

import java.awt.Point;
import java.util.HashSet;

/**
 * The Player class is the main class a user interface should interact
 * with; it represents the role a player (human or AI) is playing. It
 * knows the player's rank, coordinates, superior officer, immediate
 * subordinates; and associated module (unit or building) that
 * represents the player in the game-world.
 * 
 * @author kingjon Jonathan Lovelace
 */

public abstract class Player extends Module {
	// TODO: Should we extend Module, or not?

	/**
	 * 
	 */
	private static final long serialVersionUID = 6226483273770602765L;

	/**
	 * The player's rank. TODO: Enumerate
	 */
	protected int myRank = 0;

	/**
	 * Our coordinates (mostly for use in first-person interface).
	 */
	protected Point myLocation1;
	/**
	 * Our superior officer -- we should only accept orders from
	 * ourself, from him, or from one of his superiors.
	 */
	protected Player mySuperior;

	/**
	 * Our subordinates; we can only give orders to ourself or to
	 * them. TODO: What data structure should we use?
	 */
	protected HashSet<Player> mySubordinates; // NOPMD

	/**
	 * Our associated unit or building in the game-world.
	 */
	protected Weapon associatedModule;

	/**
	 * Send an order. Target can be either ourself -- for
	 * execution in our next turn -- or a subordinate. TODO:
	 * Implement this
	 * 
	 * @param order
	 *            The order
	 * @param target
	 *            The player to whom the order is given
	 */
	public abstract void sendOrder(Order order, Player target);

	/**
	 * Send a report. Used after an order is executed, or at other
	 * times a superior needs information. TODO: Implement.
	 * 
	 * @param report
	 *            The report
	 * @param target
	 *            The player the report is sent to.
	 */
	public abstract void sendReport(Report report, Player target);

	/**
	 * Receive an order, queueing it for execution in our next
	 * turn if it is valid. TODO: Implement.
	 * 
	 * @param order
	 *            The order
	 * @param sender
	 *            The player from whom the order came.
	 */
	public abstract void takeOrder(Order order, Player sender);

	/**
	 * Receive a report. TODO: Implement.
	 * 
	 * @param report
	 *            The report
	 * @param sender
	 *            The player the report came from.
	 */
	public abstract void takeReport(Report report, Player sender);
}
