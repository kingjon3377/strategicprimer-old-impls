package model.module.features;

import model.module.kinds.TransferableModule;
import model.player.IPlayer;


/**
 * A canal, a man-made river.
 * @author kingjon
 *
 */
public class Canal extends River implements TransferableModule {
	/**
	 * Constructor.
	 */
	public Canal() {
		super();
		setName("Canal");
	}
	/**
	 * @return a description of the feature
	 */
	@Override
	public String description() {
		return "A canal is here.";
	}
	/**
	 * The moduleID of all non-subclass Canals.
	 */
	private static final int MODULE_ID = 11;
	/**
	 * @return the moduleID indicating that this is a Canal.
	 */
	@Override
	public int getModuleID() {
		return MODULE_ID;
	}
	/**
	 * @param newOwner the canal's new owner
	 */
	@Override
	public final void setOwner(final IPlayer newOwner) {
		owner = newOwner;
	}
	/**
	 * The canal's owner.
	 */
	private IPlayer owner;
	/**
	 * @return the canal's owner
	 */
	@Override
	public final IPlayer getOwner() {
		return owner;
	}
}
