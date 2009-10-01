package pathfinding;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple sorted list
 * 
 * @author kevin
 * 
 *         Adapted to use generics and extend, rather than encapsulate,
 *         ArrayList by
 * @author Jonathan Lovelace
 * @param <T>
 *            What this is a list of
 */
public class SortedList<T extends Comparable<T>> extends ArrayList<T> {

	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = -892161502148230780L;

	/**
	 * Retrieve the first element from the list
	 * 
	 * @return The first element from the list
	 */
	public T first() {
		return get(0);
	}

	/**
	 * Add an element to the list - causes sorting
	 * 
	 * @param element
	 *            The element to add
	 * @return whether adding it succeeded
	 */
	@Override
	public boolean add(final T element) {
		if (super.add(element)) {
			Collections.sort(this);
			return true; // NOPMD
		} else {
			return false;
		}
	}
}