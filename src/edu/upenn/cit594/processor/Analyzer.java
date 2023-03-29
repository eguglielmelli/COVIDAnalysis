package edu.upenn.cit594.processor;


import edu.upenn.cit594.datamanagement.Reader;

public class Analyzer {
    private Reader reader;

    public Analyzer(Reader reader) {
        this.reader = reader;
    }


    public int totalPopulation() throws Exception {
        int sum = 0;
        for(int values : reader.getPopulation().getData().values()) {
            sum += values;
        }
        return sum;
    }
    public void vaccinationsPerCapita(String partialOrFull,String date) {

    }
    public void averagePropertyValue(String ZIP) throws Exception {

    }
    public void averageTotalLivableArea(String ZIP) {

    }
    public void totalMarketValuePerCapita(String ZIP) {

    }


}
