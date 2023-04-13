package edu.upenn.cit594.processor;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.util.Covid;
import edu.upenn.cit594.util.Zip;

public class Processor {
	
    private List<Integer> availableActions;
    PopulationProcessor populationProcessor;
    CovidProcessor covidProcessor;
    PropertyProcessor propertyProcessor;
    TreeMap<String,Zip> data = null;
    HashMap<String,TreeMap<String, HashMap<Integer,Double>>> memoTable = new HashMap<>();

    public Processor(Reader reader) {
        availableActions = new ArrayList<>();
        availableActions.add(0);
        availableActions.add(1);
        data = reader.getData();
        boolean[] readFiles = reader.getReadFiles();
        if(readFiles[1]) {
            populationProcessor = new PopulationProcessor(data);
            availableActions.add(2);
        }
        if(readFiles[2]) {covidProcessor = new CovidProcessor(data);}

        if(readFiles[3]) {
            propertyProcessor = new PropertyProcessor(data);
            availableActions.add(4);
            availableActions.add(5);
        }
        if(readFiles[1] && readFiles[2]) {
            if(!availableActions.contains(2)) {
                availableActions.add(2);
            }
            availableActions.add(3);
        }
        if(readFiles[1] && readFiles[3]) {
            availableActions.add(6);
        }
        if(readFiles[1] && readFiles[2] && readFiles[3]) {
            availableActions.add(7);
        }
        Collections.sort(availableActions);
    }


    public List<Integer> getAvailableActions() {return availableActions;}
    
    public int getTotalPopulation() {return populationProcessor.totalPopulation();}
    
    public TreeMap<Integer, Double> getVaccinationsPerCapita(String partialOrFull, String date) {
    	return covidProcessor.vaccinationsPerCapita(partialOrFull, date);
    }
    
    public int getAveragePropertyValue(String zipCode) {return propertyProcessor.averagePropertyValue(zipCode);}
    
    public int getAverageTotalLivableArea(String zipCode) {return propertyProcessor.averageTotalLivableArea(zipCode);}
    
    public int getTotalMarketValuePerCapita(String zipCode) {return propertyProcessor.totalMarketValuePerCapita(zipCode);}

    public String[] findClosestDates(String startDate,String endDate,String zipCode) {
        String[] dateArray = new String[2];
        Covid startCovid = data.get(zipCode).getCovidCases().getOrDefault(startDate, null);
        Covid endCovid = data.get(zipCode).getCovidCases().getOrDefault(endDate, null);
        LocalDate beginDate;
        LocalDate endingDate;
        try {
            beginDate = LocalDate.parse(startDate);
            endingDate = LocalDate.parse(endDate);
        }catch(DateTimeParseException e) {
            return null;
        }
        if (startCovid == null && endCovid != null) {
            while (startCovid == null) {
                beginDate = beginDate.plusDays(1);
                String newStartDate = beginDate.toString();
                startCovid = data.get(zipCode).getCovidCases().getOrDefault(newStartDate, null);
                if (beginDate.compareTo(endingDate) == 0) return null;
            }
            dateArray[0] = beginDate.toString();
            dateArray[1] = endDate;
            return dateArray;
        }else if (endCovid == null && startCovid != null) {
            while (endCovid == null) {
                endingDate = endingDate.minusDays(1);
                String newEndDate = endingDate.toString();
                endCovid = data.get(zipCode).getCovidCases().getOrDefault(newEndDate, null);
                if (endingDate.compareTo(beginDate) == 0) return null;
            }
            dateArray[0] = startDate;
            dateArray[1] = endingDate.toString();
            return dateArray;
        }else if (startCovid == null && endCovid == null) {
            while (startCovid == null || endCovid == null) {
                if (startCovid == null) {
                    beginDate = beginDate.plusDays(1);
                    String newStartDate = beginDate.toString();
                    Covid newStartCovid = data.get(zipCode).getCovidCases().getOrDefault(newStartDate, null);
                    if (newStartCovid != null) {
                        startCovid = newStartCovid;
                    }
                }
                if (endCovid == null) {
                    endingDate = endingDate.minusDays(1);
                    String newEndDate = endingDate.toString();
                    Covid newEndCovid = data.get(zipCode).getCovidCases().getOrDefault(newEndDate, null);
                    if (newEndCovid != null) {
                        endCovid = newEndCovid;
                    }
                }
                if (beginDate.compareTo(endingDate) == 0 || beginDate.compareTo(endingDate) > 0) return null;
            }
            dateArray[0] = beginDate.toString();
            dateArray[1] = endingDate.toString();
            return dateArray;
        }else {
            dateArray[0] = startDate;
            dateArray[1] = endDate;
            return dateArray;
        }
    }
    public TreeMap<String, HashMap<Integer,Double>> getVaccinationIncreaseForDate(String startDate, String endDate) {
        TreeMap<String,HashMap<Integer,Double>>marketValueVaxxIncrease = new TreeMap<>();
        if(startDate.compareTo(endDate) == 0 || startDate.compareTo(endDate) > 0 || startDate.compareTo("2022-11-07") >= 0 || endDate.compareTo("2021-03-25") <= 0) return marketValueVaxxIncrease;
        DecimalFormat rounder = new DecimalFormat("#.00");
        String key = startDate + "-" + endDate;
        if(memoTable.containsKey(key)) return memoTable.get(key);
        for(String zip : data.keySet()) {
            HashMap<Integer,Double> map = new HashMap<>();
            String[] array = findClosestDates(startDate,endDate,zip);
            if(array != null) {
                double increaseVaccinated = covidProcessor.vaccinationIncrease(array[0], array[1], zip);
                int marketValuePerCapita = propertyProcessor.totalMarketValuePerCapita(zip);
                if(marketValuePerCapita != 0 && increaseVaccinated > 0) {
                    map.put(marketValuePerCapita,Double.parseDouble(rounder.format(increaseVaccinated)));
                    marketValueVaxxIncrease.put(zip, map);
                }
            }
        }
        memoTable.put(key,marketValueVaxxIncrease);
        return marketValueVaxxIncrease;
    }
   
}
