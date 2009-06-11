package model.module;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

/**
 * An encapsulation of various statistics. Some traditional "statistics" such as
 * attack and defense are subsumed in callbacks, but some (such as a laser's
 * remaining power) need to be encapsulated.
 * 
 * TODO: Should this extend Map<Stats,Number> rather than having one?
 * 
 * @author Jonathan Lovelace
 * 
 */
public class Statistics implements Serializable {

	/**
	 * The possible statistics. Not all modules will have all of them.
	 * 
	 * @author Jonathan Lovelace
	 */
	public enum Stats {
		// TODO: Enumerate
		/**
		 * Hit points
		 */
		HP,
		/**
		 * Maximum hit points
		 */
		MAX_HP
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4949435769559622883L;

	/**
	 * The statistics
	 */
	protected final EnumMap<Stats, Number> stats; // NOPMD by kingjon on 2/10/08
												// 10:49 PM

	/**
	 * Constructor.
	 */
	public Statistics() {
		stats = new EnumMap<Stats, Number>(Stats.class);
	}

	/**
	 * @return the statistics
	 */
	public Map<Stats, Number> getStats() {
		return stats;
	}
}
