package proj_sp6;
class RawResource {
	Point location;
	int resourceNumber; // unsigned
	long amount; // unsigned
	static RawResource createRawResource(final Point location, 
			final int resourceNumber, final long amount) {
		RawResource r = new RawResource();
		r.location = location;
		r.resourceNumber = resourceNumber;
		r.amount = amount;
	}
	static RawResource readRawResourceFromFile(final InputStream in) {
		readStringFromFile(in, "STRATEGIC PRIMER RAW RESOURCE");
		return createRawResource(readPointFromFile(), 
				readUIntFromFile(), readULongFromFile());
	}
	static void writeRawResourceToFile(final OutputStream out,
			final RawResource r) {
		writeStringToFile(out, "STRATEGIC PRIMER RAW RESOURCE");
		writePointToFile(out, r.location);
		writeUIntToFile(out, r.resourceNumber);
		writeULongToFile(out, r.amount);
	}
	static RawResource getRawResourceFromUser() {
		puts("Please enter data for a Raw Resource");
		puts("Position:");
		Point location = getPointFromUser();
		puts("Resource Number:");
		int resourceNumber = getUIntFromUser();
		puts("Amount:");
		long amount = getULongFromUser();
		return createRawResource(location, resourceNumber, amount);
	}
	static void showRawResourceToUser(final RawResource r) {
		puts("Data of a Raw Resource:");
		puts("Location:");
		puts(r.location);
		puts("Resource Number:");
		puts(r.resourceNumber);
		puts("Amount:");
		puts(r.amount);
	}
}
