package edu.upenn.cit594;

import java.util.HashMap;
import java.util.regex.Pattern;
import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.ui.Display;

public class Main {
	
	protected static HashMap<String, String> validArguments = new HashMap<String, String>();
	
	public static void main(String[] args) {	
		try {
			setInputs(args);
			Reader reader = new Reader(validArguments);
			Processor processor = new Processor(reader);
			Display display = new Display(processor);
			display.startProgram();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * This method decides which files were provided and sets the appropriate filename and boolean value in the array
	 * @param inputs (String []) : array of strings with the provided arguments
	 * @throws Exception : if argument provided is not valid
	 */
	public static void setInputs(String[] inputs) throws Exception {
		// Create a pattern to match the expected argument '--name=value'
		Pattern pattern = Pattern.compile("^--(?<name>.+?)=(?<value>.+)$");
		
		// Loop through each input
		for(String input : inputs) {
			// If it does not match the pattern, throw an exception
			if(!pattern.matcher(input).find()) {
				throw new Exception("Argument not of form '--name=value'");
			}
			// Otherwise, split based on the equals sign
			String[] property = input.split("=");
			// If it matches --log, set the filename
			if(property[0].equals("--log") && !validArguments.containsKey("logFilename")) {
				validArguments.put("logFilename", property[1]);
			// If it matches --population, set the filename
			} else if (property[0].equals("--population") && !validArguments.containsKey("populationFilename")) {
				validArguments.put("populationFilename", property[1]);
			// If it matches --covid, set the filename
			} else if (property[0].equals("--covid") && !validArguments.containsKey("covidFilename")) {
				validArguments.put("covidFilename", property[1]);
			// If it matches --properties, set the filename
			} else if (property[0].equals("--properties") && !validArguments.containsKey("propertyFilename")) {
				validArguments.put("propertyFilename", property[1]);
			// Else, the argument provided is invalid or the same type of argument was provided twice, so throw an exception
			} else {
				throw new Exception("Provided invalid argument. Program exiting...");
			}
		}
	}
	
	/**
	 * Method to return valid arguments so they can be tested
	 * @return data (boolean[]) : boolean with which files were read
	 */	
	public static HashMap<String, String> getValidArguments() {
		return validArguments;
	}
	
	/**
	 * Method to reset valid arguments, used during unit tests
	 */
	public static void resetValidArguments() {
		validArguments = new HashMap<String, String>();
	}
}
