package proj_sp5;
private class Main {
	public static void main(final String[] args) {
		List<Player> players = new ArrayList<Player>();
		Player currentPlayer;
		int action = 1;
		int i,j,k,tempd;
		char tempX, tempY;
		List<Unit> tempU;
		Unit tempU2;
		char tempC;
		Structure tempS;
		boolean exit = false;
		Point tmpTile;
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
		players.add(createPlayer(15,16,500.0,2,1));
		debugPrint("Player 1 created\n");
		players.add(createPlayer(90,80,500.0,2,2));
		debugPrint("Player 2 created\n");
		players.get(0).resources.add(createResource(15,0));
		debugPrint("Player 1's resource created\n");
		players.get(1).resources.add(createResource(15,0));
		debugPrint("Player 2's resource created\n");
		players.get(0).units.add(createUnit("Unit 1", 10, 10, 10, 10, 
				1, 0, 15, 16, 1, 1, 1, 0, 1, 0, 0, 6, 1, 1, 0, 
				0, 5, 5, 2, 0));
		debugPrint("Player 1's unit created\n");
		players.get(1).units.add(createUnit("Unit 1", 10, 10, 10, 10,
				1, 0, 90, 80, 1, 2, 2, 0, 1, 0, 0, 6, 1, 1, 0,
				0, 5, 5, 2, 0));
		debugPrint("Player 2's unit created\n");
		players.get(0).structures.add(createStructure("Keep", 50, 20, 
				20, 50, 1, 0, 15, 16, 1, 1, 1, 0, 1, 0));
		debugPrint("Player 1's keep created\n");
		players.get(1).structures.add(createStructure("Keep", 50, 20, 
				20, 50, 1, 0, 90, 80, 1, 2, 2, 0, 1, 0));
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
		while (action != 0) {
			action = getAction();
			switch (action) {
			case 1: // attack
				debugPrint("Attack selected\n");
				puts("Who attacks?");
				tempU = selectUnit(currentPlayer.units);
				if (tempU.currentMovement == 0) {
					puts("This unit can't do anything " +
						"else this turn. Choose " +
						"another action.");
					break;
				}
				puts("Attacking unit or structure?");
				tempC = getUnitOrStructure();
				// that should prevent illegal input
				// and return a lowercase answer: u
				// for unit, s for structure.
				puts("Attacking what tile?");
				tmpTile = getTile();
				if (tempC == 'u') {
					tempU2 = getUnit(tmpTile, players);
					if (tempU.checkAttack(tempU2)) {
						tempU.attack(tempU2);
					} else {
						puts("Could not attack.");
					}
				} else {
					tempS = getStructure(tmpTile);
					if (tempU.checkAttackS(tempS)) {
						tempU.attackStructure(tempS);
					} else {
						puts("Could not attack.");
					}
				}
				break;
			case 2:
				debugPrint("Bombard selected.\n");
				puts("Who is bombarding?");
				tempU = selectUnit(currentPlayer.units);
				if (tempU.currentMovement == 0) {
					puts("This unit can't do anything " +
						"else this turn. Choose " +
						"another action.");
					break;
				}
				puts("Bombarding unit or structure?");
				tempC = getUnitOrStructure();
				puts("Bombarding what tile?");
				tempTile = getTile();
				if (tempC == 'u') {
					tempU2 = getUnit(tempTile, players);
					if (tempU.checkBombard(tempU2)) {
						tempU.bombard(tempU2);
					} else {
						puts("Could not bombard.");
					}
				} else {
					tempS = getStructure(tempTile);
					if (tempU.checkBombardS(tempS)) {
						tempU.bombardStructure(tempS);
					} else {
						puts("Could not bombard.");
					}
				}
				break;
			case 3:
				debugPrint("Move selected.\n");
				puts("Who is moving?");
				tempU = selectUnit(currentPlayer.units);
				if (tempU.currentMovement == 0) {
					puts("This unit can't do anything " +
						"else this turn. Choose " +
						"another action.");
					break;
				}
				tempd = selectDirection();
				tempPoint = getPoint(tempU, tempd);
				debugPrint("Got coordinates from direction.\n");
				handleAttemptedMove(tempU, tempPoint, players);
				break;
			case 4:
				debugPrint("Construct selected.");
				constructStructure(currentPlayer);
				break;
			case 5:
				debugPrint("Gather resources selected.");
				puts("Sorry --- not implemented yet.");
				break;
			case 6:
				debugPrint("Research selected.");
				puts("Sorry --- not implemented yet.");
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
				puts("Ending turn.");
				break;
			} // end switch (action)
		} // end while (action != 0)
		switchPlayer();
		refillMovement();
		if (!exit) {
			action = 1;
		}
	} // end mainloop()
	Point getPoint(final Unit u, final int direction) {
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
			puts("Shouldn't get here!");
			return null;
		}
	}
	private static void handleAttemptedMove(final Unit u, final Point p,
			final List<Player> players) {
		switch (u.checkMove(p)) {
		case FRIENDLY_OCCUPIED:
			puts("There's a friendly unit in the way. If it's " +
				"yours, move it first; if it's not yours, ask" +
				" its owner to vacate the tile.");
			break;
		case UNFRIENDLY_OCCUPIED:
			puts("There's an unfriendly unit in the way. Ask its " +
				"owner to vacate the tile.");
			break;
		case ENEMY_OCCUPIED:
			puts("There's an enemy unit in the way. Do you want " +
				"to attack it?");
			if (getBool()) {
				tempU.move(p);
			} else {
				puts("Go around, then.");
			}
			break;
		case FRIENDLY_HELD:
			puts("There's a friendly structure in the way. If " +
				"it's yours, you could get rid of it; " +
				"otherwise, go around.");
			break;
		case SP_UNFRIENDLY_HELD:
			puts("There's an unfriendly structure in the way. " +
				"Go around.");
			break;
		case ENEMY_HELD:
			puts("There's an enemy structure in the way. Do you " +
				"want to attack it?");
			if (getBool()) {
				puts("Moving . . .");
				tempU.move(p);
			} else {
				puts("So go around.");
			}
			break;
		case OK:
			tempU.move(p);
			break;
		case ILLEGAL_DATA:
			puts("The list of units or structures isn't " +
				"initialized. This is a bug.");
			break;
		default:
			puts("Could not move.");
			break;
		}
		puts("The unit has " + u.getCurrentMovement() + " MP left");
	}
	private static void debugPrint(final String s) {
		if (Debug.LEVEL != NONE) {
			System.out.print(s);
		}
	}
}
