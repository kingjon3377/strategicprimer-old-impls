package proj_sp6;
class Unit {
	void gatherResources() {
		puts("Stubbed-out function gatherResources() called.");
	}
	static final int NUM_UNIT_TYPES = 5;
	String name;
	int maxHitPoints; // unsigned
	int meleeAC; // originally unsigned
	int rangedAC; // originally unsigned
	int currentHitPoints; // unsigned
	int currentLevel; // unsigned
	long currentExperience; // unsigned
	Point position;
	long typeid; // unsigned
	long uid; // unsigned
	long playerid; // unsigned
	boolean delete;
	boolean military;
	int meleeBonus;
	int rangedBonus;
	int damageDie; // unsigned
	int meleeDice; // unsigned
	int rangedDice; // unsigned
	int meleeDmgBonus;
	int rangedDmgBonus;
	int maxMovement; // unsigned
	int currentMovement; // unsigned
	int elevation;
	int range; // unsigned
	/**
	 * Attack another unit. Military must be set, or this will return
	 * ILLEGAL_USE. Each round of battle costs the attacker 1 MP (unless it
	 * has none left), but battle is to continue to the end.
	 */
	int attack(final Unit target) {
		if (!military || !checkAttack(target)) {
			return ILLEGAL_USE;
		} else if (currentMovement == 0) {
			return OUT_OF_MP;
		} else if ((Math.rand() % 20) + meleeBonus >= target.meleeAC) {
			int dmg = 0;
			for (int i = 0; i < meleeDice; i++) {
				dmg += (Math.rand() % damageDie) + 
						meleeDmgBonus;
			}
			if (dmg > target.currentHitPoints) {
				target.currentHitPoints = 0;
			} else {
				target.currentHitPoints -= dmg;
			}
		}
		currentMovement--;
		if (target.currentHitPoints == 0) {
			target.delete = true;
			currentExperience += Globals.
				XP_Level[currentLevel][target.currentLevel];
		}
		return OK;
	}
	/**
	 * Bombard another unit. This must have Military set, or this will
	 * return ILLEGAL_USE. This method subtracts 1 MP.
	 */
	int bombard(final Unit target) {
		if (!military || !checkBombard(target)) {
			return ILLEGAL_USE;
		} else if (currentMovement == 0) {
			return OUT_OF_MP;
		} else if ((Math.rand() % 20) + rangedBonus >= target.rangedAC){
			int dmg;
			for (int i = 0; i < rangedDice; i++) {
				dmg += (Math.rand() % damageDie) +
						rangedDmgBonus;
			}
			if (target.currentHitPoints <= dmg) {
				target.currentHitPoints = 0;
				target.delete = true;
				currentExperience += Globals.XP_Level
					[currentLevel][target.currentLevel];
			} else {
				target.currentHitPoints -= dmg;
			}
		}
		currentMovement--;
		return OK;
	}
	/**
	 * Attack a Structure. This must have Military set, or this will return
	 * ILLEGAL_USE. Each round of attacking costs 1 MP. This should return
	 * when the unit has no MP left or the structure reaches 0 HP.
	 */
	int attackStructure(final Structure target) {
		if (!military || !checkAttackS(target)) {
			return ILLEGAL_USE;
		} else if (currentMovement == 0) {
			return OUT_OF_MP;
		} else if ((Math.rand() % 20) + meleeBonus >= target.meleeAC) {
			int dmg = 0;
			for (int i = 0; i < meleeDice; i++) {
				dmg += (Math.rand() % dmgDie) + meleeDmgBonus;
			}
			if (dmg > target.currentHitPoints) {
				target.currentHitPoints = 0;
			} else {
				target.currentHitPoints -= dmg;
			}
		}
		currentMovement--;
		if (target.currentHitPoints == 0) {
			target.delete = true;
		}
		return OK;
	}
	/**
	 * Bombard a Structure. This must have Military set, or this will
	 * return ILLEGAL_USE. This function subtracts 1 MP. */
	int bombardStructure(final Structure target) {
		if (!military|| !checkBombardS(target)) {
			return ILLEGAL_USE;
		} else if (currentMovement == 0) {
			return OUT_OF_MP;
		} else if ((Math.rand() % 20) + rangedBonus >= target.rangedAC){
			int dmg = 0;
			for (int i = 0; i < rangedDice; i++) {
				dmg += (Math.rand() % dmgDie) + rangedDmgBonus;
			}
			if (dmg >= target.currentHitPoints) {
				target.currentHitPoints = 0;
				target.delete = true;
			} else {
				target.currentHitPoints -= dmg;
			}
		}
		currentMovement--;
		return OK;
	}
	int move(final Direction dir) {
		Tile temp;
		int mpReq;
		Point p;
		switch (dir) {
		case NORTH:
			temp = Globals.Level[position.X + 1][position.Y];
			p = new Point(position.X + 1, position.Y);
			break;
		case NORTHEAST:
			temp = Globals.Level[position.X + 1][position.Y -1];
			p = new Point(position.X + 1, position.Y - 1);
			break;
		case EAST:
			temp = Globals.Level[position.X][position.Y - 1];
			p = new Point(position.X, position.Y - 1);
			break;
		case SOUTHEAST:
			temp = Globals.Level[position.X - 1][position.Y - 1];
			p = new Point(position.X - 1, position.Y - 1);
			break;
		case SOUTH:
			temp = Globals.Level[position.X - 1][position.Y];
			p = new Point(position.X - 1, position.Y);
			break;
		case SOUTHWEST:
			temp = Globals.Level[position.X - 1][position.Y + 1];
			p = new Point(position.X - 1, position.Y + 1);
			break;
		case WEST:
			temp = Globals.Level[position.X][position.Y + 1];
			p = new Point(position.X, position.Y + 1);
			break;
		case NORTHWEST:
			temp = Globals.Level[position.X + 1][position.Y + 1];
			p = new Point(position.X + 1, position.Y + 1);
			break;
		}
		Tile curr = Globals.Level[position.X][position.Y];
		mpReq = Globals.MPRequired[temp.type] + abs(curr.elevation - 
				temp.elevation) / 2;
		switch (checkMove(p)) {
		case OK:
			position = p;
			if (currentMovement < mpReq) {
				currentMovement = 0;
			} else {
				currentMovement -= mpReq;
			}
			break;
		case ENEMY_OCCUPIED:
			Unit tempU = getUnit(p);
			attack(tempU);
			if (tempU.delete) {
				position = p;
				if (currentMovement < mpReq) {
					currentMovement = 0;
				} else {
					currentMovement -= mpReq;
				}
			}
			break;
		case ENEMY_HELD:
			Structure tempS = getStructure(p);
			attackStructure(tempS);
			if (tempS.delete) {
				position = p;
				if (currentMovement < mpReq) {
					currentMovement = 0;
				} else {
					currentMovement -= mpReq;
				}
			}
			break;
		default:
			return ILLEGAL_MOVE;
		}
		return OK;
	}
	int checkMove(final Point p) {
		for (Structure s : allStructures) {
			if (s.position.equals(p)) {
				// Assuming each player is friendly to himself.
				switch (Globals.
					PlayerRelations[playerid][s.playerid]) {
				case FRIENDLY:
					if (s.friendlyOccupancy) {
						continue;
					} else {
						return FRIENDLY_HELD;
					}
				case UNFRIENDLY:
					return UNFRIENDLY_HELD;
				case ENMITY:
					return ENEMY_HELD;
				}
			}
		}
		for (Unit u : allUnits) {
			if (u.position.equals(p)) {
				// Assuming each player is friendly to himself.
				switch (Globals.
					PlayerRelations[playerid][u.playerid]) {
				case FRIENDLY:
					return FRIENDLY_OCCUPIED;
				case UNFRIENDLY:
					return UNFRIENDLY_OCCUPIED;
				case ENMITY:
					return ENEMY_OCCUPIED;
				}
			}
		}
		return OK;
	}
	static Unit readUnitFromFile(final InputStream in) {
		readStringFromFile(in, "STRATEGIC_PRIMER_UNIT");
		return createUnit(readStringFromFile(in), readUIntFromFile(in),
			readIntFromFile(in), readIntFromFile(in),
			readUIntFromFile(in), readUIntFromFile(in),
			readULongFromFile(in), readPointFromFile(in),
			readULongFromFile(in), readULongFromFile(in),
			readULongFromFile(in), readBooleanFromFile(in),
			readBooleanFromFile(in), readIntFromFile(in),
			readIntFromFile(in), readUIntFromFile(in),
			readUIntFromFile(in), readUIntFromFile(in),
			readIntFromFile(in), readIntFromFile(in),
			readUIntFromFile(in), readUIntFromFile(in),
			readIntFromFile(in), readUIntFromFile(in))
	}
	static void writeUnitToFile(final OutputStream out, final Unit u) {
		writeStringToFile(out, "STRATEGIC_PRIMER_UNIT");
		writeStringToFile(out, u.name);
		writeUIntToFile(out, u.maxHitPoints);
		writeIntToFile(out, u.meleeAC);
		writeIntToFile(out, u.rangedAC);
		writeUIntToFile(out, u.currentHitPoints);
		writeUIntToFile(out, u.currentLevel);
		writeULongToFile(out, u.currentExperience);
		writePointToFile(out, u.position);
		writeULongToFile(out, u.typeid);
		writeULongToFile(out, u.uid);
		writeULongToFile(out, u.playerid);
		writeBooleanToFile(out, u.delete);
		writeBooleanToFile(out, u.military);
		writeIntToFile(out, u.meleeBonus);
		writeIntToFile(out, u.rangedBonus);
		writeUIntToFile(out, u.damageDie);
		writeUIntToFile(out, u.meleeDice);
		writeUIntToFile(out, u.rangedDice);
		writeIntToFile(out, u.meleeDmgBonus);
		writeIntToFile(out, u.rangedDmgBonus);
		writeUIntToFile(out, u.maxMovement);
		writeUIntToFile(out, u.currentMovement);
		writeIntToFile(out, u.elevation);
		writeUIntToFile(out, u.range);
	}
	static Unit getUnitFromUser() {
		Unit u;
		puts("Please enter data for a Unit.");
		puts("Name:");
		u.name = getStringFromUser();
		puts("Maximum HP:");
		u.maxHitPoints = getUIntFromUser();
		puts("Melee Armor Class:");
		u.meleeAC = getIntFromUser();
		puts("Ranged Armor Class:");
		u.rangedAC = getIntFromUser();
		puts("Current HP:");
		u.currentHitPoints = getUIntFromUser();
		puts("Current Level:");
		u.currentLevel = getUIntFromUser();
		puts("Current Experience:");
		u.currentExperience = getULongFromUser();
		puts("Current Position:");
		u.position = getPointFromUser();
		puts("Type ID:");
		u.typeid = getULongFromUser();
		puts("Unique ID:");
		u.uid = getULongFromUser();
		puts("Player ID:");
		u.playerid = getULongFromUser();
		puts("Delete?");
		u.delete = getBooleanFromUser();
		puts("Military?");
		u.military = getBooleanFromUser();
		puts("Melee Bonus:");
		u.meleeBonus = getIntFromUser();
		puts("Ranged Bonus:");
		u.rangedBonus = getIntFromUser();
		puts("Damage die:");
		u.dmgDie = getUIntFromUser();
		puts("Melee dice:");
		u.meleeDice = getUIntFromUser();
		puts("Ranged dice:");
		u.rangedDice = getUIntFromUser();
		puts("Melee damage bonus:");
		u.meleeDmgBonus = getIntFromUser();
		puts("Ranged damage bonus:");
		u.rangedDmgBonus = getIntFromUser();
		puts("Maximum movement:");
		u.maxMovement = getUIntFromUser();
		puts("Current movement:");
		u.currentMovement = getUIntFromUser();
		puts("Vision range:");
		u.vision = getUIntFromUser();
		puts("Elevation:");
		u.elevation = getIntFromUser();
		puts("Bombardment range:");
		u.range = getUIntFromUser();
		return u;
	}
	static void showUnitToUser(final Unit u) {
		puts("Data of a Unit.");
		puts("Name:");
		puts(u.name);
		puts("Maximum HP:");
		puts(u.maxHitPoints);
		puts("Melee Armor Class:");
		puts(u.meleeAC);
		puts("Ranged Armor Class:");
		puts(u.rangedAC);
		puts("Current HP:");
		puts(u.currentHitPoints);
		puts("Current Level:");
		puts(u.currentLevel);
		puts("Current Experience:");
		puts(u.currentExperience);
		puts("Current Position:");
		puts(u.position);
		puts("Type ID:");
		puts(u.typeid);
		puts("Unique ID:");
		puts(u.uid);
		puts("Player ID:");
		puts(u.playerid);
		puts("Delete?");
		puts(u.delete);
		puts("Military?");
		puts(u.military);
		puts("Melee Bonus:");
		puts(u.meleeBonus);
		puts("Ranged Bonus:");
		puts(u.rangedBonus);
		puts("Damage die:");
		puts(u.dmgDie);
		puts("Melee dice:");
		puts(u.meleeDice);
		puts("Ranged dice:");
		puts(u.rangedDice);
		puts("Melee damage bonus:");
		puts(u.meleeDmgBonus);
		puts("Ranged damage bonus:");
		puts(u.rangedDmgBonus);
		puts("Maximum movement:");
		puts(u.maxMovement);
		puts("Current movement:");
		puts(u.currentMovement);
		puts("Vision range:");
		puts(u.vision);
		puts("Elevation:");
		puts(u.elevation);
		puts("Bombardment range:");
		puts(u.range);
	}
	static Unit getUnit(final Point p) {
		for (Unit u : allUnits) {
			if (u.position.equals(p)) {
				return u;
			}
		}
		return null;
	}
	int checkAttack(final Unit target) {
		if (Globals.PlayerRelations[playerid][target.playerid] !=
				ENMITY) {
			return ILLEGAL_USE;
		} else if (Math.abs(position.X - target.position.X) > 1 || 
				Math.abs(position.Y - target.position.Y) > 1) {
			return TOO_FAR;
		} else {
			return OK;
		}
	}
	int checkAttackS(final Structure target) {
		if (Globals.PlayerRelations[playerid][target.playerid] !=
				ENMITY) {
			return ILLEGAL_USE;
		} else if (Math.abs(position.X - target.position.X) > 1 || 
				Math.abs(position.Y - target.position.Y) > 1) {
			return TOO_FAR;
		} else {
			return OK;
		}
	}
	int checkBombard(final Unit target) {
		if (Globals.PlayerRelations[playerid][target.playerid] !=
				ENMITY) {
			return ILLEGAL_USE;
		} else if (Math.abs(position.X - target.position.X) > range || 
			Math.abs(position.Y - target.position.Y) > range) {
			return TOO_FAR;
		} else {
			return OK;
		}
	}
	int checkBombardS(final Structure target) {
		if (Globals.PlayerRelations[playerid][target.playerid] !=
				ENMITY) {
			return ILLEGAL_USE;
		} else if (Math.abs(position.X - target.position.X) > range || 
			Math.abs(position.Y - target.position.Y) > range) {
			return TOO_FAR;
		} else {
			return OK;
		}
	}
	static Unit createUnit(String name,int maxHP,int mac,int rac,int currHP,
			int currLvl,long currXP,Point p,long typeid,long uid,
			long playerid,boolean delete,boolean mil,int mbonus,
			int rbonus,int dmgdie,int mdice,int rdice,int mdbonus,
			int rdbonus,int maxMv,int currMv,int vis,int elev) {
		Unit u = new Unit();
		u.name = name;
		u.maxHitPoints = maxHP;
		u.meleeAC = mac;
		u.rangedAC = rac;
		u.currentHitPoints = currHP;
		u.currentLevel = currLvl;
		u.currentExperience = currXP;
		u.position = p;
		u.typeid = typeid;
		u.uid = uid;
		u.playerid = playerid;
		u.delete = delete;
		u.military = mil;
		u.meleeBonus = mbonus;
		u.rangedBonus = rbonus;
		u.damageDie = dmgdie;
		u.meleeDice = mdice;
		u.rangedDice = rdice;
		u.meleeDmgBonus = mdbonus;
		u.rangedDmgBonus = rdbonus;
		u.maxMovement = maxMv;
		u.currentMovement = currMv;
		u.vision = vis;
		u.elevation = elev;
		return u;
	}
	static Unit produceUnit() {
		puts("Stubbed-out function produceUnit() called.");
		return null;
	}
	static Unit selectUnit(List<Unit> units) {
		// Ask the user to select a unit from the list.
		return null;
	}
	static Direction selectDirection() {
		// Ask the user to select a direction.
		return NORTH;
	}
}
