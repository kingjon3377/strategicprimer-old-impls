package proj_sp6;

import java.io.InputStream;
import java.io.PrintStream;

class Point {
	int X; // unsigned
	int Y; // unsigned
	Point(int _x, int _y) {
		X = _x;
		Y = _y;
	}
	Point (Point p) {
		X = p.X;
		Y = p.Y;
	}
	Point copy(Point p) {
		X = p.X;
		Y = p.Y;
		return this;
	}
	static Point getPointFromUser() {
		Globals.puts("Please enter data for a Point");
		Globals.puts("X:");
		int X = User.getIntegerFromUser();
		Globals.puts("Y:");
		int Y = User.getIntegerFromUser();
		return new Point(X,Y);
	}
	static Point getPointFromFile(InputStream in) {
		throw new IllegalStateException("Unimplemented");
	}
	static void writePointToFile(PrintStream out, Point p) {
		throw new IllegalStateException("Unimplemented");
	}
	static void showPointToUser(Point p) {
		Globals.puts("Data of a Point:");
		Globals.puts("X: " + p.X);
		Globals.puts("Y: " + p.Y);
	}
}
