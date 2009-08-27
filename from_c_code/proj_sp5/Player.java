package proj_sp5;
class Player {
	long id; // unsigned
	double money;
	List<Resource> resources;
	List<Unit> units;
	List<Structure> structures;
	Point headquartersLocation;
//	Collection<Tile> visible;
	Boolean[][] visible;
	static Player createPlayer(final Point hq, final double money, 
			final int vision, final int id) {
		Player temp = new Player();
		temp.id = id;
		temp.money = money;
		temp.hq = new Point(hq);
		temp.visible = new Boolean[Global.maxX][Global.maxY];
		for (int i = 0; i < Global.maxX; i++) {
			for (int j = 0;j < Global.maxY; j++) {
				if (distance(i, j, hq) > vision) {
					temp.visible[i][j] = Boolean.TRUE;
				} else {
					temp.visible[i][j] = Boolean.FALSE;
				}
			}
		}
		temp.resources = new ArrayList<Resource>();
		temp.units = new ArrayList<Unit>();
		temp.structures = new ArrayList<Unit>();
		return temp;
	}
}
