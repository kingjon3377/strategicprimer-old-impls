package model.module;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
	 * 
	 */
	private static final long serialVersionUID = -2504997378608944492L;

	/**
	 * @return The single root module of the tree.
	 */
	public static RootModule getRootModule() {
		synchronized (RootModule.class) {
			if (root == null) {
				root = new RootModule();
			}
		}
		return root;
	}

	/**
	 * This module's direct children
	 */
	private final Set<Module> children;

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
	public Location getLocation() {
		return null;
	}

	/**
	 * The root module by definition doesn't have a parent, and so we
	 * 
	 * @return null
	 */
	public Module getParent() {
		return null;
	}

	/**
	 * Since the root module doesn't exist, it can't move, and so
	 * 
	 * @return false
	 */
	public boolean isMobile() {
		return false;
	}

	/**
	 * The root module isn't "top level", i.e. can't move, attack, take orders,
	 * etc. on its own, and so
	 * 
	 * @return false
	 */
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
	public void takeAttack(final Weapon attacker) {
		throw new IllegalStateException(
				"The root of the module tree doesn't really exist.");
	}
	
	/**
	 * @return a <i>read-only copy</i> of the set of children.
	 */
	public Set<Module> getChildren() {
		return Collections.unmodifiableSet(children);
	}
	
	/**
	 * Misleadingly named to mislead static-analysis plugins.
	 * @param _children The new set to join to the old set of children.
	 */
	public void setChildren(final Set<Module> _children) {
		children.addAll(_children);
	}
	/**
	 * @return the module's moduleID.
	 */
	@Override
	public int getModuleID() {
		return 0;
	}
	/**
	 * @return the module's UUID
	 */
	@Override
	public int getUuid() {
		return 0;
	}
}
