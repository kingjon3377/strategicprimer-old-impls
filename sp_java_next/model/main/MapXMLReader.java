package model.main;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import model.location.Point;
import model.location.SPMap;
import model.location.TerrainType;
import model.location.Tile;
import model.module.Module;
import model.module.ModuleFactory;
import model.module.kinds.Fortress;
import model.module.kinds.Renameable;
import model.module.kinds.TransferableModule;
import model.player.SimplePlayer;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import view.module.ModuleGUIManager;

/**
 * A SAX parser for an XML map
 * 
 * @author Jonathan Lovelace
 * 
 */
public class MapXMLReader extends DefaultHandler implements Serializable {
	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = 5145532899438459585L;
	/**
	 * getMap() will block until this is true, i.e. until the end of the XML
	 * file has been reached.
	 */
	private transient boolean safeToReturn;
	/**
	 * The tile we're currently parsing
	 */
	private transient Tile currentTile;
	/**
	 * The fortress we're currently parsing
	 */
	private transient Fortress currentFort;
	/**
	 * The tiles that will go in the map
	 */
	private final transient Map<Point, Tile> tiles = new HashMap<Point, Tile>();
	/**
	 * The module we're currently parsing
	 */
	private transient Module currentModule;
	/**
	 * Module factory
	 */
	private static final ModuleFactory FACTORY = new ModuleFactory();
	/**
	 * The map
	 */
	private transient SPMap map;

	/**
	 * Constructor.
	 * 
	 * @param filename
	 *            Filename of the XML file that contains a map.
	 * @throws SAXException
	 *             when thrown by factory method
	 * @throws IOException
	 *             when the parser encounters an I/O error
	 */
	public MapXMLReader(final String filename) throws SAXException, IOException {
		super();
		safeToReturn = false;
		parserSetContentHandler(XMLReaderFactory.createXMLReader()).parse(
				filename);
	}

	/**
	 * Avoids having an XMLReader local variable.
	 * 
	 * @param reader
	 *            An XMLReader
	 * @return the XMLReader, with its ContentHandler having been set to this.
	 */
	private XMLReader parserSetContentHandler(final XMLReader reader) {
		reader.setContentHandler(this);
		return reader;
	}

	/**
	 * Blocks until ready to return
	 * 
	 * @return the parsed map
	 */
	public SPMap getMap() {
		while (!safeToReturn) {
			try {
				// FIXME: If the app is single-threaded, this could be just as
				// bad as busy-waiting.
				// ESCA-JAVA0087:
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				continue;
			}
		}
		return map;
	}

	/**
	 * Called when we're done parsing.
	 * 
	 * @throws SAXException
	 *             required by spec
	 */
	@Override
	public void endDocument() throws SAXException {
		safeToReturn = true;
	}

	/**
	 * Called when we've finished parsing an element.
	 * 
	 * @param namespaceURI
	 *            the Namespace URI
	 * @param localName
	 *            the local name
	 * @param qualifiedName
	 *            the qualified XML name
	 * @throws SAXException
	 *             required by spec
	 */
	@Override
	public void endElement(final String namespaceURI, final String localName,
			final String qualifiedName) throws SAXException {
		if ("module".equals(localName)) {
			if (currentFort == null) {
				currentTile.add(currentModule);
				currentModule.setLocation(currentTile);
			} else {
				currentFort.add(currentModule);
				currentModule.setLocation(currentFort);
			}
			currentModule = null; // NOPMD
		} else if ("tile".equals(localName)) {
			tiles.put(currentTile.getLocation(), currentTile);
			currentTile = null; // NOPMD
			currentFort = null; // NOPMD
		} else if ("map".equals(localName)) {
			map.addTiles(tiles);
		} else if ("fortress".equals(localName)) {
			currentTile.setModuleOnTile(currentFort);
			currentFort.setLocation(currentTile);
		}
	}

	/**
	 * Start parsing an element.
	 * 
	 * @param namespaceURI
	 *            the namespace URI of the element
	 * @param localName
	 *            the local name of the element
	 * @param qualifiedName
	 *            the qualified name of the element
	 * @param atts
	 *            attributes of the element
	 * @throws SAXException
	 *             required by spec
	 */
	@Override
	public void startElement(final String namespaceURI, final String localName,
			final String qualifiedName, final Attributes atts)
			throws SAXException {
		if (map == null) {
			if ("map".equals(localName)) {
				map = new SPMap(Integer.parseInt(atts.getValue("rows")),
						Integer.parseInt(atts.getValue("columns")));
				Game.getGame().clearPlayers();
			} else {
				throw new SAXException(new IllegalStateException(
						"Must start with a map tag!"));
			}
		} else {
			if ("map".equals(localName)) {
				throw new SAXException(new IllegalStateException(
						"Shouldn't have multiple map tags"));
			} else if ("tile".equals(localName)) {
				parseTile(atts);
			} else if ("module".equals(localName)) {
				parseModule(atts);
			} else if ("player".equals(localName)) {
				parsePlayer(atts);
			} else if ("fortress".equals(localName)) {
				parseFortress(atts);
			}
		}
	}

	/**
	 * Parse a fortress.
	 * 
	 * @param atts
	 *            The XML tag's attributes
	 * @throws SAXException
	 *             on various illegal constructs in the XML
	 */
	private void parseFortress(final Attributes atts) throws SAXException {
		if (currentFort == null) {
			if (currentTile == null) {
				throw new SAXException(new IllegalStateException(
						"Can't have a fort outside a tile"));
			} else {
				currentFort = new Fortress(currentTile, Game.getGame()
						.getPlayer(Integer.parseInt(atts.getValue("owner"))),
						atts.getValue("name"));
			}
		} else {
			throw new SAXException(new IllegalStateException(
					"Can't have nested forts"));
		}
	}

	/**
	 * Parse a tile.
	 * 
	 * @param atts
	 *            The XML tag's attributes.
	 * @throws SAXException
	 *             when one tile is inside another (wraps IllegalStateException)
	 */
	private void parseTile(final Attributes atts) throws SAXException {
		if (currentTile == null) {
			currentTile = new Tile(Integer.parseInt(atts.getValue("row")),
					Integer.parseInt(atts.getValue("column")));
			currentTile.setTerrain(TerrainType.getTileType(atts
					.getValue("type")));
		} else {
			throw new SAXException(new IllegalStateException(
					"Cannot (at present) have one tile inside another"));
		}
	}

	/**
	 * Parse a module
	 * 
	 * @param atts
	 *            The XML tag's attributes.
	 * @throws SAXException
	 *             when one module is inside another or not inside a tile (wraps
	 *             IllegalStateException)
	 */
	private void parseModule(final Attributes atts) throws SAXException {
		if (currentTile == null) {
			throw new SAXException(new IllegalStateException(
					"Cannot (at present) have a module outside a tile"));
		} else if (currentModule == null) {
			currentModule = FACTORY.createModule(Integer.parseInt(atts
					.getValue("mid")));
			if (atts.getValue("image") != null) {
				ModuleGUIManager
						.addImage(currentModule, atts.getValue("image"));
			}
			if (currentModule instanceof Renameable
					&& atts.getValue("name") != null) {
				((Renameable) currentModule).setName(atts.getValue("name"));
			}
			if (currentModule instanceof TransferableModule
					&& atts.getValue("owner") != null) {
				((TransferableModule) currentModule).setOwner(Game.getGame()
						.getPlayer(Integer.valueOf(atts.getValue("owner"))));
			}
		} else {
			throw new SAXException(new IllegalStateException(
					"Cannot (at present) have one module inside another"));
		}
	}

	/**
	 * Parse a player.
	 * 
	 * @param atts
	 *            The XML tag's attributes.
	 */
	private static void parsePlayer(final Attributes atts) {
		Game.getGame().addPlayer(
				new SimplePlayer(atts.getValue("code_name"), Integer
						.parseInt(atts.getValue("number"))));
	}
}
