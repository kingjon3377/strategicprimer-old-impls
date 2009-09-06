package proj_sp6;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

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
		return r;
	}
	static RawResource readRawResourceFromFile(final InputStream in) throws IOException {
		Globals.getStringFromFile(in, "STRATEGIC PRIMER RAW RESOURCE");
		return createRawResource(Point.getPointFromFile(in), 
				Globals.getUIntFromFile(in), Globals.getULongFromFile(in));
	}
	static void writeRawResourceToFile(final PrintStream out,
			final RawResource r) {
		Globals.writeStringToFile(out, "STRATEGIC PRIMER RAW RESOURCE");
		Point.writePointToFile(out, r.location);
		Globals.writeUIntToFile(out, r.resourceNumber);
		Globals.writeULongToFile(out, r.amount);
	}
	static RawResource getRawResourceFromUser() {
		Globals.puts("Please enter data for a Raw Resource");
		Globals.puts("Position:");
		Point location = Point.getPointFromUser();
		Globals.puts("Resource Number:");
		int resourceNumber = User.getUIntFromUser();
		Globals.puts("Amount:");
		long amount = User.getULongFromUser();
		return createRawResource(location, resourceNumber, amount);
	}
	static void showRawResourceToUser(final RawResource r) {
		Globals.puts("Data of a Raw Resource:");
		Globals.puts("Location:");
		Point.showPointToUser(r.location);
		Globals.puts("Resource Number:");
		Globals.puts(Integer.toString(r.resourceNumber));
		Globals.puts("Amount:");
		Globals.puts(Long.toString(r.amount));
	}
}
