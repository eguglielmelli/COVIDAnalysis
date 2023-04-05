package edu.upenn.cit594.processor;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.upenn.cit594.util.Covid;
import edu.upenn.cit594.util.Zip;

public class CovidProcessor {
	
	private TreeMap<String, Zip> data = null;
	private HashMap<String,TreeMap<Integer,Double>> memoTable = new HashMap<>();
	
    public CovidProcessor(TreeMap<String, Zip> data) {
    	this.data = data;
	}


    // Memoization may need a little more work, will revisit this one
	public TreeMap<Integer,Double> vaccinationsPerCapita(String choice, String date) {
		String key = choice + "-" + date;
		if(memoTable.containsKey(key)) {
			return memoTable.get(key);
		}
		TreeMap<Integer, Double> vaccinations = new TreeMap<Integer, Double>();
		DecimalFormat rounder = new DecimalFormat("#.0000");
        for(Map.Entry<String, Zip> entry : data.entrySet()) {
        	int zip = Integer.parseInt(entry.getKey());
        	int population = entry.getValue().getTotalPopulation();
        	Covid cases = entry.getValue().getCovidCasesForDate(date);

        	if(population == -1 || cases == null) {continue;}

        	int vaccinated = 0;
        	if(choice.equals("full")) {vaccinated = cases.getFullyVaccinated();}
        	else if (choice.equals("partial")) {vaccinated = cases.getPartiallyVaccinated();}

        	if(vaccinated == 0) {continue;}

        	double perCapita = (double) vaccinated/population;
            vaccinations.put(zip, Double.parseDouble(rounder.format(perCapita)));
          }
		memoTable.put(key,vaccinations);
		return vaccinations;
    }

	public double vaccinationIncrease(String startDate,String endDate,String zipCode) {
		if(data.get(zipCode).getCovidCasesForDate(startDate) != null && data.get(zipCode).getCovidCasesForDate(endDate) != null) {
			double fullyVaccinatedStart = data.get(zipCode).getCovidCasesForDate(startDate).getFullyVaccinated();
			double fullyVaccinatedEnd = data.get(zipCode).getCovidCasesForDate(endDate).getFullyVaccinated();
			if(fullyVaccinatedStart == 0 || fullyVaccinatedEnd == 0) return 0;
			return ((fullyVaccinatedEnd-fullyVaccinatedStart)/fullyVaccinatedStart)*100;
		}
		return 0;
	}
	

}
