package model.messaging;

/**
 * A message from one module to another. Messages that are not Orders are
 * presumed to be informative rather than imperative.
 * 
 * TODO: Should this be an abstract class?
 * 
 * TODO: Look into CORBA, etc.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class Message {
	/**
	 * The various priorities I can think of. TODO: Evaluate this decision
	 * 
	 * @author Jonathan Lovelace
	 */
	public enum Priority {
		/**
		 * Lowest priority.
		 */
		LOWEST,

		/**
		 * Slightly higher.
		 */
		VERY_LOW,

		/**
		 * Low priority.
		 */
		LOW,

		/**
		 * Somewhat low priority.
		 */
		MEDIUM_LOW,

		/**
		 * Medium priority.
		 */
		MEDIUM,

		/**
		 * Somewhat high priority.
		 */
		MEDIUM_HIGH,

		/**
		 * High priority.
		 */
		HIGH,

		/**
		 * Even higher priority.
		 */
		VERY_HIGH,

		/**
		 * Highest priority.
		 */
		HIGHEST
	}

	/**
	 * "Time" the message arrived.
	 */
	private final long arrivalTime;
	/**
	 * "Time" the message is intended to be dealt with (usually the same as the
	 * arrival time unless it's an order).
	 */
	private long targetTime = 0;
	/**
	 * Message priority. Messages are ordered first by targetTime, then by
	 * arrivalTime, then by priority. (Those last two might be switched, but the
	 * algorithm for dealing with messages a module isn't ready to handle
	 * involves changing the timestamps.)
	 */
	private Priority priority;

	/**
	 * Constructor.
	 * 
	 * @param timestamp
	 *            When the message arrived TODO: Check that this is sane
	 * @param targTime
	 *            When the message should be dealt with, if possible TODO: Add a
	 *            flag on 'what should be done if this doesn't get dealt with in
	 *            time?'
	 * @param pri
	 *            The priority of the message
	 * 
	 * TODO: Add constructors with default values for timestamp (some way of
	 * getting "now" from the Game object -- and we need a singleton pattern for
	 * that), targetTime (arrivalTime), and priority (medium).
	 */
	public Message(final long timestamp, final long targTime,
			final Priority pri) {
		arrivalTime = timestamp;
		targetTime = targTime;
		priority = pri;
	}

	/**
	 * @return the time the message arrived
	 */
	public long getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * @return the priority of the message
	 */
	public Priority getPriority() {
		return priority;
	}

	/**
	 * @return the time at which the message should be dealt with
	 */
	public long getTargetTime() {
		return targetTime;
	}

	/**
	 * @param pri
	 *            the priority of the message
	 */
	public void setPriority(final Priority pri) {
		this.priority = pri;
	}

	/**
	 * @param tTime
	 *            the time at which the message should be dealt with
	 */
	public void setTargetTime(final long tTime) {
		this.targetTime = tTime;
	}
}

