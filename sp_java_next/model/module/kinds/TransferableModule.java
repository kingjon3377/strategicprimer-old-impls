package model.module.kinds;

import model.module.Module;
import model.player.IPlayer;

/**
 * A module whose owner can be changed. (Most, if not all, modules should
 * implement this; perhaps it should be folded back into Module.)
 * 
 * @author Jonathan Lovelace
 * 
 */
public interface TransferableModule extends Module {
	/**
	 * @param owner the module's new owner
	 */
	void setOwner(final IPlayer owner);
}
