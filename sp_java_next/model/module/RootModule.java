package model.module;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import model.location.Location;

/**
 * The root of the module tree. A Singleton class.
 * 
 * @author Jonathan Lovelace.
 */
public final class RootModule implements Module, Serializable {
	/**
	 * The single root module of the tree.
	 */
	private static RootModule root;

	/**
	 * A lock to synchronize on (to avoid a synchronized method)
	 */
	private static Lock lock = new ReentrantLock();

	/**
	 * 
	 */
	private static final long serialVersionUID = -2504997378608944492L;

	/**
	 * @return The single root module of the tree.
	 */
	public static RootModule getRootModule() {
		synchronized (lock) {
			if (root == null) {
				root = new RootModule();
			}
		}
		return root;
	}

	/**
	 * This module's direct children
	 */
	private final transient Set<Module> children;

	/**
	 * Private because of singleton pattern.
	 */
	private RootModule() {
		children = new HashSet<Module>();
	}

	/**
	 * Add a module as a child.
	 * 
	 * @param child
	 *            The new child
	 */
	public void addChild(final Module child) {
		children.add(child);
	}

	/**
	 * The root module doesn't really exist anywhere, and so we
	 * 
	 * @return null
	 */
	@Override
	public Location getLocation() {
		return null;
	}

	/**
	 * The root module by definition doesn't have a parent, and so we
	 * 
	 * @return null
	 */
	@Override
	public Module getParent() {
		return null;
	}

	/**
	 * Since the root module doesn't exist, it can't move, and so
	 * 
	 * @return false
	 */
	@Override
	public boolean isMobile() {
		return false;
	}

	/**
	 * The root module isn't "top level", i.e. can't move, attack, take orders,
	 * etc. on its own, and so
	 * 
	 * @return false
	 */
	@Override
	public boolean isTopLevel() {
		return false;
	}

	/**
	 * Remove a module as a child.
	 * 
	 * @param child
	 *            The module to be removed.
	 */
	public void removeChild(final Module child) {
		children.remove(child);
	}

	/**
	 * The root module can't take an attack.
	 * 
	 * @param attacker
	 *            ignored
	 */
	@Override
	public void takeAttack(@SuppressWarnings("unused")
	final Weapon attacker) {
		throw new IllegalStateException(
				"The root of the module tree doesn't really exist.");
	}

	/**
	 * Have our children take upkeep.
	 */
	@Override
	public void upkeep(final long interval) {
		for (final Module mod : children) {
			mod.upkeep(interval);
		}
	}
}
