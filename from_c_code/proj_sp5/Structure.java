package proj_sp5;
class Structure {
	String name;
	int maxHitPoints; // unsigned
	int meleeAC; // originally unsigned
	int rangedAC; // originally unsigned
	int currentHitPoints; //unsigned
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
			final int currentLevel, final long currentXP, 
			final Point position, final long typeid, final long uid,
			final long playerid, final boolean delete, 
			final boolean friendlyOcc, final int elevation) {
		Structure temp = new Structure(); 
		temp.name = name; 
		temp.maxHitPoints = maxHP; 
		temp.meleeAC = mac; 
		temp.rangedAC = rac; 
		temp.currentHitPoints = currentHP; 
		temp.currentLevel = currentLevel; 
		temp.currentExperience = currentXP; 
		temp.position = new Point(position); 
		temp.typeid = typeid; 
		temp.uid = uid; 
		temp.playerid = playerid; 
		temp.delete = delete; 
		temp.friendlyOccupancy = friendlyOcc; 
		temp.elevation = elevation; 
		return temp; 
	}
	static Structure getStructureFromFile(final InputStream in) {
		getStringFromFile(in, "STRATEGIC PRIMER STRUCTURE");
		return createStructure(getStringFromFile(), getIntFromFile(), 
			getIntFromFile(), getIntFromFile(), getIntFromFile(), 
			getIntFromFile(), getLongFromFile(), getPointFromFile(),
			getLongFromFile(), getLongFromFile(), getLongFromFile(),
			getBooleanFromFile(), getBooleanFromFile(),
			getIntFromFile());
	}
	static void writeStructureToFile(final OutputStream out, 
			final Structure s) {
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
		puts("Please enter data for a Structure.");
		puts("Name");
		String name = getStringFromUser();
		puts("Maximum HP");
		int maxHP = getUIntFromUser();
		puts("Melee AC");
		int meleeAC = getIntFromUser();
		puts("Ranged AC");
		int rangedAC = getIntFromUser();
		puts("Current HP");
		int currHP = getUIntFromUser();
		puts("Current Level");
		int currentLevel = getUIntFromUser();
		puts("Current Experience");
		long currentXP = getULongFromUser();
		puts("Current Location");
		Point location = getPointFromUser();
		puts("Type ID");
		long typeid = getULongFromUser();
		puts("Unique ID");
		long uid = getULongFromUser();
		puts("PlayerID");
		long playerid = getULongFromUser();
		puts("Delete?");
		boolean delete = getBooleanFromUser();
		puts("Friendly Occupancy?");
		boolean frOcc = getBooleanFromUser();
		puts("Elevation");
		int elev = getIntFromUser();
		return createStructure(name, maxHP, meleeAC, rangedAC, currHP,
				currentLevel, currentXP, location, typeid, uid,
				playerid, delete, frOcc, elev);
	}
	static void showStructureToUser(final Structure s) {
		puts("Data of a Structure");
		puts("Name:");
		puts(s.name);
		puts("Maximum HP:");
		puts(s.maxHitPoints);
		puts("Melee Armor Class:");
		puts(s.meleeAC);
		puts("Ranged Armor Class:");
		puts(s.rangedAC);
		puts("Current HP:");
		puts(s.currentHitPoints);
		puts("Current Level:");
		puts(s.currentLevel);
		puts("Current Experience:");
		puts(s.currentExperience);
		puts("Current Position:");
		puts(s.position);
		puts("Type ID");
		puts(s.typeid);
		puts("Unique ID");
		puts(s.uid);
		puts("Player ID");
		puts(s.playerid);
		puts("Marked for deletion?");
		puts(s.delete);
		puts("Friendly Occupancy?");
		puts(s.friendlyOccupancy);
		puts("Elevation");
		puts(s.elevation);
	}
	static Structure getStructure(final Point p) {
		return getStructure(p.X, p.Y);
	}
	static Structure getStructure(final int X, final int Y) {
		for (Structure s : Global.allStructures) {
			if (s.position.X == X && s.position.Y == Y) {
				return s;
			}
		}
		return null;
	}
	static boolean constructStructure(final Player owner) {
		puts("What kind of structure would you like to build?");
		puts("(1) Low Wooden Wall,");
		puts("(2) Wooden Pallisade,");
		puts("(3) Wooden Wall, or");
		puts("(q) none of the above.");
		puts("What would you like to build?");
		// actually implement; stubbed-out in original
		// Original just shows which was selected.
	}
} 
