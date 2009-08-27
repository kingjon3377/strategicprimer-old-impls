package proj_sp5;
class RawResource {
	Point location;
	int resourceNumber; // unsigned
	long amount; // unsigned
	static RawResource createRawResource(final Point p, int number,
			long amount) {
		RawResource temp = new RawResource();
		temp.location = new Point(p);
		temp.resourceNumber = number;
		temp.amount = amount;
		return temp;
	}
	static RawResource getResourceFromFile(final InputStream in) {
		getStringFromFile(in, "STRATEGIC PRIMER RAW RESOURCE");
		return createRawResource(getPointFromFile(), getIntFromFile(),
				getDoubleFromFile());
	}
	static void writeResourceToFile(final OutputStream out, 
			final Resource res) {
		out.print("STRATEGIC PRIMER RAW RESOURCE");
		out.print(res.location);
		out.print(res.resourceNumber);
		out.print(res.amount);
	}
	static Resource getResourceFromUser() {
		puts("Please enter data for a Raw Resource.");
		puts("Location");
		double location = getPoint();
		puts("Resource Number");
		int number = getInteger();
		puts("Amount");
		double amount = getDouble();
		return createRawResource(location, number, amount);
	}
	static void showRawResourceToUser(final RawResource res) {
		// This should be changed to do this graphically
		puts("Data of a raw resource:");
		puts("Location: " + res.location);
		puts("Resource Number: " + res.resourceNumber);
		puts("Amount: " + res.amount);
	}
}
