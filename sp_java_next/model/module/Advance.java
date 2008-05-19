package model.module;

/**
 * A kludged interface to allow the standardized creation/conversion of modules
 * to be somewhat regulated.
 * 
 * Dependency handling is managed via the Comparable interface -- if I the
 * advance depend on another advance (directly or indirectly), it is greater
 * than me (directly or indirectly); if it depends on me, it is less than me; if
 * neither of us is the ancestor of the other, we are "equal."
 * 
 * TODO: Put that description into the JavaDoc for the compareTo() method.
 * 
 * TODO: Circular/otherwise more complicated dependencies
 * 
 * TODO: More robust dependency logic
 * 
 * TODO: Should the dependency logic become its own class, AdvanceComparator?
 * (Might want another comparison for other purposes -- what leaps to mind now
 * is AI deciding which advance to pursue.)
 * 
 * TODO: Should this be an interface or an abstract class?
 * 
 * TODO: This should be a singleton, however that's implemented in Java.
 * 
 * @author Jonathan Lovelace
 * 
 */
public interface Advance extends Comparable<Advance> {
	/**
	 * Compare this advance with another
	 * 
	 * @param advance
	 *            The other advance
	 * @return The result of the comparison
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	int compareTo(final Advance advance);

	/**
	 * Converts the source Module into the one specified by id, using
	 * sub-Modules from the given Vault.
	 * 
	 * @param source
	 *            The Module to be converted; usually a "blank" one returned by
	 *            the Module constructor.
	 * @param vault
	 *            A vault to draw sub-Modules from.
	 * @param ident
	 *            Specifies what Module to generate
	 * @return The generated Module.
	 * 
	 * TODO: Should this be void?
	 */
	Module convert(final Module source, final Vault vault, final String ident);

	/**
	 * Some advances give bonuses to all modules of a certain type; this
	 * function is thus called when the advance is discovered.
	 * 
	 * @param root
	 *            The root of a tree of Modules to be traversed to make the
	 *            normative changes.
	 * 
	 * TODO: Figure out a way to make sure a change isn't made more than once.
	 */
	void normativeEffect(final Module root);
}
