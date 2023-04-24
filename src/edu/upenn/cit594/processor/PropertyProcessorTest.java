package edu.upenn.cit594.processor;

import edu.upenn.cit594.Main;
import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.util.Zip;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.TreeMap;
import static org.junit.jupiter.api.Assertions.*;

class PropertyProcessorTest {

    @Test
    void averagePropertyValue() throws Exception {
        String[] arguments = {"--population=population.csv","--properties=downsampled_properties.csv"};
	    Main.resetValidArguments();
		Main.setInputs(arguments);
        Reader reader = new Reader(Main.getValidArguments());
        TreeMap<String, Zip> map = reader.getData();
        PropertyProcessor propertyProcessor = new PropertyProcessor(map);

        //quick check with a valid zipcode
        int avgPropVal = propertyProcessor.averagePropertyValue("19102");
        assertTrue(avgPropVal == 6219409);

        //check with invalid zip code, should return 0
        avgPropVal = propertyProcessor.averagePropertyValue("19999");
        assertTrue(avgPropVal == 0);

        //check with valid zip code, no underlying data
        avgPropVal = propertyProcessor.averagePropertyValue("19108");
        assertTrue(avgPropVal == 0);

    }

    @Test
    void averageTotalLivableArea() throws Exception {
        String[] arguments = {"--population=population.csv","--properties=downsampled_properties.csv"};
	    Main.resetValidArguments();
		Main.setInputs(arguments);
        Reader reader = new Reader(Main.getValidArguments());
        TreeMap<String, Zip> map = reader.getData();
        PropertyProcessor propertyProcessor = new PropertyProcessor(map);

        //valid test case
        int avgTotalLiveableArea = propertyProcessor.averageTotalLivableArea("19102");
        assertTrue(avgTotalLiveableArea == 29809);

        //invalid test case with zipcode which does not exist in the data
        avgTotalLiveableArea = propertyProcessor.averageTotalLivableArea("19999");
        assertTrue(avgTotalLiveableArea == 0);

        //invalid test case with zip code which does exist but no data associated with it
        avgTotalLiveableArea = propertyProcessor.averageTotalLivableArea("19108");
        assertTrue(avgTotalLiveableArea == 0);
    }

    @Test
    void totalMarketValuePerCapita() throws Exception {
        String[] arguments = {"--population=population.csv","--properties=downsampled_properties.csv"};
	    Main.resetValidArguments();
		Main.setInputs(arguments);
        Reader reader = new Reader(Main.getValidArguments());
        TreeMap<String, Zip> map = reader.getData();
        PropertyProcessor propertyProcessor = new PropertyProcessor(map);

        //check a valid zip with underlying data
        int marketValuePC = propertyProcessor.totalMarketValuePerCapita("19102");
        assertTrue(marketValuePC == 26297);

        //check case with invalid zip should return 0
        marketValuePC = propertyProcessor.totalMarketValuePerCapita("19900");
        assertTrue(marketValuePC == 0);

        //check valid zip with no underlying data
        marketValuePC = propertyProcessor.totalMarketValuePerCapita("19108");
        assertTrue(marketValuePC == 0);

    }

    @Test
    void calculateAvgVal() throws Exception{
        String[] arguments = {"--population=population.csv","--properties=downsampled_properties.csv"};
	    Main.resetValidArguments();
		Main.setInputs(arguments);
        Reader reader = new Reader(Main.getValidArguments());
        TreeMap<String, Zip> map = reader.getData();
        PropertyProcessor propertyProcessor = new PropertyProcessor(map);

        //do a check against the method that calls calculate avg val with market val comparator
        int value = propertyProcessor.averagePropertyValue("19102");
        MarketValueComparator marketValComp = new MarketValueComparator();
        int marketVal = propertyProcessor.calculateAvgVal("19102",marketValComp);
        assertEquals(value,marketVal);

        //do a check with the average livable area comparator
        int avgLivableArea = propertyProcessor.averageTotalLivableArea("19102");
        AvgLivableAreaComparator avg = new AvgLivableAreaComparator();
        int avgLivArea = propertyProcessor.calculateAvgVal("19102",avg);
        assertEquals(avgLivableArea,avgLivArea);

        //also do a manual check here against the function called value
        List<Float> list = avg.getList(map.get("19102"));
        int sum = 0;
        for(float i : list) {
            sum += i;
        }
        assertTrue(sum/list.size() == avgLivArea);


        //check for case where zip code is not contained in the map using avgliveablearea comparator
        avgLivArea = propertyProcessor.calculateAvgVal("19999",avg);
        assertTrue(avgLivArea == 0);

        //check again using the market value comparator
        marketVal = propertyProcessor.calculateAvgVal("19999",marketValComp);
        assertTrue(marketVal == 0);

    }
}