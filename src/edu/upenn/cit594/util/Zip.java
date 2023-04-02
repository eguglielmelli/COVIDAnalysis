package edu.upenn.cit594.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a given ZIP code. It stores information about the market value of 
 * each property, the total livable area of each property, the total population, and the
 * Covid cases in a given day
 *
 */
public class Zip {
	
	// Ptivate attributes of this class
	private String zipCode;
    private List<Float> marketValue = new LinkedList<Float>();
    private List<Float> totalArea = new LinkedList<Float>();
    private int totalPopulation = -1;
    private HashMap<String, Covid> covidCases = new HashMap<String, Covid>();

    // Constructor method. Only the unchanged ZIP code is required, other attributes can be set later
    public Zip(String zipCode) {
        this.zipCode = zipCode;
    }
    
    // Getter method: once set, Zip code cannot be changed
    public String getZipCode() {
    	return zipCode;
    }

    // Getter method for the list with the market value of all properties in the Zip code
    public List<Float> getMarketValue() {
        return marketValue;
    }

    // Setter method to add a given value to the list of properties market value
    public void setMarketValue(float value) {
        marketValue.add(value);
    }
    
    // Getter method for the list with the total livable area of all properties in the Zip code
    public List<Float> getTotalArea() {
        return totalArea;
    }

    // Setter method to add a given value to the list of properties total area
    public void setTotalArea(float area) {
        totalArea.add(area);
    }

    // Getter method for the total population of the Zip code
    public int getTotalPopulation() {
        return totalPopulation;
    }

    // Setter method for the total population of the Zip code
    public void setTotalPopulation(int totalPopulation) {
        this.totalPopulation = totalPopulation;
    }
    
    // Getter method for the Map with Dates (in a String format) and Covid class for that date
    public HashMap<String, Covid> getCovidCases() {
    	return covidCases;
    }
    
    // Getter method to get the Covid class for a specific date
    public Covid getCovidCasesForDate(String date) {
    	return covidCases.get(date);
    }
    
}
