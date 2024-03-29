package model.messaging;

/**
 * An order, a message sent from a superior to a unit or building under him.
 * 
 * @author Jonathan Lovelace
 */
public class Order extends Message {
	/**
	 * Types of orders; used here and in Result.
	 * 
	 * @author Jonathan Lovelace
	 */
	public enum OrderType {
		/**
		 * Attack another module.
		 */
		ATTACK,
		/**
		 * Defend (another module or merely oneself? -- the latter meaning
		 * "total defense until ordered otherwise"
		 */
		DEFEND,
		/**
		 * Move to a location.
		 */
		MOVE,
		/**
		 * Build something.
		 */
		BUILD
	}

	/**
	 * What kind of order this is.
	 */
	private OrderType orderType;

	/**
	 * Constructor. TOOD: Figure out some way of passing in arguments to the
	 * order -- what to build, where to move, what to attack, etc.
	 * 
	 * @param timestamp
	 *            The time the order arrived
	 * @param targetTime
	 *            The time the order should be executed
	 * @param priority
	 *            The priority of the order
	 * @param type
	 *            What kind of order this is
	 * @see Message#Message(long, long, Priority)
	 */
	public Order(final long timestamp, final long targetTime,
			final Priority priority, final OrderType type) {
		super(timestamp, targetTime, priority);
		orderType = type;
	}

	/**
	 * Accessor.
	 * 
	 * @return what kind of order this is
	 */
	public OrderType getOrderType() {
		return orderType;
	}

	/**
	 * @param type
	 *            what kind of order this is
	 */
	public void setOrderType(final OrderType type) {
		orderType = type;
	}
}
