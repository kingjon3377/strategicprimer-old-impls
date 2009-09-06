package proj_sp3;
class SPPoint {
	int X;
	int Y;
	SPPoint(int _x, int _y) {
		X = _x;
		Y = _y;
	}
	SPPoint(SPPoint p) {
		X = p.X;
		Y = p.Y;
	}
}
