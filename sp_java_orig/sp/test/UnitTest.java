package sp.test;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import sp.model.BuildConstants;
import sp.model.SPMap;
import sp.model.SimpleUnit;
import finalproject.astar.Location;
import finalproject.astar.Tile;

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
public class UnitTest extends TestCase { // NOPMD
	/**
	 * The second dimension of the test map; to make a "magic number" warning go
	 * away.
	 */
	private static final int TESTMAP_2D_DIM = 6;
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
	@Before
	public void setUp() throws Exception {
		super.setUp();
		unit = new SimpleUnit();
		unit.setSpeed(5);
		final List<List<Tile>> tiles = SPMap.createArray(5, TESTMAP_2D_DIM,3);
		tiles.get(0).get(0).setModuleOnTile(unit);
		unit.setLocation(tiles.get(0).get(0));
		unit2 = new SimpleUnit();
		unit2.setMeleeAttack(BuildConstants.BALLISTA_RANGED);
		unit2.setRanged(25);
		unit2.setAccuracy(0.9);
		tiles.get(0).get(1).setModuleOnTile(unit2);
		unit2.setLocation(tiles.get(0).get(1));
		map = new SPMap(5, TESTMAP_2D_DIM, tiles);
		for (int i = 0; i < TESTMAP_2D_DIM; i++) {
			tiles.get(2).get(i).setModuleOnTile(new SimpleUnit());//NOPMD
		}
	}
	/**
	 * Tests setAccuracy(): don't accept negative input
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetAccuracyNegative() {
		unit.setAccuracy(-0.1);
		fail("setAccuracy() accepted negative accuracy");
	}
	/**
	 * Tests setAccuracy(): don't accept input > 1
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetAccuracyTooBig() {
		unit.setAccuracy(1.1);
		fail("setAccuracy() accepted accuracy > 1");
	}
	/**
	 * Tests SimpleUnit.setAccuracy()
	 */
	@Test
	public void testSetAccuracy() {
		try {
			unit.setAccuracy(0.2);
		} catch (IllegalArgumentException e) {
			fail("setAccuracy choked on legal input");
		}
	}

	/**
	 * Tests Unit.move()
	 */
	@Test
	public void testMove() {
		unit.move(map.getTile(new Location(4, 4)), map);
		if (unit.getLocation() == map.getTile(new Location(4, 4))) {
			fail("Unit moved to unreachable location");
		}
		unit.move(map.getTile(new Location(1, 1)), map);
		if (map.getTile(new Location(1, 1)).getModuleOnTile() != unit) {
			fail("Unit failed to move to reachable location (tile.moduleOnTile)");
		}
		if (map.getTile(new Location(0, 0)).getModuleOnTile() != null) {
			fail("Unit failed to be removed from start tile when moving");
		}
		if (unit.getLocation() != map.getTile(new Location(1, 1))) {
			fail("Unit failed to move to reachable location");
		}
		if (!unit.isHasMoved()) {
			fail("Unit, having moved, is not so marked");
		}

	}

	/**
	 * Tests unit.attack()
	 */
	@Test
	public void testAttack() {
		unit.setMaxHP(BuildConstants.PHALANX_HP);
		unit.setHitPoints(BuildConstants.PHALANX_HP);
		unit2.attack(unit);
		if (unit.getHitPoints() != BuildConstants.PHALANX_HP
				- BuildConstants.BALLISTA_RANGED) {
			fail("Attack() dealt wrong amount of damage");
		}
		if (!unit2.isHasAttacked()) {
			fail("Attack() didn't set hasAttacked");
		}
		unit.setHitPoints(BuildConstants.PHALANX_HP);
		unit.setDefense(BuildConstants.CAVALRY_DEFENSE);
		unit2.attack(unit);
		if (unit.getHitPoints() != BuildConstants.PHALANX_HP
				- BuildConstants.BALLISTA_RANGED
				+ BuildConstants.CAVALRY_DEFENSE) {
			fail("Attack() didn't use defense stat properly");
		}
	}

	/**
	 * Test SimpleUnit.rangedAttack()
	 */
	@Test
	public void testRangedAttack() {
		unit.setMaxHP(BuildConstants.PHALANX_HP);
		unit.setHitPoints(BuildConstants.PHALANX_HP);
		unit2.rangedAttack(unit);
		if (unit.getHitPoints() != BuildConstants.PHALANX_HP
				- BuildConstants.BALLISTA_ATTACK) {
			fail("RangedAttack() dealt wrong amount of damage");
		}
		if (!unit2.isHasAttacked()) {
			fail("RangedAttack() didn't set hasAttacked");
		}
		unit.setHitPoints(BuildConstants.PHALANX_HP);
		unit.setDefense(BuildConstants.CAVALRY_DEFENSE);
		unit2.rangedAttack(unit);
		if (unit.getHitPoints() != BuildConstants.PHALANX_HP
				- BuildConstants.BALLISTA_ATTACK
				+ BuildConstants.CAVALRY_DEFENSE) {
			fail("RangedAttack() didn't use defense stat properly");
		}
	}

	/**
	 * Tests SimpleUnit.checkMove()
	 */
	@Test
	public void testCheckMove() {
		assertFalse("FIXME: Can't recall why this is false", ((SimpleUnit) map
				.getTile(new Location(2, 0)).getModuleOnTile()).checkMove(map.getTile(new Location(3, 0)),
				map));
		assertFalse("Can't move to occupied tile", unit.checkMove(map.getTile(new Location(
				2, 0)), map));
		unit2.setHasMoved(true);
		assertFalse("Can't move twice", unit2.checkMove(map.getTile(new Location(1, 1)), map));
		assertFalse("FIXME: Can't recall why this is false", unit.checkMove(map
				.getTile(new Location(3, 3)), map));
		assertTrue("A legal move", unit.checkMove(map.getTile(new Location(1, 1)), map));
	}
	/**
	 * Tests the checkAttack() method's handling of a null argument
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCheckAttackNull() {
		unit.checkAttack(null);
		fail("checkAttack() didn't catch null argument");
	}
	/**
	 * Tests the checkAttack() method's handling of an argument not on the map
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCheckAttackDisconnected() {
		unit.checkAttack(map.getTile(new Location(2, 2)).getModuleOnTile());
		fail("checkAttack() didn't catch target with no location or parent");
	}
	/**
	 * Tests the checkAttack() method
	 */
	@Test
	public void testCheckAttack() {
		unit2.setHasAttacked(true);
		assertFalse("Can't attack twice", unit2.checkAttack(unit));
		map.getTile(new Location(2, 2)).getModuleOnTile().setLocation(map.getTile(new Location(2, 2)));
		assertFalse("Non-adjacent unit", unit.checkAttack(map.getTile(new Location(2, 2))
				.getModuleOnTile()));
		assertTrue("Adjacent unit", unit.checkAttack(unit2));
	}
	/**
	 * Tests the checkRangedAttack() method's handling of a null argument
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCheckRangedAttackNull() {
		unit.checkRangedAttack(null);
		fail("checkRangedAttack() didn't catch null argument");
	}
	/**
	 * Tests the checkRangedAttack() method's handling of an argument not on the map
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCheckRangedAttackDisconnected() {
		unit.checkRangedAttack(map.getTile(new Location(2, 2)).getModuleOnTile());
		fail("checkRangedAttack() didn't catch target with no location or parent");
	}
	/**
	 * Tests checkRangedAttack()
	 */
	@Test
	public void testCheckRangedAttack() {
		unit2.setHasAttacked(true);
		assertFalse("Can't ranged-attack if already attacked", unit2
				.checkRangedAttack(unit));
		map.getTile(new Location(2, 2)).getModuleOnTile().setLocation(map.getTile(new Location(2, 2)));
		assertTrue("One successful possible attack", unit.checkRangedAttack(map
				.getTile(new Location(2, 2)).getModuleOnTile()));
		assertTrue("Another true possible attack", unit
				.checkRangedAttack(unit2));
	}
	/**
	 * Tests the takeAttack() method.
	 */
	@Test
	public void testTakeAttack() {
		unit.setMaxHP(BuildConstants.PHALANX_DEFENSE);
		unit.setHitPoints(BuildConstants.PHALANX_DEFENSE);
		unit2.attack(unit);
		assertTrue("Killed units get deleted",unit.isDeleted());
	}

}
