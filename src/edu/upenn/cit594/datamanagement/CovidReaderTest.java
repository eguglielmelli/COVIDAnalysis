package edu.upenn.cit594.datamanagement;

import static org.junit.jupiter.api.Assertions.*;

import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import edu.upenn.cit594.util.Zip;

class CovidReaderTest {
	
	// This test tries to pass an unknown extension to the reader
	@Test
	void testExtension() throws Exception {
		TreeMap<String, Zip> data = new TreeMap<String, Zip>();
	    Exception e = assertThrows(Exception.class, () -> {
	    	new CovidReader("./testFiles/covid.txt", data);
	    });
	    assertEquals("Unrecognized Covid file format. Program exiting...", e.getMessage());
	}

	// This test uses a file that has a header missing and thus is invalid
	@Test
	void testCSVCovid1() throws Exception {
		TreeMap<String, Zip> data = new TreeMap<String, Zip>();
	    Exception e = assertThrows(Exception.class, () -> {
	    	new CovidReader("./testFiles/covid1.csv", data);
	    });
	    assertEquals("Error reading Covid Csv file. Program exiting...", e.getMessage());
	}
	
	// This test uses a CSV file with some valid data, as well as a ZIP code less than five digits, more than five
	// digits, invalid date formats, and empty strings for covid cases
	@SuppressWarnings("unused")
	@Test
	void testCSVCovid2() throws Exception {
		TreeMap<String, Zip> data = new TreeMap<String, Zip>();
		CovidReader reader = new CovidReader("./testFiles/covid2.csv", data);
	    assertTrue(data.size() == 4);
	    assertTrue(data.containsKey("19100"));
	    assertTrue(data.get("19100").getCovidCases().containsKey("2021-03-25"));
	    assertTrue(data.get("19100").getCovidCases().get("2021-03-25").getPartiallyVaccinated() == 0);
	    assertTrue(data.get("19100").getCovidCases().get("2021-03-25").getFullyVaccinated() == 0);
	    assertTrue(data.containsKey("19101"));
	    assertTrue(data.get("19101").getCovidCases().containsKey("2021-03-25"));
	    assertTrue(data.get("19101").getCovidCases().get("2021-03-25").getPartiallyVaccinated() == 10);
	    assertTrue(data.get("19101").getCovidCases().get("2021-03-25").getFullyVaccinated() == 10);
	    assertTrue(data.containsKey("19102"));
	    assertTrue(data.get("19102").getCovidCases().containsKey("2021-03-25"));
	    assertTrue(data.get("19102").getCovidCases().get("2021-03-25").getPartiallyVaccinated() == 20);
	    assertTrue(data.get("19102").getCovidCases().get("2021-03-25").getFullyVaccinated() == 20);
	    assertTrue(data.containsKey("19103"));
	    assertTrue(data.get("19103").getCovidCases().containsKey("2021-03-25"));
	    assertTrue(data.get("19103").getCovidCases().get("2021-03-25").getPartiallyVaccinated() == 30);
	    assertTrue(data.get("19103").getCovidCases().get("2021-03-25").getFullyVaccinated() == 30);
	    assertFalse(data.containsKey("19104"));
	    assertFalse(data.containsKey("19105"));
	    assertFalse(data.containsKey("19106"));
	    assertFalse(data.containsKey("tenten7"));
	}
	
	// This test uses a JSONfile with some valid data, as well as a ZIP code less than five digits, more than five
	// digits, invalid date formats, and empty strings for covid cases
	@SuppressWarnings("unused")
	@Test
	void testJSONCovid() throws Exception {
		TreeMap<String, Zip> data = new TreeMap<String, Zip>();
		CovidReader reader = new CovidReader("./testFiles/covid.json", data);
	    assertTrue(data.size() == 4);
	    assertTrue(data.containsKey("19100"));
	    assertTrue(data.get("19100").getCovidCases().containsKey("2021-03-25"));
	    assertTrue(data.get("19100").getCovidCases().get("2021-03-25").getPartiallyVaccinated() == 0);
	    assertTrue(data.get("19100").getCovidCases().get("2021-03-25").getFullyVaccinated() == 0);
	    assertTrue(data.containsKey("19101"));
	    assertTrue(data.get("19101").getCovidCases().containsKey("2021-03-25"));
	    assertTrue(data.get("19101").getCovidCases().get("2021-03-25").getPartiallyVaccinated() == 10);
	    assertTrue(data.get("19101").getCovidCases().get("2021-03-25").getFullyVaccinated() == 10);
	    assertTrue(data.containsKey("19102"));
	    assertTrue(data.get("19102").getCovidCases().containsKey("2021-03-25"));
	    assertTrue(data.get("19102").getCovidCases().get("2021-03-25").getPartiallyVaccinated() == 20);
	    assertTrue(data.get("19102").getCovidCases().get("2021-03-25").getFullyVaccinated() == 20);
	    assertTrue(data.containsKey("19103"));
	    assertTrue(data.get("19103").getCovidCases().containsKey("2021-03-25"));
	    assertTrue(data.get("19103").getCovidCases().get("2021-03-25").getPartiallyVaccinated() == 30);
	    assertTrue(data.get("19103").getCovidCases().get("2021-03-25").getFullyVaccinated() == 30);
	    assertFalse(data.containsKey("19104"));
	    assertFalse(data.containsKey("19105"));
	    assertFalse(data.containsKey("19106"));
	    assertFalse(data.containsKey("tenten7"));
	}
}
