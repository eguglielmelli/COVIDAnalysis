package edu.upenn.cit594.processor;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import edu.upenn.cit594.util.Zip;

public class PropertyProcessor {
	
	private TreeMap<String, Zip> data = null;
	private HashMap<String,Integer> memoTablePropVal = new HashMap<>();
	private HashMap<String,Integer> memoTableLivableAreaVal = new HashMap<>();
	private HashMap<String,Integer> marketValPerCap = new HashMap<>();
	
    public PropertyProcessor(TreeMap<String, Zip> data) {
    	this.data = data;
	}

	private int calculateAvgVal(String zipCode,PropertyComparator comparator) {
		if(!data.containsKey(zipCode)) return 0;
		Zip instance = data.get(zipCode);
		float sum = 0;
		int count = 0;
		List<Float> list = comparator.getList(instance);
		if (!list.isEmpty()) {
			for (Float number : list) {
				sum += number;
				count++;
			}
			return (int) sum / count;
		}
		return 0;
	}

    // Strategy method implemented
	public int averagePropertyValue(String zipCode) {
		if(memoTablePropVal.containsKey(zipCode)) return memoTablePropVal.get(zipCode);
		else {
			MarketValueComparator comparator = new MarketValueComparator();
			int avgPropVal = calculateAvgVal(zipCode,comparator);
			memoTablePropVal.put(zipCode,avgPropVal);
			return avgPropVal;
		}
    }
	
    // Strategy pattern should be correct, will visit again if we need to make changes
    public int averageTotalLivableArea(String zipCode) {
		if(memoTableLivableAreaVal.containsKey(zipCode)) return memoTableLivableAreaVal.get(zipCode);
		else {
			AvgLivableAreaComparator comparator = new AvgLivableAreaComparator();
			int avgPropVal = calculateAvgVal(zipCode,comparator);
			memoTableLivableAreaVal.put(zipCode,avgPropVal);
			return avgPropVal;
		}
    }

	private int MarketValuePerCapitaCalculator(String zipCode) {
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

    // memoization should be done, will redo if need be
    public int totalMarketValuePerCapita(String zipCode) {
		if(!data.containsKey(zipCode)) return 0;
		if(marketValPerCap.containsKey(zipCode)) return marketValPerCap.get(zipCode);
		else{
			int mvPerCapita = MarketValuePerCapitaCalculator(zipCode);
			marketValPerCap.put(zipCode,mvPerCapita);
			return mvPerCapita;
		}
    }

}
