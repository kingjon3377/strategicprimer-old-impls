package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.ClientTile;
import common.QueryMessage;
import common.SPPoint;

/**
 * A client-side data structure to store what it knows about the map. TODO: have
 * some sort of distinction between immediately-visible and shrouded tiles,
 * since any changes to the former should be immediately visible and the server
 * might not even tell us about the latter.
 * 
 * @author Jonathan Lovelace
 */
public class ClientMap {
	/**
	 * The data structure we use to cache our knowledge.
	 */
	private Map<SPPoint, ClientTile> map = new HashMap<SPPoint, ClientTile>();
	/**
	 * The number of rows in the map
	 */
	private int rows;
	/**
	 * The number of columns in the map
	 */
	private int cols;
	/**
	 * Our connection to the server.
	 */
	private ClientAPIWrapper api;
	/**
	 * Constructor.
	 * @param apiWrapper the connection to the server
	 * @throws ClassNotFoundException on deserialization error while calling the server
	 * @throws IOException on I/O error calling the server
	 */
	public ClientMap(final ClientAPIWrapper apiWrapper) throws IOException, ClassNotFoundException {
		api = apiWrapper;
		final SPPoint size = (SPPoint) api.call(new QueryMessage("size")).getSecondArg();
		rows = size.row();
		cols = size.col();
	}
	/**
	 * @param point
	 *            a point
	 * @return the tile at that point, retrieving it from the server if
	 *         necessary.
	 * @throws ClassNotFoundException on deserialization error querying the server
	 * @throws IOException on I/O error querying the server
	 */
	@SuppressWarnings("unchecked")
	public ClientTile getTile(final SPPoint point) throws IOException, ClassNotFoundException {
		if (point.row() < 0 || point.row() > rows || point.col() < 0
				|| point.col() > cols) {
			throw new IllegalArgumentException("Point outside the map");
		}
		if (!map.containsKey(point)) {
			List<SPPoint> list = new ArrayList<SPPoint>();
			list.add(point);
			list.add(new SPPoint(point.row() - 1, point.col() - 1));
			list.add(new SPPoint(point.row() - 1, point.col()));
			list.add(new SPPoint(point.row() - 1, point.col() + 1));
			list.add(new SPPoint(point.row(), point.col() - 1));
			list.add(new SPPoint(point.row(), point.col() + 1));
			list.add(new SPPoint(point.row() + 1, point.col() - 1));
			list.add(new SPPoint(point.row() + 1, point.col()));
			list.add(new SPPoint(point.row() + 1, point.col() + 1));
			for (SPPoint pt : new ArrayList<SPPoint>(list)) {
				if (map.containsKey(pt)) {
					list.remove(pt);
				}
			}
			List<ClientTile> reply = (List<ClientTile>) api.call(new QueryMessage("tiles", list)).getSecondArg();
			for (ClientTile tile : reply) {
				map.put(tile.getLocation(), tile);
			}
		}
		return map.get(point);
	}
	/**
	 * @return the size of the map in rows
	 */
	public int getRows() {
		return rows;
	}
	/**
	 * @return the size of the map in columns
	 */
	public int getCols() {
		return cols;
	}
	/**
	 * Invalidate the cache for a tile.
	 * @param point the point to invalidate the cache for
	 */
	public void invalidate(final SPPoint point) {
		map.remove(point);
	}
}
