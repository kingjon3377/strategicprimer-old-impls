package proj_sp5;
class Resource {
	long amount; // unsigned
	int resourceNumber; // unsigned
	static Resource createResource(final long amount, int number) {
		Resource temp = new Resource();
		temp.amount = amount;
		temp.resourceNumber = number;
		return temp;
	}
	static Resource getResourceFromFile(final InputStream in) {
		getStringFromFile(in, "STRATEGIC PRIMER RESOURCE");
		return createResource(getDoubleFromFile(),
				getIntFromFile());
	}
	static void writeResourceToFile(final OutputStream out, 
			final Resource res) {
		out.print("STRATEGIC PRIMER RESOURCE");
		out.print(res.amount);
		out.print(res.resourceNumber);
	}
	static Resource getResourceFromUser() {
		puts("Please enter data for a Resource.");
		puts("Amount");
		double amount = getDouble();
		puts("Resource Number");
		int number = getInteger();
		return createResource(amount, number);
	}
	static void showResourceToUser(final Resource res) {
		// This should be changed to do this graphically
		puts("Data of a resource:");
		puts("Amount: " + res.amount);
		puts("Resource Number: " + res.resourceNumber);
	}
}
