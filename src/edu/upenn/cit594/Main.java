package edu.upenn.cit594;

import edu.upenn.cit594.datamanagement.Reader;

public class Main {
	
	public static void main(String[] args) {
		
		// FOR TESTING ONLY :)
		String[] arguments = {"--population=population.csv", "--log=events.log", 
				"--covid=covid-data.json", "--properties=downsampled_properties.csv"};
		try {
			Reader reader = new Reader(arguments);
			reader.getPopulation().getData();
			reader.getProperty().getData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
