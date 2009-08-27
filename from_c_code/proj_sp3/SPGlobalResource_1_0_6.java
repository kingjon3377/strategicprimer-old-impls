package proj_sp3;
class SPGlobalResource_1_0_6 {
	SPGlobalResource_1_0_6() {
		name = "";
		marketValue = 0;
		resourceNumber = 0;
	}
	SPGlobalResource_1_0_6(final String _name, 
			final double _marketValue, final int _resourceNumber) {
		name = _name;
		marketValue = _marketValue;
		resourceNumber = _resourceNumber;
	}
	SPGlobalResource_1_0_6(final SPGlobalResource_1_0_6 rhs) {
		name = rhs.getName();
		marketValue = rhs.getMarketValue();
		resourceNumber = rhs.getResourceNumber();
	}
	String getName() { return name; }
	boolean setName(final String _name) {
		name = _name;
		return true;
	}
	double getMarketValue() { return marketValue; }
	boolean setMarketValue(final double value) {
		if (value >= 0) {
			marketValue = value;
			return true;
		} else {
			return false; // SP_ILLEGAL_DATA
		}
	}
	boolean changeMarketValue(final double change) {
		if (marketValue + change >= 0) {
			marketValue += change;
			return true;
		} else {
			return false; // SP_ILLEGAL_DATA
		}
	}
	int getResourceNumber() { return resourceNumber; }
	boolean setResourceNumber(final int number) {
		if (number >= 0) {
			resourceNumber = number;
			return true;
		} else {
			return false;
		}
	}
	SPGlobalResource_1_0_6 copy(final SPGlobalResource_1_0_6 rhs) {
		if (this != rhs) {
			name = rhs.getName();
			marketValue = rhs.getMarketValue();
			resourceNumber = rhs.getResourceNumber();
		}
		return this;
	}
	int compareTo(final SPGlobalResource_1_0_6 rsr) {
		return Integer.valueOf(resourceNumber).compareTo(
			Integer.valueOf(rhs.getResourceNumber()));
	}
	void show() { Debug.print(resourceNumber); }
	private String name;
	private double marketValue;
	private int resourceNumber; // unsigned
}
