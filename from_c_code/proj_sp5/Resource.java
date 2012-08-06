package proj_sp5;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

class Resource {
	long amount; // unsigned
	int resourceNumber; // unsigned
	static Resource createResource(final long amount, int number) {
		Resource temp = new Resource();
		temp.amount = amount;
		temp.resourceNumber = number;
		return temp;
	}
	static Resource getResourceFromFile(final InputStream in) throws IOException {
		Globals.getStringFromFile(in, "STRATEGIC PRIMER RESOURCE");
		return createResource(Globals.getLongIntFromFile(in),
				Globals.getIntFromFile(in));
	}
	static void writeResourceToFile(final PrintStream out, 
			final Resource res) {
		out.print("STRATEGIC PRIMER RESOURCE");
		out.print(res.amount);
		out.print(res.resourceNumber);
	}
	static Resource getResourceFromUser() {
		Globals.puts("Please enter data for a Resource.");
		Globals.puts("Amount");
		long amount = User.getLongIntegerFromUser();
		Globals.puts("Resource Number");
		int number = User.getIntegerFromUser();
		return createResource(amount, number);
	}
	static void showResourceToUser(final Resource res) {
		// This should be changed to do this graphically
		Globals.puts("Data of a resource:");
		Globals.puts("Amount: " + res.amount);
		Globals.puts("Resource Number: " + res.resourceNumber);
	}
}
