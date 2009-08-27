package proj_sp5;
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
	}
	static GlobalResource getGlobalResourceFromFile(final InputStream in) {
		getStringFromFile(in, "STRATEGIC PRIMER GLOBAL RESOURCE");
		return createGlobalResource(getStringFromFile(in),
				getDoubleFromFile(),getIntFromFile());
	}
	static void writeGlobalResourceToFile(final OutputStream out, 
			final GlobalResource res) {
		out.print("STRATEGIC PRIMER GLOBAL RESOURCE");
		out.print(res.name);
		out.print(res.marketValue);
		out.print(res.resourceNumber);
	}
	static GlobalResource getGlobalResourceFromUser() {
		puts("Please enter data for a Global Resource.");
		puts("Name");
		String name = getString();
		puts("Market Value");
		double value = getDouble();
		puts("Resource Number");
		int number = getInteger();
		return createGlobalResource(name, value, number);
	}
	static void showGlobalResourceToUser(final GlobalResource res) {
		// This should be changed to do this graphically
		puts("Data of a global resource:");
		puts("Name: " + res.name);
		puts("Market Value: " + res.marketValue);
		puts("Resource Number: " + res.resourceNumber);
	}
}
