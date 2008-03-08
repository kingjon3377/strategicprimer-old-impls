package model.player;

import model.module.Module;

/**
 * A human player on the local machine.
 * 
 * TODO: Implement.
 * 
 * @author Jonathan Lovelace
 */
public class LocalPlayer extends Player {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4415831057829296712L;

	/**
	 * Constructor. Decisions here only made to make warnings go away
	 * @param name The player's "name"
	 * @param associatedModule The module that represents the player in the game-world
	 */
	public LocalPlayer(String name, Module associatedModule) {
		super(associatedModule);
		setName(name);
	}
}
