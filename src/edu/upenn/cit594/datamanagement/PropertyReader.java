package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.TreeMap;
import java.util.regex.Pattern;

import edu.upenn.cit594.util.Zip;

public class PropertyReader extends CSVReader {
	
	private String filename;
	
	protected PropertyReader(String input, TreeMap<String, Zip> data) throws Exception {
		this.filename = input;
		readProperty(data);
	}
	
	private void readProperty(TreeMap<String, Zip> data) throws Exception {
		
		try {
        	reader = new BufferedReader(new FileReader(filename));
        	String[] header = readRow();
        	int[] indexes = getIndexes(header);
        	
        	String[] contents;
        	while((contents = readRow()) != null) {
        		String zip = validateZip(contents[indexes[0]]);
        		float marketValue = validateNumber(contents[indexes[1]]);
        		float totalLivableArea = validateNumber(contents[indexes[2]]);
        		
        		if(zip == null) { continue;}
        		
        		if(!data.containsKey(zip)) {
        			data.put(zip, new Zip(zip));
        			data.get(zip).setMarketValue(0);
        			data.get(zip).setTotalArea(0);
        			
        		}
        		if(marketValue != -1) {
        			float currentMarketValue = data.get(zip).getMarketValue();
        			data.get(zip).setMarketValue(currentMarketValue + marketValue);
        		}
        		if(totalLivableArea != -1) {
        			float currentTotalLivableArea = data.get(zip).getTotalArea();
        			data.get(zip).setTotalArea(currentTotalLivableArea + totalLivableArea);
        		}
        	}
        	
		} catch (Exception e) {
			throw new Exception("Error reading property file. Program exiting...");
		}	
	}
	
	private int[] getIndexes(String[] header) throws Exception {
		int[] indexes = {-1,-1,-1};
		
    	for(int i = 0; i < header.length; i++) {
    		if(header[i].equals("zip_code")) {indexes[0] = i;}
    		if(header[i].equals("market_value")) {indexes[1] = i;}
    		if(header[i].equals("total_livable_area")) {indexes[2] = i;}
    	}
    	if(indexes[0] == -1 || indexes[1] == -1 || indexes[2] == -1) {
    		throw new Exception();
    	}
    	
		return indexes;
	}
	
	private String validateZip(String zip) {
		String validatedZip = null;
		Pattern pattern = Pattern.compile("^\\d{5}");
		
		if(zip.length() >= 5 && pattern.matcher(zip).find()) {
			validatedZip = zip.substring(0, 5);
		}
		return validatedZip;
	}
	
	private float validateNumber(String number) {
		float validatedNumber = -1;
		Pattern pattern = Pattern.compile("^-?\\d*\\.?\\d+$");
		
		if(pattern.matcher(number).find()) {
			validatedNumber = Float.parseFloat(number);
		}
		return validatedNumber;
	}
}
