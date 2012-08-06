package model.module.kinds;

import java.util.Collection;
import java.util.Set;

import model.module.Module;
import model.module.Vault;


/**
 * A unit in the game. (TODO: Describe better.)
 * 
 * @author Jonathan Lovelace
 * 
 */
public interface Unit extends Module {
	/**
	 * A person can have several jobs. (Or "classes" -- but that's
	 * a reserved word in lowercase and an existing class
	 * capitalized in Java.)
	 * 
	 * A job represents training. To avoid the problems of
	 * inheritance, using this training is also put into the job
	 * rather than into the unit. Using the training may require
	 * specific equipment (TODO: Something like APT's
	 * replacement/other-name mechanism is needed here so a better
	 * version of something, sufficiently superior to become its
	 * own module type rather than a higher level one, will still
	 * work without modification to jobs requiring the old one ...
	 * and something like Gentoo's virtuals for categorization
	 * while we're at it.)
	 * 
	 * TODO: But what about multi-person Units? (Can they have
	 * Jobs?) And what about upgrades? (And with multi-class
	 * characters how can we enforce mutually exclusive branching
	 * upgrades?)
	 * 
	 * This is an inner class because it is a Unit that has
	 * equipment, not the Job, and it's not acceptable to have to
	 * pass the Unit into every call (and to have to have the Unit
	 * expose functionality when there isn't a friend keyword in
	 * Java. Though we seem to be doing that already.)
	 * 
	 * @author Jonathan Lovelace
	 * 
	 */
	interface Job {
		/**
		 * @return What level the unit is in the job.
		 */
		int getLevel();

		/**
		 * Do something -- apply skills conferred by the job.
		 * 
		 * @param action
		 *            What action to take
		 * @param args
		 *            Any arguments it takes (including something to put the
		 *            result in, if any).
		 */
		void doAction(int action, Object... args);

		/**
		 * Level up in the job. This may mean that new equipment is required to
		 * use skills, or some old equipment isn't needed anymore.
		 * 
		 * @param equip
		 *            Should I re-equip if necessary?
		 * @param vault
		 *            The vault from which to draw equipment. (Or put it back
		 *            into, as the case may be.)
		 */
		void levelUp(boolean equip, Vault vault);

		/**
		 * Equip the unit with what it needs to use skills (and hopefully get
		 * rid of what no job needs .. TODO: really?)
		 * 
		 * @param vault
		 *            Whence to draw equipment.
		 */
		void equip(Vault vault);
		// what else? TODO: Upgrades? Dependency stuff?
	}

	/**
	 * @return (A copy of) the collection of equipment the unit has.
	 * 
	 *         (Can't specify that the unit will have equipment, since this is
	 *         an interface.)
	 */
	Collection<Module> getEquipment();

	/**
	 * Add equipment.
	 * 
	 * @param equipment
	 *            The equipment to add.
	 */
	void addEquipment(Collection<Module> equipment);

	/**
	 * Remove equipment.
	 * 
	 * @param equipment
	 *            The equipment to remove.
	 */
	void removeEquipment(Collection<Module> equipment);

	/**
	 * @return The jobs the unit has.
	 */
	Set<Job> getJobs();

	/**
	 * Add a job.
	 * 
	 * @param job
	 *            The job to add.
	 */
	void addJob(Job job);

	/**
	 * Remove a job.
	 * 
	 * @param job
	 *            The job to remove.
	 */
	void removeJob(Job job);

	/**
	 * A Unit can use equipment it does not possess (e.g. if the equipment is
	 * fixed in place)
	 * 
	 * @return (A copy of) the Unit's collection of external equipment within
	 *         reach that it knows how to use.
	 * 
	 *         TODO: Make this more robust. E.g.: What if more than one Unit is
	 *         within reach of a piece of equipment?
	 */
	Collection<Module> getExternalEquipment();
	// TODO: what else?
}
