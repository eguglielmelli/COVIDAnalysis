package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.TreeMap;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.util.Zip;

/**
 * This class reads a formatted population CSV file and adds it to the data structure of the reader
 */
public class PopulationReader extends GeneralReader {
	
	// The filename is the only private parameter
	private String filename;
	
	// Constructor: requires the filename to read from and the data structure to add items to
	protected PopulationReader(String input, TreeMap<String, Zip> data) throws Exception {
		this.filename = input;
		readPopulation(data);
	}
	
	/**
	 * Method to read the population from the CSV file and add it to the data structure
	 * @param data (TreeMap<String, Zip>) : data structure to update population information
	 * @throws Exception : if error when reading the file
	 */
	private void readPopulation(TreeMap<String, Zip> data) throws Exception {
		
		try {
			// Try to create a reader with the given filename
        	reader = new BufferedReader(new FileReader(filename));
        	Logger logger = Logger.getInstance();
        	logger.writeToLog(filename);
        	// Get the header (first row) and determine the indexes we need using the helper function
        	String[] header = readRow();
        	String[] arguments = {"zip_code", "population"};
        	int[] indexes = getIndexes(header, arguments);
        	
        	// Create a String array for the contents and loop until the whole file has been read
        	String[] contents;
        	while((contents = readRow()) != null) {
        		// Get the Zip code from the array of contents and validate it using the helper function
        		// CONDITION: Zip code must be exactly 5 digits
        		String zip = validateZip(contents[indexes[0]], "^[0-9]{5}$");
        		
        		// Get the population from the array of contents and validate it using the helper function
        		// CONDITION: Population must be a valid integer
        		int population = validateInteger((contents[indexes[1]]), "^-?\\d+$");
        		
        		// If either of the parameters are invalid, skip this iteration of the loop
        		if(zip == null || population == -1) { continue;}
        		
        		// Otherwise, check whether the Zip code already exist in data and add it if it does not
        		if(!data.containsKey(zip)) {
        			data.put(zip, new Zip(zip));
        		}
        		
        		// Update the information inside the data array
    			data.get(zip).setTotalPopulation(population);
        	}
			reader.close();
        // Catch any exceptions and throw it as human readable error explaining where the issue is
		} catch (Exception e) {
			throw new Exception("Error reading population file. Program exiting...");
		}		
	}
}
