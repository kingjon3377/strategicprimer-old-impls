package controller.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import model.building.SimpleBuilding;
import model.map.SPMap;
import model.map.TerrainObject;
import model.map.Tile;
import model.unit.SimpleUnit;

/**
 * A class to write a map to a file.
 * @author Jonathan Lovelace
 *
 */
public class MapWriter {
	/**
	 * Write a map to a file.
	 * @param filename the file to write to
	 * @param map the map to write
	 * @throws IOException on I/O error
	 */
	public void writeMap(final String filename, final SPMap map) throws IOException {
		writeMap(new PrintWriter(new BufferedWriter(new FileWriter(filename))), map);
	}
	/**
	 * Write a map to an output stream.
	 * @param ostream the stream we're writing to
	 * @param map the map to write
	 */
	public void writeMap(final PrintWriter ostream, final SPMap map) {
		ostream.print(map.getRows());
		ostream.print(' ');
		ostream.println(map.getCols());
		Tile tile = null;
		final StringBuffer elevations = new StringBuffer("");
		final StringBuffer waterLevels = new StringBuffer("");
		int numObjects = 0;
		final StringBuffer objects = new StringBuffer("");
		int numUnits = 0;
		final StringBuffer units = new StringBuffer("");
		int numBuildings = 0;
		final StringBuffer buildings = new StringBuffer("");
		for (int row = 0; row < map.getRows(); row++) {
			for (int col = 0; col < map.getCols(); col++) {
				tile = map.terrainAt(row, col);
				ostream.print(tile.getType().ordinal());
				ostream.print(' ');
				elevations.append(Integer.toString(tile.getElevation()));
				elevations.append(' ');
				waterLevels.append(Integer.toString(tile.getWaterLevel()));
				waterLevels.append(' ');
				if (!TerrainObject.NOTHING.equals(tile.getObject())) {
					numObjects++;
					writeObject(tile.getObject(), objects, row, col);
				}
				if (tile.getModule() != null && tile.getModule() instanceof SimpleUnit) {
					numUnits++;
					writeUnit((SimpleUnit) tile.getModule(), units, row, col);
				} else if (tile.getModule() != null && tile.getModule() instanceof SimpleBuilding) {
					numBuildings++;
					writeBuilding((SimpleBuilding) tile.getModule(), buildings, row, col);
				}
			}
			ostream.println();
			elevations.append('\n');
			waterLevels.append('\n');
		}
		ostream.print(elevations);
		ostream.print(waterLevels);
		ostream.println(numObjects);
		ostream.print(objects);
		ostream.println(numUnits);
		ostream.print(units);
		ostream.println(numBuildings);
		ostream.print(buildings);
		ostream.close();
	}

	/**
	 * Write a terrain object.
	 * 
	 * @param obj
	 *            the object
	 * @param objects
	 *            The buffer to write to, containing all the objects; it'll get
	 *            appended to the file after the tile-types, elevations, and
	 *            water levels.
	 * @param row
	 *            the row the object is in
	 * @param col
	 *            the column the object is in
	 */
	private static void writeObject(TerrainObject obj, final StringBuffer objects,
			int row, int col) {
		objects.append(Integer.toString(obj.ordinal()));
		objects.append(' ');
		objects.append(row);
		objects.append(' ');
		objects.append(col);
		objects.append('\n');
	}
	/**
	 * Write a unit.
	 * @param unit the unit
	 * @param units the buffer to write to.
	 * @param row the row the unit is in
	 * @param col the column the unit is in
	 */
	private static void writeUnit(final SimpleUnit unit, final StringBuffer units, final int row, final int col) {
		if (unit instanceof Harvester) {
			units.append("1 ");
			units.append(row);
			units.append(' ');
			units.append(col);
			units.append(' ');
			units.append(unit.getOwner());
			units.append(' ');
			units.append(((Harvester) unit).getBurden());
		} else {
			units.append("0 ");
			units.append(row);
			units.append(' ');
			units.append(col);
			units.append(' ');
			units.append(unit.getOwner());
		}
		units.append('\n');
	}

	/**
	 * Write a building.
	 * @param building the unit
	 * @param buildings the buffer to write to.
	 * @param row the row the building is in
	 * @param col the column the building is in
	 */
	private static void writeBuilding(final SimpleBuilding building, final StringBuffer buildings, final int row, final int col) {
		buildings.append("0 ");
		buildings.append(row);
		buildings.append(' ');
		buildings.append(col);
		buildings.append(' ');
		buildings.append(building.getOwner());
		buildings.append('\n');
	}
}
