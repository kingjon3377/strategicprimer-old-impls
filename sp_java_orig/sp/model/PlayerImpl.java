package sp.model;

/**
 * A temporary class to make compile errors that should show up in superclasses show up.
 * @author Jonathan Lovelace
 */
public class PlayerImpl extends Player {
	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = -4353236286130131126L;
	/**
	 * Send an order to a player. FIXME: Implement
	 * @param order the order to send
	 * @param target the player to send it to
	 */
	@Override
	public void sendOrder(final Order order, final Player target) {
		// TODO Auto-generated method stub
	}
	/**
	 * Send a report to a player
	 * @param report the report to send
	 * @param target the player to send it to
	 */
	@Override
	public void sendReport(final Report report, final Player target) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Take an order from a player
	 * @param order the order to receive
	 * @param sender the player that sent it
	 */
	@Override
	public void takeOrder(final Order order, final Player sender) {
		// TODO Auto-generated method stub
	}
	/**
	 * Take a report
	 * @param report the report to receive
	 * @param sender the player that sent it
	 */
	@Override
	public void takeReport(final Report report, final Player sender) {
		// TODO Auto-generated method stub
	}
	/**
	 * Attack a module (with one of the player's modules
	 * FIXME: Implement
	 * @param target The module to attack
	 */
	@Override
	public void attack(final Module target) {
		// TODO Auto-generated method stub
	}

	/**
	 * Check whether this can attack the given module.
	 * FIXME: Implement.
	 * @param target The module to attack
	 * @return false (unimplemented)
	 */
	@Override
	public boolean checkAttack(final Module target) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * Check whether the player can make a ranged attack against the given module.
	 * FIXME: IMplement
	 * @param target The module to attack
	 * @return false (unimplemented)
	 */
	@Override
	public boolean checkRangedAttack(final Module target) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * A player is not a mobile module.
	 * @return false
	 */
	@Override
	public boolean mobile() {
		return false;
	}
	/**
	 * Make a ranged attack against a module
	 * FIXME: Implement (using subordinates)
	 * @param target The module to attack
	 */
	@Override
	public void rangedAttack(final Module target) {
		// TODO Auto-generated method stub
	}
	/**
	 * Does nothing; a player cannot be "mobile."
	 * @param _mobile ignored
	 */
	@Override
	public void setMobile(final boolean _mobile) {
		// Do nothing
	}
	/**
	 * Mark this to be pruned at the end of the turn
	 */
	@Override
	public void delete() {
		this.deleted = true;
	}
}
