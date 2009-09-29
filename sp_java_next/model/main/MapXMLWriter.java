package model.main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import model.location.SPMap;
import model.location.Tile;
import model.module.Module;
import model.module.kinds.RootModule;
import view.module.ModuleGUIManager;

/**
 * A class to write a map to XML.
 * 
 * @author Jonathan Lovelace
 */
public final class MapXMLWriter {
	/**
	 * Don't instantiate
	 */
	private MapXMLWriter() {
		// Don't instantiate
	}

	/**
	 * Write the map to an XML file
	 * 
	 * @param filename
	 *            The name of the file to write to.
	 * @throws IOException
	 *             on I/O error
	 */
	public static void writeMapToXML(final String filename) throws IOException {
		writeMapToXML(filename, Game.getGame().getMap());
	}

	/**
	 * Write a map to an XML file
	 * 
	 * @param filename
	 *            The name of the file to write to
	 * @param map
	 *            The map to write
	 * @throws IOException
	 *             on I/O error
	 */
	public static void writeMapToXML(final String filename, final SPMap map)
			throws IOException {
		writeMap(map, new PrintWriter(new BufferedWriter(new FileWriter(
				filename))));
	}

	/**
	 * Write a map to XML
	 * 
	 * @param map
	 *            The map to write
	 * @param writer
	 *            The Writer to write to
	 */
	private static void writeMap(final SPMap map, final PrintWriter writer) {
		writer.println("<map rows=\"" + map.getSizeRows() + "\" columns=\""
				+ map.getSizeCols() + "\" version=\"0\">");
		for (int i = 0; i < map.getSizeRows(); i++) {
			writer.println("	<row index=\"" + i + "\">");
			for (int j = 0; j < map.getSizeCols(); j++) {
				writeTile(map.getTileAt(i, j), writer);
			}
			writer.println("	</row>");
		}
		writer.println("</map>");
		writer.close();
	}

	/**
	 * Write a tile to XML
	 * 
	 * @param tile
	 *            the tile to write
	 * @param writer
	 *            the Writer to write to
	 */
	private static void writeTile(final Tile tile, final PrintWriter writer) {
		if (tile.getModuleOnTile() == null
				|| tile.getModuleOnTile().equals(RootModule.getRootModule())) {
			writer.println("		<tile row=\"" + tile.x + "\" column=\"" + tile.y
					+ "\" type=\"" + tile.getTerrain().toString()
					+ "\"></tile>");
		} else {
			writer.println("		<tile row=\"" + tile.x + "\" column=\"" + tile.y
					+ "\" type=\"" + tile.getTerrain().toString() + "\">");
			writeModule(tile.getModuleOnTile(), writer, 3);
			writer.println("		</tile>");
		}
	}

	/**
	 * Write a module to XML
	 * 
	 * @param module
	 *            the module to write
	 * @param writer
	 *            the Writer to write to
	 * @param indent
	 *            how many tabs to indent
	 */
	private static void writeModule(final Module module,
			final PrintWriter writer, final int indent) {
		for (int i = 0; i < indent; i++) {
			writer.append('\t');
		}
		writer.println("<module mid=\""
				+ module.getModuleID()
				+ ("".equals(ModuleGUIManager.getFilename(module)) ? ""
						: "\" image=\"" + ModuleGUIManager.getFilename(module))
				+ "\"></module>");
	}
}
