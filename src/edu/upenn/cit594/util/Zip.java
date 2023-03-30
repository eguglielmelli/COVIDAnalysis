package edu.upenn.cit594.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Zip {
	
	private String zipCode;
    private List<Float> marketValue = new LinkedList<Float>();
    private List<Float> totalArea = new LinkedList<Float>();
    private int totalPopulation = -1;
    private HashMap<String, Covid> covidCases = new HashMap<String, Covid>();

    public Zip(String zipCode) {
        this.zipCode = zipCode;
    }
    
    public String getZipCode() {
    	return zipCode;
    }

    public List<Float> getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(float value) {
        marketValue.add(value);
    }

    public List<Float> getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(float area) {
        totalArea.add(area);
    }

    public int getTotalPopulation() {
        return totalPopulation;
    }

    public void setTotalPopulation(int totalPopulation) {
        this.totalPopulation = totalPopulation;
    }
    
    public HashMap<String, Covid> getCovidCases() {
    	return covidCases;
    }
    
    public Covid getCovidCasesForDate(String date) {
    	return covidCases.get(date);
    }
    
}
