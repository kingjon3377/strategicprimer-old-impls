package proj_sp5;
public abstract class Trig {
	public static double sec(double x) {
		return 1/Math.cos(x);
	}
	public static double cot(double x) {
		return 1/Math.tan(x);
	}
	public static double csc(double x) {
		return 1/Math.sin(x);
	}
	public static double asec(double x) {
		return Math.acos(1.0/x);
	}
	public static double acot(double x) {
		return Math.atan(1.0/x);
	}
	public static double acsc(double x) {
		return Math.asin(1.0/x);
	}
	public static double pythagorean(double a, double b) {
		return Math.sqrt(a * a + b * b);
	}
	public static double distance(double x, double y, Point target) {
		return distance(x,y,target.X, target.Y);
	}
	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)));
	}
	public static double distance(Point one, Point two) {
		return distance(one.X,one.Y,two.X,two.Y);
	}
}
