package edu.upenn.cit594.util;

import java.util.HashMap;

public class Zip {
	
	private String zipCode;
    private float marketValue = -1;
    private float totalArea = -1;
    private int totalPopulation = -1;
    private HashMap<String, Covid> covidCases = new HashMap<String, Covid>();

    public Zip(String zipCode) {
        this.zipCode = zipCode;
    }
    
    public String getZipCode() {
    	return zipCode;
    }

    public float getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(float marketValue) {
        this.marketValue = marketValue;
    }

    public float getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(float totalArea) {
        this.totalArea = totalArea;
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
    
}
