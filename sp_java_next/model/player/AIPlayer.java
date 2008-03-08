package model.player;

import model.module.Module;

/**
 * An AI ("computer") player on the local machine.
 * 
 * @author Jonathan Lovelace
 * 
 * TODO: Should this be abstract, to allow plugable AI algorithm modules?
 */
public class AIPlayer extends Player {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1310880988917379941L;

	/**
	 * Types of AIs.
	 */
	public enum AIType {
		// TODO: Specify
	}

	/**
	 * Constructor
	 * 
	 * @param associatedModule
	 *            The module that represents this player in the game-world.
	 */
	public AIPlayer(Module associatedModule) {
		super(associatedModule);
	}
}
