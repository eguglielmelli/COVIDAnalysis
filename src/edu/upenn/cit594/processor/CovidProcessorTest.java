package edu.upenn.cit594.processor;

import edu.upenn.cit594.Main;
import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.util.Zip;
import org.junit.jupiter.api.Test;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class CovidProcessorTest {

    @Test
    void vaccinationsPerCapita() throws Exception {
        String[] arguments = {"--population=population.csv","--covid=covid_data.csv"};
	    Main.resetValidArguments();
		Main.setInputs(arguments);
        Reader reader = new Reader(Main.getValidArguments());
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
	    Main.resetValidArguments();
		Main.setInputs(arguments);
        Reader reader = new Reader(Main.getValidArguments());
        TreeMap<String, Zip> data = reader.getData();
        CovidProcessor cProcessor = new CovidProcessor(data);

        //this method does not have cases where the output will be null because it is null checked prior to calling
        //in the processor class, we will test very basic computations and some 0 tests

        //two valid dates, we round the number for simplicity of checking assertions
        double increaseInVaxx = cProcessor.vaccinationIncrease("2021-03-25","2021-05-31","19102");
        increaseInVaxx = Math.round(increaseInVaxx);
        assertTrue(increaseInVaxx == 173) ;

        //another valid non-zero case
        increaseInVaxx = cProcessor.vaccinationIncrease("2021-03-25","2022-05-01","19122");
        increaseInVaxx = Math.round(increaseInVaxx);
        assertTrue(increaseInVaxx == 891);

        //this is a case where there is no data for the date, but the cases are not null, so this method returns 0
        increaseInVaxx = cProcessor.vaccinationIncrease("2021-03-25","2022-10-31","19193");
        assertEquals(increaseInVaxx,0);
    }
}
