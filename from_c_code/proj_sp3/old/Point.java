package proj_sp3.old;
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
}
