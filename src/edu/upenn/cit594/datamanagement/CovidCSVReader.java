package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.TreeMap;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.util.Covid;
import edu.upenn.cit594.util.Zip;

/**
 * This class reads a formatted population JSON or CSV file and adds it to the data structure of the reader.
 * 
 * NOTE: See notes in CovidReader for more information about this class.
 */
public class CovidCSVReader extends GeneralReader {
	
	// The filename is the only private parameter
	private String filename;
	
	// Constructor: requires the filename to read from and the data structure to add items to
	protected CovidCSVReader(String input, TreeMap<String, Zip> data) throws Exception {
		this.filename = input;
		readCsvCovid(data);
	}
	
	/**
	 * Method to read the Covid data from the CSV file and add it to the data structure
	 * @param data (TreeMap<String, Zip>) : data structure to update population information
	 * @throws Exception : if error when reading the file
	 */
	private void readCsvCovid(TreeMap<String, Zip> data) throws Exception {
		
		try {
			// Try to create a reader with the given filename
        	reader = new BufferedReader(new FileReader(filename));
        	Logger logger = Logger.getInstance();
        	logger.writeToLog(filename);
        	// Get the header (first row) and determine the indexes we need using the helper function
        	String[] header = readRow();
        	String[] arguments = {"zip_code", "etl_timestamp", "partially_vaccinated", "fully_vaccinated"};
        	int[] indexes = getIndexes(header, arguments);
        	
        	// Create a String array for the contents and loop until the whole file has been read
        	String[] contents;
        	while((contents = readRow()) != null) {
        		// Get the Zip code from the array of contents and validate it using the helper function
        		// CONDITION: Zip code must have exactly 5 digits
        		String zip = validateZip(contents[indexes[0]], "^[0-9]{5}$");
        		
        		// Get the date from the array of contents and validate it using the helper function
        		// CONDITION: Date must contain string in the format YYYY:MM:DD. hh:mm:ss is optional.
        		String date = validateDate(contents[indexes[1]]);
        		
        		// Get the partially and fully vaccinated counts and validate it using the helper function
        		// CONDITION: Count must contain an integer
        		int partiallyVaccinated = validateInteger(contents[indexes[2]], "^-?\\d+$");
        		int fullyVaccinated = validateInteger(contents[indexes[3]], "^-?\\d+$");
        		
        		// If either of the parameters are invalid, skip this iteration of the loop
        		if(zip == null || date == null) { continue;}
        		
        		// Else, add validated data to data structure
        		addCovidToData(zip, date, partiallyVaccinated, fullyVaccinated, data);
        		
        	}
			reader.close();
		} catch (Exception e) {
			throw new Exception("Error reading Covid Csv file. Program exiting...");
		}
	}
	
	/**
	 * This helper function adds the validated data into the data structure
	 * @param zip (String) : validated Zip of this Covid data
	 * @param date : validated Date of this Covid data
	 * @param partiallyVaccinated (int) : number of partially vaccinated people in a given date
	 * @param fullyVaccinated (int) : number of fully vaccinated people in a given date
	 * @param data
	 */
	private void addCovidToData(String zip, String date, int partiallyVaccinated, int fullyVaccinated, TreeMap<String, Zip> data) {
		// Otherwise, check whether the Zip code already exist in data and add it if it does not
		if(!data.containsKey(zip)) {
			data.put(zip, new Zip(zip));
		}
		
		// Get the map with the covid cases for the given zip code
		HashMap<String, Covid> covidCases = data.get(zip).getCovidCases();
		
		// If the map does not contain Covid cases for given date, then create one
		if(!covidCases.containsKey(date)) {
			covidCases.put(date, new Covid(date));
		}
		
		// Retrieve cases for given date
		Covid cases = covidCases.get(date);
		
		// Add partially vaccinated if it is valid integer
		if(partiallyVaccinated != -1) {
			cases.setPartiallyVaccinated(partiallyVaccinated);
		}
		
		// Add fully vaccinated if it is valid integer
		if(fullyVaccinated != -1) {
			cases.setFullyVaccinated(fullyVaccinated);
		}
	}
}
