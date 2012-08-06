package view.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;

import view.map.GUITile;

import model.location.TerrainType;
/**
 * A popup menu to allow an admin to change a tile's terrain type.
 * @author Jonathan Lovelace
 *
 */
public final class TerrainTypeMenu extends JMenu implements ActionListener {
	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = -8078872146001436461L;
	/**
	 * The tile that this menu would change.
	 */
	private GUITile tile;
	/**
	 * The singleton object.
	 */
	public static final TerrainTypeMenu MENU = new TerrainTypeMenu();
	/**
	 * Constructor.
	 */
	private TerrainTypeMenu() {
		super("Change Terrain Type");
		for (TerrainType type : TerrainType.values()) {
			add(type.toString()).addActionListener(this);
		}
	}
	/**
	 * Handle menu item clicks.
	 * @param event The event we're handling
	 */
	@Override
	public void actionPerformed(final ActionEvent event) {
		if (tile != null) {
			tile.getTile().setTerrain(TerrainType.getTileType(event.getActionCommand()));
			tile.refreshTerrainImage();
		}
	}
	/**
	 * Set the tile we're referring to.
	 * @param newTile the tile we're to refer to
	 */
	public void setTile(final GUITile newTile) {
		tile = newTile;
	}
}
