package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.TreeMap;

import edu.upenn.cit594.util.Zip;

public class PropertyReader extends CSVReader {
	
	private String filename;
	
	PropertyReader(String input, TreeMap<String, Zip> data) throws Exception {
		this.filename = input;
		readProperty(data);
	}
	
	public void readProperty(TreeMap<String, Zip> data) throws Exception {
		
		try {
        	reader = new BufferedReader(new FileReader(filename));
        	String[] header = readRow();
        	int[] indexes = getIndexes(header);
        	
        	String[] contents;
        	while((contents = readRow()) != null) {
        		String zip = contents[indexes[0]];
        		float marketValue = Float.parseFloat(contents[indexes[1]]);
        		float totalLivableArea = Float.parseFloat(contents[indexes[2]]);
        		
        		if(!data.containsKey(zip)) {
        			data.put(zip, new Zip(zip));
        		}

    			data.get(zip).setMarketValue(marketValue);
    			data.get(zip).setTotalArea(totalLivableArea);

        	}
        	
		} catch (Exception e) {
			throw new Exception("READER: Error reading population file");
		}	
	}
	
	public int[] getIndexes(String[] header) throws Exception {
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
}
