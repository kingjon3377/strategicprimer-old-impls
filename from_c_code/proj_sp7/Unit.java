package proj_sp7;
class Unit {
	AdvanceId number;
	boolean human; // Is the unit one person?
	/** If one person, his stats; otherwise, averages. */
	class HumanStats {
		int strength; /* Strength, used in melee */
		int dexterity; /* Dexterity, used in ranged combat */
		int charisma; /* Charisma, used in negotiations and command */
		int wisdom; /* Wisdom -- deductive ability */
		int intelligence; /* Intelligence -- head knowledge */
		int constitution; /* Constitution -- ability to take damage */
	}
	HumanStats humanStats;
	class OtherStats {
		int vision; /* How far it can see */
		int movement; /* How far it can move per turn */
		int defense; /* Damage reduction */
	}
	OtherStats otherStats;
	AdvanceId primaryWeapon;
	AdvanceId secondaryWeapon;
	AdvanceId tertiaryWeapon;
}
