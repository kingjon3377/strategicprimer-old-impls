package model.location;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import model.module.Module;
import model.module.kinds.RootModule;

/**
 * The null location.
 * 
 * @author Jonathan Lovelace
 * 
 */
public final class NullLocation implements Location {

	/**
	 * The one null location.
	 */
	public static final NullLocation NULL_LOC = new NullLocation();

	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = 4346702324428423006L;

	/**
	 * The modules in the null location.
	 */
	private final Set<Module> members;

	/**
	 * Private to allow Singleton pattern.
	 */
	private NullLocation() {
		members = new HashSet<Module>();
	}

	/**
	 * Do nothing -- this is the null location. FIXME: Should modules be able to
	 * be "nowhere" and thus here?
	 * 
	 * @param module
	 *            a module, ignored
	 */
	public void add(final Module module) {
		members.add(module);
	}

	/**
	 * The null location contains no other locations. So
	 * 
	 * @return false
	 * @param location
	 *            a location, ignored
	 */
	public boolean contains(final Location location) {
		return false;
	}

	/**
	 * @param module
	 *            the module to search for
	 * @return whether it is in the null location
	 */
	public boolean contains(final Module module) {
		return members.contains(module);
	}

	/**
	 * Do nothing -- this is the null location.
	 * 
	 * @param module
	 *            a module, ignored
	 */
	public void remove(final Module module) {
		members.remove(module);
	}

	/**
	 * @return a <i>read-only copy</i> of the modules here
	 */
	public Set<Module> getMembers() {
		return Collections.unmodifiableSet(members);
	}

	/**
	 * (Name misleading to mislead static analysis plugins.).
	 * 
	 * @param newMembers
	 *            A set to join to the set of modules here.
	 */
	public void setMembers(final Set<Module> newMembers) {
		members.addAll(newMembers);
	}

	/**
	 * @param module
	 *            a module to pretend to add
	 * @return that it's possible to add it to this location.
	 */
	@Override
	public boolean checkAdd(final Module module) {
		return true;
	}

	/**
	 * @return the modules in the null location.
	 */
	@Override
	public Set<Module> getModules() {
		return getMembers();
	}

	/**
	 * @param module
	 *            ignored
	 */
	@Override
	public void select(final Module module) {
		throw new IllegalStateException("The null location can't have a module selected");
	}
	/**
	 * @return the Module equivalent of null
	 */
	@Override
	public Module getSelected() {
		return RootModule.ROOT_MODULE;
	}
}
