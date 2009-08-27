package proj_sp5;
private class Globals {
	public static final Tile[][] = new Tile[256][256];
	public int maxX;
	public int maxY;
	public List<Structure> allStructures; // Structure-underbar-what?
	public List<Resource>[] structureRequirements;
	// that's probably best to do *in* each structure type
	int movementRequired[]; 
	// how much is needed for each tile type. Should be in Tile.
	int PlayerRelations[][]; // inter-player status:war, alliance, what?
	// Should be handled differently. Somehow. But how?
	long ExperiencePerLevel[][]; // unsigned. 
	// Is this the XP required to advance?
	List<Resource>[] unitRequirements;
	// again, probably best done *in* each unit type
	List<Unit> allUnits;
	// debugPrint, debugOpen, and debugClose will go in a
	// different class.
}
