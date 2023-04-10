package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.TreeMap;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.util.Zip;

/**
 * This class reads a formatted property CSV file and adds it to the data structure of the reader
 */
public class PropertyReader extends GeneralReader {
	
	// The filename is the only private parameter
	private String filename;
	
	// Constructor: requires the filename to read from and the data structure to add items to
	protected PropertyReader(String input, TreeMap<String, Zip> data) throws Exception {
		this.filename = input;
		readProperty(data);
	}
	
	/**
	 * Method to read the property from the CSV file and add it to the data structure
	 * @param data (TreeMap<String, Zip>) : data structure to update property information
	 * @throws Exception : if error when reading the file
	 */
	private void readProperty(TreeMap<String, Zip> data) throws Exception {
		
		try {
			// Try to create a reader with the given filename
        	reader = new BufferedReader(new FileReader(filename));
        	Logger logger = Logger.getInstance();
        	logger.writeToLog(filename);
        	// Get the header (first row) and determine the indexes we need using the helper function
        	String[] header = readRow();
        	String[] arguments = {"zip_code", "market_value", "total_livable_area"};
        	int[] indexes = getIndexes(header, arguments);
        	
        	// Create a String array for the contents and loop until the whole file has been read
        	String[] contents;
        	while((contents = readRow()) != null) {
        		// Get the Zip code from the array of contents and validate it using the helper function
        		// CONDITION: Zip code must have 5 integer as the first characters
        		String zip = validateZip(contents[indexes[0]], "^\\d{5}");

        		// Get the market value from the array of contents and validate it using the helper function
        		// CONDITION: VAlue should be numeric (including decimal numbers:
        		float marketValue = validateFloat(contents[indexes[1]], "^-?\\d*\\.?\\d+$");
        		
        		// Get the total livable area from the array of contents and validate it using the helper function
        		// CONDITION: VAlue should be numeric (including decimal numbers
        		float totalLivableArea = validateFloat(contents[indexes[2]], "^-?\\d*\\.?\\d+$");
        		
        		// If a valid Zip code has not been found in the line, skip this iteration
        		if(zip == null || zip.length() < 5) { continue;}
        		else{ zip = zip.substring(0, 5);}
        		
        		// Otherwise, check whether the Zip code already exist in data and add it if it does not
        		if(!data.containsKey(zip)) {
        			data.put(zip, new Zip(zip));        			
        		}
        		
        		// If the market value is valid, add it to the list in Zip code
        		if(marketValue != -1) {
        			data.get(zip).setMarketValue(marketValue);
        		}
        		// If the total livable area is valid, add it to the list in Zip code
        		if(totalLivableArea != -1) {
        			data.get(zip).setTotalArea(totalLivableArea);
        		}
        	}
			reader.close();
        // Catch any exceptions and throw it as human readable error explaining where the issue is
		} catch (Exception e) {
			throw new Exception("Error reading property file. Program exiting...");
		}
	}
}
