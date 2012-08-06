package model.messaging;

/**
 * The results from carrying out an order, sent to the superior officer.
 * 
 * @author Jonathan Lovelace
 */
public class Result extends Message {

	/**
	 * The order this is the report of the result of.
	 */
	private final Order order; // NOPMD by kingjon on 2/9/08 3:03 PM

	/**
	 * Constructor.
	 * 
	 * @param arrivalTime
	 *            When the report arrived
	 * @param targetTime
	 *            When the report should be dealt with
	 * @param priority
	 *            The report's priority
	 * @param theOrder
	 *            The order this is the report of the result of
	 */
	public Result(final long arrivalTime, final long targetTime,
			final Priority priority, final Order theOrder) {
		super(arrivalTime, targetTime, priority);
		order = theOrder;
	}

	/**
	 * Accessor.
	 * 
	 * @return The order this is the report of the result of
	 */
	public Order getOrder() {
		return order;
	}
}
