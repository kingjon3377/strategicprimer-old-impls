package proj_sp6;
class Player {
	long id; // unsigned
	double money;
	Point hqLoc;
	boolean visible[][] = new boolean[MAX_X][MAX_Y];
	static Player createPlayer(final Point p, final double money, 
			final int vision, final long id) {
		Player pl = new Player();
		pl.hqLoc = new Point(p);
		pl.money = money;
		pl.id = id;
		for (int i = 0; i < MAX_X; i++) {
			for (int j = 0; j < MAX_Y; j++) {
				if (pythagorean(abs(i - p.X),abs(j - p.Y)) >
						vision) {
					pl.visible[i][j] = false;
				} else {
					pl.visible[i][j] = true;
				}
			}
		}
		return pl;
	}
	static Player loadPlayerFromFile(final InputStream in) {
		readStringFromFile(in, "STRATEGIC_PRIMER_PLAYER");
		Player p = new Player();
		p.id = readULongFromFile();
		p.money = readDoubleFromFile();
		p.hqLoc = readPointFromFile();
		readUIntFromFile(in, MAX_X);
		readUIntFromFile(in, MAX_Y);
		for (int i = 0; i < MAX_X; i++) [
			for (int j = 0; j < MAX_Y; j++) {
				p.visible[i][j] = readBooleanFromFile();
			}
		}
		return p;
	}
	static void writePlayerToFile(final OutputStream out, final Player pl) {
		writeStringToFile(out, "STRATEGIC_PRIMER_PLAYER");
		writeULongToFile(out, pl.id);
		writeDoubleToFile(out, pl.money);
		writePointToFile(out, pl.hqLoc);
		writeUIntToFile(out, MAX_X);
		writeUIntToFile(out, MAX_Y);
		for (int i = 0; i < MAX_X; i++) [
			for (int j = 0; j < MAX_Y; j++) {
				writeBooleanToFile(out, pl.visible[i][j]);
			}
		}
	}
	static Player getPlayerFromUser() {
		puts("Please enter data for a Player.");
		puts("ID:");
		long id = getULongFromUser();
		puts("Money:");
		double money = getDoubleFromUser();
		puts("HQ location:");
		Point hqLoc = getPointFromUser();
		puts("Vision:");
		int vision = getUIntFromUser();
		return createPlayer(id,money,hqLoc,vision);
	}
	static void showPlayerToUser(final Player p) {
		puts("Showing a Player");
		puts("ID: " + p.id);
		puts("Money: " + p.money);
		puts("HQ location: "); 
		showPointToUser(p.hqLoc);
		puts("Vision: " + p.vision);
	}
}
