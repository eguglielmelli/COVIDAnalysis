package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.TreeMap;
import java.util.regex.Pattern;

import edu.upenn.cit594.util.Zip;

public class PopulationReader extends CSVReader {
	
	private String filename;
	
	protected PopulationReader(String input, TreeMap<String, Zip> data) throws Exception {
		this.filename = input;
		readPopulation(data);
	}
	
	private void readPopulation(TreeMap<String, Zip> data) throws Exception {
		
		try {
        	reader = new BufferedReader(new FileReader(filename));
        	String[] header = readRow();
        	int[] indexes = getIndexes(header);
        	
        	String[] contents;
        	while((contents = readRow()) != null) {
        		String zip = validateZip(contents[indexes[0]]);
        		int population = validatePopulation((contents[indexes[1]]));
        		
        		if(zip == null || population == -1) { continue;}
        		
        		if(!data.containsKey(zip)) {
        			data.put(zip, new Zip(zip));
        		}
        		
    			data.get(zip).setTotalPopulation(population);

        	}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error reading population file. Program exiting...");
		}
				
	}
	
	private int[] getIndexes(String[] header) throws Exception {
		int[] indexes = {-1,-1};
		
    	for(int i = 0; i < header.length; i++) {
    		if(header[i].equals("zip_code")) {indexes[0] = i;}
    		if(header[i].equals("population")) {indexes[1] = i;}
    	}
    	if(indexes[0] == -1 || indexes[1] == -1) {
    		throw new Exception();
    	}
    	
		return indexes;
	}
	
	private String validateZip(String zip) {
		String validatedZip = null;
		Pattern pattern = Pattern.compile("^[0-9]{5}$");
		
		if(pattern.matcher(zip).find()) {
			validatedZip = zip;
		}
		
		return validatedZip;
	}
	
	private int validatePopulation(String population) {
		int validatedPopulation = -1;
		Pattern pattern = Pattern.compile("^-?\\d+$");
		
		if(pattern.matcher(population).find()) {
			validatedPopulation = Integer.parseInt(population);
		}
		
		return validatedPopulation;
	}
}
