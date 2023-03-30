package edu.upenn.cit594.processor;

import java.util.TreeMap;

import edu.upenn.cit594.util.Zip;

public class PropertyProcessor {
	
	private TreeMap<String, Zip> data = null;
	
    public PropertyProcessor(TreeMap<String, Zip> data) {
    	this.data = data;
	}
    
    // REFACTOR TO IMPLEMENT STRATEGY PATTERN  (MODULE 11)
	public int averagePropertyValue(String zipCode) {
		if(!data.containsKey(zipCode)) {return 0;}
		Zip instance = data.get(zipCode);
		float sum = 0;
		int count = 0;
		if(!instance.getMarketValue().isEmpty()) {
			for(Float number : instance.getMarketValue()) {
				sum += number;
				count++;
			}
			return (int) sum/count;
		}
		return 0;
    }
	
    // REFACTOR TO IMPLEMENT STRATEGY PATTERN  (MODULE 11)
    public int averageTotalLivableArea(String zipCode) {
		if(!data.containsKey(zipCode)) {return 0;}
		Zip instance = data.get(zipCode);
		float sum = 0;
		int count = 0;
		if(!instance.getMarketValue().isEmpty()) {
			for(Float number : instance.getTotalArea()) {
				sum += number;
				count++;
			}
			return (int) sum/count;
		}
		return 0;
    }
    
    // Check memoization techniques (Module 13). Might need to refactor...
    public int totalMarketValuePerCapita(String zipCode) {
		if(!data.containsKey(zipCode)) {return 0;}
		Zip instance = data.get(zipCode);
		float sum = 0;
		int count = instance.getTotalPopulation();
		if(!instance.getMarketValue().isEmpty()) {
			for(Float number : instance.getTotalArea()) {
				sum += number;
			}
			return (int) sum/count;
		}
		return 0;
    }

}
