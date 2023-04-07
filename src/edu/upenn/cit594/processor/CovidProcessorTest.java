package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.CovidReader;
import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.util.Zip;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class CovidProcessorTest {

    @Test
    void vaccinationsPerCapita() throws Exception {
        String[] arguments = {"--population=population.csv","--covid=covid_data.csv"};
        Reader reader = new Reader(arguments);
        TreeMap<String, Zip> data = reader.getData();
        CovidProcessor cProcessor = new CovidProcessor(data);

        //simple partial test case to make sure that the vaccinations per capita are being computed correctly
        TreeMap<Integer,Double> map = cProcessor.vaccinationsPerCapita("partial","2021-03-25");
        assertTrue(map.get(19102).equals(.1566));
        assertTrue(map.get(19103).equals(.1687));

        //test with invalid date (out of our range)
        map = cProcessor.vaccinationsPerCapita("full","2023-04-20");
        assertTrue(map.isEmpty());

        //test with valid date (within the range of the file) but no underlying data for that date
        map = cProcessor.vaccinationsPerCapita("full","2021-05-28");
        assertTrue(map.isEmpty());

        map = cProcessor.vaccinationsPerCapita("full","2021-03-25");
        assertTrue(map.get(19102).equals(.1513));
        assertTrue(map.get(19103).equals(.1983));
        //no underlying data for zip 19108, so there should not be any key for it
        assertTrue(!map.containsKey(19108));

    }

    @Test
    void vaccinationIncrease() throws Exception {
        String[] arguments = {"--population=population.csv","--covid=covid_data.csv"};
        Reader reader = new Reader(arguments);
        TreeMap<String, Zip> data = reader.getData();
        CovidProcessor cProcessor = new CovidProcessor(data);

        //two valid dates, we round the number for simplicity of checking assertions
        double increaseInVaxx = cProcessor.vaccinationIncrease("2021-03-25","2021-05-31","19102");
        increaseInVaxx = Math.round(increaseInVaxx);
        assertTrue(increaseInVaxx == 173) ;

        //one valid date, one invalid date, should return 0
        increaseInVaxx = cProcessor.vaccinationIncrease("2021-03-25","2023-04-30","19102");
        assertTrue(increaseInVaxx == 0);

        //two invalid dates (out of range), should return 0 also
        increaseInVaxx = cProcessor.vaccinationIncrease("2023-04-40","2024-03-20","19101");
        assertTrue(increaseInVaxx == 0);

        //two valid dates but flipped (ie later date comes before the earlier date), should return 0 because increase would be negative
        increaseInVaxx = cProcessor.vaccinationIncrease("2021-05-31","2021-03-25","19101");
        assertTrue(increaseInVaxx == 0);

        //two valid dates, but no underlying data for the zip code, should be 0
        increaseInVaxx = cProcessor.vaccinationIncrease("2021-03-25","2021-05-27","19155");
        assertTrue(increaseInVaxx == 0) ;

    }
}