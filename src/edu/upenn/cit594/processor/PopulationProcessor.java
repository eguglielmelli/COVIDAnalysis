package edu.upenn.cit594.processor;

import java.util.*;

import edu.upenn.cit594.util.Zip;

public class PopulationProcessor {
	
	private TreeMap<String, Zip> data = null;
    private HashMap<String,Integer> memoTable = new HashMap<>();


    public PopulationProcessor(TreeMap<String, Zip> data) {
    	this.data = data;
	}

    public int doCalculation() {
        int sum = 0;
        for(String code : data.keySet()) {
            Zip zip = data.get(code);
            sum += zip.getTotalPopulation();
        }
        return sum;
    }

    // memoization should be done correctly here, feel free to make changes if needed.
	protected int totalPopulation() {
        String code = data.keySet().toString();
        if(memoTable.containsKey(code)) return memoTable.get(code);
        else {
            int totalPopulation = doCalculation();
            memoTable.put(code,totalPopulation);
            return totalPopulation;
        }
    }
}
