package proj_sp6;
class Globals {
	private Globals() {
		// do not instantiate
	}
	static enum TerrainType {
		OCEAN,
		DESERT,
		FOREST,
		PLAIN,
		TUNDRA,
		MOUNTAIN
	}
	static int MAX_X = 512, MAX_Y = 512;
	static List<Player> allPlayers;
	static Player currentPlayer;
	static List<Structure> allStructures;
	static List<Resource>[] structureRequirements = 
			new List<Resource>[NUM_STRUCTURE_TYPES];
	static Tile[][] Level = new Tile[MAX_X][MAX_Y];
	static int movementRequired[6];
	static enum PlayerRelation { }
	static PlayerRelation[][] PlayerRelations = 
			new PlayerRelation[NUM_PLAYERS][NUM_PLAYERS];
	static /* unsigned */ long XP_Level[100][100];
	static List<Resource>[] unitRequirements = 
			new List<Resource>[NUM_UNIT_TYPES];
	static List<Unit> allUnits>;
	static void debugPrint(final String str) {
		if (Debug.enabled) {
			puts(str);
		}
	}
	public static void main(final String[] args) {
		int action = 1;
		MPRequired[0] = 1;
		MPRequired[1] = 2;
		MPRequired[2] = 3;
		MPRequired[3] = 1;
		MPRequired[4] = 4;
		MPRequired[5] = 5;
		debugPrint("Allocation phase");
		allPlayers = new ArrayList<Player>();
		allPlayers.add(createPlayer(15, 16, 500.0, 2, 1));
		allPlayers.add(createPlayer(90, 80, 500.0, 2, 2));
		debugPrint("Players allocated");
		allPlayers.get(0).resources.add(createResource(15, 0));
		allPlayers.get(1).resources.add(createResource(15, 0));
		debugPrint("Resources allocated");
		allPlayers.get(0).units.add(createUnit("Unit 1", 10, 10, 10, 10,
				1, 0, 15, 16, 1, 1, 1, 0, 1, 0, 0, 6, 1, 1, 0,
				0, 5, 5, 2, 0));
		allPlayers.get(1).units.add(createUnit("Unit 1", 10, 10, 10, 10,
				1, 0, 90, 80, 1, 2, 2, 0, 1, 0, 0, 6, 1, 1, 0,
				0, 5, 5, 2, 0);
		debugPrint("Units allocated");
		allPlayers.get(0).structures.add(createStrucutre("Keep", 50, 20,
			20, 50, 1, 0, 15, 16, 1, 1, 1, 0, 1, 0));
		allPlayers.get(1).structures.add(createStructure("Keep", 50, 20,
			20, 50, 1, 0, 90, 80, 1, 2, 2, 0, 1, 0));
		debugPrint("Structures allocated");
		currentPlayer = allPlayers.get(0);
		debugPrint("Phase complete!");
		debugPrint("Action Phase");
		boolean exit = false;
		while (!exit) {
			while (action != 0) {
				action = getAction();
				switch (action) {
				case ATTACK:
					debugPrint("Attack selected.\n");
					puts("Who is attacking?");
					Unit tempU = selectUnit(allUnits);
					if (tempU.currentMovement == 0) {
						puts("This unit can't do " +
							"anything else this " +
							"turn. Choose another "+
							"action.");
						break;
					}
					puts("Attacking Unit or Structure?");
					boolean isUnit = getBoolean();
					puts("Attacking what tile?");
					Tile targetTile = getTile();
					if (isUnit) {
						Unit tempU2 = getUnit(tile);
						if (tempU.checkAttack(tempU2)) {
							tempU.attack(tempU2);
						} else {
							puts("Couldn't attack");
						}
					} else {
						Structure tempS = 
							getStructure(tile);
						if (tempU.checkAttackS(tempS)) {
							tempU.attackStructure(
									tempS);
						} else {
							puts("Couldn't attack");
						}
					}
					break;
				case BOMBARD:
					debugPrint("Bombard selected");
					puts("Who is bombarding?");
					tempU = selectUnit(allUnits);
					if (tempU.currentMovement == 0) {
						puts("This unit can't do " +
							"anything else this " +
							"turn. Choose another "+
							"action.");
						break;
					}
					puts("Bombarding unit or structure?");
					boolean isUnit = getBoolean();
					puts("Bombarding what tile?");
					Tile tmpTile = getTile();
					if (isUnit) {
						Unit tempU2 = getUnit(tmpTile);
						if (tempU.checkBombard(tempU2)){
							tempU.bombard(tempU2);
						} else {
							puts("Could not " +
								"bombard.");
						}
					} else {
						Structure tempS = 
							getStructure(tmpTile);
						if (tempU.checkBombardS(tempS)){
							tempU.bombardStructure(
									tempS);
						} else {
							puts("Could not " +
								"bombard.");
						}
					}
					break;
				case MOVE:
					debugPrint("Move selected");
					puts("Who is moving?");
					Unit tempU = selectUnit(allUnits);
					if (tempU.currentMovement == 0) {
						puts("This unit can't do " +
							"anything else this " +
							"turn. Choose another "+
							"action.");
						break;
					}
					Direction tempd = selectDirection();
					Tile tile = getTargetTile(tempU,tempd);
					switch (tempU.checkMove(tile)) {
					case FRIENDLY_OCCUPIED:
						puts("There's a friendly unit "+
							"in the way. If it's " +
							"yours, move it first;"+
							" if not, ask its " +
							"owner to vacate the " +
							"tile.");
						break;
					case UNFRIENDLY_OCCUPIED:
						puts("There's an unfriendly " +
							"unit in the way. Ask "+
							"its owner to vacate " +
							"the tile.");
						break;
					case ENEMY_OCCUPIED:
						puts("There's an enemy unit " +
							"in the way. Do you " +
							"want to attack it?");
						if (getBoolean()) {
							puts("Moving ...");
							tempU.move(tile);
							// tempU.move(tempd)
						} else {
							puts("Go around, then");
						}
						break;
					case FRIENDLY_HELD:
						puts("There's a friendly " +
							"structure in the way."+
							" If it's yours you " +
							"could get rid of it; "+
							"otherwise, go around");
						break;
					case UNFRIENDLY_HELD:
						puts("There's an unfriendly " +
							"structure in the way."+
							" Go around.");
						break;
					case ENEMY_HELD:
						puts("There's an enemy " +
							"structure in the way."+
							" Do you want to " +
							"attack it?");
						if (getBoolean()) {
							puts("Moving ...");
							tempU.move(tile);
							// tempU.move(tempd);
						} else {
							puts("So go around.");
						}
						break;
					case OK:
						tempU.move(tile);
						// tempU.move(tempd);
						break;
					case ILLEGAL_DATA:
						puts("Something's not " +
							"initialized properly."+
							" This is a bug.");
						break;
					default:
						puts("Could not move.");
						break;
					}
					puts("It has " + tempU.currentMovement +
						" MP left.");
					break;
				case CONSTRUCT:
					debugPrint("Construct selected.");
					currentPlayer.constructStructure();
					break;
				case GATHER_RESOURCES:
					debugPrint("Gather resources selected");
					puts("Sorry; not implemented yet.");
					break;
				case RESEARCH:
					debugPrint("Research selected");
					puts("Sorry; not implemented yet.");
					break;
				case PRODUCE:
					debugPrint("Produce Unit selected");
					produceUnit();
					break;
				case QUIT:
					debugPrint("Quit selected");
					puts("Quitting ...");
					action = 0;
					exit = true;
					break;
				default:
					debugPrint("End Turn selected");
					puts("Ending your turn ...");
					break;
				} // switch (action)
			} // while (action != 0)
			incrementCurrentPlayer(); //includes refreshing movement
			if (!exit) action = 1;
		}
	}
	static Tile getTargetTile(final Unit u, final Direction d) {
		switch (d) {
		case NORTH:
			return getTile(u.position.X + 1, u.position.Y);
		case NORTHEAST:
			return getTile(u.position.X + 1, u.position.Y - 1);
		case EAST:
			return getTile(u.position.X, u.position.Y - 1);
		case SOUTHEAST:
			return getTile(u.position.X - 1, u.position.Y - 1);
		case SOUTH:
			return getTile(u.position.X - 1, u.position.Y);
		case SOUTHWEST:
			return getTile(u.position.X - 1, u.position.Y + 1);
		case WEST:
			return getTile(u.position.X, u.position.Y + 1);
		case NORTHWEST:
			return getTile(u.position.X + 1, u.position.Y + 1);
		}
	}
}
