package proj_sp6;
class Resource {
	long amount; // unsigned
	int resourceNumber; // unsigned
	static Resource createResource(final long amount, 
			final int resourceNumber);
	static Resource readResourceFromFile(final InputStream in) {
		readStringFromFile(in, "STRATEGIC PRIMER RESOURCE");
		return createResource(readULongFromFile(), readUIntFromFile());
	}
	static void writeResourceToFile(final OutputStream out,
			final Resource r) {
		writeStringToFile(out, "STRATEGIC PRIMER RESOURCE");
		writeStringToFile(out, r.amount);
		writeStringToFile(out, r.resourceNumber);
	}
	static Resource getResourceFromUser() {
		puts("Please enter data for a Resource.");
		puts("Amount:");
		double amount = getDoubleFromUser();
		puts("Resource Number:");
		int resourceNumber = getUIntFromUser();
		return createResource(amount, resourceNumber);
	}
	static void showResourceToUser(final Resource r) {
		puts("Data of a Resource:");
		puts("Amount:");
		puts(r.amount);
		puts("Resource Number:");
		puts(r.resourceNumber);
	}
}
