package proj_sp5;

import java.io.InputStream;

class Point {
	int X;
	int Y;
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
}
