package edu.upenn.cit594.processor;


import java.text.DecimalFormat;
import java.util.*;

import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.util.Zip;

public class Processor {
	
    private List<Integer> availableActions;
    PopulationProcessor populationProcessor;
    CovidProcessor covidProcessor;
    PropertyProcessor propertyProcessor;
    TreeMap<String,Zip> data = null;
    HashMap<String,TreeMap<String, HashMap<Integer,Double>>> memoTable = new HashMap<>();

    public Processor(Reader reader) throws Exception {
        availableActions = new ArrayList<>();
        availableActions.add(0);
        availableActions.add(1);
        data = reader.getData();
        boolean[] readFiles = reader.getReadFiles();
        if(readFiles[1]) {
            populationProcessor = new PopulationProcessor(data);
            availableActions.add(2);
        }
        if(readFiles[2]) {covidProcessor = new CovidProcessor(data);}

        if(readFiles[3]) {
            propertyProcessor = new PropertyProcessor(data);
            availableActions.add(4);
            availableActions.add(5);
        }
        if(readFiles[1] && readFiles[2]) {
            if(!availableActions.contains(2)) {
                availableActions.add(2);
            }
            availableActions.add(3);
        }
        if(readFiles[1] && readFiles[3]) {
            availableActions.add(6);
        }
        if(readFiles[1] && readFiles[2] && readFiles[3]) {
            availableActions.add(7);
        }
        Collections.sort(availableActions);
    }


    public List<Integer> getAvailableActions() {return availableActions;}
    
    public int getTotalPopulation() {return populationProcessor.totalPopulation();}
    
    public TreeMap<Integer, Double> getVaccinationsPerCapita(String partialOrFull, String date) {
    	return covidProcessor.vaccinationsPerCapita(partialOrFull, date);
    }
    
    public int getAveragePropertyValue(String zipCode) {return propertyProcessor.averagePropertyValue(zipCode);}
    
    public int getAverageTotalLivableArea(String zipCode) {return propertyProcessor.averageTotalLivableArea(zipCode);}
    
    public int getTotalMarketValuePerCapita(String zipCode) {return propertyProcessor.totalMarketValuePerCapita(zipCode);}

    public TreeMap<String, HashMap<Integer,Double>> getVaccinationIncreaseForDate(String startDate, String endDate) {
        DecimalFormat rounder = new DecimalFormat("#.00");
        String key = startDate + "-" + endDate;
        if(memoTable.containsKey(key)) return memoTable.get(key);
        TreeMap<String,HashMap<Integer,Double>>marketValueVaxxIncrease = new TreeMap<>();
        for(String zip : data.keySet()) {
            HashMap<Integer,Double> map = new HashMap<>();
            if(data.get(zip).getCovidCases() != null) {
                double increaseVaccinated = covidProcessor.vaccinationIncrease(startDate, endDate, zip);
                int marketValuePerCapita = propertyProcessor.totalMarketValuePerCapita(zip);
                if(marketValuePerCapita != 0 && increaseVaccinated > 0) {
                    map.put(marketValuePerCapita,Double.parseDouble(rounder.format(increaseVaccinated)));
                    marketValueVaxxIncrease.put(zip, map);
                }
            }
        }
        memoTable.put(key,marketValueVaxxIncrease);
        return marketValueVaxxIncrease;
    }
   
}
