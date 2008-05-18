package model.player;

import java.io.Serializable;
import java.net.InetAddress;

import model.module.Module;

/**
 * A remote player (somewhere across the network; not necessarily human)
 * 
 * TODO: Implement.
 * 
 * TODO: Should there be a RemoteHumanPlayer and a RemoteAIPlayer?
 * 
 * @author Jonathan Lovelace
 * 
 */
public class RemotePlayer extends Player implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4274908939314028492L;
	/**
	 * The player's IP address (should this be a network socket instead? Does
	 * this even belong here?)
	 */
	private final transient InetAddress ipAddress;

	/**
	 * Constructor. Decisions made only to make warnings go away.
	 * @param address The IP address of the player
	 * @param associatedModule The module that represents the player in the game-world
	 */
	public RemotePlayer(final InetAddress address, final Module associatedModule) {
		super(associatedModule);
		ipAddress = address;
	}
	
	/**
	 * @return The IP address of the player
	 */
	public InetAddress getIpAddress() {
		return ipAddress;
	}
}
