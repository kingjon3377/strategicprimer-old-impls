package proj_sp5;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

class GlobalResource {
	String name;
	double marketValue;
	int resourceNumber; // unsigned

	public static GlobalResource createGlobalResource(final String name,
			final double value, final int number) {
		GlobalResource temp = new GlobalResource();
		temp.name = name;
		temp.marketValue = value;
		temp.resourceNumber = number;
		return temp;
	}

	static GlobalResource getGlobalResourceFromFile(final InputStream in) throws IOException {
		Globals.getStringFromFile(in, "STRATEGIC PRIMER GLOBAL RESOURCE");
		return createGlobalResource(Globals.getStringFromFile(in),
				Globals.getDoubleFromFile(in), Globals.getIntFromFile(in));
	}

	static void writeGlobalResourceToFile(final PrintStream out,
			final GlobalResource res) {
		out.print("STRATEGIC PRIMER GLOBAL RESOURCE");
		out.print(res.name);
		out.print(res.marketValue);
		out.print(res.resourceNumber);
	}

	static GlobalResource getGlobalResourceFromUser() {
		Globals.puts("Please enter data for a Global Resource.");
		Globals.puts("Name");
		String name = User.getStringFromUser();
		Globals.puts("Market Value");
		double value = User.getDoubleFromUser();
		Globals.puts("Resource Number");
		int number = User.getIntegerFromUser();
		return createGlobalResource(name, value, number);
	}

	static void showGlobalResourceToUser(final GlobalResource res) {
		// This should be changed to do this graphically
		Globals.puts("Data of a global resource:");
		Globals.puts("Name: " + res.name);
		Globals.puts("Market Value: " + res.marketValue);
		Globals.puts("Resource Number: " + res.resourceNumber);
	}
}
