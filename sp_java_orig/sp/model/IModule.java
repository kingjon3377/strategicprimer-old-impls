package sp.model;

import java.io.Serializable;


/**
 * An extracted interface for a module.
 * 
 * @author Jonathan Lovelace
 * 
 */
public interface IModule extends Serializable {

	/**
	 * @return the tile the module is on
	 */
	MoveTarget getLocation();

	/**
	 * @return the module's name
	 */
	String getName();

	/**
	 * @return which player owns the module
	 */
	int getOwner();

	/**
	 * @return the module's parent in the tree
	 */
	IModule getParent();

	/**
	 * @return the module's UUID.
	 */
	long getUuid();

	/**
	 * @return whether to remove this module
	 */
	boolean isDeleted();

	/**
	 * @return whether the module can move
	 */
	boolean mobile();

	/**
	 * @param location
	 *            the module's new location
	 */
	void setLocation(MoveTarget location);

//	/**
//	 * @param mobile
//	 *            whether the module can move
//	 */
//	void setMobile(boolean mobile);

//	/**
//	 * @param name
//	 *            the module's "name"
//	 */
//	void setName(String name);

	/**
	 * @param owner
	 *            which player owns the module
	 */
	void setOwner(int owner);

	/**
	 * @param parent
	 *            the module's parent in the tree
	 */
	void setParent(IModule parent);

	/**
	 * @param uuid
	 *            the module's UUID.
	 */
	void setUuid(long uuid);

	/**
	 * This is called during an attack on the module, as the last step,
	 * immediately after damage has been dealt. It takes care of removing the
	 * module if it has zero HP, so overriding implementations should call
	 * super.takeAttack().
	 * 
	 * @param attacker
	 *            The attacking module (most likely a unit)
	 */
	void takeAttack(Weapon attacker);

}
