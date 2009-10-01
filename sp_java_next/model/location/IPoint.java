package model.location;

/**
 * A pair (X,Y) used to identify a Tile in a map (or a GUITile in a GUIMap)
 * 
 * A single step within a path
 * 
 * @author Kevin Glass
 * @author Jonathan Lovelace extracted interface
 */
public interface IPoint {

	/**
	 * Get the x coordinate of the point
	 * 
	 * @return The x coodindate of the point
	 */
	int getX();

	/**
	 * Get the y coordinate of the point
	 * 
	 * @return The y coodindate of the point
	 */
	int getY();

}