package model.main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import model.location.SPMap;
import model.location.Tile;
import model.module.Module;
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
	public static void writeGameToXML(final String filename) throws IOException {
		writeGameToXML(filename, Game.getGame());
	}

	/**
	 * Write a map to an XML file
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
		writeGame(game, new PrintWriter(new BufferedWriter(new FileWriter(
				filename))));
	}

	/**
	 * Write a game to XML
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
	 * Write the initial element of the XML
	 * 
	 * @param map
	 *            The map we'll be writing
	 * @param writer
	 *            the Writer to write to
	 */
	private static void writeInitialElement(final SPMap map,
			final PrintWriter writer) {
		writer.println("<map rows=\"" + map.getSizeRows() + "\" columns=\""
				+ map.getSizeCols() + "\" version=\"0\">");
	}

	/**
	 * Write players to XML
	 * 
	 * @param players
	 *            The set of players to write
	 * @param writer
	 *            the Writer to write to
	 */
	public static void writePlayers(final Map<Integer, IPlayer> players,
			final PrintWriter writer) {
		for (IPlayer player : players.values()) {
			writer.println("\t<player number=\"" + player.getNumber()
					+ "\" code_name=\"" + player.getName() + "\" />");
		}
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
		for (int i = 0; i < map.getSizeRows(); i++) {
			writer.println("\t<row index=\"" + i + "\">");
			for (int j = 0; j < map.getSizeCols(); j++) {
				writeTile(map.getTileAt(i, j), writer);
			}
			writer.println("\t</row>");
		}
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
			writer.println("\t\t<tile row=\"" + tile.getLocation().getX()
					+ "\" column=\"" + tile.getLocation().getY() + "\" type=\""
					+ tile.getTerrain().toString() + "\"></tile>");
		} else {
			writer.println("\t\t<tile row=\"" + tile.getLocation().getX()
					+ "\" column=\"" + tile.getLocation().getY() + "\" type=\""
					+ tile.getTerrain().toString() + "\">");
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
				+ "\" name=\""
				+ module.getName()
				+ (module.getOwner() == null ? "" : "\" owner=\""
						+ module.getOwner().getNumber()) + "\"></module>");
	}
}
