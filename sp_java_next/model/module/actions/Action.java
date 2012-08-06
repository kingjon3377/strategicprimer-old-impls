package model.module.actions;

/**
 * A class to identify actions (mapping from ID to description, number of
 * arguments, etc.).
 * 
 * @author Jonathan Lovelace
 * 
 */
public class Action {
	/**
	 * The ID number of the action.
	 */
	private final long number;
	/**
	 * A description of the action.
	 */
	private final String description;
	/**
	 * How many arguments the action takes.
	 */
	private final int arguments;

	/**
	 * Constructor.
	 * 
	 * @param idNum
	 *            the ID number of the action
	 * @param desc
	 *            a description of the action
	 * @param args
	 *            how many arguments the action takes
	 */
	public Action(final long idNum, final String desc, final int args) {
		number = idNum;
		description = desc;
		arguments = args;
	}

	/**
	 * @return the ID number of the action
	 */
	public long getNumber() {
		return number;
	}

	/**
	 * @return A description of the action
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return How many arguments the action takes
	 */
	public int getArguments() {
		return arguments;
	}

	/**
	 * @return a hash value for the action
	 */
	@Override
	public int hashCode() {
		return (int) number;
	}

	/**
	 * @param obj
	 *            another object
	 * @return true if obj is an action with the same number, otherwise false
	 */
	@Override
	public boolean equals(final Object obj) {
		return obj instanceof Action && ((Action) obj).number == number;
	}
}
