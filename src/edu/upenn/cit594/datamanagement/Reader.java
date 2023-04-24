package edu.upenn.cit594.datamanagement;

import java.util.HashMap;
import java.util.TreeMap;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.util.Zip;

/**
 * This class takes in arguments from Main, reads data from given arguments, and initializes the logger.
 * It returns a data structure with all the information and a boolean with which files the user provided
 */
public class Reader {
	
	// Local arguments include the name of each file, the data structure to hold the data, and an array with which files were read
	protected HashMap<String, String> filenames;
	protected TreeMap<String, Zip> data = new TreeMap<String, Zip>();
	protected boolean[] readFiles = {false, false, false, false};
	
	// Constructor: decides which inputs were provided and calls the appropriate readers
	public Reader(HashMap<String, String> filenames) throws Exception {
		this.filenames = filenames;
		readFiles();
	}

	/**
	 * Method to create the readers and populate the data structure with the information from the files
	 * @throws Exception if any error occurs when creating the files
	 */
	private void readFiles() throws Exception {
		// Determine which arguments exist in the map and populate the array with available filenames
		if(filenames.containsKey("logFilename"))        {readFiles[0] = true;}
		if(filenames.containsKey("populationFilename")) {readFiles[1] = true;}
		if(filenames.containsKey("covidFilename"))      {readFiles[2] = true;}
		if(filenames.containsKey("propertyFilename"))   {readFiles[3] = true;}
		// Create the logger and log the inputs
		Logger logger = Logger.getInstance();
		if(readFiles[0]) {logger.setDestination(filenames.get("logFilename"));}
		String log = "";
		for (HashMap.Entry<String, String> entry : filenames.entrySet()) {
			log += entry.getValue() + " ";
		}
		logger.writeToLog(log);
		// Create readers if the corresponding argument has been passed
        if(readFiles[1]) {new PopulationReader(filenames, data);}
        if(readFiles[2]) {new CovidReader(filenames, data);}
        if(readFiles[3]) {new PropertyReader(filenames, data);}
	}
	
	/**
	 * Method to return data to the upper tier to be processed
	 * @return data (TreeMap<String, Zip>) : Map with all the Zip classes and the relevant information from files
	 */
	public TreeMap<String, Zip> getData() {
		return data;
	}
	
	/**
	 * Method to return read files to the upper tier to be processed
	 * @return data (boolean[]) : boolean with which files were read
	 */	
	public boolean[] getReadFiles() {
		return readFiles;
	}
}
