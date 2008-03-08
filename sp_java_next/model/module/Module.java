package model.module;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.PriorityQueue;

import model.location.Location;
import model.messaging.Message;
import model.messaging.Order;
import model.messaging.Result;
import model.messaging.TimestampMessageComparator;

/**
 * A unit, building, weapon, wonder, resource, or other object in the game.
 * 
 * TODO: Should this really be final?
 * 
 * TODO: Proper data hiding ... I've been adding lots of getters/setters to
 * placate PMD.
 * 
 * @author Jonathan Lovelace
 */
public abstract class Module implements Serializable {
	/**
	 * Gleaned from the PriorityQueue or similar source, the size used on no-arg
	 * constructor but otherwise not allowed to be implied.
	 */
	private static final int QUEUE_DFLT_SIZE = 11;

	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = 3541005400450385744L;

	/**
	 * Where am I?
	 */
	protected Location location = null;
	/**
	 * Protected because user classes should use move() instead.
	 * @param loc The new location.
	 */
	protected abstract void setLocation(final Location loc);
	
	/**
	 * If I'm part of another Module, what Module am I part of? (If this is a
	 * top-level Module, this is null.) TODO: Is it really?
	 */
	protected Module parent = null;

	/**
	 * A module which can attack is a weapon. A module can have more than one
	 * weapon, hence the collection below. Any module that can attack, whether
	 * itself or by delegation, should implement this interface.
	 * 
	 * @author Jonathan Lovelace
	 */
	public interface Weapon extends Serializable {
		/**
		 * How much damage is the attack likely to do?
		 * 
		 * @param target
		 *            The module we might be attacking
		 * @return The projected damage
		 */
		int projectedDamage(Module target);

		/**
		 * Actually make the attack
		 * 
		 * @param target
		 *            The module we're attacking
		 */
		void attack(Module target);
	}

	/**
	 * Since there aren't function pointers in Java, we use callback interfaces.
	 * This is a module's action() function, which does any miscellaneous action
	 * (i.e. not an attack)
	 * 
	 * @author Jonathan Lovelace
	 */
	public interface ActionCallback {
		/**
		 * Any action that can be taken by the module.
		 */
		void action();
	}

	/**
	 * The actions I know how to perform.
	 */
	private Collection<ActionCallback> actionCallbacks;

	/**
	 * My submodules, including weapons, (possibly) armor, carried objects,
	 * passengers, etc.
	 * 
	 * TODO: Figure out which collection type to use.
	 */
	protected Collection<Module> modules;

	/**
	 * The appropriate AttackCallbacks from my weapons.
	 */
	protected Collection<Weapon> weapons;

	/**
	 * Take an attack from a specified module.
	 * 
	 * @see Weapon#attack(Module)
	 * @param attacker
	 *            The attacker
	 */
	public abstract void takeAttack(Module attacker);

	/**
	 * Execute upkeep functions. Should pass this on to submodules.
	 * 
	 * @param interval
	 *            How long it's been since the last time upkeep was
	 *            executed.
	 */
	public abstract void upkeep(long interval);
	/**
	 * My statistics. Some traditional "statistics" such as attack and defense
	 * are subsumed in callbacks, but some (such as a laser's remaining power)
	 * need to be encapsulated here.
	 */
	protected Statistics statistics;

	/**
	 * Messages that have been received and not dealt with.
	 * 
	 * @see #handleMessages() .
	 */
	private PriorityQueue<Message> pendingMessages; 

	/**
	 * Since all modules on each side form a tree, we can't rely on the parent
	 * field to tell us whether a Module should be able to move or not, and
	 * since all Modules should have locations (for FPS interface if nothing
	 * else) we can't rely on that either ...
	 * 
	 * TODO: Should this be folded into "mobile"?
	 */
	private boolean topLevel;

	/**
	 * Constructor. At present it just sets up the pending messages queue.
	 * 
	 * TODO: Implement more fully.
	 */
	protected Module() {
		constructor();
	}

	/**
	 * 
	 */
	private void constructor() {
		pendingMessages = new PriorityQueue<Message>(QUEUE_DFLT_SIZE,
				new TimestampMessageComparator());
		actionCallbacks = new HashSet<ActionCallback>();
	}

	/**
	 * Move to the specified location. Only implemented to make "is not read"
	 * warnings go away.
	 * 
	 * TODO: Implement properly
	 * 
	 * TODO: Use more specific exceptions.
	 * 
	 * @param loc
	 *            The location to go to.
	 * @throws UnableToMoveException
	 *             if we can't move.
	 */
	public void move(final Location loc) throws UnableToMoveException {
		if (!isMobile()) {
			throw new UnableToMoveException("I'm not able to move!");
		}
		if (location == null) {
			throw new UnableToMoveException("I don't know where I am!");
		}
		if (!topLevel) {
			throw new UnableToMoveException(
					"Not a top-level module; use parent instead");
		}
		location = loc;
	}

	// /**
	// * Attack the specified Module. If I can attack directly (or my weapons
	// are
	// * inferior to my direct-attack capability), do so; otherwise pass this on
	// * to my weapons.
	// *
	// * TODO: Implement better.
	// *
	// * @param target
	// * The module to be attacked.
	// */
	// public void attack(final Module target) {
	// modules.isEmpty(); // To make "unused" warning go away
	// int dmg = (attackCallback == null ? Integer.MIN_VALUE : attackCallback
	// .projectedDamage(target));
	// AttackCallback weapon = attackCallback;
	// for (AttackCallback ac : weapons) {
	// if (ac != null && ac.projectedDamage(target) > dmg) {
	// dmg = ac.projectedDamage(target);
	// weapon = ac;
	// }
	// }
	// if (weapon != null) {
	// weapon.attack(target);
	// }
	// sendResult(new Result(0, 0, Message.Priority.MEDIUM, null), parent); //
	// mitigate
	// // "unused"
	// // warning
	// // TODO: Actually make and send a proper result, since this method is
	// // void.
	// }

	/**
	 * Perform the specified action. I need some way of passing in parameters;
	 * perhaps a vector ...
	 * 
	 * TODO: Implement more fully TODO: Use more specific exceptions
	 * 
	 * @param index
	 *            Which action to perform
	 * @throws CannotPerformActionException
	 *             if I can't perform the specified action for whatever reason
	 */
	public void action(final int index) throws CannotPerformActionException {
		if (actionCallbacks == null || actionCallbacks.size() <= 0) {
			throw new CannotPerformActionException(
					"I don't know how to perform any actions");
		} else if (actionCallbacks.size() < index) {
			throw new CannotPerformActionException(
					"I don't know how to perform the action");
		}
	}

	/**
	 * Send the message msg to the target module.
	 * 
	 * @param msg
	 *            The message to be sent.
	 * @param target
	 *            The module to send the message to.
	 */
	public void sendMessage(final Message msg, final Module target) {
		// TODO: Implement
		target.receiveMessage(msg, this);
	}

	/**
	 * Take (and eventually deal with, on our turn if we're turn-based) a
	 * message.
	 * 
	 * @param msg
	 *            The message
	 * @param sender
	 *            The module that sent it.
	 */
	public void receiveMessage(final Message msg, final Module sender) {
		// TODO: Implement
		pendingMessages.add(msg);
		sender.getClass();
	}

	/**
	 * Give the target module the given order.
	 * 
	 * @param order
	 *            The order
	 * @param target
	 *            The
	 */
	public void order(final Order order, final Module target) {
		// TODO: Implement
		target.takeOrder(order, this);
	}

	/**
	 * Take (and eventually execute if it's proper to do so) an order.
	 * 
	 * @param order
	 *            The order
	 * @param sender
	 *            The module that sent it
	 */
	public void takeOrder(final Order order, final Module sender) {
		// TODO: Implement
		pendingMessages.add(order);
		sender.getClass();
	}

	/**
	 * Having dealt with an order, sends the results to the target module.
	 * 
	 * @param result
	 *            The Result object to be sent.
	 * @param target
	 *            The module to send it to.
	 */
	public void sendResult(final Result result, final Module target) {
		// TODO: Implement
		target.receiveResult(result, this);
	}

	/**
	 * Receive the results of an order.
	 * 
	 * @param result
	 *            The Results object being received
	 * @param sender
	 *            The module that sent it.
	 */
	public void receiveResult(final Result result, final Module sender) {
		// TODO: Implement
		pendingMessages.add(result);
		sender.getClass();
	}

	/**
	 * Handle as many of the messages in the queue as "time" allows. If the
	 * message at the top of the queue cannot be handled yet (usually due to
	 * earlier messages having arrived later), it is marked as having arrived
	 * "now" to move it back in the queue. If that doesn't fix it, its
	 * targetTime is incremented.
	 * 
	 * TODO: Implement.
	 * 
	 */
	public void handleMessages() {
		pendingMessages.element();
	}

	/**
	 * @return whether the module is able to move
	 */
	public abstract boolean isMobile();

	/**
	 * @param _modules
	 *            my submodules
	 */
	public void setModules(final Collection<Module> _modules) {
		modules = new HashSet<Module>(_modules);
	}

	/**
	 * @return whether the module is top-level.
	 */
	public abstract boolean isTopLevel();

	/**
	 * @return The module's location
	 */
	public abstract Location getLocation();

	/**
	 * @return the module's parent in the tree
	 */
	public abstract Module getParent();
	
	/**
	 * @param inStream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(final ObjectInputStream inStream) throws IOException, ClassNotFoundException {
		constructor();
		inStream.defaultReadObject();
	}
}
