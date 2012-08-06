package proj_sp6;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

class GlobalResource {
	String name;
	double marketValue;
	int resourceNumber; // unsigned

	static GlobalResource createGlobalResource(final String name,
			final double marketValue, final int resourceNumber) {
		GlobalResource g = new GlobalResource();
		g.name = name;
		g.marketValue = marketValue;
		g.resourceNumber = resourceNumber;
		return g;
	}

	static GlobalResource readGlobalResourceFromFile(final InputStream in)
			throws IOException {
		Globals.getStringFromFile(in, "STRATEGIC_PRIMER_GLOBAL_RESOURCE");
		return createGlobalResource(Globals.getStringFromFile(in), Globals
				.getDoubleFromFile(in), Globals.getUIntFromFile(in));
	}

	static void writeGlobalResourceToFile(final PrintStream out,
			final GlobalResource gr) {
		Globals.writeStringToFile(out, "STRATEGIC_PRIMER_GLOBAL_RESOURCE");
		Globals.writeStringToFile(out, gr.name);
		Globals.writeDoubleToFile(out, gr.marketValue);
		Globals.writeUIntToFile(out, gr.resourceNumber);
	}

	static GlobalResource getGlobalResourceFromUser() {
		Globals.puts("Please enter data for a Global Resource");
		Globals.puts("Name:");
		String name = User.getStringFromUser();
		Globals.puts("Market Value:");
		double marketValue = User.getDoubleFromUser();
		Globals.puts("Resource Number:");
		int resourceNumber = User.getUIntFromUser();
		return createGlobalResource(name, marketValue, resourceNumber);
	}

	static void showGlobalResourceToUser(final GlobalResource gr) {
		Globals.puts("Data of a Global Resource:");
		Globals.puts("Name:");
		Globals.puts(gr.name);
		Globals.puts("Market Value:");
		Globals.puts(Double.toString(gr.marketValue));
		Globals.puts("Resource Number:");
		Globals.puts(Integer.toString(gr.resourceNumber));
	}
}
