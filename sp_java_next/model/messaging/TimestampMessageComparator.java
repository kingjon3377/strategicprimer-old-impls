package model.messaging;

import java.io.Serializable;
import java.util.Comparator;

// ESCA-JAVA0044:
// ESCA-JAVA0235:
/**
 * A comparator for Messages. The ordering is not what you might expect -- the
 * earlier a message is, the "greater" it is -- because Messages are to be
 * handled in a priority queue, with misorderings (due usually to later messages
 * arriving earlier) handled by re-timestamping. Note that this comparator is
 * not necessarily consistent with equals as defined by the Message class, since
 * it concerns itself only with timestamps and priorities.
 * 
 * @author Jonathan Lovelace
 * 
 */
public final class TimestampMessageComparator implements Comparator<Message>,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5552122955935461392L;
	/**
	 * Result based on arrival times.
	 */
	private static final int ARRIVAL_TIME = 2;
	/**
	 * Result based on target times.
	 */
	private static final int TARGET_TIME = 3;
	/**
	 * Compare two Messages based on target timestamp and priority.
	 * 
	 * @param msg1
	 *            The first message
	 * @param msg2
	 *            The second message
	 * @return The result of the comparison
	 */
	public int compare(final Message msg1, final Message msg2) {
		return (msg1.getTargetTime() == msg2.getTargetTime() ? (msg1.getArrivalTime() == msg2.getArrivalTime() ? (msg1.getPriority()
				.equals(msg2.getPriority()) ? 0
				: (msg1.getPriority().compareTo(msg2.getPriority()) > 0 ? 1 : -1))
				: (msg1.getArrivalTime() > msg2.getArrivalTime() ? -ARRIVAL_TIME : ARRIVAL_TIME))
				: (msg1.getTargetTime() > msg2.getTargetTime() ? -TARGET_TIME : TARGET_TIME));
	}
}
