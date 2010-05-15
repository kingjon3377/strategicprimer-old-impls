package controller.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import model.map.SPMap;
import model.map.TerrainObject;
import model.map.Tile;

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
					objects.append(Integer.toString(tile.getObject().ordinal()));
					objects.append(' ');
					objects.append(row);
					objects.append(' ');
					objects.append(col);
					objects.append('\n');
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
		ostream.close();
	}
}
