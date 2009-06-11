package model;
/**
 * A simple module
 * @author Jonathan Lovelace
 *
 */
public class SimpleModule extends Module {
	/**
	 * The module's parent in the tree
	 */
	protected SimpleModule parent;
	/**
	 * @return the module's name
	 */
	@Override
	public String getName() {
		return "SimpleModule";
	}
	/**
	 * Constructor.
	 * @bug FIXME: We need a NullLocation class!
	 */
	public SimpleModule() {
		super(null);
	}
	/**
	 * Constructor
	 * @param loc The module's location
	 */
	public SimpleModule(final MoveTarget loc) {
		super(loc);
	}
}
