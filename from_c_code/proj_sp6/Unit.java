package proj_sp6;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Random;

import proj_sp6.Debug.Relations;
import proj_sp6.Debug.ReturnValues;
import proj_sp6.Globals.Direction;

class Unit {
	void gatherResources() {
		Globals.puts("Stubbed-out function gatherResources() called.");
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
	final Random random = new Random();

	/**
	 * Attack another unit. Military must be set, or this will return
	 * ILLEGAL_USE. Each round of battle costs the attacker 1 MP (unless it has
	 * none left), but battle is to continue to the end.
	 */
	ReturnValues attack(final Unit target) {
		if (!military || (checkAttack(target) != ReturnValues.OK)) {
			return ReturnValues.ILLEGAL_USE;
		} else if (currentMovement == 0) {
			return ReturnValues.OUT_OF_MP;
		} else if (random.nextInt(20) + 1 + meleeBonus >= target.meleeAC) {
			int dmg = 0;
			for (int i = 0; i < meleeDice; i++) {
				dmg += random.nextInt(damageDie) + 1 + meleeDmgBonus;
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
			currentExperience += Globals.XP_Level[currentLevel][target.currentLevel];
		}
		return ReturnValues.OK;
	}

	/**
	 * Bombard another unit. This must have Military set, or this will return
	 * ILLEGAL_USE. This method subtracts 1 MP.
	 */
	ReturnValues bombard(final Unit target) {
		if (!military || (checkBombard(target)!=ReturnValues.OK)) {
			return ReturnValues.ILLEGAL_USE;
		} else if (currentMovement == 0) {
			return ReturnValues.OUT_OF_MP;
		} else if (random.nextInt(20)+1 + rangedBonus >= target.rangedAC) {
			int dmg = 0;
			for (int i = 0; i < rangedDice; i++) {
				dmg += random.nextInt(damageDie) + 1 + rangedDmgBonus;
			}
			if (target.currentHitPoints <= dmg) {
				target.currentHitPoints = 0;
				target.delete = true;
				currentExperience += Globals.XP_Level[currentLevel][target.currentLevel];
			} else {
				target.currentHitPoints -= dmg;
			}
		}
		currentMovement--;
		return ReturnValues.OK;
	}

	/**
	 * Attack a Structure. This must have Military set, or this will return
	 * ILLEGAL_USE. Each round of attacking costs 1 MP. This should return when
	 * the unit has no MP left or the structure reaches 0 HP.
	 */
	ReturnValues attackStructure(final Structure target) {
		if (!military || (checkAttackS(target)!=ReturnValues.OK)) {
			return ReturnValues.ILLEGAL_USE;
		} else if (currentMovement == 0) {
			return ReturnValues.OUT_OF_MP;
		} else if (random.nextInt(20)+1 + meleeBonus >= target.meleeAC) {
			int dmg = 0;
			for (int i = 0; i < meleeDice; i++) {
				dmg += random.nextInt(damageDie)+1+ + meleeDmgBonus;
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
		return ReturnValues.OK;
	}

	/**
	 * Bombard a Structure. This must have Military set, or this will return
	 * ILLEGAL_USE. This function subtracts 1 MP.
	 */
	ReturnValues bombardStructure(final Structure target) {
		if (!military || (checkBombardS(target)!=ReturnValues.OK)) {
			return ReturnValues.ILLEGAL_USE;
		} else if (currentMovement == 0) {
			return ReturnValues.OUT_OF_MP;
		} else if (random.nextInt(20)+1 + rangedBonus >= target.rangedAC) {
			int dmg = 0;
			for (int i = 0; i < rangedDice; i++) {
				dmg += random.nextInt(damageDie) +1+ rangedDmgBonus;
			}
			if (dmg >= target.currentHitPoints) {
				target.currentHitPoints = 0;
				target.delete = true;
			} else {
				target.currentHitPoints -= dmg;
			}
		}
		currentMovement--;
		return ReturnValues.OK;
	}

	ReturnValues move(final Direction dir) {
		Tile temp;
		int mpReq;
		Point p;
		switch (dir) {
		case NORTH:
			temp = Globals.Level[position.X + 1][position.Y];
			p = new Point(position.X + 1, position.Y);
			break;
		case NORTHEAST:
			temp = Globals.Level[position.X + 1][position.Y - 1];
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
		default:
			throw new IllegalStateException("Unknown direction");
		}
		Tile curr = Globals.Level[position.X][position.Y];
		mpReq = Globals.movementRequired[temp.type]
				+ Math.abs(curr.elevation - temp.elevation) / 2;
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
			Structure tempS = Globals.getStructure(p);
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
			return ReturnValues.ILLEGAL_MOVE;
		}
		return ReturnValues.OK;
	}

	ReturnValues checkMove(final Point p) {
		for (Structure s : Globals.allStructures) {
			if (s.position.equals(p)) {
				// Assuming each player is friendly to himself.
				switch (Globals.PlayerRelations[(int) playerid][(int) s.playerid]) {
				case FRIENDLY:
					if (s.friendlyOccupancy) {
						continue;
					} else {
						return ReturnValues.FRIENDLY_HELD;
					}
				case UNFRIENDLY:
					return ReturnValues.UNFRIENDLY_HELD;
				case ENMITY:
					return ReturnValues.ENEMY_HELD;
				}
			}
		}
		for (Unit u : Globals.allUnits) {
			if (u.position.equals(p)) {
				// Assuming each player is friendly to himself.
				switch (Globals.PlayerRelations[(int) playerid][(int) u.playerid]) {
				case FRIENDLY:
					return ReturnValues.FRIENDLY_OCCUPIED;
				case UNFRIENDLY:
					return ReturnValues.UNFRIENDLY_OCCUPIED;
				case ENMITY:
					return ReturnValues.ENEMY_OCCUPIED;
				}
			}
		}
		return ReturnValues.OK;
	}

	static Unit readUnitFromFile(final InputStream in) throws IOException {
		Globals.getStringFromFile(in, "STRATEGIC_PRIMER_UNIT");
		return createUnit(Globals.getStringFromFile(in), Globals.getUIntFromFile(in),
				Globals.getIntFromFile(in), Globals.getIntFromFile(in),
				Globals.getUIntFromFile(in), Globals.getUIntFromFile(in),
				Globals.getULongFromFile(in), Point.getPointFromFile(in),
				Globals.getULongFromFile(in), Globals.getULongFromFile(in),
				Globals.getULongFromFile(in), Globals.getBooleanFromFile(in),
				Globals.getBooleanFromFile(in), Globals.getIntFromFile(in),
				Globals.getIntFromFile(in), Globals.getUIntFromFile(in),
				Globals.getUIntFromFile(in), Globals.getUIntFromFile(in),
				Globals.getIntFromFile(in), Globals.getIntFromFile(in),
				Globals.getUIntFromFile(in), Globals.getUIntFromFile(in),
				Globals.getIntFromFile(in), Globals.getUIntFromFile(in));
	}

	static void writeUnitToFile(final PrintStream out, final Unit u) {
		Globals.writeStringToFile(out, "STRATEGIC_PRIMER_UNIT");
		Globals.writeStringToFile(out, u.name);
		Globals.writeUIntToFile(out, u.maxHitPoints);
		Globals.writeIntegerToFile(out, u.meleeAC);
		Globals.writeIntegerToFile(out, u.rangedAC);
		Globals.writeUIntToFile(out, u.currentHitPoints);
		Globals.writeUIntToFile(out, u.currentLevel);
		Globals.writeULongToFile(out, u.currentExperience);
		Point.writePointToFile(out, u.position);
		Globals.writeULongToFile(out, u.typeid);
		Globals.writeULongToFile(out, u.uid);
		Globals.writeULongToFile(out, u.playerid);
		Globals.writeBooleanToFile(out, u.delete);
		Globals.writeBooleanToFile(out, u.military);
		Globals.writeIntegerToFile(out, u.meleeBonus);
		Globals.writeIntegerToFile(out, u.rangedBonus);
		Globals.writeUIntToFile(out, u.damageDie);
		Globals.writeUIntToFile(out, u.meleeDice);
		Globals.writeUIntToFile(out, u.rangedDice);
		Globals.writeIntegerToFile(out, u.meleeDmgBonus);
		Globals.writeIntegerToFile(out, u.rangedDmgBonus);
		Globals.writeUIntToFile(out, u.maxMovement);
		Globals.writeUIntToFile(out, u.currentMovement);
		Globals.writeIntegerToFile(out, u.elevation);
		Globals.writeUIntToFile(out, u.range);
	}

	static Unit getUnitFromUser() {
		Unit u = new Unit();
		Globals.puts("Please enter data for a Unit.");
		Globals.puts("Name:");
		u.name = User.getStringFromUser();
		Globals.puts("Maximum HP:");
		u.maxHitPoints = User.getUIntFromUser();
		Globals.puts("Melee Armor Class:");
		u.meleeAC = User.getIntegerFromUser();
		Globals.puts("Ranged Armor Class:");
		u.rangedAC = User.getIntegerFromUser();
		Globals.puts("Current HP:");
		u.currentHitPoints = User.getUIntFromUser();
		Globals.puts("Current Level:");
		u.currentLevel = User.getUIntFromUser();
		Globals.puts("Current Experience:");
		u.currentExperience = User.getULongFromUser();
		Globals.puts("Current Position:");
		u.position = Point.getPointFromUser();
		Globals.puts("Type ID:");
		u.typeid = User.getULongFromUser();
		Globals.puts("Unique ID:");
		u.uid = User.getULongFromUser();
		Globals.puts("Player ID:");
		u.playerid = User.getULongFromUser();
		Globals.puts("Delete?");
		u.delete = User.getBooleanFromUser();
		Globals.puts("Military?");
		u.military = User.getBooleanFromUser();
		Globals.puts("Melee Bonus:");
		u.meleeBonus = User.getIntegerFromUser();
		Globals.puts("Ranged Bonus:");
		u.rangedBonus = User.getIntegerFromUser();
		Globals.puts("Damage die:");
		u.damageDie = User.getUIntFromUser();
		Globals.puts("Melee dice:");
		u.meleeDice = User.getUIntFromUser();
		Globals.puts("Ranged dice:");
		u.rangedDice = User.getUIntFromUser();
		Globals.puts("Melee damage bonus:");
		u.meleeDmgBonus = User.getIntegerFromUser();
		Globals.puts("Ranged damage bonus:");
		u.rangedDmgBonus = User.getIntegerFromUser();
		Globals.puts("Maximum movement:");
		u.maxMovement = User.getUIntFromUser();
		Globals.puts("Current movement:");
		u.currentMovement = User.getUIntFromUser();
		Globals.puts("Vision range:");
//		u.vision = User.getUIntFromUser();
		Globals.puts("Elevation:");
		u.elevation = User.getIntegerFromUser();
		Globals.puts("Bombardment range:");
		u.range = User.getUIntFromUser();
		return u;
	}

	static void showUnitToUser(final Unit u) {
		Globals.puts("Data of a Unit.");
		Globals.puts("Name:");
		Globals.puts(u.name);
		Globals.puts("Maximum HP:");
		Globals.puts(Integer.toString(u.maxHitPoints));
		Globals.puts("Melee Armor Class:");
		Globals.puts(Integer.toString(u.meleeAC));
		Globals.puts("Ranged Armor Class:");
		Globals.puts(Integer.toString(u.rangedAC));
		Globals.puts("Current HP:");
		Globals.puts(Integer.toString(u.currentHitPoints));
		Globals.puts("Current Level:");
		Globals.puts(Integer.toString(u.currentLevel));
		Globals.puts("Current Experience:");
		Globals.puts(Long.toString(u.currentExperience));
		Globals.puts("Current Position:");
		Point.showPointToUser(u.position);
		Globals.puts("Type ID:");
		Globals.puts(Long.toString(u.typeid));
		Globals.puts("Unique ID:");
		Globals.puts(Long.toString(u.uid));
		Globals.puts("Player ID:");
		Globals.puts(Long.toString(u.playerid));
		Globals.puts("Delete?");
		Globals.puts(Boolean.toString(u.delete));
		Globals.puts("Military?");
		Globals.puts(Boolean.toString(u.military));
		Globals.puts("Melee Bonus:");
		Globals.puts(Integer.toString(u.meleeBonus));
		Globals.puts("Ranged Bonus:");
		Globals.puts(Integer.toString(u.rangedBonus));
		Globals.puts("Damage die:");
		Globals.puts(Integer.toString(u.damageDie));
		Globals.puts("Melee dice:");
		Globals.puts(Integer.toString(u.meleeDice));
		Globals.puts("Ranged dice:");
		Globals.puts(Integer.toString(u.rangedDice));
		Globals.puts("Melee damage bonus:");
		Globals.puts(Integer.toString(u.meleeDmgBonus));
		Globals.puts("Ranged damage bonus:");
		Globals.puts(Integer.toString(u.rangedDmgBonus));
		Globals.puts("Maximum movement:");
		Globals.puts(Integer.toString(u.maxMovement));
		Globals.puts("Current movement:");
		Globals.puts(Integer.toString(u.currentMovement));
		Globals.puts("Vision range:");
//		Globals.puts(Integer.toString(u.vision));
		Globals.puts("Elevation:");
		Globals.puts(Integer.toString(u.elevation));
		Globals.puts("Bombardment range:");
		Globals.puts(Integer.toString(u.range));
	}

	static Unit getUnit(final Point p) {
		for (Unit u : Globals.allUnits) {
			if (u.position.equals(p)) {
				return u;
			}
		}
		return null;
	}

	ReturnValues checkAttack(final Unit target) {
		if (Globals.PlayerRelations[(int) playerid][(int) target.playerid] != Relations.ENMITY) {
			return ReturnValues.ILLEGAL_USE;
		} else if (Math.abs(position.X - target.position.X) > 1
				|| Math.abs(position.Y - target.position.Y) > 1) {
			return ReturnValues.TOO_FAR;
		} else {
			return ReturnValues.OK;
		}
	}

	ReturnValues checkAttackS(final Structure target) {
		if (Globals.PlayerRelations[(int) playerid][(int) target.playerid] != Relations.ENMITY) {
			return ReturnValues.ILLEGAL_USE;
		} else if (Math.abs(position.X - target.position.X) > 1
				|| Math.abs(position.Y - target.position.Y) > 1) {
			return ReturnValues.TOO_FAR;
		} else {
			return ReturnValues.OK;
		}
	}

	ReturnValues checkBombard(final Unit target) {
		if (Globals.PlayerRelations[(int) playerid][(int) target.playerid] != Relations.ENMITY) {
			return ReturnValues.ILLEGAL_USE;
		} else if (Math.abs(position.X - target.position.X) > range
				|| Math.abs(position.Y - target.position.Y) > range) {
			return ReturnValues.TOO_FAR;
		} else {
			return ReturnValues.OK;
		}
	}

	ReturnValues checkBombardS(final Structure target) {
		if (Globals.PlayerRelations[(int) playerid][(int) target.playerid] != Relations.ENMITY) {
			return ReturnValues.ILLEGAL_USE;
		} else if (Math.abs(position.X - target.position.X) > range
				|| Math.abs(position.Y - target.position.Y) > range) {
			return ReturnValues.TOO_FAR;
		} else {
			return ReturnValues.OK;
		}
	}

	static Unit createUnit(String name, int maxHP, int mac, int rac,
			int currHP, int currLvl, long currXP, Point p, long typeid,
			long uid, long playerid, boolean delete, boolean mil, int mbonus,
			int rbonus, int dmgdie, int mdice, int rdice, int mdbonus,
			int rdbonus, int maxMv, int currMv, int vis, int elev) {
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
//		u.vision = vis;
		u.elevation = elev;
		return u;
	}

	static Unit produceUnit() {
		Globals.puts("Stubbed-out function produceUnit() called.");
		return null;
	}

	static Unit selectUnit(List<Unit> units) {
		// Ask the user to select a unit from the list.
		return null;
	}

	static Direction selectDirection() {
		// Ask the user to select a direction.
		return Direction.NORTH;
	}

	public void move(Tile tile) {
		// TODO Auto-generated method stub

	}
}
