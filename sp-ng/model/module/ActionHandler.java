package model.module;

import model.building.SimpleBuilding;
import model.map.TerrainObject;
import model.map.Tile;
import model.unit.Harvester;
import model.unit.SimpleUnit;
import model.unit.UnitAction;

/**
 * A class to handle actions.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class ActionHandler {
	/**
	 * Handle an action requiring a Module object.
	 * 
	 * @param action
	 *            the action to be taken
	 * @param actor
	 *            the acting module
	 * @param object
	 *            the module to be acted on
	 */
	public void runAction(final UnitAction action, final SPModule actor,
			final SPModule object) {
		if (actor == null || object == null || UnitAction.Cancel.equals(action)) {
			return;
		} else if (UnitAction.Unload.equals(action)) {
			if (actor instanceof Harvester && object instanceof SimpleBuilding) {
				// TODO: Make the building have to be a stockpile and increment
				// it.
				// TODO: Should have to be adjacent.
				((Harvester) actor)
						.setBurden(((Harvester) actor).getBurden() - 1);
			}
		}
	}

	/**
	 * Handle an action requiring an object.
	 * 
	 * FIXME: Shouldn't take Tiles, but rather Modules.
	 * 
	 * @param action
	 *            The action to be taken
	 * @param actor
	 *            The tile the actor is on
	 * @param object
	 *            The tile the actor is acting on, or containing the module it
	 *            is acitng on.
	 */
	public void runAction(final UnitAction action, final Tile actor,
			final Tile object) {
		if (actor == null || object == null || UnitAction.Cancel.equals(action)) {
			return;
		} else if (UnitAction.Move.equals(action)) {
			if (actor.getModule() != null
					&& actor.getModule() instanceof SimpleUnit
					&& object.getModule() == null) {
				object.setModule(actor.getModule());
				actor.setModule(null);
			}
		} else if (UnitAction.Harvest.equals(action)) {
			if (actor.getModule() != null
					&& actor.getModule() instanceof Harvester
					&& TerrainObject.TREE.equals(object.getObject())) {
				// TODO: Should have to be adjacent.
				((Harvester) actor.getModule()).setBurden(((Harvester) actor
						.getModule()).getBurden() + 1);
				object.setObject(TerrainObject.NOTHING);
			}
		} else if (UnitAction.Unload.equals(action)) {
			runAction(action, actor.getModule(), object.getModule());
		}
	}
}
