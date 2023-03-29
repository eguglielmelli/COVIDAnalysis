package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PropertyReader extends CSVReader {
	
	private String filename;
	private HashMap<String, List<Double>> propertyData; 
	
	PropertyReader(String input) {
		this.filename = input;
	}
	
	public HashMap<String, List<Double>> getData() throws Exception {
		propertyData = new HashMap<String, List<Double>>();
		int zipIndex = -1;
		int valueIndex = -1;
		int areaIndex = -1;
		
		try {
        	reader = new BufferedReader(new FileReader(filename));
        	String[] header = readRow();
        	String[] contents;
        	
        	for(int i = 0; i < header.length; i++) {
        		if(header[i].equals("zip_code")) {zipIndex = i;}
        		if(header[i].equals("market_value")) {valueIndex = i;}
        		if(header[i].equals("total_livable_area")) {areaIndex = i;}
        	}
        	if(zipIndex == -1 || valueIndex == -1 || areaIndex == -1) {
        		throw new Exception();
        	}
   
        	while((contents = readRow()) != null) {
        		for(String a : contents) {
        			System.out.println(a);
        		}
        		List<Double> parsedValues = new LinkedList<>();
        		parsedValues.add(Double.parseDouble(contents[valueIndex]));
        		parsedValues.add(Double.parseDouble(contents[areaIndex]));
        		propertyData.put(contents[zipIndex], parsedValues);

        	}
        	
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("READER: Error reading population file");
		}
				
		return propertyData;
	}

}