package proj_sp5;
import java.util.ArrayList;
import java.util.List;
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
		temp.headquartersLocation = new Point(hq);
		temp.visible = new Boolean[Globals.maxX][Globals.maxY];
		for (int i = 0; i < Globals.maxX; i++) {
			for (int j = 0;j < Globals.maxY; j++) {
				if (Trig.distance(i, j, hq) > vision) {
					temp.visible[i][j] = Boolean.TRUE;
				} else {
					temp.visible[i][j] = Boolean.FALSE;
				}
			}
		}
		temp.resources = new ArrayList<Resource>();
		temp.units = new ArrayList<Unit>();
		temp.structures = new ArrayList<Structure>();
		return temp;
	}
}
