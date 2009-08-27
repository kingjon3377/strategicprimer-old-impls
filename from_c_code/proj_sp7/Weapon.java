package proj_sp7;
class Weapon {
	AdvanceId weaponid;
	/* How much damage the weapon would do in melee if the target had no
	 * melee defense */
	int assault; // unsigned
	/* How much damage the weapon would do in ranged combat if the target
	 * had no ranged defense */
	int ranged; // unsigned
	/* How much the weapon changes the user's defense; usually 0. */
	int defense;
	/* Does the weapon have an area effect? */
	boolean areaEffect;
	/* Does the weapon use it's user's base statistics (adding the user's
	 * Str to assault above and Dex to ranged)? */
	boolean useUnitStats;
}
