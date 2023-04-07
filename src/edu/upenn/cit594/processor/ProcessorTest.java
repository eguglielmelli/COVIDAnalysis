package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.util.Zip;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class ProcessorTest {

    @Test
    void getAvailableActions() throws Exception {
        //test case where we have population and property files only
        //this gives the user access to functions 0,1,2,4,5,6
        String[] arguments = {"--population=population.csv","--properties=downsampled_properties.csv"};
        Reader reader = new Reader(arguments);
        Processor processor = new Processor(reader);
        List<Integer> list = processor.getAvailableActions();
        assertTrue(list.size() == 6);


        //this is the case where all files are there, so the user should have access to the options
        //0,1,2,3,4,5,6,7
        String[] arguments1 = {"--population=population.csv",
                "--covid=covid_data.csv","--properties=downsampled_properties.csv"};
        Reader reader1 = new Reader(arguments1);
        Processor processor1 = new Processor(reader1);
        List<Integer> list1 = processor1.getAvailableActions();
        assertTrue(list1.size() == 8);

        //Case where there are no valid files, the list should still contain 0 and 1,
        //as these are always in there by default
        String[] arguments2 = {};
        Reader reader2 = new Reader(arguments2);
        Processor processor2 = new Processor(reader2);
        List<Integer> list2 = processor2.getAvailableActions();
        assertTrue(list2.size() == 2);

        //only covid file, should be 0 and 1 because covid computations require total population
        String[] arguments3 = {"--covid=covid_data.csv"};
        Reader reader3 = new Reader(arguments3);
        Processor processor3 = new Processor(reader3);
        List<Integer> list3 = processor3.getAvailableActions();
        assertTrue(list3.size() == 2);

    }

    @Test
    void getVaccinationIncreaseForDate() throws Exception {
        String[] arguments = {"--population=population.csv",
                "--covid=covid_data.csv","--properties=downsampled_properties.csv"};
        Reader reader = new Reader(arguments);
        Processor processor = new Processor(reader);
        TreeMap<String,Zip> data = reader.getData();


        //test case with two valid dates
        TreeMap<String, HashMap<Integer,Double>> map = processor.getVaccinationIncreaseForDate("2021-03-25","2021-05-01");
        double firstVax = data.get("19102").getCovidCases().get("2021-03-25").getFullyVaccinated();
        double secondVax = data.get("19102").getCovidCases().get("2021-05-01").getFullyVaccinated();
        double increase = ((secondVax-firstVax)/firstVax)*100;
        DecimalFormat rounder = new DecimalFormat("#.00");
        increase = Double.parseDouble(rounder.format(increase));
        int marketValPerCap = processor.getTotalMarketValuePerCapita("19102");
        assertTrue(map.get("19102").get(marketValPerCap) == increase);


        //test case with one valid date and one not, this should return an empty map because the data cannot be computed
        map = processor.getVaccinationIncreaseForDate("2021-03-25","2024-05-01");
        assertTrue(map.isEmpty());

        //test case with two valid dates (within our range) but one doesn't have data, so map should be empty
        map = processor.getVaccinationIncreaseForDate("2021-03-25","2021-05-19");
        assertTrue(map.isEmpty());
    }
}