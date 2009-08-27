package proj_sp6;
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
	static Structure readStructureFromFile(final InputStream in) {
		readStringFromFile(in, "STRATEGIC_PRIMER_STRUCTURE");
		return createStructure(readStringFromFile(in), 
			readUIntFromFile(in), readIntFromFile(in), 
			readIntFromFile(in), readUIntFromFile(in), 
			readUIntFromFile(in), readULongFromFile(in), 
			readPointFromFile(in), readULongFromFile(in), 
			readULongFromFile(in), readULongFromFile(in), 
			readBooleanFromFile(in), readBooleanFromFile(in), 
			readIntFromFile(in));
	}
	static void writeStructureToFile(final OutputStream out, 
			final Structure s) {
		writeStringToFile(out,"STRATEGIC_PRIMER_STRUCTURE");
		writeStringToFile(out, s.name);
		writeUIntToFile(out, s.maxHitPoints);
		writeIntToFile(out, s.meleeAC);
		writeIntToFile(out, s.rangedAC);
		writeUIntToFile(out, s.currentHitPoints);
		writeUIntToFile(out, s.currentLevel);
		writeULongToFile(out, s.currentExperience);
		writePointToFile(out, s.position);
		writeULongToFile(out, s.typeid);
		writeULongToFile(out, s.uid);
		writeULongToFile(out, s.playerid);
		writeBooleanToFile(out, s.delete);
		writeBooleanToFile(out, s.friendlyOccupancy);
		writeIntToFile(out, s.elevation);
	}
	static Structure getStructureFromUser() {
		puts("Please enter data for a Structure");
		puts("Name:");
		Structure s = new Structure();
		s.name = getStringFromUser();
		puts("Maximum Hit Points:");
		s.maxHitPoints = getUIntFromUser();
		puts("Melee Armor Class:");
		s.meleeAC = getIntFromUser();
		puts("Ranged Armor Class:");
		s.rangedAC = getIntFromUser();
		puts("Current Hit Points:");
		s.currentHitPoints = getUIntFromUser();
		puts("Current Level:");
		s.currentLevel = getUIntFromUser();
		puts("Current Experience:");
		s.currentExperience = getULongFromUser();
		puts("Position:");
		s.location = getPointFromUser();
		puts("Type ID:");
		s.typeid = getULongFromUser();
		puts("Unique ID:");
		s.uid = getULongFromUser();
		puts("Player ID:");
		s.playerid = getULongFromUser();
		puts("Delete?");
		s.delete = getBooleanFromUser();
		puts("Allow Friendly Occupancy?");
		s.friendlyOccupancy = getBooleanFromUser():
		puts("Elevation:");
		s.elevation = getIntFromUser();
	}
	static void showStructureToUser(final Structure s) {
		puts("Data of a Structure:");
		puts("Name:");
		puts(s.name);
		puts("Maximum Hit Points:");
		puts(s.maxHitPoints);
		puts("Melee Armor Class:");
		puts(s.meleeAC);
		puts("Ranged Armor Class:");
		puts(s.rangedAC);
		puts("Current Hit Points:");
		puts(s.currentHitPoints);
		puts("Current Level:");
		puts(s.currentLevel);
		puts("Current Experience:");
		puts(s.currentExperience);
		puts("Current Position:");
		puts(s.position);
		puts("Type ID:");
		puts(s.typeid);
		puts("Unique ID:");
		puts(s.uid);
		puts("Player ID:");
		puts(s.playerid);
		puts("Delete?");
		puts(s.delete);
		puts("Allow Friendly Occupancy?");
		puts(s.friendlyOccupancy);
		puts("Elevation:");
		puts(s.elevation);
	}
	static Structure getStructure(final Point p) {
		for (Structure s : allStructures) {
			if (s.location.equals(p)) {
				return s;
			}
		}
		return null;
	}
	static Structure createStructure(String name, int maxHitPoints, int mac,
			int rac, int currHitPoints, int currLevel, long currXP,
			Point p, long typeid, long uid, long playerid,
			boolean delete, boolean friendOcc, int elev) {
		Structure s = new Structure();
		s.name = name;
		s.maxHitPoints = maxHitPoints;
		s.meleeAC = mac;
		s.rangedAC = rac;
		s.currentHitPoints = currHitPoints;
		s.currentLevel = currLevel;
		s.currentExperience = currXP;
		s.location = p;
		s.typeid = typeid;
		s.uid = uid;
		s.playerid = playerid;
		s.delete = delete;
		s.friendlyOccupancy = friendOcc;
		s.elevation = elev;
		return s;
	}
}
