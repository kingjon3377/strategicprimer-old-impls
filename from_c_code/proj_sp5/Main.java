package proj_sp5;

import java.util.List;
import java.util.ArrayList;

import proj_sp5.Globals.Direction;

abstract class Main {
	static List<Player> players = new ArrayList<Player>();
	static Player currentPlayer;
	static boolean exit = false;

	public static void main(final String[] args) {
		int i, j, k;
		char tempX, tempY;
		Globals.movementRequired = new int[6];
		Globals.movementRequired[0] = 1;
		Globals.movementRequired[1] = 2;
		Globals.movementRequired[2] = 3;
		Globals.movementRequired[3] = 1;
		Globals.movementRequired[4] = 4;
		Globals.movementRequired[5] = 5;
		debugPrint("ALLOCATION PHASE\n");
		debugPrint("Declarations in main()\n");
		Globals.maxX = 100;
		Globals.maxY = 100;
		debugPrint("maxX and maxY set\n");
		players.add(Player.createPlayer(new Point(15, 16), 500.0, 2, 1));
		debugPrint("Player 1 created\n");
		players.add(Player.createPlayer(new Point(90, 80), 500.0, 2, 2));
		debugPrint("Player 2 created\n");
		players.get(0).resources.add(Resource.createResource(15, 0));
		debugPrint("Player 1's resource created\n");
		players.get(1).resources.add(Resource.createResource(15, 0));
		debugPrint("Player 2's resource created\n");
		players.get(0).units.add(Unit.createUnit("Unit 1", 10, 10, 10, 10, 1,
				0L, new Point(15, 16), 1L, 1L, 1L, false, true, 0, 0, 6, 1, 1,
				0, 0, 5, 5, 2, 0, 0));
		debugPrint("Player 1's unit created\n");
		players.get(1).units.add(Unit.createUnit("Unit 1", 10, 10, 10, 10, 1,
				0L, new Point(90, 80), 1L, 2L, 2L, false, true, 0, 0, 6, 1, 1,
				0, 0, 5, 5, 2, 0, 0));
		debugPrint("Player 2's unit created\n");
		players.get(0).structures.add(Structure.createStructure("Keep", 50, 20,
				20, 50, 1, 0L, new Point(15, 16), 1L, 1L, 1L, false, true, 0));
		debugPrint("Player 1's keep created\n");
		players.get(1).structures.add(Structure.createStructure("Keep", 50, 20,
				20, 50, 1, 0L, new Point(90, 80), 1L, 2L, 2L, false, true, 0));
		debugPrint("Player 2's keep created\n");
		currentPlayer = players.get(0);
		debugPrint("Success!\n");
		debugPrint("ACTION PHASE\n");
		while (!exit) {
			mainloop();
		}
		// Skipping deallocation phase due to garbage
		// collection
	}

	private static void mainloop() {
		int action = 1;
		Unit tempU;
		Unit tempU2;
		char tempC;
		Structure tempS;
		Point tmpTile;
		Direction tempd;
		while (action != 0) {
			action = getAction();
			switch (action) {
			case 1: // attack
				debugPrint("Attack selected\n");
				Globals.puts("Who attacks?");
				tempU = selectUnit(currentPlayer.units);
				if (tempU.currentMovement == 0) {
					Globals.puts("This unit can't do anything "
							+ "else this turn. Choose " + "another action.");
					break;
				}
				Globals.puts("Attacking unit or structure?");
				tempC = getUnitOrStructure();
				// that should prevent illegal input
				// and return a lowercase answer: u
				// for unit, s for structure.
				Globals.puts("Attacking what tile?");
				tmpTile = getTile();
				if (tempC == 'u') {
					tempU2 = getUnit(tmpTile, players);
					if (tempU.checkAttack(tempU2) == Debug.OK) {
						tempU.attack(tempU2);
					} else {
						Globals.puts("Could not attack.");
					}
				} else {
					tempS = getStructure(tmpTile);
					if (tempU.checkAttackS(tempS) == Debug.OK) {
						tempU.attackStructure(tempS);
					} else {
						Globals.puts("Could not attack.");
					}
				}
				break;
			case 2:
				debugPrint("Bombard selected.\n");
				Globals.puts("Who is bombarding?");
				tempU = selectUnit(currentPlayer.units);
				if (tempU.currentMovement == 0) {
					Globals.puts("This unit can't do anything "
							+ "else this turn. Choose " + "another action.");
					break;
				}
				Globals.puts("Bombarding unit or structure?");
				tempC = getUnitOrStructure();
				Globals.puts("Bombarding what tile?");
				tmpTile = getTile();
				if (tempC == 'u') {
					tempU2 = getUnit(tmpTile, players);
					if (tempU.checkBombard(tempU2) == Debug.OK) {
						tempU.bombard(tempU2);
					} else {
						Globals.puts("Could not bombard.");
					}
				} else {
					tempS = getStructure(tmpTile);
					if (tempU.checkBombardS(tempS) == Debug.OK) {
						tempU.bombardStructure(tempS);
					} else {
						Globals.puts("Could not bombard.");
					}
				}
				break;
			case 3:
				debugPrint("Move selected.\n");
				Globals.puts("Who is moving?");
				tempU = selectUnit(currentPlayer.units);
				if (tempU.currentMovement == 0) {
					Globals.puts("This unit can't do anything "
							+ "else this turn. Choose " + "another action.");
					break;
				}
				tempd = selectDirection();
				tmpTile = getPoint(tempU, tempd);
				debugPrint("Got coordinates from direction.\n");
				handleAttemptedMove(tempU, tmpTile, players);
				break;
			case 4:
				debugPrint("Construct selected.");
				constructStructure(currentPlayer);
				break;
			case 5:
				debugPrint("Gather resources selected.");
				Globals.puts("Sorry --- not implemented yet.");
				break;
			case 6:
				debugPrint("Research selected.");
				Globals.puts("Sorry --- not implemented yet.");
				break;
			case 7:
				debugPrint("Produce Unit selected.");
				produceUnit(currentPlayer);
				break;
			case 8:
				debugPrint("Quit selected.");
				action = 0;
				exit = true;
				break;
			default:
				debugPrint("End Turn selected.");
				Globals.puts("Ending turn.");
				break;
			} // end switch (action)
		} // end while (action != 0)
		switchPlayer();
		refillMovement();
		if (!exit) {
			action = 1;
		}
	} // end mainloop()

	private static void refillMovement() {
		// TODO Auto-generated method stub
		
	}

	private static void switchPlayer() {
		// TODO Auto-generated method stub
		
	}

	private static void produceUnit(Player currentPlayer2) {
		// TODO Auto-generated method stub
		
	}

	private static void constructStructure(Player currentPlayer2) {
		// TODO Auto-generated method stub
		
	}

	private static Direction selectDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	private static Structure getStructure(Point tmpTile) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Unit getUnit(Point tmpTile, List<Player> players2) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Point getTile() {
		// TODO Auto-generated method stub
		return null;
	}

	private static char getUnitOrStructure() {
		// TODO Auto-generated method stub
		return 0;
	}

	private static Unit selectUnit(List<Unit> units) {
		// TODO Auto-generated method stub
		return null;
	}

	private static int getAction() {
		// TODO Auto-generated method stub
		return 0;
	}

	static Point getPoint(final Unit u, final Direction direction) {
		switch (direction) {
		case NORTH:
			return new Point(u.position.X + 1, u.position.Y);
		case NORTHEAST:
			return new Point(u.position.X + 1, u.position.Y - 1);
		case EAST:
			return new Point(u.position.X, u.position.Y - 1);
		case SOUTHEAST:
			return new Point(u.position.X - 1, u.position.Y - 1);
		case SOUTH:
			return new Point(u.position.X - 1, u.position.Y);
		case SOUTHWEST:
			return new Point(u.position.X - 1, u.position.Y + 1);
		case WEST:
			return new Point(u.position.X, u.position.Y + 1);
		case NORTHWEST:
			return new Point(u.position.X + 1, u.position.Y + 1);
		default:
			Globals.puts("Shouldn't get here!");
			return null;
		}
	}

	private static void handleAttemptedMove(final Unit u, final Point p,
			final List<Player> players) {
		switch (u.checkMove(p,players)) {
		case Debug.FRIENDLY_OCCUPIED:
			Globals.puts("There's a friendly unit in the way. If it's "
					+ "yours, move it first; if it's not yours, ask"
					+ " its owner to vacate the tile.");
			break;
		case Debug.UNFRIENDLY_OCCUPIED:
			Globals.puts("There's an unfriendly unit in the way. Ask its "
					+ "owner to vacate the tile.");
			break;
		case Debug.ENEMY_OCCUPIED:
			Globals.puts("There's an enemy unit in the way. Do you want "
					+ "to attack it?");
			if (User.getBooleanFromUser()) {
				u.move(p);
			} else {
				Globals.puts("Go around, then.");
			}
			break;
		case Debug.FRIENDLY_HELD:
			Globals.puts("There's a friendly structure in the way. If "
					+ "it's yours, you could get rid of it; "
					+ "otherwise, go around.");
			break;
		case Debug.UNFRIENDLY_HELD:
			Globals.puts("There's an unfriendly structure in the way. " + "Go around.");
			break;
		case Debug.ENEMY_HELD:
			Globals.puts("There's an enemy structure in the way. Do you "
					+ "want to attack it?");
			if (User.getBooleanFromUser()) {
				Globals.puts("Moving . . .");
				u.move(p);
			} else {
				Globals.puts("So go around.");
			}
			break;
		case Debug.OK:
			u.move(p);
			break;
		case Debug.ILLEGAL_DATA:
			Globals.puts("The list of units or structures isn't "
					+ "initialized. This is a bug.");
			break;
		default:
			Globals.puts("Could not move.");
			break;
		}
		Globals.puts("The unit has " + u.currentMovement + " MP left");
	}

	private static void debugPrint(final String s) {
//		if (Debug.LEVEL != Debug.NONE) {
			System.out.print(s);
//		}
	}
}
