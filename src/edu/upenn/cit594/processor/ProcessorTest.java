package edu.upenn.cit594.processor;
import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.util.Zip;
import org.junit.jupiter.api.Test;
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
        CovidProcessor covidProcessor = new CovidProcessor(data);

        //test case with two invalid dates, we will call closestdates, manually compute increase, and then check map too
        //after running closest dates, the dates should be 2021-03-25, and 2022-11-07
        String startDate = "2010-03-25";
        String endDate = "2023-05-01";
        String zip = "19102";
        String[] array = processor.findClosestDates(startDate,endDate,zip);
        assertEquals("2021-03-25",array[0]);
        assertEquals("2022-11-07",array[1]);
        double fullvaxxStart = data.get(zip).getCovidCasesForDate(array[0]).getFullyVaccinated();
        double fullvaxEnd = data.get(zip).getCovidCasesForDate(array[1]).getFullyVaccinated();
        double result = ((fullvaxEnd-fullvaxxStart)/fullvaxxStart)*100;
        double increase = covidProcessor.vaccinationIncrease(array[0],array[1],zip);
        TreeMap<String,HashMap<Integer,Double>> map = processor.getVaccinationIncreaseForDate(array[0],array[1]);
        int mvPC = processor.getTotalMarketValuePerCapita(zip);
        double actualVaxIncrease = map.get(zip).get(mvPC);
        assertEquals(increase,result,actualVaxIncrease);

        //valid case where the two dates have no underlying data in between them map is empty
        startDate = "2022-05-24";
        endDate = "2022-05-29";
        map = processor.getVaccinationIncreaseForDate(startDate,endDate);
        assertTrue(map.isEmpty());

        //another valid test case to make sure computation is correct
        startDate = "2021-02-20";
        endDate = "2022-07-01";
        zip = "19115";
        map = processor.getVaccinationIncreaseForDate(startDate,endDate);
        String[] dates = processor.findClosestDates(startDate,endDate,"19115");
        double vaxxIncrease = covidProcessor.vaccinationIncrease(dates[0],dates[1],zip);
        int mvpc = processor.getTotalMarketValuePerCapita(zip);
        result = map.get(zip).get(mvpc);
        vaxxIncrease = Math.round(vaxxIncrease);
        result = Math.round(result);
        assertEquals(result,vaxxIncrease);

        //test simple case where start date and end date are same
        map = processor.getVaccinationIncreaseForDate("2021-03-25","2021-03-25");
        assertTrue(map.isEmpty());

        //test simple case where end date before start date
        map = processor.getVaccinationIncreaseForDate("2022-05-01","2021-03-25");
        assertTrue(map.isEmpty());

        //test simple case where both dates are out of range
        map = processor.getVaccinationIncreaseForDate("2023-01-01","2024-01-01");
        assertTrue(map.isEmpty());

        //test simple case with invalid date ie 2021-02-30
        map = processor.getVaccinationIncreaseForDate("2022-02-40","2022-03-25");
        assertTrue(map.isEmpty());

    }
    @Test
    void findClosestDates() throws Exception {
        String[] arguments = {"--population=population.csv",
                "--covid=covid_data.csv", "--properties=downsampled_properties.csv"};
        Reader reader = new Reader(arguments);
        Processor processor = new Processor(reader);
        TreeMap<String, Zip> data = reader.getData();
        CovidProcessor covidProcessor = new CovidProcessor(data);
        //test case with zip 19147, start of data for this zip is 3/25/2021, and date closest to end date is 2/11/2022
        //case where both start and end date are null, so we need to adjust both pointers
        String startDate = "2021-02-25";
        String endDate = "2022-02-13";
        String zipCode = "19147";
        String[] array = processor.findClosestDates(startDate, endDate, zipCode);
        assertEquals("2021-03-25",array[0]);
        assertEquals("2022-02-11",array[1]);

        //case where start date is null and end date is not null, start date should be adjusted to 3/25/2021
        startDate = "2020-01-10";
        endDate = "2021-03-27";
        zipCode = "19147";
        array = processor.findClosestDates(startDate, endDate, zipCode);
        assertEquals("2021-03-25",array[0]);
        assertEquals("2021-03-27",array[1]);

        //case where end date is null and needs to be moved up until valid, end date should be adjusted to 11/07/2022
        startDate = "2022-05-01";
        endDate = "2023-11-12";
        zipCode = "19147";
        array = processor.findClosestDates(startDate, endDate, zipCode);
        assertEquals("2022-05-01",array[0]);
        assertEquals("2022-11-07",array[1]);

        //both start and end are null, and difference is years (20 year span), start date should be 03/25/2021, end date 11/07/2022
        //this includes the entire data set
        startDate = "2010-01-10";
        endDate = "2030-12-10";
        zipCode = "19147";
        array = processor.findClosestDates(startDate, endDate, zipCode);
        assertEquals("2021-03-25",array[0]);
        assertEquals("2022-11-07",array[1]);

        //check with a zipcode that has no full vaccinations but still appears with dates in the file
        startDate = "2021-03-25";
        endDate = "2021-11-05";
        zipCode = "19108";
        array = processor.findClosestDates(startDate, endDate, zipCode);
        assertEquals("2021-03-25",array[0]);
        assertEquals("2021-11-05",array[1]);

        //check with a completely null zipcode for these dates
        startDate = "2021-03-25";
        endDate = "2022-05-01";
        zipCode = "19199";
        array = processor.findClosestDates(startDate,endDate,zipCode);
        assertEquals(array,null);

        //check with a regular zip code that has data throughout the dates
        startDate = "2021-02-25";
        endDate = "2022-11-07";
        zipCode = "19102";
        array = processor.findClosestDates(startDate,endDate,zipCode);
        assertEquals("2021-03-25",array[0]);
        assertEquals("2022-11-07",array[1]);

        //test case where next closest date for each one ends up being the same date (2022-11-07) so array is null
        startDate = "2022-11-01";
        endDate = "2022-11-10";
        zipCode = "19102";
        array = processor.findClosestDates(startDate,endDate,zipCode);
        assertEquals(array,null);
    }
}