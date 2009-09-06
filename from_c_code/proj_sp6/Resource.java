package proj_sp6;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

class Resource {
	long amount; // unsigned
	int resourceNumber; // unsigned

	static Resource createResource(final long amount, final int resourceNumber) {
		Resource r = new Resource();
		r.amount = amount;
		r.resourceNumber = resourceNumber;
		return r;
	}

	static Resource readResourceFromFile(final InputStream in) throws IOException {
		Globals.getStringFromFile(in, "STRATEGIC PRIMER RESOURCE");
		return createResource(Globals.getULongFromFile(in), Globals
				.getUIntFromFile(in));
	}

	static void writeResourceToFile(final PrintStream out, final Resource r) {
		Globals.writeStringToFile(out, "STRATEGIC PRIMER RESOURCE");
		Globals.writeULongToFile(out, r.amount);
		Globals.writeUIntToFile(out, r.resourceNumber);
	}

	static Resource getResourceFromUser() {
		Globals.puts("Please enter data for a Resource.");
		Globals.puts("Amount:");
		long amount = User.getULongFromUser();
		Globals.puts("Resource Number:");
		int resourceNumber = User.getUIntFromUser();
		return createResource(amount, resourceNumber);
	}

	static void showResourceToUser(final Resource r) {
		Globals.puts("Data of a Resource:");
		Globals.puts("Amount:");
		Globals.puts(Long.toString(r.amount));
		Globals.puts("Resource Number:");
		Globals.puts(Integer.toString(r.resourceNumber));
	}
}
