package model;

/**
 * A unit is something that can move.
 * 
 * Alternate definition: A Unit represents a "person" in the game (human or AI).
 * It thus can change what equipment (Modules) it has and most likely has (to
 * borrow concepts from RPGs) "levels" in the various "classes"/"jobs" it has
 * been, giving it varying levels of competence with various equipment, stat
 * changes, etc.
 * 
 * @author Jonathan Lovelace
 * 
 */
public interface Unit {
	/**
	 * A unit is defined as something that can move.
	 * 
	 * @param location
	 *            Whither to move.
	 */
	void move(MoveTarget location);
}
