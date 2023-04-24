package edu.upenn.cit594.datamanagement;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;
import edu.upenn.cit594.util.Zip;

class PropertyReaderTest {

	// This test uses a file that does not have the necessary headers
	@Test
	void testProperties1() throws Exception {
		TreeMap<String, Zip> data = new TreeMap<String, Zip>();
		HashMap<String, String> arguments = new HashMap<String, String>();
		arguments.put("propertyFilename", "./testFiles/properties1.csv");
	    Exception e = assertThrows(Exception.class, () -> {
	    	new PropertyReader(arguments, data);
	    });
	    assertEquals("Error reading property file. Program exiting...", e.getMessage());
	}
	
	// This test uses a file that has the perfect format according to the instructions
	@SuppressWarnings("unused")
	@Test
	void testProperties2() throws Exception {
		TreeMap<String, Zip> data = new TreeMap<String, Zip>();
		HashMap<String, String> arguments = new HashMap<String, String>();
		arguments.put("propertyFilename", "./testFiles/properties2.csv");
		PropertyReader reader = new PropertyReader(arguments, data);
		assertEquals(data.size(), 3);
		assertTrue(data.containsKey("19101"));
		assertTrue(data.get("19101").getTotalArea().get(0) == 100.0);
		assertTrue(data.get("19101").getMarketValue().get(0) == 10.0);
		assertTrue(data.containsKey("19102"));
		assertTrue(data.get("19102").getTotalArea().get(0) == 200.0);
		assertTrue(data.get("19102").getMarketValue().get(0) == 20.0);
		assertTrue(data.containsKey("19103"));
		assertTrue(data.get("19103").getTotalArea().get(0) == 300.0);
		assertTrue(data.get("19103").getMarketValue().get(0) == 30.0);
	}
	
	// This test uses a file that has invalid ZIP codes, ZIP codes longer than 5 digits, invalid
	// property and market value data, and different whitespaces
	@SuppressWarnings("unused")
	@Test
	void testProperties3() throws Exception {
		TreeMap<String, Zip> data = new TreeMap<String, Zip>();
		HashMap<String, String> arguments = new HashMap<String, String>();
		arguments.put("propertyFilename", "./testFiles/properties3.csv");
		PropertyReader reader = new PropertyReader(arguments, data);
		assertEquals(data.size(), 2);
		assertFalse(data.containsKey("1914"));
		assertFalse(data.containsKey("1tenten"));
		assertTrue(data.containsKey("19100"));
		assertTrue(data.get("19100").getTotalArea().size() == 2);
		assertTrue(data.get("19100").getTotalArea().get(0) == 1000);
		assertTrue(data.get("19100").getTotalArea().get(1) == 2000);
		assertTrue(data.get("19100").getMarketValue().size() == 2);
		assertTrue(data.get("19100").getMarketValue().get(0) == 1000);
		assertTrue(data.get("19100").getMarketValue().get(1) == 3000);
		assertTrue(data.containsKey("19101"));
		assertTrue(data.get("19101").getTotalArea().size() == 3);
		assertTrue(data.get("19101").getTotalArea().get(0) == 10);
		assertTrue(data.get("19101").getTotalArea().get(1) == 20);
		assertTrue(data.get("19101").getTotalArea().get(2) == 30);
		assertTrue(data.get("19101").getMarketValue().size() == 3);
		assertTrue(data.get("19101").getMarketValue().get(0) == 100);
		assertTrue(data.get("19101").getMarketValue().get(1) == 200);
		assertTrue(data.get("19101").getMarketValue().get(2) == 300);

	}
}
