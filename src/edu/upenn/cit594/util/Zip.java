package edu.upenn.cit594.util;

public class Zip {

    private String covidDate;
    private float marketValue;
    private float totalArea;
    private float totalPopulation;

    public Zip(String covidDate,float marketValue,float totalArea,float totalPopulation) {
        this.covidDate = covidDate;
        this.marketValue = marketValue;
        this.totalArea = totalArea;
        this.totalPopulation = totalPopulation;
    }

    public String getCovidDate() {
        return covidDate;
    }

    public void setCovidDate(String covidDate) {
        this.covidDate = covidDate;
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

    public float getTotalPopulation() {
        return totalPopulation;
    }

    public void setTotalPopulation(float totalPopulation) {
        this.totalPopulation = totalPopulation;
    }
}
