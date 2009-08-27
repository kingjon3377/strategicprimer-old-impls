package proj_sp6;
class GlobalResource {
	String name;
	double marketValue;
	int resourceNumber; // unsigned
	static GlobalResource createGlobalResource(final String name, 
			final double marketValue, final int resourceNumber) {
		GlobalResource g = new GlobalResource; 
		g.name = name;
		g.marketValue = marketValue;
		g.resourceNumber = resourceNumber;
		return g;
	}
	static GlobalResource readGlobalResourceFromFile(final InputStream in) {
		readStringFromFile(in, "STRATEGIC_PRIMER_GLOBAL_RESOURCE");
		return createGlobalResource(readStringFromFile(in), 
				readDoubleFromFile(in), readUIntFromFile(in));
	}
	static void writeGlobalResourceToFile(final OutputStream out, 
			final GlobalResource gr) {
		writeStringToFile(out, "STRATEGIC_PRIMER_GLOBAL_RESOURCE");
		writeStringToFile(out, gr.name);
		writeDoubleToFile(out, gr.marketValue);
		writeUIntToFile(out, gr.resourceNumber);
	}
	static GlobalResource getGlobalResourceFromUser() {
		puts("Please enter data for a Global Resource");
		puts("Name:");
		String name = getStringFromUser();
		puts("Market Value:");
		double marketValue = getDoubleFromUser();
		puts("Resource Number:");
		int resourceNumber = getUIntFromUser();
		return createGlobalResource(name, marketValue, resourceNumber);
	}
	static void showGlobalResourceToUser(final GlobalResource gr) {
		puts("Data of a Global Resource:");
		puts("Name:");
		puts(gr.name);
		puts("Market Value:");
		puts(gr.marketValue);
		puts("Resource Number:");
		puts(gr.resourceNumber);
	}
}
