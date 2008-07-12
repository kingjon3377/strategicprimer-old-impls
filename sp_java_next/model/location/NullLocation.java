package model.location;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
	 * A lock to synchronize on (to avoid a synchronized method)
	 */
	private static Lock lock = new ReentrantLock();

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
	 */
	@Override
	public void add(final Module module) {
		members.add(module);
	}

	/**
	 * The null location contains no other locations, so
	 * 
	 * @return false
	 */
	@Override
	public boolean contains(@SuppressWarnings("unused")
	final Location location) {
		return false;
	}

	/**
	 * @param module
	 *            the module to search for
	 * @return whether it is in the null location
	 */
	@Override
	public boolean contains(final Module module) {
		return members.contains(module);
	}

	/**
	 * Do nothing -- this is the null location.
	 */
	@Override
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
}
