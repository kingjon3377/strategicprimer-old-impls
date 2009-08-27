package proj_sp5;
private class Trig {
	public static double sec(double x) {
		return secant(x);
	}
	public static double cot(double x) {
		return cotangent(x);
	}
	public static double csc(double x) {
		return cosecant(x);
	}
	public static double asec(double x) {
		return secant_inverse(x);
	}
	public static double acot(double x) {
		return cotangent_inverse(x);
	}
	public static double acsc(double x) {
		return cosecant_inverse(x);
	}
	public static double pythagorean(double a, double b) {
		return Math.sqrt(a * a + b * b);
	}
}
