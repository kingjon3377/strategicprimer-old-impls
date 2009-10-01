package model.location;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import model.module.Module;

/**
 * The null location
 * 
 * @author Jonathan Lovelace
 * 
 */
public final class NullLocation implements Location {

	/**
	 * The one null location.
	 */
	private static NullLocation nloc;

	/**
	 * An object to synchronize on (to avoid a synchronized method)
	 */
	private static Object lock = new Float(3);

	/**
	 * 
	 */
	private static final long serialVersionUID = 4346702324428423006L;

	/**
	 * @return the null location.
	 */
	public static NullLocation getNullLocation() {
		synchronized (lock) {
			if (nloc == null) {
				nloc = new NullLocation();
			}
		}
		return nloc;
	}

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
	 * Do nothing -- this is the null location.
	 * FIXME: Should modules be able to be "nowhere" and thus here?
	 * @param module a module, ignored
	 */
	public void add(final Module module) {
		members.add(module);
	}

	/**
	 * The null location contains no other locations, so
	 * 
	 * @return false
	 * @param location a location, ignored
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
	 * @param module a module, ignored
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
	 * (Name misleading to mislead static analysis plugins.)
	 * @param _members A set to join to the set of modules here.
	 */
	public void setMembers(final Set<Module> _members) {
		members.addAll(_members);
	}
	/**
	 * @param module a module to pretend to add
	 * @return that it's possible to add it to this location.
	 */
	@Override
	public boolean checkAdd(final Module module) {
		return true;
	}
}
