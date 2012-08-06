package model.module.kinds;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import model.location.Location;
import model.module.Module;
import model.player.IPlayer;

/**
 * The root of the module tree. A Singleton class.
 * 
 * @author Jonathan Lovelace.
 */
public final class RootModule implements Module, Serializable {
	/**
	 * The single root module of the tree.
	 */
	public static final RootModule ROOT_MODULE = new RootModule();

	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = -2504997378608944492L;

	/**
	 * This module's direct children.
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
	 * The root module doesn't really exist anywhere. So we
	 * 
	 * @return null
	 */
	public Location getLocation() {
		return null;
	}

	/**
	 * The root module by definition doesn't have a parent. So we
	 * 
	 * @return null
	 */
	public Module getParent() {
		return null;
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
	 * 
	 * @param newChildren
	 *            The new set to join to the old set of children.
	 */
	public void setChildren(final Set<Module> newChildren) {
		children.addAll(newChildren);
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
	public long getUuid() {
		return 0;
	}

	/**
	 * This method is required by the Module interface (since every other module
	 * needs it) but is meaningless here.
	 * @param location ignored
	 */
	@Override
	public void setLocation(final Location location) {
		// Do nothing.
	}

	/**
	 * Like setting the location, this is meaningless.
	 */
	public void die() {
		throw new IllegalStateException("Asked the root module to die");
	}

	/**
	 * @return the name of the RootModule
	 */
	@Override
	public String getName() {
		return "RootModule";
	}

	/**
	 * This is meaningless for THE root module, though if this weren't singleton
	 * it would be meaningful for EACH root module.
	 * 
	 * @return null
	 */
	@Override
	public IPlayer getOwner() {
		return null;
	}
}
