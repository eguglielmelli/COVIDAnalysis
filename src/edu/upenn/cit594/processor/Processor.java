package edu.upenn.cit594.processor;


import java.util.List;
import java.util.TreeMap;

import edu.upenn.cit594.datamanagement.Reader;

public class Processor {
	
    private List<Integer> availableActions;
    PopulationProcessor populationProcessor;
    CovidProcessor covidProcessor;
    PropertyProcessor propertyProcessor;

    public Processor(Reader reader) {
        boolean[] readFiles = reader.getReadFiles();
        if(readFiles[1]) {populationProcessor = new PopulationProcessor(reader.getData());}
        if(readFiles[2]) {covidProcessor = new CovidProcessor(reader.getData());}
        if(readFiles[3]) {propertyProcessor = new PropertyProcessor(reader.getData());}
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
