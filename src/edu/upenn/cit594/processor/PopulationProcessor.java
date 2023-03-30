package edu.upenn.cit594.processor;

import java.util.Map;
import java.util.TreeMap;

import edu.upenn.cit594.util.Zip;

public class PopulationProcessor {
	
	private TreeMap<String, Zip> data = null;


    public PopulationProcessor(TreeMap<String, Zip> data) {
    	this.data = data;
	}
    
    // Check memoization techniques (Module 13). Might need to refactor...
	protected int totalPopulation() {
        int sum = 0;
        for(Map.Entry<String, Zip> entry : data.entrySet()) {
            sum += entry.getValue().getTotalPopulation();
        }
        return sum;
    }
    
}
