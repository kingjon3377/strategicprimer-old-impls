package proj_sp5;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

class Structure {
	String name;
	int maxHitPoints; // unsigned
	int meleeAC; // originally unsigned
	int rangedAC; // originally unsigned
	int currentHitPoints; // unsigned
	int currentLevel; // unsigned
	long currentExperience; // unsigned
	Point position;
	long typeID; // unsigned
	long uid; // unsigned
	long playerID; // unsigned
	boolean delete;
	boolean friendlyOccupancy;
	int elevation;

	static Structure createStructure(final String name, final int maxHP,
			final int mac, final int rac, final int currentHP,
			final int currentLevel, final long currentXP, final Point position,
			final long typeid, final long uid, final long playerid,
			final boolean delete, final boolean friendlyOcc, final int elevation) {
		Structure temp = new Structure();
		temp.name = name;
		temp.maxHitPoints = maxHP;
		temp.meleeAC = mac;
		temp.rangedAC = rac;
		temp.currentHitPoints = currentHP;
		temp.currentLevel = currentLevel;
		temp.currentExperience = currentXP;
		temp.position = new Point(position);
		temp.typeID = typeid;
		temp.uid = uid;
		temp.playerID = playerid;
		temp.delete = delete;
		temp.friendlyOccupancy = friendlyOcc;
		temp.elevation = elevation;
		return temp;
	}

	static Structure getStructureFromFile(final InputStream in) throws IOException {
		Globals.getStringFromFile(in, "STRATEGIC PRIMER STRUCTURE");
		return createStructure(Globals.getStringFromFile(in), Globals
				.getIntFromFile(in), Globals.getIntFromFile(in), Globals
				.getIntFromFile(in), Globals.getIntFromFile(in), Globals
				.getIntFromFile(in), Globals.getLongIntFromFile(in), Point
				.getPointFromFile(in), Globals.getLongIntFromFile(in), Globals
				.getLongIntFromFile(in), Globals.getLongIntFromFile(in), Globals
				.getBooleanFromFile(in), Globals.getBooleanFromFile(in),
				Globals.getIntFromFile(in));
	}

	static void writeStructureToFile(final PrintStream out, final Structure s) {
		out.print("STRATEGIC PRIMER STRUCTURE");
		out.print(s.name);
		out.print(s.maxHitPoints);
		out.print(s.meleeAC);
		out.print(s.rangedAC);
		out.print(s.currentHitPoints);
		out.print(s.currentLevel);
		out.print(s.currentExperience);
		out.print(s.position);
		out.print(s.typeID);
		out.print(s.uid);
		out.print(s.playerID);
		out.print(s.delete);
		out.print(s.friendlyOccupancy);
		out.print(s.elevation);
	}

	static Structure getStructureFromUser() {
		Globals.puts("Please enter data for a Structure.");
		Globals.puts("Name");
		String name = User.getStringFromUser();
		Globals.puts("Maximum HP");
		int maxHP = User.getUIntFromUser();
		Globals.puts("Melee AC");
		int meleeAC = User.getIntegerFromUser();
		Globals.puts("Ranged AC");
		int rangedAC = User.getIntegerFromUser();
		Globals.puts("Current HP");
		int currHP = User.getUIntFromUser();
		Globals.puts("Current Level");
		int currentLevel = User.getUIntFromUser();
		Globals.puts("Current Experience");
		long currentXP = User.getULongFromUser();
		Globals.puts("Current Location");
		Point location = Point.getPointFromUser();
		Globals.puts("Type ID");
		long typeid = User.getULongFromUser();
		Globals.puts("Unique ID");
		long uid = User.getULongFromUser();
		Globals.puts("PlayerID");
		long playerid = User.getULongFromUser();
		Globals.puts("Delete?");
		boolean delete = User.getBooleanFromUser();
		Globals.puts("Friendly Occupancy?");
		boolean frOcc = User.getBooleanFromUser();
		Globals.puts("Elevation");
		int elev = User.getIntegerFromUser();
		return createStructure(name, maxHP, meleeAC, rangedAC, currHP,
				currentLevel, currentXP, location, typeid, uid, playerid,
				delete, frOcc, elev);
	}

	static void showStructureToUser(final Structure s) {
		Globals.puts("Data of a Structure");
		Globals.puts("Name:");
		Globals.puts(s.name);
		Globals.puts("Maximum HP:");
		Globals.puts(Integer.toString(s.maxHitPoints));
		Globals.puts("Melee Armor Class:");
		Globals.puts(Integer.toString(s.meleeAC));
		Globals.puts("Ranged Armor Class:");
		Globals.puts(Integer.toString(s.rangedAC));
		Globals.puts("Current HP:");
		Globals.puts(Integer.toString(s.currentHitPoints));
		Globals.puts("Current Level:");
		Globals.puts(Integer.toString(s.currentLevel));
		Globals.puts("Current Experience:");
		Globals.puts(Long.toString(s.currentExperience));
		Globals.puts("Current Position:");
		Globals.puts(s.position.toString());
		Globals.puts("Type ID");
		Globals.puts(Long.toString(s.typeID));
		Globals.puts("Unique ID");
		Globals.puts(Long.toString(s.uid));
		Globals.puts("Player ID");
		Globals.puts(Long.toString(s.playerID));
		Globals.puts("Marked for deletion?");
		Globals.puts(Boolean.toString(s.delete));
		Globals.puts("Friendly Occupancy?");
		Globals.puts(Boolean.toString(s.friendlyOccupancy));
		Globals.puts("Elevation");
		Globals.puts(Integer.toString(s.elevation));
	}

	static Structure getStructure(final Point p) {
		return getStructure(p.X, p.Y);
	}

	static Structure getStructure(final int X, final int Y) {
		for (Structure s : Globals.allStructures) {
			if (s.position.X == X && s.position.Y == Y) {
				return s;
			}
		}
		return null;
	}

	static boolean constructStructure(final Player owner) {
		Globals.puts("What kind of structure would you like to build?");
		Globals.puts("(1) Low Wooden Wall,");
		Globals.puts("(2) Wooden Pallisade,");
		Globals.puts("(3) Wooden Wall, or");
		Globals.puts("(q) none of the above.");
		Globals.puts("What would you like to build?");
		// actually implement; stubbed-out in original
		// Original just shows which was selected.
		return false;
	}
}
