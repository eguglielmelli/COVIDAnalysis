package edu.upenn.cit594.datamanagement;

import java.util.TreeMap;
import edu.upenn.cit594.util.Zip;

/**
 * This class reads a formatted population JSON or CSV file and adds it to the data structure of the reader
 * 
 * NOTE: This class originally had the method to decide which reader to call (readCovid), a helper method to 
 * add the COVID information from the file to the data structure (addCovidToData), a CSV reader method (readCSVCovid), 
 * and a JSON reader method (readJSONCovid). The class was fairly short (less than 200 lines) and matched the 
 * design of the rest of the package: a class to organize the inputs, call the readers, and to store the getter 
 * methods (Reader class), a general class with multi-purpose methods for all readers (GeneralReader), and a class 
 * for each type of file we need to read (PopulationReader, PropertyReader, CovidReader). However, due to the 
 * requirement of separating the CSV and JSON reader, additional classes were created. This created three classes 
 * with very few methods (1-2 methods per class) and some redundant code, which seems like not the best design choice 
 * but matches the project requirements. A staff member confirmed this must be done to fulfill the requirements of the 
 * project. See "OriginalCovidReader" for my preferred implementation. See private post 2918394 on Ed for more details.
 */
public class CovidReader extends GeneralReader {
	
	// The filename is the only private parameter
	private String filename;
	
	// Constructor: requires the filename to read from and the data structure to add items to
	protected CovidReader(String input, TreeMap<String, Zip> data) throws Exception {
		this.filename = input;
		readCovid(data);
	}
	
	/**
	 * Method to determine whether to call the JSON method or the CSV Method and read the Covid data
	 * @param data
	 * @throws Exception
	 */
	private void readCovid(TreeMap<String, Zip> data) throws Exception {
		// Split the filename based on any periods in the String
		String[] fileEnding = filename.trim().split("\\.");

		// If file ends in csv, call the CSV method
		if(fileEnding[fileEnding.length-1].toLowerCase().equals("csv")) {
			new CovidCSVReader(filename, data);
		// If file ends in json, call the JSON method
		} else if (fileEnding[fileEnding.length-1].toLowerCase().equals("json")) {
			new CovidJSONReader(filename, data);
	    // Else, generalize all error messages to a human readable error pinpointing where the issue is
		} else {
			throw new Exception("Unrecognized Covid file format. Program exiting...");
		}		
	}
}
