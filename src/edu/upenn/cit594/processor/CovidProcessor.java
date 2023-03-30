package edu.upenn.cit594.processor;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;

import edu.upenn.cit594.util.Covid;
import edu.upenn.cit594.util.Zip;

public class CovidProcessor {
	
	private TreeMap<String, Zip> data = null;
	
    public CovidProcessor(TreeMap<String, Zip> data) {
    	this.data = data;
	}

    // Check memoization techniques (Module 13). Might need to refactor...
	public TreeMap<Integer, Double> vaccinationsPerCapita(String choice, String date) {
		TreeMap<Integer, Double> vaccinations = new TreeMap<Integer, Double>();
		DecimalFormat rounder = new DecimalFormat("#.0000");
		
        for(Map.Entry<String, Zip> entry : data.entrySet()) {
        	int zip = Integer.parseInt(entry.getKey());
        	int population = entry.getValue().getTotalPopulation();
        	Covid cases = entry.getValue().getCovidCasesForDate(date);
        	
        	if(population == -1 || cases == null) {continue;}
        		
        	int totalVaccinated = 0;
        	if(choice.equals("full")) {totalVaccinated = cases.getFullyVaccinated();}
        	else if (choice.equals("partial")) {totalVaccinated = cases.getPartiallyVaccinated();}
        	        	
        	if(totalVaccinated == 0) {continue;}
        	
        	double perCapita = (double) totalVaccinated/population;
            vaccinations.put(zip, Double.parseDouble(rounder.format(perCapita)));
          }
        
		return vaccinations;
    }
	

}
