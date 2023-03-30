package edu.upenn.cit594.datamanagement;

import java.util.TreeMap;
import java.util.regex.Pattern;

import edu.upenn.cit594.util.Zip;

public class Reader {
	
	protected String[] arguments;
	protected String covidFilename = null;
	protected String propertyFilename = null;
	protected String populationFilename = null;
	protected String logFilename = null;
	protected TreeMap<String, Zip> data = new TreeMap<String, Zip>();
	
	public Reader(String[] inputs) throws Exception {
		setInputs(inputs);
		new PopulationReader(populationFilename, data);
		new PropertyReader(propertyFilename, data);
	}

	private void setInputs(String[] inputs) throws Exception {
		Pattern pattern = Pattern.compile("^--(?<name>.+?)=(?<value>.+)$");
		for(String input : inputs) {
			if(!pattern.matcher(input).find()) {
				throw new Exception("READER: Argument not of form '--name=value'");
			}
			String[] property = input.split("=");
			if(property[0].equals("--log") && logFilename == null) {
				logFilename = property[1];
			} else if (property[0].equals("--population") && populationFilename == null) {
				populationFilename = property[1];
			} else if (property[0].equals("--covid") && covidFilename == null) {
				covidFilename = property[1];
			} else if (property[0].equals("--properties") && propertyFilename == null) {
				propertyFilename = property[1];
			} else {
				throw new Exception("READER: Invalid argument type.");
			}
		}

	}
	

	public TreeMap<String, Zip> getData() {return data;}
}
