package proj_sp3.old;
class Resource_1_0_5 {
	public Resource_1_0_5() {
		amount = 0;
		resourceNumber = 0;
	}
	public Resource_1_0_5(final Resource_1_0_5 rhs) {
		amount = rhs.getAmount();
		resourceNumber = rhs.getResourceNumber();
	}
	public Resource_1_0_5(final long amt, final int number) {
		setAmount(amt);
		setResourceNumber(number);
	}
	public Resource_1_0_5(final long amt) {
		setAmount(amt);
		resourceNumber = 0;
	}
	public Resource_1_0_5(final int number) {
		setResourceNumber(number);
		amount = 0;
	}
	public long getAmount() {
		return amount;
	}
	public boolean setAmount(final long amt) {
		if (amt >= 0) {
			amount = amt;
			return true;
		} else {
			return false;
		}
	}
	public boolean changeAmount(final long change) {
		if (amount + change >= 0) {
			amount += change;
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
	public Resource_1_0_5 copy(final Resource_1_0_5 rhs) {
		if (this != rhs) {
			amount = rhs.getAmount();
			resourceNumber = rhs.getResourceNumber();
		}
		return this;
	}
	public int compareTo(final Resource_1_0_5 rhs) {
		return Integer.valueOf(resourceNumber).compareTo(
				Integer.valueOf(rhs.getResourceNumber()));
	}
	public void show() {
		Debug.print(resourceNumber);
	}
	private long amount; // unsigned
	private int resourceNumber; // unsigned
}
