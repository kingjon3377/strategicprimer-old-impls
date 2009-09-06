package proj_sp5;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

abstract class Globals {
	public static final Tile[][] map = new Tile[256][256];
	public static int maxX;
	public static int maxY;
	public static List<Structure> allStructures; // Structure-underbar-what?
	public static List<Resource>[] structureRequirements;
	// that's probably best to do *in* each structure type
	static int movementRequired[]; 
	// how much is needed for each tile type. Should be in Tile.
	static int PlayerRelations[][]; // inter-player status:war, alliance, what?
	// Should be handled differently. Somehow. But how?
	static long ExperiencePerLevel[][]; // unsigned. 
	// Is this the XP required to advance?
	static List<Resource>[] unitRequirements;
	// again, probably best done *in* each unit type
	static List<Unit> allUnits;
	public static long uid;
	// debugPrint, debugOpen, and debugClose will go in a
	// different class.
	enum Direction { NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST }
	static void puts(String line) {
		System.out.println(line);
	}
	static String getStringFromFile(InputStream in) {
		throw new IllegalStateException("Unimplemented");
	}
	static void getStringFromFile(InputStream in, String expected) throws IOException {
		if (!(getStringFromFile(in).equals(expected))) {
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
	public static Structure getStructure(int x, int y) {
		throw new IllegalStateException("Unimplemented");
	}
	static int getUIntFromFile(InputStream in) {
		throw new IllegalStateException("Unimplemented");
	}
	static long getULongFromFile(InputStream in) {
		throw new IllegalStateException("Unimplemented");
	}
}
