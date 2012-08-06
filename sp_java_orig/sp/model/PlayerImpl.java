package sp.model;


/**
 * A temporary class to make compile errors that should show up in superclasses
 * show up.
 * 
 * @author Jonathan Lovelace
 */
public class PlayerImpl extends Player {
	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = -4353236286130131126L;

	/**
	 * Send an order to a player. FIXME: Implement
	 * 
	 * @param order
	 *            the order to send
	 * @param target
	 *            the player to send it to
	 */
	@Override
	public void sendOrder(final Order order, final Player target) {
		// TODO Auto-generated method stub
	}

	/**
	 * Send a report to a player
	 * 
	 * @param report
	 *            the report to send
	 * @param target
	 *            the player to send it to
	 */
	@Override
	public void sendReport(final Report report, final Player target) {
		// TODO Auto-generated method stub

	}

	/**
	 * Take an order from a player
	 * 
	 * @param order
	 *            the order to receive
	 * @param sender
	 *            the player that sent it
	 */
	@Override
	public void takeOrder(final Order order, final Player sender) {
		// TODO Auto-generated method stub
	}

	/**
	 * Take a report
	 * 
	 * @param report
	 *            the report to receive
	 * @param sender
	 *            the player that sent it
	 */
	@Override
	public void takeReport(final Report report, final Player sender) {
		// TODO Auto-generated method stub
	}

	/**
	 * A player is not a mobile module.
	 * 
	 * @return false
	 */
	@Override
	public boolean mobile() {
		return false;
	}

	/**
	 * Mark this to be pruned at the end of the turn
	 */
	@Override
	public void delete() {
		this.deleted = true;
	}
	/**
	 * Take an attack from a module
	 * @param attacker The attacking module
	 */
	@Override
	public void takeAttack(final Weapon attacker) {
		if (attacker instanceof Player) {
			throw new IllegalStateException("Not implemented yet");
		}
		throw new IllegalStateException(
				"A player can't be attacked by a non-player");

	}
}
