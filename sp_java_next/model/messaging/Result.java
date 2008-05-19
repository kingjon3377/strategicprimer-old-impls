package model.messaging;

/**
 * The results from carrying out an order, sent to the superior officer.
 * 
 * @author Jonathan Lovelace
 */
public class Result extends Message {

	/**
	 * The order this is the report of the result of
	 */
	private final Order order; // NOPMD by kingjon on 2/9/08 3:03 PM

	/**
	 * Constructor
	 * 
	 * @param _arrivalTime
	 *            When the report arrived
	 * @param _targetTime
	 *            When the report should be dealt with
	 * @param _priority
	 *            The report's priority
	 * @param _order
	 *            The order this is the report of the result of
	 */
	public Result(final long _arrivalTime, final long _targetTime,
			final Priority _priority, final Order _order) {
		super(_arrivalTime, _targetTime, _priority);
		order = _order;
	}

	/**
	 * Accessor.
	 * 
	 * @return The order this is the report of the result of
	 */
	public Order getOrder() {
		return this.order;
	}
}
