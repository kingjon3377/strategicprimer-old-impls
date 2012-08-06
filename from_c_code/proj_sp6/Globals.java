package proj_sp6;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import proj_sp6.Debug.ReturnValues;
import proj_sp6.User.Action;

class Globals {
	enum Direction { NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST }
	private static final int NUM_STRUCTURE_TYPES = 0;
	private static final int NUM_PLAYERS = 0;
	private static final int NUM_UNIT_TYPES = 0;
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
	static List<List<Resource>> structureRequirements = 
			new ArrayList<List<Resource>>(NUM_STRUCTURE_TYPES);
	static Tile[][] Level = new Tile[MAX_X][MAX_Y];
	static int movementRequired[] = new int[6];
	static Debug.Relations[][] PlayerRelations = 
			new Debug.Relations[NUM_PLAYERS][NUM_PLAYERS];
	static /* unsigned */ long XP_Level[][] = new long[100][100];
	static List<List<Resource>> unitRequirements = 
			new ArrayList<List<Resource>>(NUM_UNIT_TYPES);
	static List<Unit> allUnits;
	static void debugPrint(final String str) {
		if (Debug.enabled) {
			puts(str);
		}
	}
	static void puts(String str) {
		// TODO Auto-generated method stub
		
	}
	public static void main(final String[] args) {
		Action action = Action.DEFAULT;
		movementRequired[0] = 1;
		movementRequired[1] = 2;
		movementRequired[2] = 3;
		movementRequired[3] = 1;
		movementRequired[4] = 4;
		movementRequired[5] = 5;
		debugPrint("Allocation phase");
		allPlayers = new ArrayList<Player>();
		allPlayers.add(Player.createPlayer(new Point(15, 16), 500.0, 2, 1));
		allPlayers.add(Player.createPlayer(new Point(90, 80), 500.0, 2, 2));
		debugPrint("Players allocated");
		allPlayers.get(0).resources.add(Resource.createResource(15, 0));
		allPlayers.get(1).resources.add(Resource.createResource(15, 0));
		debugPrint("Resources allocated");
		allPlayers.get(0).units.add(Unit.createUnit("Unit 1", 10, 10, 10, 10,
				1, 0L, new Point(15, 16), 1L, 1L, 1L, false, true, 0, 0, 6, 1, 1, 0,
				0, 5, 5, 2, 0));
		allPlayers.get(1).units.add(Unit.createUnit("Unit 1", 10, 10, 10, 10,
				1, 0L, new Point(90, 80), 1L, 2L, 2L, false, true, 0, 0, 6, 1, 1, 0,
				0, 5, 5, 2, 0));
		debugPrint("Units allocated");
		allPlayers.get(0).structures.add(Structure.createStructure("Keep", 50, 20,
			20, 50, 1, 0L, new Point(15, 16), 1L, 1L, 1L, false, true, 0));
		allPlayers.get(1).structures.add(Structure.createStructure("Keep", 50, 20,
			20, 50, 1, 0L, new Point(90, 80), 1L, 2L, 2L, false, true, 0));
		debugPrint("Structures allocated");
		currentPlayer = allPlayers.get(0);
		debugPrint("Phase complete!");
		debugPrint("Action Phase");
		boolean exit = false;
		while (!exit) {
			while (action != action.END_TURN) {
				action = User.getAction();
				switch (action) {
				case ATTACK:
					debugPrint("Attack selected.\n");
					puts("Who is attacking?");
					Unit tempU = User.selectUnit(allUnits);
					if (tempU.currentMovement == 0) {
						puts("This unit can't do " +
							"anything else this " +
							"turn. Choose another "+
							"action.");
						break;
					}
					puts("Attacking Unit or Structure?");
					boolean isUnit = User.getBooleanFromUser();
					puts("Attacking what tile?");
					Tile targetTile = getTile();
					if (isUnit) {
						Unit tempU2 = getUnit(targetTile);
						if (tempU.checkAttack(tempU2)!= ReturnValues.OK) {
							tempU.attack(tempU2);
						} else {
							puts("Couldn't attack");
						}
					} else {
						Structure tempS = 
							getStructure(targetTile);
						if (tempU.checkAttackS(tempS) != ReturnValues.OK) {
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
					tempU = User.selectUnit(allUnits);
					if (tempU.currentMovement == 0) {
						puts("This unit can't do " +
							"anything else this " +
							"turn. Choose another "+
							"action.");
						break;
					}
					puts("Bombarding unit or structure?");
					isUnit = User.getBooleanFromUser();
					puts("Bombarding what tile?");
					Tile tmpTile = getTile();
					if (isUnit) {
						Unit tempU2 = getUnit(tmpTile);
						if (tempU.checkBombard(tempU2)!=ReturnValues.OK){
							tempU.bombard(tempU2);
						} else {
							puts("Could not " +
								"bombard.");
						}
					} else {
						Structure tempS = 
							getStructure(tmpTile);
						if (tempU.checkBombardS(tempS)!=ReturnValues.OK){
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
					tempU = User.selectUnit(allUnits);
					if (tempU.currentMovement == 0) {
						puts("This unit can't do " +
							"anything else this " +
							"turn. Choose another "+
							"action.");
						break;
					}
					Direction tempd = User.selectDirection();
					Tile tile = getTargetTile(tempU,tempd);
					switch (tempU.checkMove(tile.location)) {
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
						if (User.getBooleanFromUser()) {
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
						if (User.getBooleanFromUser()) {
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
					action = Action.END_TURN;
					exit = true;
					break;
				default:
					debugPrint("End Turn selected");
					puts("Ending your turn ...");
					break;
				} // switch (action)
			} // while (action != 0)
			incrementCurrentPlayer(); //includes refreshing movement
			if (!exit) action = Action.DEFAULT;
		}
	}
	private static void incrementCurrentPlayer() {
		// TODO Auto-generated method stub
		
	}
	private static void produceUnit() {
		// TODO Auto-generated method stub
		
	}
	private static Structure getStructure(Tile targetTile) {
		// TODO Auto-generated method stub
		return null;
	}
	private static Unit getUnit(Tile targetTile) {
		// TODO Auto-generated method stub
		return null;
	}
	private static Tile getTile() {
		// TODO Auto-generated method stub
		return null;
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
		default:
			throw new IllegalStateException("Unknown direction");
		}
	}
	private static Tile getTile(int i, int y) {
		// TODO Auto-generated method stub
		return null;
	}
	static String getStringFromFile(InputStream in) {
		throw new IllegalStateException("Unimplemented");
	}
	static void getStringFromFile(InputStream in, String expected) throws IOException {
		if (!(getStringFromFile(in).equals(expected))) {
			throw new IOException("Unexpected input from file");
		}
	}
	static void getUIntFromFile(InputStream in, int expected) throws IOException {
		if (!(getUIntFromFile(in) == expected)) {
			throw new IOException("Unexpected input from file");
		}
	}
	static double getDoubleFromFile(InputStream in) {
		throw new IllegalStateException("Unimplemented");
	}
	static int getIntFromFile(InputStream in) {
		throw new IllegalStateException("Unimplemented");
	}
	static int getLongIntFromFile(InputStream in) {
		throw new IllegalStateException("Unimplemented");
	}
	static boolean getBooleanFromFile(InputStream in) {
		throw new IllegalStateException("Unimplemented");
	}
	static int getUIntFromFile(InputStream in) {
		throw new IllegalStateException("Unimplemented");
	}
	static long getULongFromFile(InputStream in) {
		throw new IllegalStateException("Unimplemented");
	}
	public static void writeStringToFile(PrintStream out, String string) {
		out.print(string);
	}
	public static void writeDoubleToFile(PrintStream out, double d) {
		out.print(Double.toString(d));
	}
	public static void writeUIntToFile(PrintStream out, int i) {
		out.print(Integer.toString(i));
	}
	public static void writeULongToFile(PrintStream out, long l) {
		out.print(Long.toString(l));
	}
	public static void writeBooleanToFile(PrintStream out, boolean b) {
		out.print(Boolean.toString(b));
	}
	public static void writeIntegerToFile(PrintStream out, int i) {
		out.print(Integer.toString(i));
	}
	public static Structure getStructure(Point p) {
		// TODO Auto-generated method stub
		return null;
	}
}