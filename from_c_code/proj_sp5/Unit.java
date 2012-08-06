package proj_sp5;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Random;

import proj_sp5.Globals.Direction;

class Unit {
	static final int NUM_UNIT_TYPES = 5;

	boolean gatherResources() {
		// Should signature be changed?
		Globals.puts("Stubbed-out function gatherResources called.");
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
	final Random random = new Random();

	/**
	 * Attack another unit. Attacker must have military set. Each round of
	 * battle costs the attacker 1 MP (until all gone), but battle continues to
	 * the end. [actually, it doesn't yet, but it should.]
	 */
	int attack(final Unit target) {
		if (!military || (checkAttack(target) != Debug.OK)) {
			return Debug.ILLEGAL_USE;
		} else if (currentMovement == 0) {
			return Debug.OUT_OF_MOVEMENT;
		} else if ((random.nextInt(20) + 1 + meleeBonus >= target.meleeAC)) {
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
			currentExperience += Globals.ExperiencePerLevel[currentLevel][target.currentLevel];
		}
		return Debug.OK;
	}

	/**
	 * Bombard another unit. Bombardier must have military set. This method
	 * subtracts 1 MP from the unit.
	 */
	int bombard(final Unit target) {
		if (!military || (checkBombard(target) != Debug.OK)) {
			return Debug.ILLEGAL_USE;
		} else if (currentMovement == 0) {
			return Debug.OUT_OF_MOVEMENT;
		} else if (random.nextInt(20) + 1 + rangedBonus >= target.rangedAC) {
			int dmg = 0;
			for (int i = 0; i < rangedDice; i++) {
				dmg += (random.nextInt(damageDie) + 1 + rangedDmgBonus);
			}
			if (target.currentHitPoints < dmg) {
				target.currentHitPoints = 0;
				target.delete = true;
				currentExperience += Globals.ExperiencePerLevel[currentLevel][target.currentLevel];
			} else {
				target.currentHitPoints -= dmg;
			}
		}
		return Debug.OK;
	}

	/**
	 * Attack a Structure. Attacker must have military set. Each round of the
	 * attack costs the unit 1 MP, and the method will return when the unit has
	 * no MP left or the structure reaches 0 HP.
	 */
	int attackStructure(final Structure target) {
		if (!military || (checkAttackS(target) != Debug.OK)) {
			return Debug.ILLEGAL_USE;
		} else if (currentMovement == 0) {
			return Debug.OUT_OF_MOVEMENT;
		} else if (random.nextInt(20) + 1 + meleeBonus >= target.meleeAC) {
			int dmg = 0;
			for (int i = 0; i < meleeDice; i++) {
				dmg += (random.nextInt(damageDie) + 1) + meleeDmgBonus;
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
		return Debug.OK;
	}

	/**
	 * Bombard a Structure. Bombardier must have military set. This method
	 * subtracts 1 MP from the unit.
	 */
	int bombardStructure(final Structure target) {
		if (!military || (checkBombardS(target) != Debug.OK)) {
			return Debug.ILLEGAL_USE;
		} else if (currentMovement == 0) {
			return Debug.OUT_OF_MOVEMENT;
		} else if (random.nextInt(20) + 1 + rangedBonus >= target.rangedAC) {
			int dmg = 0;
			for (int i = 0; i < rangedDice; i++) {
				dmg += random.nextInt(damageDie) + 1 + rangedDmgBonus;
			}
			if (dmg > target.currentHitPoints) {
				target.currentHitPoints = 0;
				target.delete = true;
			} else {
				target.currentHitPoints -= dmg;
			}
		}
		currentMovement--;
		return Debug.OK;
	}

	int move(final Direction direction, final List<Player> players) {
		Tile temp, curr;
		int movementReq;
		int x, y;
		if (currentMovement == 0) {
			return Debug.ILLEGAL_USE;
		}
		curr = Globals.map[position.X][position.Y];
		switch (direction) {
		case NORTH:
			temp = Globals.map[position.X + 1][position.Y];
			x = position.X + 1;
			y = position.Y;
			break;
		case NORTHEAST:
			temp = Globals.map[position.X + 1][position.Y - 1];
			x = position.X + 1;
			y = position.Y - 1;
			break;
		case EAST:
			temp = Globals.map[position.X][position.Y - 1];
			x = position.X;
			y = position.Y - 1;
			break;
		case SOUTHEAST:
			temp = Globals.map[position.X - 1][position.Y - 1];
			x = position.X - 1;
			y = position.Y - 1;
			break;
		case SOUTH:
			temp = Globals.map[position.X - 1][position.Y];
			x = position.X - 1;
			y = position.Y;
			break;
		case SOUTHWEST:
			temp = Globals.map[position.X - 1][position.Y + 1];
			x = position.X - 1;
			y = position.Y + 1;
			break;
		case WEST:
			temp = Globals.map[position.X][position.Y + 1];
			x = position.X;
			y = position.Y + 1;
			break;
		case NORTHWEST:
			temp = Globals.map[position.X + 1][position.Y + 1];
			x = position.X + 1;
			y = position.Y + 1;
			break;
		default:
			throw new IllegalStateException("Unkonwn direction");
		}
		movementReq = Globals.movementRequired[temp.type]
				+ Math.abs(curr.elevation - temp.elevation) / 2;
		switch (checkMove(new Point(x, y), players)) {
		case Debug.OK:
			position = new Point(x, y);
			if (currentMovement < movementReq) {
				currentMovement = 0;
			} else {
				currentMovement -= movementReq;
			}
			break;
		case Debug.ENEMY_OCCUPIED:
			Unit tempU = getUnit(new Point(x, y), players);
			attack(tempU);
			if (tempU.delete) {
				position = new Point(x, y);
				if (currentMovement < movementReq) {
					currentMovement = 0;
				} else {
					currentMovement -= movementReq;
				}
			}
			break;
		case Debug.ENEMY_HELD:
			Structure tempS = Globals.getStructure(x, y);
			attackStructure(tempS);
			if (tempS.delete) {
				position = new Point(x, y);
				if (currentMovement < movementReq) {
					currentMovement = 0;
				} else {
					currentMovement -= movementReq;
				}
			}
			break;
		default:
			return Debug.ILLEGAL_MOVE;
		}
		return Debug.OK;
	}

	int checkMove(final Point target, final List<Player> players) {
		for (Player temp3 : players) {
			for (Structure temp2 : temp3.structures) {
				if (temp2.position.equals(target)) {
					switch (Globals.PlayerRelations[(int) playerid][(int) (temp2.playerID)]) {
					case Debug.FRIENDLY:
						// assuming PR[i][i] == FRIENDLY
						if (temp2.friendlyOccupancy) {
							continue;
						} else {
							return Debug.FRIENDLY_HELD;
						}
					case Debug.UNFRIENDLY:
						return Debug.UNFRIENDLY_HELD;
					case Debug.ENMITY:
						return Debug.ENEMY_HELD;
					}
				}
			}
			for (Unit temp : temp3.units) {
				if (temp == this) {
					continue;
				} else if (temp.position.equals(target)) {
					switch (Globals.PlayerRelations[(int)playerid][(int)temp.playerid]) {
					case Debug.FRIENDLY:
						// assuming PR[i][i] == FRIENDLY
						return Debug.FRIENDLY_OCCUPIED;
					case Debug.UNFRIENDLY:
						return Debug.UNFRIENDLY_OCCUPIED;
					case Debug.ENMITY:
						return Debug.ENEMY_OCCUPIED;
					}
				}
			}
		}
		return Debug.OK;
	}

	static Unit getUnitFromFile(final InputStream in) throws IOException {
		Globals.getStringFromFile(in, "STRATEGIC PRIMER UNIT");
		String name = Globals.getStringFromFile(in);
		int maxHitPoints = Globals.getUIntFromFile(in);
		int meleeAC = Globals.getIntFromFile(in);
		int rangedAC = Globals.getIntFromFile(in);
		int currentHitPoints = Globals.getUIntFromFile(in);
		int currentLevel = Globals.getUIntFromFile(in);
		long currentExperience = Globals.getULongFromFile(in);
		Point position = Point.getPointFromFile(in);
		long typeid = Globals.getULongFromFile(in);
		long uid = Globals.getULongFromFile(in);
		long playerid = Globals.getULongFromFile(in);
		boolean delete = Globals.getBooleanFromFile(in);
		boolean military = Globals.getBooleanFromFile(in);
		int meleeBonus = Globals.getIntFromFile(in);
		int rangedBonus = Globals.getIntFromFile(in);
		int damageDie = Globals.getUIntFromFile(in);
		int meleeDice = Globals.getUIntFromFile(in);
		int rangedDice = Globals.getUIntFromFile(in);
		int meleeDmgBonus = Globals.getIntFromFile(in);
		int rangedDmgBonus = Globals.getIntFromFile(in);
		int maxMovement = Globals.getUIntFromFile(in);
		int currentMovement = Globals.getUIntFromFile(in);
		int vision = Globals.getUIntFromFile(in);
		int elevation = Globals.getIntFromFile(in);
		int range = Globals.getUIntFromFile(in);
		return Unit.createUnit(name, maxHitPoints, meleeAC, rangedAC,
				currentHitPoints, currentLevel, currentExperience, position,
				typeid, uid, playerid, delete, military, meleeBonus,
				rangedBonus, damageDie, meleeDice, rangedDice, meleeDmgBonus,
				rangedDmgBonus, maxMovement, currentMovement, vision,
				elevation, range);
	}

	static void writeUnitToFile(final PrintStream out, final Unit u) {
		out.print(u.name);
		out.print(u.maxHitPoints);
		out.print(u.meleeAC);
		out.print(u.rangedAC);
		out.print(u.currentHitPoints);
		out.print(u.currentLevel);
		out.print(u.currentExperience);
		out.print(u.position);
		out.print(u.typeid);
		out.print(u.uid);
		out.print(u.playerid);
		out.print(u.delete);
		out.print(u.military);
		out.print(u.meleeBonus);
		out.print(u.rangedBonus);
		out.print(u.damageDie);
		out.print(u.meleeDice);
		out.print(u.rangedDice);
		out.print(u.meleeDmgBonus);
		out.print(u.rangedDmgBonus);
		out.print(u.maxMovement);
		out.print(u.currentMovement);
		out.print(u.vision);
		out.print(u.elevation);
		out.print(u.range);
	}

	static Unit getUnitFromUser() {
		Unit temp = new Unit();
		Globals.puts("Please enter data for a Unit.");
		Globals.puts("Name:");
		temp.name = User.getStringFromUser();
		Globals.puts("Maximum Hit Points:");
		temp.maxHitPoints = User.getUIntFromUser();
		Globals.puts("Melee Armor Class:");
		temp.meleeAC = User.getIntegerFromUser();
		Globals.puts("Ranged Armor Class:");
		temp.rangedAC = User.getIntegerFromUser();
		Globals.puts("Current Hit Points:");
		temp.currentHitPoints = User.getUIntFromUser();
		Globals.puts("Current Level:");
		temp.currentLevel = User.getUIntFromUser();
		Globals.puts("Current Experience:");
		temp.currentExperience = User.getULongFromUser();
		Globals.puts("Current Position:");
		temp.position = Point.getPointFromUser();
		Globals.puts("Type ID:");
		temp.typeid = User.getULongFromUser();
		Globals.puts("Unique ID:");
		temp.uid = User.getULongFromUser();
		Globals.puts("Player ID:");
		temp.playerid = User.getULongFromUser();
		Globals.puts("Delete?");
		temp.delete = User.getBooleanFromUser();
		Globals.puts("Military?");
		temp.delete = User.getBooleanFromUser();
		Globals.puts("Melee bonus:");
		temp.meleeBonus = User.getIntegerFromUser();
		Globals.puts("Ranged bonus:");
		temp.rangedBonus = User.getIntegerFromUser();
		Globals.puts("Damage die:");
		temp.damageDie = User.getUIntFromUser();
		Globals.puts("Melee dice");
		temp.meleeDice = User.getUIntFromUser();
		Globals.puts("Ranged dice");
		temp.rangedDice = User.getUIntFromUser();
		Globals.puts("Melee damage bonus");
		temp.meleeDmgBonus = User.getIntegerFromUser();
		Globals.puts("Ranged damage bonus");
		temp.rangedDmgBonus = User.getIntegerFromUser();
		Globals.puts("Maximum movement");
		temp.maxMovement = User.getUIntFromUser();
		Globals.puts("Current movement");
		temp.currentMovement = User.getUIntFromUser();
		Globals.puts("Vision range");
		temp.vision = User.getUIntFromUser();
		Globals.puts("Elevation");
		temp.elevation = User.getIntegerFromUser();
		Globals.puts("Bombardment range");
		temp.range = User.getUIntFromUser();
		return temp;
	}

	static void showUnitToUser(final Unit u) {
		Globals.puts("Data of a Unit:");
		Globals.puts("Name:");
		Globals.puts(u.name);
		Globals.puts("Maximum Hit Points:");
		Globals.puts(Integer.toString(u.maxHitPoints));
		Globals.puts("Melee Armor Class:");
		Globals.puts(Integer.toString(u.meleeAC));
		Globals.puts("Ranged Armor Class:");
		Globals.puts(Integer.toString(u.rangedAC));
		Globals.puts("Current Hit Points:");
		Globals.puts(Integer.toString(u.currentHitPoints));
		Globals.puts("Current Level:");
		Globals.puts(Integer.toString(u.currentLevel));
		Globals.puts("Current Experience:");
		Globals.puts(Long.toString(u.currentExperience));
		Globals.puts("Current Position:");
		Globals.puts(u.position.toString());
		Globals.puts("Type ID");
		Globals.puts(Long.toString(u.typeid));
		Globals.puts("Unique ID");
		Globals.puts(Long.toString(u.uid));
		Globals.puts("Player ID");
		Globals.puts(Long.toString(u.playerid));
		Globals.puts("Marked for deletion?");
		Globals.puts(Boolean.toString(u.delete));
		Globals.puts("Military?");
		Globals.puts(Boolean.toString(u.military));
		Globals.puts("Melee Bonus");
		Globals.puts(Integer.toString(u.meleeBonus));
		Globals.puts("Ranged bonus");
		Globals.puts(Integer.toString(u.rangedBonus));
		Globals.puts("Damage die");
		Globals.puts(Integer.toString(u.damageDie));
		Globals.puts("Melee dice");
		Globals.puts(Integer.toString(u.meleeDice));
		Globals.puts("Ranged dice");
		Globals.puts(Integer.toString(u.rangedDice));
		Globals.puts("Melee damage bonus");
		Globals.puts(Integer.toString(u.meleeDmgBonus));
		Globals.puts("Ranged damage bonus");
		Globals.puts(Integer.toString(u.rangedDmgBonus));
		Globals.puts("Maximum movement");
		Globals.puts(Integer.toString(u.maxMovement));
		Globals.puts("Current movement");
		Globals.puts(Integer.toString(u.currentMovement));
		Globals.puts("Vision range");
		Globals.puts(Integer.toString(u.vision));
		Globals.puts("Elevation");
		Globals.puts(Integer.toString(u.elevation));
		Globals.puts("Bombardment range");
		Globals.puts(Integer.toString(u.range));
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

	int checkAttack(final Unit target) {
		if (Globals.PlayerRelations[(int) playerid][(int) target.playerid] != Debug.ENMITY) {
			return Debug.ILLEGAL_USE;
		} else if ((Math.abs(position.X - target.position.X) > 1)
				|| (Math.abs(position.Y - target.position.Y) > 1)) {
			return Debug.TOO_FAR;
		} else {
			return Debug.OK;
		}
	}

	int checkAttackS(final Structure target) {
		if (Globals.PlayerRelations[(int) playerid][(int) target.playerID] != Debug.ENMITY) {
			return Debug.ILLEGAL_USE;
		} else if ((Math.abs(position.X - target.position.X) > 1)
				|| (Math.abs(position.Y - target.position.Y) > 1)) {
			return Debug.TOO_FAR;
		} else {
			return Debug.OK;
		}
	}

	int checkBombard(final Unit target) {
		if (Globals.PlayerRelations[(int) playerid][(int) target.playerid] != Debug.ENMITY) {
			return Debug.ILLEGAL_USE;
		} else if ((Math.abs(position.X - target.position.X) > range)
				|| (Math.abs(position.Y - target.position.Y) > range)) {
			return Debug.TOO_FAR;
		} else {
			return Debug.OK;
		}
	}

	int checkBombardS(final Structure target) {
		if (Globals.PlayerRelations[(int) playerid][(int) target.playerID] != Debug.ENMITY) {
			return Debug.ILLEGAL_USE;
		} else if ((Math.abs(position.X - target.position.X) > range)
				|| (Math.abs(position.Y - target.position.Y) > range)) {
			return Debug.TOO_FAR;
		} else {
			return Debug.OK;
		}
	}

	static Unit createUnit(String name, int maxHP, int mac, int rac,
			int currHP, int currLevel, long currXP, Point p, long typeid,
			long uid, long playerid, boolean delete, boolean military,
			int mbonus, int rbonus, int die, int mdice, int rdice, int mdbonus,
			int rdbonus, int maxMv, int currMv, int vis, int elev, int range) {
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
		temp.range = range;
		return temp;
	}

	static Unit produceUnit(final Player owner) {
		Globals.puts("Stubbed-out method produceUnit() called.");
		Globals.puts("Creating a fighter at HQ.");
		Unit temp = createUnit("Fighter", 12, 18, 18, 12, 1, 0L, owner.headquartersLocation, 2L,
				++Globals.uid, owner.id, false, true, 5, 2, 8, 1, 1, 4, 2, 5, 5, 2, 0, 0);
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

	public void move(Point p) {
		// TODO Auto-generated method stub

	}
}
