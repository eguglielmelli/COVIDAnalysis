package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import edu.upenn.cit594.util.Covid;
import edu.upenn.cit594.util.Zip;

public class CovidReader extends CSVReader {
	
	private String filename;
	
	protected CovidReader(String input, TreeMap<String, Zip> data) throws Exception {
		this.filename = input;
		readCovid(data);
	}
	
	private void readCovid(TreeMap<String, Zip> data) throws Exception {
		String[] fileEnding = filename.trim().split("\\.");

		// If file ends in txt, call the TXT method
		if(fileEnding[fileEnding.length-1].toLowerCase().equals("csv")) {
			readCsvCovid(data);
		// If file ends in json, call the JSON method
		} else if (fileEnding[fileEnding.length-1].toLowerCase().equals("json")) {
			 readJsonCovid(data);
	    // Else, generalize all error messages to a human readable error pinpointing where the issue is
		} else {
			throw new Exception("Unrecognized Covid file format. Program exiting...");
		}		
	}
	
	private void readJsonCovid(TreeMap<String, Zip> data) throws Exception {
		
		try {
			// Open the file for reading using JSON parser
			JSONArray jsonArray = (JSONArray) new JSONParser().parse(new FileReader(filename));
			
			// Loop through each object, extract relevant information to Java objects, and add it to tweet list
			for(Object obj : jsonArray) {
				JSONObject jo = (JSONObject) obj;
				if(!jo.containsKey("zip_code") || !jo.containsKey("etl_timestamp")) {continue;}
				String zip = validateZip(Long.toString((Long) jo.get("zip_code")));
				String date = validateDate((String) jo.get("etl_timestamp"));
				int partiallyVaccinated = 0;
				int fullyVaccinated = 0;
				
				if(jo.containsKey("partially_vaccinated")) {
					partiallyVaccinated = validateCount(Long.toString((Long) jo.get("partially_vaccinated")));
				}
				if(jo.containsKey("fully_vaccinated")) {
					fullyVaccinated = validateCount(Long.toString((Long) jo.get("fully_vaccinated")));
				}
				
        		if(zip == null || date == null) {continue;}
				
        		if(!data.containsKey(zip)) {
        			data.put(zip, new Zip(zip));
        		}
        		
        		HashMap<String, Covid> covidCases = data.get(zip).getCovidCases();
        		
        		covidCases.put(date, new Covid(date, partiallyVaccinated, fullyVaccinated));
			}
						
	    // Generalize all error messages to a human readable error pinpointing where the issue is
        } catch (Exception e) {
        	e.printStackTrace();
			throw new Exception("Error reading Covid Json file. Program exiting...");
		}
	}

	private void readCsvCovid(TreeMap<String, Zip> data) throws Exception {
		
		try {
        	reader = new BufferedReader(new FileReader(filename));
        	String[] header = readRow();
        	int[] indexes = getIndexes(header);
        	
        	String[] contents;
        	while((contents = readRow()) != null) {
        		String zip = validateZip(contents[indexes[0]]);
        		String date = validateDate(contents[indexes[1]]);
        		int partiallyVaccinated = validateCount(contents[indexes[2]]);
        		int fullyVaccinated = validateCount(contents[indexes[3]]);
        		
        		if(zip == null || date == null) { continue;}
        		
        		if(!data.containsKey(zip)) {
        			data.put(zip, new Zip(zip));
        		}
        		
        		HashMap<String, Covid> covidCases = data.get(zip).getCovidCases();
        		
        		covidCases.put(date, new Covid(date, partiallyVaccinated, fullyVaccinated));
        		
        	}
        	
		} catch (Exception e) {
			throw new Exception("Error reading Covid Csv file. Program exiting...");
		}	
	}
	
	private int[] getIndexes(String[] header) throws Exception {
		int[] indexes = {-1,-1, -1,-1};
		
    	for(int i = 0; i < header.length; i++) {
    		if(header[i].equals("zip_code")) {indexes[0] = i;}
    		if(header[i].equals("etl_timestamp")) {indexes[1] = i;}
    		if(header[i].equals("partially_vaccinated")) {indexes[2] = i;}
    		if(header[i].equals("fully_vaccinated")) {indexes[3] = i;}
    	}
    	if(indexes[0] == -1 || indexes[1] == -1 || indexes[2] == -1 || indexes[3] == -1) {
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
	
	private String validateDate(String date) throws ParseException {
		String validatedDate = null;
		Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$");
		
		if(pattern.matcher(date).find()) {
			String[] splitDate = date.split("\\s+", 2);
			validatedDate = splitDate[0];
		}
		
		return validatedDate;
	}
	
	private int validateCount(String count) {
		int validatedCount = 0;
		Pattern pattern = Pattern.compile("^-?\\d+$");
		
		if(pattern.matcher(count).find()) {
			validatedCount = Integer.parseInt(count);
		}

		return validatedCount;
	}
	

}
