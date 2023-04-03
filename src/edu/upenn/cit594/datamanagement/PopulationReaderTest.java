package edu.upenn.cit594.datamanagement;

import static org.junit.jupiter.api.Assertions.*;

import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import edu.upenn.cit594.util.Zip;

class PopulationReaderTest {
	
	// This test uses a file that has a header missing and thus is invalid
	@Test
	void testPopulation1() throws Exception {
		TreeMap<String, Zip> data = new TreeMap<String, Zip>();
	    Exception e = assertThrows(Exception.class, () -> {
	    	new PopulationReader("./testFiles/population1.csv", data);
	    });
	    assertEquals("Error reading population file. Program exiting...", e.getMessage());
	}
	
	// This test uses a file with some valid data, as well as a ZIP code less than five digits, more than five
	// digits, non-integer data for population, non-numeric data for population, etc.
	@SuppressWarnings("unused")
	@Test
	void testPopulation2() throws Exception {
		TreeMap<String, Zip> data = new TreeMap<String, Zip>();
		PopulationReader reader = new PopulationReader("./testFiles/population2.csv", data);
	    assertTrue(data.size() == 3);
	    assertEquals(data.get("19101").getTotalPopulation(), 10);
	    assertEquals(data.get("19102").getTotalPopulation(), 20);
	    assertEquals(data.get("19103").getTotalPopulation(), 30);
	    assertTrue(!data.containsKey("1201"));
	    assertTrue(!data.containsKey("12000"));
	    assertTrue(!data.containsKey("ten-ten-4"));
	    assertTrue(!data.containsKey("19104"));
	    assertTrue(!data.containsKey("191054"));
	}
}
