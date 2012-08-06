package proj_sp6;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import proj_sp5.Trig;
class Player {
	List<Resource> resources;
	List<Unit> units;
	List<Structure> structures;
	long id; // unsigned
	double money;
	Point hqLoc;
	boolean visible[][] = new boolean[Globals.MAX_X][Globals.MAX_Y];
	static Player createPlayer(final Point p, final double money, 
			final int vision, final long id) {
		Player pl = new Player();
		pl.hqLoc = new Point(p);
		pl.money = money;
		pl.id = id;
		for (int i = 0; i < Globals.MAX_X; i++) {
			for (int j = 0; j < Globals.MAX_Y; j++) {
				if (Trig.pythagorean(Math.abs(i - p.X),Math.abs(j - p.Y)) >
						vision) {
					pl.visible[i][j] = false;
				} else {
					pl.visible[i][j] = true;
				}
			}
		}
		return pl;
	}
	static Player loadPlayerFromFile(final InputStream in) throws IOException {
		Globals.getStringFromFile(in, "STRATEGIC_PRIMER_PLAYER");
		Player p = new Player();
		p.id = Globals.getULongFromFile(in);
		p.money = Globals.getDoubleFromFile(in);
		p.hqLoc = Point.getPointFromFile(in);
		Globals.getUIntFromFile(in, Globals.MAX_X);
		Globals.getUIntFromFile(in, Globals.MAX_Y);
		for (int i = 0; i < Globals.MAX_X; i++) {
			for (int j = 0; j < Globals.MAX_Y; j++) {
				p.visible[i][j] = Globals.getBooleanFromFile(in);
			}
		}
		return p;
	}
	static void writePlayerToFile(final PrintStream out, final Player pl) {
		Globals.writeStringToFile(out, "STRATEGIC_PRIMER_PLAYER");
		Globals.writeULongToFile(out, pl.id);
		Globals.writeDoubleToFile(out, pl.money);
		Point.writePointToFile(out, pl.hqLoc);
		Globals.writeUIntToFile(out, Globals.MAX_X);
		Globals.writeUIntToFile(out, Globals.MAX_Y);
		for (int i = 0; i < Globals.MAX_X; i++) {
			for (int j = 0; j < Globals.MAX_Y; j++) {
				Globals.writeBooleanToFile(out, pl.visible[i][j]);
			}
		}
	}
	static Player getPlayerFromUser() {
		Globals.puts("Please enter data for a Player.");
		Globals.puts("ID:");
		long id = User.getULongFromUser();
		Globals.puts("Money:");
		double money = User.getDoubleFromUser();
		Globals.puts("HQ location:");
		Point hqLoc = Point.getPointFromUser();
		Globals.puts("Vision:");
		int vision = User.getUIntFromUser();
		return createPlayer(hqLoc,money,vision,id);
	}
	static void showPlayerToUser(final Player p) {
		Globals.puts("Showing a Player");
		Globals.puts("ID: " + p.id);
		Globals.puts("Money: " + p.money);
		Globals.puts("HQ location: "); 
		Point.showPointToUser(p.hqLoc);
//		puts("Vision: " + p.vision);
	}
	public void constructStructure() {
		// TODO Auto-generated method stub
		
	}
}
