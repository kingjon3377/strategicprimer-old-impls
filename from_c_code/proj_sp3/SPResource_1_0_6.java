package proj_sp3;
class SPResource_1_0_6 {
	SPResource_1_0_6() {
		amount = 0;
		resourceNumber = 0;
	}
	SPResource_1_0_6(final long amt, final int number) {
		amount = amt;
		resourceNumber = number;
	}
	SPResource_1_0_6(final SPResource_1_0_6 rhs) {
		amount = rhs.getAmount();
		resourceNumber = rhs.getResourceNumber();
	}
	long getAmount() { return amount; }
	boolean setAmount(final long amt) {
		if (amt >= 0) {
			amount = amt;
			return true;
		} else {
			return false;
		}
	}
	boolean changeAmount(final long change) {
		if (amount + change >= 0) {
			amount += change;
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
	SPResource_1_0_6 copy(final SPResource_1_0_6 rhs) {
		if (this != rhs) {
			amount = rhs.getAmount();
			resourceNumber = rhs.getResourceNumber();
		}
		return this;
	}
	int compareTo(final SPResource_1_0_6 rhs) {
		return Integer.valueOf(resourceNumber).compareTo(
				Integer.valueOf(rhs.getResourceNumber()));
	void show() { Debug.print(resourceNumber); }
	private long amount; // unsigned
	private int resourceNumber; // unsigned
}
