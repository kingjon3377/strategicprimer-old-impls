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
	 * The various priorities I can think of TODO: evaluate this decision
	 * 
	 * @author Jonathan Lovelace
	 */
	public enum Priority {
		/**
		 * Lowest priority
		 */
		LOWEST,

		/**
		 * Slightly higher
		 */
		VERY_LOW,

		/**
		 * Low priority
		 */
		LOW,

		/**
		 * Somewhat low priority
		 */
		MEDIUM_LOW,

		/**
		 * Medium priority
		 */
		MEDIUM,

		/**
		 * Somewhat high priority
		 */
		MEDIUM_HIGH,

		/**
		 * High priority
		 */
		HIGH,

		/**
		 * Even higher priority
		 */
		VERY_HIGH,

		/**
		 * Highest priority
		 */
		HIGHEST
	}

	/**
	 * "Time" the message arrived
	 */
	protected final long arrivalTime; // NOPMD by kingjon on 2/10/08 10:03 PM

	/**
	 * "Time" the message is intended to be dealt with (usually the same as the
	 * arrival time unless it's an order)
	 */
	protected long targetTime = 0;

	/**
	 * Message priority. Messages are ordered first by targetTime, then by
	 * arrivalTime, then by priority. (Those last two might be switched, but the
	 * algorithm for dealing with messages a module isn't ready to handle
	 * involves changing the timestamps.)
	 */
	protected Priority priority;

	/**
	 * Constructor.
	 * 
	 * @param timestamp
	 *            When the message arrived TODO: Check that this is sane
	 * @param _targetTime
	 *            When the message should be dealt with, if possible TODO: Add a
	 *            flag on 'what should be done if this doesn't get dealt with in
	 *            time?'
	 * @param _priority
	 *            The priority of the message
	 * 
	 * TODO: Add constructors with default values for timestamp (some way of
	 * getting "now" from the Game object -- and we need a singleton pattern for
	 * that), targetTime (arrivalTime), and priority (medium).
	 */
	public Message(final long timestamp, final long _targetTime,
			final Priority _priority) {
		arrivalTime = timestamp;
		targetTime = _targetTime;
		priority = _priority;
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
	 * @param _priority
	 *            the priority of the message
	 */
	public void setPriority(final Priority _priority) {
		this.priority = _priority;
	}

	/**
	 * @param _targetTime
	 *            the time at which the message should be dealt with
	 */
	public void setTargetTime(final long _targetTime) {
		this.targetTime = _targetTime;
	}
}

