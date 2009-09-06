package proj_sp6;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

class Structure {
	static final int NUM_STRUCTURE_TYPES = 5;
	String name;
	int maxHitPoints; // unsigned
	int meleeAC; // unsigned
	int rangedAC; // unsigned
	int currentHitPoints; // unsigned
	int currentLevel; // unsigned
	long currentExperience; // unsigned
	Point position; // unsigned
	long typeid; // unsigned
	long uid; // unsigned
	long playerid; // unsigned
	boolean delete;
	boolean friendlyOccupancy;
	int elevation;

	static Structure readStructureFromFile(final InputStream in) throws IOException {
		Globals.getStringFromFile(in, "STRATEGIC_PRIMER_STRUCTURE");
		return createStructure(Globals.getStringFromFile(in), Globals
				.getUIntFromFile(in), Globals.getIntFromFile(in), Globals
				.getIntFromFile(in), Globals.getUIntFromFile(in), Globals
				.getUIntFromFile(in), Globals.getULongFromFile(in), Point
				.getPointFromFile(in), Globals.getULongFromFile(in), Globals
				.getULongFromFile(in), Globals.getULongFromFile(in), Globals
				.getBooleanFromFile(in), Globals.getBooleanFromFile(in),
				Globals.getIntFromFile(in));
	}

	static void writeStructureToFile(final PrintStream out, final Structure s) {
		Globals.writeStringToFile(out, "STRATEGIC_PRIMER_STRUCTURE");
		Globals.writeStringToFile(out, s.name);
		Globals.writeUIntToFile(out, s.maxHitPoints);
		Globals.writeIntegerToFile(out, s.meleeAC);
		Globals.writeIntegerToFile(out, s.rangedAC);
		Globals.writeUIntToFile(out, s.currentHitPoints);
		Globals.writeUIntToFile(out, s.currentLevel);
		Globals.writeULongToFile(out, s.currentExperience);
		Point.writePointToFile(out, s.position);
		Globals.writeULongToFile(out, s.typeid);
		Globals.writeULongToFile(out, s.uid);
		Globals.writeULongToFile(out, s.playerid);
		Globals.writeBooleanToFile(out, s.delete);
		Globals.writeBooleanToFile(out, s.friendlyOccupancy);
		Globals.writeIntegerToFile(out, s.elevation);
	}

	static Structure getStructureFromUser() {
		Globals.puts("Please enter data for a Structure");
		Globals.puts("Name:");
		Structure s = new Structure();
		s.name = User.getStringFromUser();
		Globals.puts("Maximum Hit Points:");
		s.maxHitPoints = User.getUIntFromUser();
		Globals.puts("Melee Armor Class:");
		s.meleeAC = User.getIntegerFromUser();
		Globals.puts("Ranged Armor Class:");
		s.rangedAC = User.getIntegerFromUser();
		Globals.puts("Current Hit Points:");
		s.currentHitPoints = User.getUIntFromUser();
		Globals.puts("Current Level:");
		s.currentLevel = User.getUIntFromUser();
		Globals.puts("Current Experience:");
		s.currentExperience = User.getULongFromUser();
		Globals.puts("Position:");
		s.position = Point.getPointFromUser();
		Globals.puts("Type ID:");
		s.typeid = User.getULongFromUser();
		Globals.puts("Unique ID:");
		s.uid = User.getULongFromUser();
		Globals.puts("Player ID:");
		s.playerid = User.getULongFromUser();
		Globals.puts("Delete?");
		s.delete = User.getBooleanFromUser();
		Globals.puts("Allow Friendly Occupancy?");
		s.friendlyOccupancy = User.getBooleanFromUser();
		Globals.puts("Elevation:");
		s.elevation = User.getIntegerFromUser();
		return s;
	}

	static void showStructureToUser(final Structure s) {
		Globals.puts("Data of a Structure:");
		Globals.puts("Name:");
		Globals.puts(s.name);
		Globals.puts("Maximum Hit Points:");
		Globals.puts(Integer.toString(s.maxHitPoints));
		Globals.puts("Melee Armor Class:");
		Globals.puts(Integer.toString(s.meleeAC));
		Globals.puts("Ranged Armor Class:");
		Globals.puts(Integer.toString(s.rangedAC));
		Globals.puts("Current Hit Points:");
		Globals.puts(Integer.toString(s.currentHitPoints));
		Globals.puts("Current Level:");
		Globals.puts(Integer.toString(s.currentLevel));
		Globals.puts("Current Experience:");
		Globals.puts(Long.toString(s.currentExperience));
		Globals.puts("Current Position:");
		Point.showPointToUser(s.position);
		Globals.puts("Type ID:");
		Globals.puts(Long.toString(s.typeid));
		Globals.puts("Unique ID:");
		Globals.puts(Long.toString(s.uid));
		Globals.puts("Player ID:");
		Globals.puts(Long.toString(s.playerid));
		Globals.puts("Delete?");
		Globals.puts(Boolean.toString(s.delete));
		Globals.puts("Allow Friendly Occupancy?");
		Globals.puts(Boolean.toString(s.friendlyOccupancy));
		Globals.puts("Elevation:");
		Globals.puts(Integer.toString(s.elevation));
	}

	static Structure getStructure(final Point p) {
		for (Structure s : Globals.allStructures) {
			if (s.position.equals(p)) {
				return s;
			}
		}
		return null;
	}

	static Structure createStructure(String name, int maxHitPoints, int mac,
			int rac, int currHitPoints, int currLevel, long currXP, Point p,
			long typeid, long uid, long playerid, boolean delete,
			boolean friendOcc, int elev) {
		Structure s = new Structure();
		s.name = name;
		s.maxHitPoints = maxHitPoints;
		s.meleeAC = mac;
		s.rangedAC = rac;
		s.currentHitPoints = currHitPoints;
		s.currentLevel = currLevel;
		s.currentExperience = currXP;
		s.position = p;
		s.typeid = typeid;
		s.uid = uid;
		s.playerid = playerid;
		s.delete = delete;
		s.friendlyOccupancy = friendOcc;
		s.elevation = elev;
		return s;
	}
}
