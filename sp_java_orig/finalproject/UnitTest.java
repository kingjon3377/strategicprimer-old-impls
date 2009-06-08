package finalproject;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

// ESCA-JAVA0137:
/**
 * Test class for the Strategic Primer/Yudexen model's SimpleUnit class.
 * 
 * @author Jonathan Lovelace
 * @assignment Final Project
 * @course CS108A
 * @semester FA06
 * 
 */
public class UnitTest extends TestCase {
	/**
	 * The second dimension of the test map; to make a "magic number" warning go
	 * away.
	 */
	private static final int TESTMAP_SECOND_DIM = 6;
	/**
	 * The first unit we're testing
	 */
	private SimpleUnit unit;
	/**
	 * The second unit we're testing
	 */
	private SimpleUnit unit2;
	/**
	 * The map we test them on
	 */
	private SPMap map;

	/**
	 * Set up for each test
	 * 
	 * @throws Exception
	 *             thrown by the superclass setUp().
	 */
	@Override
	public void setUp() throws Exception {
		super.setUp();
		unit = new SimpleUnit();
		unit.setSpeed(5);
		final List<List<Tile>> tiles = new ArrayList<List<Tile>>();
		for (int i = 0; i < 5; i++) {
			tiles.set(i, new ArrayList<Tile>());
			for (int j = 0; j < TESTMAP_SECOND_DIM; j++) {
				tiles.get(i).set(j, new Tile(i,j,2));
			}
		}
		tiles.get(0).get(0).setModuleOnTile(unit);
		unit.setLocation(tiles.get(0).get(0));
		unit2 = new SimpleUnit();
		unit2.setAttack(BuildConstants.BALLISTA_RANGED);
		unit2.setRanged(25);
		unit2.setAccuracy(0.9);
		tiles.get(0).get(1).setModuleOnTile(unit2);
		unit2.setLocation(tiles.get(0).get(1));
		map = new SPMap(5, TESTMAP_SECOND_DIM, tiles);
		for (int i = 0; i < TESTMAP_SECOND_DIM; i++) {
			tiles.get(2).get(i).setModuleOnTile(new SimpleUnit());
		}
	}

	/**
	 * Tests SimpleUnit.setAccuracy()
	 */
	public void testSetAccuracy() {
		try {
			unit.setAccuracy(-0.1);
			fail("setAccuracy() accepted negative accuracy");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
		try {
			unit.setAccuracy(1.1);
			fail("setAccuracy() accepted accuracy > 1");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
		try {
			unit.setAccuracy(0.2);
		} catch (IllegalArgumentException e) {
			fail("setAccuracy choked on legal input");
		}
	}

	/**
	 * Tests Unit.move()
	 */
	public void testMove() {
		unit.move(map.getTile(4, 4), map);
		if (unit.getLocation() == map.getTile(4, 4)) {
			fail("Unit moved to unreachable location");
		}
		unit.move(map.getTile(1, 1), map);
		if (map.getTile(1, 1).getModuleOnTile() != unit) {
			fail("Unit failed to move to reachable location (tile.moduleOnTile)");
		}
		if (map.getTile(0, 0).getModuleOnTile() != null) {
			fail("Unit failed to be removed from start tile when moving");
		}
		if (unit.getLocation() != map.getTile(1, 1)) {
			fail("Unit failed to move to reachable location");
		}
		if (!unit.getHasMoved()) {
			fail("Unit, having moved, is not so marked");
		}

	}

	/**
	 * Tests unit.attack()
	 */
	public void testAttack() {
		unit.setMaxHP(BuildConstants.PHALANX_HP);
		unit.setHP(BuildConstants.PHALANX_HP);
		unit2.attack(unit);
		if (unit.getHP() != BuildConstants.PHALANX_HP
				- BuildConstants.BALLISTA_RANGED) {
			fail("Attack() dealt wrong amount of damage");
		}
		if (!unit2.getHasAttacked()) {
			fail("Attack() didn't set hasAttacked");
		}
		unit.setHP(BuildConstants.PHALANX_HP);
		unit.setDefense(BuildConstants.CAVALRY_DEFENSE);
		unit2.attack(unit);
		if (unit.getHP() != BuildConstants.PHALANX_HP
				- BuildConstants.BALLISTA_RANGED
				+ BuildConstants.CAVALRY_DEFENSE) {
			fail("Attack() didn't use defense stat properly");
		}
	}

	/**
	 * Test SimpleUnit.rangedAttack()
	 */
	public void testRangedAttack() {
		unit.setMaxHP(BuildConstants.PHALANX_HP);
		unit.setHP(BuildConstants.PHALANX_HP);
		unit2.rangedAttack(unit);
		if (unit.getHP() != BuildConstants.PHALANX_HP
				- BuildConstants.BALLISTA_ATTACK) {
			fail("RangedAttack() dealt wrong amount of damage");
		}
		if (!unit2.getHasAttacked()) {
			fail("RangedAttack() didn't set hasAttacked");
		}
		unit.setHP(BuildConstants.PHALANX_HP);
		unit.setDefense(BuildConstants.CAVALRY_DEFENSE);
		unit2.rangedAttack(unit);
		if (unit.getHP() != BuildConstants.PHALANX_HP
				- BuildConstants.BALLISTA_ATTACK
				+ BuildConstants.CAVALRY_DEFENSE) {
			fail("RangedAttack() didn't use defense stat properly");
		}
	}

	/**
	 * Tests SimpleUnit.checkMove()
	 */
	public void testCheckMove() {
		assertFalse("FIXME: Can't recall why this is false", ((SimpleUnit) map
				.getTile(2, 0).getModuleOnTile()).checkMove(map.getTile(3, 0),
				map));
		assertFalse("Can't move to occupied tile", unit.checkMove(map.getTile(
				2, 0), map));
		unit2.setHasMoved(true);
		assertFalse("Can't move twice", unit2.checkMove(map.getTile(1, 1), map));
		assertFalse("FIXME: Can't recall why this is false", unit.checkMove(map
				.getTile(3, 3), map));
		assertTrue("A legal move", unit.checkMove(map.getTile(1, 1), map));
	}

	/**
	 * Tests the checkAttack() method
	 */
	public void testCheckAttack() {
		unit2.setHasAttacked(true);
		assertFalse("Can't attack twice", unit2.checkAttack(unit));
		try {
			unit.checkAttack(null);
			fail("checkAttack() didn't catch null argument");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
		try {
			unit.checkAttack(map.getTile(2, 2).getModuleOnTile());
			fail("checkAttack() didn't catch target with no location or parent");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
		map.getTile(2, 2).getModuleOnTile().setLocation(map.getTile(2, 2));
		assertFalse("Non-adjacent unit", unit.checkAttack(map.getTile(2, 2)
				.getModuleOnTile()));
		assertTrue("Adjacent unit", unit.checkAttack(unit2));
	}

	/**
	 * Tests checkRangedAttack()
	 */
	public void testCheckRangedAttack() {
		unit2.setHasAttacked(true);
		assertFalse("Can't ranged-attack if already attacked", unit2
				.checkRangedAttack(unit));
		try {
			unit.checkRangedAttack(null);
			fail("checkRangedAttack() didn't catch null argument");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
		try {
			unit.checkRangedAttack(map.getTile(2, 2).getModuleOnTile());
			fail("checkRangedAttack() didn't catch target with no location or parent");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
		map.getTile(2, 2).getModuleOnTile().setLocation(map.getTile(2, 2));
		assertTrue("One successful possible attack", unit.checkRangedAttack(map
				.getTile(2, 2).getModuleOnTile()));
		assertTrue("Another true possible attack", unit
				.checkRangedAttack(unit2));
	}
	/**
	 * Tests the takeAttack() method.
	 */
	public void testTakeAttack() {
		unit.setMaxHP(BuildConstants.PHALANX_DEFENSE);
		unit.setHP(BuildConstants.PHALANX_DEFENSE);
		unit2.attack(unit);
		assertTrue("Killed units get deleted",unit.getDelete());
	}

}
