package edu.upenn.cit594.datamanagement;

import java.util.TreeMap;
import java.util.regex.Pattern;
import edu.upenn.cit594.util.Zip;

/**
 * This class takes in arguments from Main, reads data from given arguments, and initializes the logger.
 * It returns a data structure with all the information and a boolean with which files the user provided
 */
public class Reader {
	
	// Local arguments include the name of each file, the data structure to hold the data, and an array with which files were read
	protected String covidFilename = null;
	protected String propertyFilename = null;
	protected String populationFilename = null;
	protected String logFilename = null;
	protected TreeMap<String, Zip> data = new TreeMap<String, Zip>();
	protected boolean[] readFiles = {false, false, false, false};
	
	// Constructor: decides which inputs were provided and calls the appropriate readers
	public Reader(String[] inputs) throws Exception {
		setInputs(inputs);
		//if(readFiles[0]) {Logger.setOrChangeDestination(logFilename);}
        if(readFiles[1]) {new PopulationReader(populationFilename, data);}
        if(readFiles[2]) {new CovidReader(covidFilename, data);}
        if(readFiles[3]) {new PropertyReader(propertyFilename, data);}
	}

	/**
	 * This method decides which files were provided and sets the appropriate filename and boolean value in the array
	 * @param inputs (String []) : array of strings with the provided arguments
	 * @throws Exception : if argument provided is not valid
	 */
	private void setInputs(String[] inputs) throws Exception {
		// Create a pattern to match the expected argument '--name=value'
		Pattern pattern = Pattern.compile("^--(?<name>.+?)=(?<value>.+)$");
		
		// Loop through each input
		for(String input : inputs) {
			// If it does not match the pattern, throw an exception
			if(!pattern.matcher(input).find()) {
				throw new Exception(" Argument not of form '--name=value'");
			}
			// Otherwise, split based on the equals sign
			String[] property = input.split("=");
			// If it matches --log, set the filename and boolean array 0 to true
			if(property[0].equals("--log") && logFilename == null) {
				logFilename = property[1];
				readFiles[0] = true;
			// If it matches --population, set the filename and boolean array 1 to true
			} else if (property[0].equals("--population") && populationFilename == null) {
				populationFilename = property[1];
				readFiles[1] = true;
			// If it matches --covid, set the filename and boolean array 2 to true
			} else if (property[0].equals("--covid") && covidFilename == null) {
				covidFilename = property[1];
				readFiles[2] = true;
			// If it matches --properties, set the filename and boolean array 3 to true
			} else if (property[0].equals("--properties") && propertyFilename == null) {
				propertyFilename = property[1];
				readFiles[3] = true;
			// Else, the argument provided is invalid or the same type of argument was provided twice, so throw an exception
			} else {
				throw new Exception("Provided invalid argument. Program exiting...");
			}
		}

	}
	
	// Getter method to return data structure
	public TreeMap<String, Zip> getData() {return data;}
	
	// Getter method to return array of which files were read
	public boolean[] getReadFiles() {return readFiles;}
}
