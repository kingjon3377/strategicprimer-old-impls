package proj_sp2;
class Unit {
	private String name;
	private int movement;
	private int vision;
	private double cost;
	private int crew;
	private int meleeAC;
	private int rangedAC;
	private int flags;
	public Unit() {
		this("New Unit", 1, 1, 100.0, 1, 10, 10, 0);
	}
	public Unit(final String _name, final int _movement, final int _vision,
			final double _cost, final int _crew, final int mac,
			final int rac, final int _flags) {
		if (setName(_name) && setMovement(_movement) && 
				setVision(_vision) && setCost(_cost) &&
				setCrew(_crew) && setMeleeAC(mac) &&
				setRangedAC(rac) && setFlags(_flags) ) {
			return;
		} else {
			throw new IllegalArgumentException("FIXME: Be more specific in error checking");
		}
	}
	String getName() { return name; }
	boolean setName(final String _name) {
		name = _name;
		return true;
	}
	int getMovement() { return movement; }
	boolean setMovement(final int _movement) {
		if (_movement >= 0) {
			movement = _movement;
			return true;
		} else {
			return false;
		}
	}
	int getVision() { return vision; }
	boolean setVision(final int _vision) {
		if (_vision >= 0) {
			vision = _vision;
			return true;
		} else {
			return false;
		}
	}
	double getCost() { return cost; }
	boolean setCost(final double _cost) {
		if (_cost >= 0.0) {
			cost = _cost;
			return true;
		} else {
			return false;
		}
	}
	int getCrew() { return crew; }
	boolean setCrew(final int _crew) {
		if (_crew >= 0) {
			crew = _crew;
			return true;
		} else {
			return false;
		}
	}
	int getMeleeAC() { return meleeAC; }
	boolean setMeleeAC(final int mac) {
		meleeAC = mac;
		return true;
	}
	int getRangedAC() { return rangedAC; }
	boolean setRangedAC(final int rac) {
		rangedAC = rac;
		return true;
	}
	int getFlags() { return flags; }
	boolean setFlags(final int _flags) {
		if (_flags >= 0) {
			flags = _flags;
			return true;
		} else {
			return false;
		}
	}
}
