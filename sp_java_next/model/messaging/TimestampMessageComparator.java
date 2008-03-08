package model.messaging;

import java.io.Serializable;
import java.util.Comparator;

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
	 * Compare two Messages based on target timestamp and priority
	 * 
	 * @param msg1
	 *            The first message
	 * @param msg2
	 *            The second message
	 * @return The result of the comparison
	 */
	public int compare(final Message msg1, final Message msg2) {
		return (msg1.targetTime == msg2.targetTime ? (msg1.arrivalTime == msg2.arrivalTime ? (msg1.priority
				.equals(msg2.priority) ? 0 : (msg1.priority
				.compareTo(msg2.priority) > 0 ? 1 : -1))
				: (msg1.arrivalTime > msg2.arrivalTime ? -2 : 2))
				: (msg1.targetTime > msg2.targetTime ? -3 : 3));
	}
}
