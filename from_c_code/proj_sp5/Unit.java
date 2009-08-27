package proj_sp5;
class Unit {
	static final int NUM_UNIT_TYPES = 5;
	boolean gatherResources() {
		// Should signature be changed?
		puts("Stubbed-out function gatherResources called.");
		return false;
	}
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
	int vision; // unsigned
	int elevation;
	int range; // unsigned
	/**
	 * Attack another unit. Attacker must have military set. Each
	 * round of battle costs the attacker 1 MP (until all gone),
	 * but battle continues to the end. [actually, it doesn't
	 * yet, but it should.]
	 */
	int attack(final Unit target) {
		if (!military || !checkAttack(target)) {
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
			currentExperience += Globals.
				XP_Level[currentLevel][target.currentLevel];
		}
		return OK;
	}
	/**
	 * Bombard another unit. Bombardier must have military set.
	 * This method subtracts 1 MP from the unit. */
	int bombard(final Unit target) {
		if (!military || !checkBombard(target)) {
			return ILLEGAL_USE;
		} else if (currentMovement == 0) {
			return OUT_OF_MP;
		} else if ((Math.rand() % 20) + rangedBonus >= target.rangedAC){
			int dmg = 0;
			for (int i = 0; i < rangedDice; i++) {
				dmg += ((Math.rand() % damageDie) + 
						rangedDmgBonus);
			}
			if (target.currentHitPoints < dmg) {
				target.currentHitPoints = 0;
				target.delete = true;
				currentExperience += Global.XP_Level
					[currentLevel][target.currentLevel];
			} else {
				target.currentHitPoints -= dmg;
			}
		}
		return OK;
	}
	/**
	 * Attack a Structure. Attacker must have military set. Each
	 * round of the attack costs the unit 1 MP, and the method
	 * will return when the unit has no MP left or the structure
	 * reaches 0 HP. */
	boolean attackStructure(final Structure target) {
		if (!military || !checkAttackS(target)) {
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
		}
		return OK;
	}
	/** Bombard a Structure. Bombardier must have military set.
	 * This method subtracts 1 MP from the unit. */
	boolean bombardStructure(final Structure target) {
		if (!military || !checkBombardS(target)) {
			return ILLEGAL_USE;
		} else if (currentMovement == 0) {
			return OUT_OF_MP;
		} else if ((Math.rand() % 20) + rangedBonus >= target.rangedAC){
			int dmg = 0;
			for (int i = 0; i < rangedDice; i++) {
				dmg += (Math.rand() % dmgDie) + rangedDmgBonus;
			}
			if (dmg > target.currentHitPoints) {
				target.currentHitPoints = 0;
				target.delete = true;
			} else {
				target.currentHitPoints -= dmg;
			}
		}
		currentMovement--;
		return OK;
	}
	boolean move(final Direction direction, final List<Player> players) {
		Tile temp, curr;
		int movementReq;
		int x, y;
		if (currentMovement == 0) {
			return ILLEGAL_USE;
		}
		curr = Globals.Level[position.X][position.Y];
		switch (direction) {
		case NORTH:
			temp = Globals.Level[position.X +1][position.Y];
			x = position.X + 1;
			y = position.Y;
			break;
		case NORTHEAST:
			temp = Globals.Level[position.X + 1][position.Y - 1];
			x = position.X + 1;
			y = position.Y - 1;
			break;
		case EAST:
			temp = Globals.Level[position.X][position.Y - 1];
			x = position.X;
			y = position.Y - 1;
			break;
		case SOUTHEAST:
			temp = Globals.Level[position.X - 1][position.Y - 1];
			x = position.X - 1;
			y = position.Y - 1;
			break;
		case SOUTH:
			temp = Globals.Level[position.X - 1][position.Y];
			x = position.X - 1;
			y = position.Y;
			break;
		case SOUTHWEST:
			temp = Globals.Level[position.X - 1][position.Y + 1];
			x = position.X - 1;
			y = position.Y + 1;
			break;
		case WEST:
			temp = Globals.Level[position.X][position.Y + 1];
			x = position.X;
			y = position.Y + 1;
			break;
		case NORTHWEST:
			temp = Globals.Level[position.X + 1][position.Y + 1];
			x = position.X + 1;
			y = position.Y + 1;
			break;
		}
		movementReq = Globals.movementReq[temp.type] + 
				Math.abs(curr.elevation - temp.elevation) / 2;
		switch (checkMove(x, y, players)) {
		case OK:
			position = new Point(x, y);
			if (currentMovement < movementReq) {
				currentMovement = 0;
			} else {
				currentMovement -= movementReq;
			}
			break;
		case ENEMY_OCCUPIED:
			Unit tempU = getUnit(x, y, players);
			attack(tempU);
			if (tempU.delete) {
				position = new Point(x,y);
				if (currentMovement < movementReq) {
					currentMovement = 0;
				} else {
					currentMovement -= movementReq;
				}
			}
			break;
		case ENEMY_HELD:
			Structure tempS = getStructure(x,y);
			attackStructure(tempS);
			if (tempS.delete) {
				position = new Point(x,y);
				if (currentMovement < movementReq) {
					currentMovement = 0;
				} else {
					currentMovement -= movementReq;
				}
			}
			break;
		default:
			return ILLEGAL_MOVE;
		}
		return OK;
	}
	boolean checkMove(final Point target, final List<Player> players) {
		for (Player temp3 : players) {
			for (Structure temp2 : temp3.structures) {
				if (temp2.position.equals(target)) {
					switch (Globals.
						PlayerRelations[playerid][temp2.
						playerid]) {
					case FRIENDLY:
						// assuming PR[i][i] == FRIENDLY
						if (temp2.friendlyOccupancy) {
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
			for (Unit temp : temp3.units) {
				if (temp == this) {
					continue;
				} else if (temp.position.equals(target) {
					switch (Globals.
						PlayerRelations[playerid][temp2.
						playerid]) {
					case FRIENDLY:
						// assuming PR[i][i] == FRIENDLY
						return FRIENDLY_OCCUPIED;
					case UNFRIENDLY:
						return UNFRIENDLY_OCCUPIED;
					case ENMITY:
						return ENEMY_OCCUPIED;
					}
				}
			}
		}
		return OK;
	}
	static Unit getUnitFromFile(final InputStream in) {
		getStringFromFile("STRATEGIC PRIMER UNIT", in);
		String name = getStringFromFile(in);
		int maxHitPoints = getUIntFromFile(in);
		int meleeAC = getIntFromFile(in);
		int rangedAC = getIntFromFile(in);
		int currentHitPoints = getUIntFromFile(in);
		int currentLevel = getUIntFromFile(in);
		long currentExperience = getULongFromFile(in);
		Point position = getPointFromFile(in);
		long typeid = getULongFromFile(in);
		long uid = getULongFromFile(in);
		long playerid = getULongFromFile(in);
		boolean delete = getBoolFromFile(in);
		boolean military = getBoolFromFile(in);
		int meleeBonus = getIntFromFile(in);
		int rangedBonus = getIntFromFile(in);
		int damageDie = getUIntFromFile(in);
		int meleeDice = getUIntFromFile(in);
		int rangedDice = getUIntFromFile(in);
		int meleeDmgBonus = getIntFromFile(in);
		int rangedDmgBonus = getIntFromFile(in);
		int maxMovement = getUIntFromFile(in);
		int currentMovement = getUIntFromFile(in);
		int vision = getUIntFromFile(in);
		int elevation = getIntFromFile(in);
		int range = getUIntFromFile(in);
		return new Unit(name, maxHitPoints, meleeAC, rangedAC,
			currentHitPoints, currentLevel, currentExperience,
			position, typeid, uid, playerid, delete, military,
			meleeBonus, rangedBonus, damageDie, meleeDice,
			rangedDice, meleeDmgBonus, rangedDmgBonus, maxMovement,
			currentMovement, vision, elevation, range);
	}
	static void writeUnitToFile(final OutputStream out, final Unit u) {
		out.print(u.name);
		out.print(u.maxHitPoints);
		out.print(meleeAC);
		out.print(rangedAC);
		out.print(currentHitPoints);
		out.print(currentLevel);
		out.print(currentExperience);
		out.print(position);
		out.print(typeid);
		out.print(uid);
		out.print(playerid);
		out.print(delete);
		out.print(military);
		out.print(meleeBonus);
		out.print(rangedBonus);
		out.print(damageDie);
		out.print(meleeDice);
		out.print(rangedDice);
		out.print(meleeDmgBonus);
		out.print(rangedDmgBonus);
		out.print(maxMovement);
		out.print(currentMovement);
		out.print(vision);
		out.print(elevation);
		out.print(range);
	}
	static Unit getUnitFromUser() {
		temp = new Unit();
		puts("Please enter data for a Unit.");
		puts("Name:");
		temp.name = getStringFromUser();
		puts("Maximum Hit Points:");
		temp.maxHitPoints = getUIntFromUser();
		puts("Melee Armor Class:");
		temp.meleeAC = getIntFromUser();
		puts("Ranged Armor Class:");
		temp.rangedAC = getIntFromUser();
		puts("Current Hit Points:");
		temp.currentHitPoints = getUIntFromUser();
		puts("Current Level:");
		temp.currentLevel = getUIntFromUser();
		puts("Current Experience:");
		temp.currentExperience = getULongFromUser();
		puts("Current Position:");
		temp.position = getPointFromUser();
		puts("Type ID:");
		temp.typeid = getULongFromUser();
		puts("Unique ID:");
		temp.uid = getULongFromUser();
		puts("Player ID:");
		temp.playerid = getULongFromUser();
		puts("Delete?");
		temp.delete = getBooleanFromUser();
		puts("Military?");
		temp.delete = getBooleanFromUser();
		puts("Melee bonus:");
		temp.meleeBonus = getIntFromUser();
		puts("Ranged bonus:");
		temp.rangedBonus = getIntFromUser();
		puts("Damage die:");
		temp.damageDie = getUIntFromUser();
		puts("Melee dice");
		temp.meleeDice = getUIntFromUser();
		puts("Ranged dice");
		temp.rangedDice = getUIntFromUser();
		puts("Melee damage bonus");
		temp.meleeDamageBonus = getIntFromUser();
		puts("Ranged damage bonus");
		temp.rangedDamageBonus = getIntFromUser();
		puts("Maximum movement");
		temp.maxMovement = getUIntFromUser();
		puts("Current movement");
		temp.currentMovement = getUIntFromUser();
		puts("Vision range");
		temp.vision = getUIntFromUser();
		puts("Elevation");
		temp.elevation = getIntFromUser();
		puts("Bombardment range");
		temp.range = getUIntFromUser();
		return temp;
	}
	static void showUnitToUser(final Unit u) {
		puts("Data of a Unit:");
		puts("Name:");
		puts(u.name);
		puts("Maximum Hit Points:");
		puts(u.maxHitPoints);
		puts("Melee Armor Class:");
		puts(u.meleeAC);
		puts("Ranged Armor Class:");
		puts(u.rangedAC);
		puts("Current Hit Points:");
		puts(u.currentHitPoints);
		puts("Current Level:");
		puts(u.currentLevel);
		puts("Current Experience:");
		puts(u.currentExperience);
		puts("Current Position:");
		puts(u.position);
		puts("Type ID");
		puts(u.typeid);
		puts("Unique ID");
		puts(u.uid);
		puts("Player ID");
		puts(u.playerid);
		puts("Marked for deletion?");
		puts(u.delete);
		puts("Military?");
		puts(u.military);
		puts("Melee Bonus");
		puts(u.meleeBonus);
		puts("Ranged bonus");
		puts(u.rangedBonus);
		puts("Damage die");
		puts(u.damageDie);
		puts("Melee dice");
		puts(u.meleeDice);
		puts("Ranged dice");
		puts(u.rangedDice);
		puts("Melee damage bonus");
		puts(u.meleeDamageBonus);
		puts("Ranged damage bonus");
		puts(u.rangedDamageBonus);
		puts("Maximum movement");
		puts(u.maxMovement);
		puts("Current movement");
		puts(u.currentMovement);
		puts("Vision range");
		puts(u.vision);
		puts("Elevation");
		puts(u.elevation);
		puts("Bombardment range");
		puts(u.range);
	}
	static Unit getUnit(final Point p, final List<Player> players) {
		for (Player temp : players) {
			for (Unit temp2 : temp.units) {
				if (temp2.position.equals(p)) {
					return temp2;
				}
			}
		}
		return null;
	}
	boolean checkAttack(final Unit target) {
		if (Globals.playerRelations[playerid][target.playerid] !=
				ENMITY) {
			return ILLEGAL_USE;
		} else if ((abs(position.X - target.position.X) > 1) || 
				(abs(position.Y - target.position.Y) > 1)) {
			return TOO_FAR;
		} else {
			return OK;
		}
	}
	boolean checkAttackS(final Structure target) {
		if (Globals.playerRelations[playerid][target.playerid] !=
				ENMITY) {
			return ILLEGAL_USE;
		} else if ((abs(position.X - target.position.X) > 1) || 
				(abs(position.Y - target.position.Y) > 1)) {
			return TOO_FAR;
		} else {
			return OK;
		}
	}
	boolean checkBombard(final Unit target) {
		if (Globals.playerRelations[playerid][target.playerid] !=
				ENMITY) {
			return ILLEGAL_USE;
		} else if ((abs(position.X - target.position.X) > range) || 
				(abs(position.Y - target.position.Y) > range)) {
			return TOO_FAR;
		} else {
			return OK;
		}
	}
	boolean checkBombardS(final Structure target) {
		if (Globals.playerRelations[playerid][target.playerid] !=
				ENMITY) {
			return ILLEGAL_USE;
		} else if ((abs(position.X - target.position.X) > range) || 
				(abs(position.Y - target.position.Y) > range)) {
			return TOO_FAR;
		} else {
			return OK;
		}
	}
	static Unit createUnit(String name, int maxHP, int mac, int rac,
		int currHP, int currLevel, long currXP, Point p, long typeid, 
		long uid, long playerid, boolean delete, boolean military, 
		int mbonus, int rbonus, int die, int mdice, int rdice, 
		int mdbonus, int rdbonus, int maxMv, int currMv, int vis, 
		int elev) {
		Unit temp = new Unit();
		temp.name = name; 
		temp.maxHitPoints = maxHP;
		temp.meleeAC = mac;
		temp.rangedAC = rac;
		temp.currentHitPoints = currHP;
		temp.currentLevel = currLevel;
		temp.currentExperience = currXP;
		temp.position = p;
		temp.typeid = typeid;
		temp.uid = uid;
		temp.playerid = playerid;
		temp.delete = delete;
		temp.military = military;
		temp.meleeBonus = mbonus;
		temp.rangedBonus = rbonus;
		temp.damageDie = die;
		temp.meleeDice = mdice;
		temp.rangedDice = rdice;
		temp.meleeDmgBonus = mdbonus;
		temp.rangedDmgBonus = rdbonus;
		temp.maxMovement = maxMv;
		temp.currentMovement = currMv;
		temp.vision = vis;
		temp.elevation = elev;
		return temp;
	}
	static Unit produceUnit(final Player owner) {
		puts("Stubbed-out method produceUnit() called.");
		puts("Creating a fighter at HQ.");
		temp = createUnit("Fighter", 12, 18, 18, 12, 1, 0,
				owner.hqLoc, 2, ++Globals.uid, owner.id, 0, 1,
				5, 2, 8, 1, 1, 4, 2, 5, 5, 2, 0);
		owner.units.add(temp);
		return temp;
	}
	static Unit selectUnit(final List<Unit> units) {
		// Present all units in the list to the player, and let the
		// user decide.
		return null;
	}
	static int selectDirection() {
		// Present choices to the player, and let him decide.
		return -1;
	}
}
