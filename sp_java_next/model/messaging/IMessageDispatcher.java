package model.messaging;

import model.module.Module;

/**
 * A centralized dispatcher for inter-module messaging.
 * @author Jonathan Lovelace
 */
public interface IMessageDispatcher {
	/**
	 * Gleaned from the PriorityQueue or similar source, the size used on no-arg
	 * constructor but otherwise not allowed to be implied.
	 */
	int QUEUE_DFLT_SIZE = 11;

	/**
	 * Send a message from sender to receiver
	 * @param receiver The module receiving the message
	 * @param sender The module sending the message
	 * @param message The message being sent
	 */
	void message(Module receiver, Module sender, Message message);
	
	/**
	 * Increment the clock and evaluate all actions that would have taken place.
	 */
	void tick();
}
