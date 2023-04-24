package edu.upenn.cit594.datamanagement;

import java.io.FileReader;
import java.util.TreeMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.util.Zip;

/**
 * This class reads a formatted population JSON or CSV file and adds it to the data structure of the reader
 * 
 * NOTE: See notes in CovidReader for more information about this class.
 */
public class CovidJSONReader extends GeneralReader {
	
	// The filename is the only private parameter
	private String filename;
	
	// Constructor: requires the filename to read from and the data structure to add items to
	protected CovidJSONReader(String input, TreeMap<String, Zip> data) throws Exception {
		this.filename = input;
		readJsonCovid(data);
	}
	
	/**
	 * Method to read the Covid data from the JSON file and add it to the data structure
	 * @param data (TreeMap<String, Zip>) : data structure to update population information
	 * @throws Exception : if error when reading the file
	 */
	private void readJsonCovid(TreeMap<String, Zip> data) throws Exception {
		
		try {
			// Open the file for reading using JSON parser
			JSONArray jsonArray = (JSONArray) new JSONParser().parse(new FileReader(filename));
        	Logger logger = Logger.getInstance();
        	logger.writeToLog(filename);
			
			// Loop through each object, extract relevant information to Java objects
			for(Object obj : jsonArray) {
				JSONObject jo = (JSONObject) obj;
				// If the object does not contain Zip Code or Timestamp, skip this iteration
				if(!jo.containsKey("zip_code") || !jo.containsKey("etl_timestamp")) {continue;}
				
        		// Get the Zip code from the object and validate it using the helper function
        		// CONDITION: Zip code must have exactly 5 digits
				String zip = validateZip(Long.toString((Long) jo.get("zip_code")), "^[0-9]{5}$");
				
        		// Get the date from the object and validate it using the helper function
        		// CONDITION: Date must contain string in the format YYYY:MM:DD. hh:mm:ss is optional.
				String date = validateDate((String) jo.get("etl_timestamp"));
				
				// Create new integers for partially and fully vaccinated
				int partiallyVaccinated = -1;
				int fullyVaccinated = -1;
				
				// If the object contains the required date, validate it using the helper functions
        		// CONDITION: Count must contain an integer
				if(jo.containsKey("partially_vaccinated")) {
					partiallyVaccinated = validateInteger(Long.toString((Long) jo.get("partially_vaccinated")), "^-?\\d+$");
				}
				if(jo.containsKey("fully_vaccinated")) {
					fullyVaccinated = validateInteger(Long.toString((Long) jo.get("fully_vaccinated")), "^-?\\d+$");
				}
				
				// If the retrieved Zip or date are invalid, skip this iteration
        		if(zip == null || date == null) {continue;}
				
        		// Else, add validated data to data structure
        		addCovidToData(zip, date, partiallyVaccinated, fullyVaccinated, data);
			}
						
	    // Generalize all error messages to a human readable error pinpointing where the issue is
        } catch (Exception e) {
			throw new Exception("Error reading Covid Json file. Program exiting...");
		}
	}
}
