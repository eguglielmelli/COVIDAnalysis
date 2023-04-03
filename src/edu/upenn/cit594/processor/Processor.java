package edu.upenn.cit594.processor;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.util.Zip;

public class Processor {
	
    private List<Integer> availableActions;
    PopulationProcessor populationProcessor;
    CovidProcessor covidProcessor;
    PropertyProcessor propertyProcessor;

    public Processor(Reader reader) throws Exception {
        availableActions = new ArrayList<>();
        availableActions.add(0);
        availableActions.add(1);
        TreeMap<String, Zip> data = reader.getData();
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
   
}
