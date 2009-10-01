package model.player;
/**
 * An interface for Player objects to implement
 * @author Jonathan Lovelace
 * 
 * Most implementers should also implement Renameable.
 *
 */
public interface IPlayer {
	/**
	 * @return the player's (code) name
	 */
	String getName();
	/**
	 * @return the player's player number
	 */
	int getNumber();
	/**
	 * @param num the player's new player number
	 */
	void setNumber(final int num);
}
