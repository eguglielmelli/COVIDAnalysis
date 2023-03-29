package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class PopulationReader extends CSVReader {
	
	private String filename;
	private HashMap<String, Integer> populationData; 
	
	PopulationReader(String input) {
		this.filename = input;
	}
	
	public HashMap<String, Integer> getData() throws Exception {
		populationData = new HashMap<String, Integer>();
		
		try {
        	reader = new BufferedReader(new FileReader(filename));
        	String[] header = readRow();
        	String[] contents;
   
        	while((contents = readRow()) != null) {
        		if(header[0].equals("zip_code") && header[1].equals("population")) {
        			populationData.put(contents[0], Integer.parseInt(contents[1]));
        		} else if (header[1].equals("zip_code") && header[0].equals("population")){
        			populationData.put(contents[1], Integer.parseInt(contents[0]));
        		} else {
        			throw new Exception();
        		}
        	}
        	
		} catch (Exception e) {
			throw new Exception("READER: Error reading population file");
		}
				
		return populationData;
	}

}
