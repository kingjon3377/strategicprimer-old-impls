package model.player;

import model.module.kinds.Renameable;

/**
 * A simple Player object.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class SimplePlayer implements IPlayer, Renameable {
	/**
	 * Constructor.
	 * 
	 * @param newName
	 *            The player's name
	 * @param num
	 *            The player's number
	 */
	public SimplePlayer(final String newName, final int num) {
		name = newName;
		number = num;
	}

	/**
	 * The player's name.
	 */
	private String name;
	/**
	 * The player's number.
	 */
	private int number;

	/**
	 * @return the player's name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @return the player's number
	 */
	@Override
	public int getNumber() {
		return number;
	}

	/**
	 * @param num
	 *            the player's new number
	 */
	@Override
	public void setNumber(final int num) {
		number = num;
	}

	/**
	 * @param newName
	 *            the player's new name
	 */
	@Override
	public void setName(final String newName) {
		name = newName;
	}

	/**
	 * @return a hash code
	 */
	@Override
	public int hashCode() {
		return number;
	}

	/**
	 * Because we override hashCode() ...
	 * 
	 * @param obj
	 *            another object
	 * @return whether this object is the same
	 */
	@Override
	public boolean equals(final Object obj) {
		return obj instanceof IPlayer && ((IPlayer) obj).getNumber() == number;
	}
}
