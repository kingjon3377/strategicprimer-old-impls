package proj_sp3.old;
class GlobalResource_1_0_5 {
	public GlobalResource_1_0_5() {
		name = "GlobalResource";
		marketValue = 0.0;
		resourceNumber = 0;
	}
	public GlobalResource_1_0_5(final String _name, final double value,
			final int number) {
		name = _name;
		setMarketValue(value);
		setResourceNumber(number);
	}
	public GlobalResource_1_0_5(final String _name, final int number) {
		name = _name;
		setResourceNumber(number);
	}
	public GlobalResource_1_0_5(final double value, final int number) {
		setMarketValue(value);
		setResourceNumber(number);
	}
	public GlobalResource_1_0_5(final String _name, final double value) {
		name = _name;
		setMarketValue(value);
	}
	public GlobalResource_1_0_5(final String _name) {
		name = _name;
	}
	public GlobalResource_1_0_5(final double value) {
		setMarketValue(value);
	}
	public GlobalResource_1_0_5(final int number) {
		setResourceNumber(number);
	}
	public GlobalResource_1_0_5(final GlobalResource_1_0_5 rhs) {
		name = rhs.getName();
		setMarketValue(rhs.getMarketValue());
		setResourceNumber(rhs.getResourceNumber());
	}
	public String getName() {
		return name;
	}
	public boolean setName(final String _name) {
		name = _name;
		return true;
	}
	public double getMarketValue() {
		return marketValue;
	}
	public boolean setMarketValue(final double value) {
		if (value >= 0) {
			marketValue = value;
			return true;
		} else {
			return false;
		}
	}
	public boolean changeMarketValue(final double change) {
		if (marketValue + change >= 0) {
			marketValue += change;
			return true;
		} else {
			return false;
		}
	}
	public int getResourceNumber() {
		return resourceNumber;
	}
	public boolean setResourceNumber(final int number) {
		if (number >= 0) {
			resourceNumber = number;
			return true;
		} else {
			return false;
		}
	}
	public GlobalResource_1_0_5 copy(final GlobalResource_1_0_5 rhs) {
		if (this != rhs) {
			name = rhs.getName();
			marketValue = rhs.getMarketValue();
			resourceNumber = rhs.getResourceNumber();
		}
		return this;
	}
	public int compareTo(final GlobalResource_1_0_5 rhs) {
		return Integer.valueOf(resourceNumber).compareTo(
				Integer.valueOf(rhs.getResourceNumber()));
	}
	public void show() {
		Debug.print(resourceNumber);
	}
	private String name;
	private double marketValue;
	private int resourceNumber; // unsigned
}
