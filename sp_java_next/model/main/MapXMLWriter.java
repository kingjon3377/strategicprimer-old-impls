package model.main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import model.location.SPMap;
import model.location.Tile;
import model.module.Module;
import model.module.features.Feature;
import model.module.kinds.Fortress;
import model.module.kinds.RootModule;
import model.player.IPlayer;
import view.module.ModuleGUIManager;

/**
 * A class to write a map to XML.
 * 
 * @author Jonathan Lovelace
 */
public final class MapXMLWriter {
	/**
	 * The closing quote of an XML attribute plus the closing bracket of the XML
	 * tag.
	 */
	private static final String CLOSE_XML_ATT_TAG = "\">";

	/**
	 * Don't instantiate.
	 */
	private MapXMLWriter() {
		// Don't instantiate
	}

	/**
	 * Write the map to an XML file.
	 * 
	 * @param filename
	 *            The name of the file to write to.
	 * @throws IOException
	 *             on I/O error
	 */
	public static void writeGameToXML(final String filename) throws IOException {
		writeGameToXML(filename, Game.getGame());
	}

	/**
	 * Write a map to an XML file.
	 * 
	 * @param filename
	 *            The name of the file to write to
	 * @param game
	 *            The game to write
	 * @throws IOException
	 *             on I/O error
	 */
	public static void writeGameToXML(final String filename, final Game game)
			throws IOException {
		writeGame(game, new PrintWriter(new BufferedWriter(new FileWriter(filename))));
	}

	/**
	 * Write a game to XML.
	 * 
	 * @param game
	 *            The game to write
	 * @param writer
	 *            The Writer to write to
	 */
	private static void writeGame(final Game game, final PrintWriter writer) {
		writeInitialElement(game.getMap(), writer);
		writePlayers(game.getPlayers(), writer);
		writeMap(game.getMap(), writer);
		writer.println("</map>");
		writer.close();
	}

	/**
	 * Write the initial element of the XML.
	 * 
	 * @param map
	 *            The map we'll be writing
	 * @param writer
	 *            the Writer to write to
	 */
	private static void writeInitialElement(final SPMap map, final PrintWriter writer) {
		writer.println("<map rows=\"" + map.getSizeRows() + "\" columns=\""
				+ map.getSizeCols() + "\" version=\"0\">");
	}

	/**
	 * Write players to XML.
	 * 
	 * @param players
	 *            The set of players to write
	 * @param writer
	 *            the Writer to write to
	 */
	public static void writePlayers(final Map<Integer, IPlayer> players,
			final PrintWriter writer) {
		for (IPlayer player : players.values()) {
			writer.println("\t<player number=\"" + player.getNumber() + "\" code_name=\""
					+ player.getName() + "\" />");
		}
	}

	/**
	 * Write a map to XML.
	 * 
	 * @param map
	 *            The map to write
	 * @param writer
	 *            The Writer to write to
	 */
	private static void writeMap(final SPMap map, final PrintWriter writer) {
		for (int i = 0; i < map.getSizeRows(); i++) {
			writer.println("\t<row index=\"" + i + CLOSE_XML_ATT_TAG);
			for (int j = 0; j < map.getSizeCols(); j++) {
				writeTile(map.getTileAt(i, j), writer);
			}
			writer.println("\t</row>");
		}
	}

	/**
	 * The starting indentation for elements inside a tile.
	 */
	private static final int START_TABS = 3;

	/**
	 * Write a tile to XML.
	 * 
	 * @param tile
	 *            the tile to write
	 * @param writer
	 *            the Writer to write to
	 */
	private static void writeTile(final Tile tile, final PrintWriter writer) {
		writer.print("\t\t<tile row=\"");
		writer.print(tile.getLocation().getX());
		writer.print("\" column=\"");
		writer.print(tile.getLocation().getY());
		writer.print("\" type=\"");
		writer.print(tile.getTerrain().toString());
		if (tile.getEvent() != null) {
			writer.print("\" event=\"");
			writer.print(tile.getEvent().getNumber());
		}
		writer.print(CLOSE_XML_ATT_TAG);
		if (tile.getModuleOnTile() != null
				&& !tile.getModuleOnTile().equals(RootModule.ROOT_MODULE)) {
			writer.println();
			if (tile.getModuleOnTile() instanceof Fortress) {
				writeFortress((Fortress) tile.getModuleOnTile(), writer, START_TABS);
			} else if (tile.getModuleOnTile() instanceof Feature) {
				writeFeature((Feature) tile.getModuleOnTile(), writer, START_TABS);
			} else {
				writeModule(tile.getModuleOnTile(), writer, START_TABS);
			}
			writer.print("		");
		}
		writer.println("</tile>");
	}

	/**
	 * Write a fortress to XML.
	 * 
	 * @param fortress
	 *            the module to write
	 * @param writer
	 *            the Writer to write to
	 * @param indent
	 *            how many tabs to indent
	 */
	private static void writeFortress(final Fortress fortress, final PrintWriter writer,
			final int indent) {
		appendTabs(writer, indent);
		writer.print("<fortress name=\"");
		writer.print(fortress.getName());
		writer.print("\" owner=\"");
		writer.print(fortress.getOwner().getNumber());
		writer.print(CLOSE_XML_ATT_TAG);
		if (fortress.getModule() != null && !(fortress.getModule() instanceof RootModule)) {
			writer.println();
			writeModule(fortress.getModule(), writer, indent + 1);
			appendTabs(writer, indent);
		}
		writer.println("</fortress>");
	}

	/**
	 * Append a specified number of tabs to the writer.
	 * 
	 * @param writer
	 *            The writer to write to to
	 * @param indent
	 *            The number of tabs to write
	 */
	private static void appendTabs(final PrintWriter writer, final int indent) {
		for (int i = 0; i < indent; i++) {
			writer.print('\t');
		}
	}

	/**
	 * Write a module to XML.
	 * 
	 * @param module
	 *            the module to write
	 * @param writer
	 *            the Writer to write to
	 * @param indent
	 *            how many tabs to indent
	 */
	private static void writeModule(final Module module, final PrintWriter writer,
			final int indent) {
		for (int i = 0; i < indent; i++) {
			writer.append('\t');
		}
		writer.println("<module mid=\""
				+ module.getModuleID()
				+ ("".equals(ModuleGUIManager.getFilename(module)) ? "" : "\" image=\""
						+ ModuleGUIManager.getFilename(module))
				+ "\" name=\""
				+ module.getName()
				+ (module.getOwner() == null ? "" : "\" owner=\""
						+ module.getOwner().getNumber()) + "\"></module>");
	}

	/**
	 * Write a landscape feature to XML.
	 * 
	 * @param feature
	 *            the feature to write
	 * @param writer
	 *            the Writer to write to
	 * @param indent
	 *            how many tabs to indent
	 */
	private static void writeFeature(final Feature feature, final PrintWriter writer,
			final int indent) {
		for (int i = 0; i < indent; i++) {
			writer.append('\t');
		}
		writer.println("<feature mid=\""
				+ feature.getModuleID()
				+ ("".equals(ModuleGUIManager.getFilename(feature)) ? "" : "\" image=\""
						+ ModuleGUIManager.getFilename(feature)) + "\" name=\""
				+ feature.getName() + "\" />");
	}
}
