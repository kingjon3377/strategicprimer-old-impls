package proj_sp5;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

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
	static RawResource getResourceFromFile(final InputStream in) throws IOException {
		Globals.getStringFromFile(in, "STRATEGIC PRIMER RAW RESOURCE");
		return createRawResource(Point.getPointFromFile(in), Globals.getIntFromFile(in),
				Globals.getLongIntFromFile(in));
	}
	static void writeResourceToFile(final PrintStream out, 
			final RawResource res) {
		out.print("STRATEGIC PRIMER RAW RESOURCE");
		out.print(res.location);
		out.print(res.resourceNumber);
		out.print(res.amount);
	}
	static RawResource getResourceFromUser() {
		Globals.puts("Please enter data for a Raw Resource.");
		Globals.puts("Location");
		Point location = Point.getPointFromUser();
		Globals.puts("Resource Number");
		int number = User.getIntegerFromUser();
		Globals.puts("Amount");
		long amount = User.getLongIntegerFromUser();
		return createRawResource(location, number, amount);
	}
	static void showRawResourceToUser(final RawResource res) {
		// This should be changed to do this graphically
		Globals.puts("Data of a raw resource:");
		Globals.puts("Location: " + res.location);
		Globals.puts("Resource Number: " + res.resourceNumber);
		Globals.puts("Amount: " + res.amount);
	}
}
