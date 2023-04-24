package edu.upenn.cit594;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

class MainTest {
	
	// Test to check one invalid argument
	@Test
	void testInvalidArgument() throws Exception {
	    String[] arg1 = {"--population=population.csv", "--log=events.log", 
	            "--covid=covid_data.csv", "invalidargument"};
	    Main.validArguments = new HashMap<String, String>();
	    Exception e = assertThrows(Exception.class, () -> {
	        Main.setInputs(arg1);
	    });
	    assertEquals("Argument not of form '--name=value'", e.getMessage());
	}
	
	// Test to check one unknown argument
	@Test
	void testUnknownArgument() throws Exception {
	    String[] arg2 = {"--population=population.csv", "--log=events.log", 
	            "--covid=covid_data.csv", "--valuesamples=downsampled_properties.csv"};
	    Main.validArguments = new HashMap<String, String>();
	    Exception e = assertThrows(Exception.class, () -> {
	        Main.setInputs(arg2);
	    });
	    assertEquals("Provided invalid argument. Program exiting...", e.getMessage());
	}
	
	// Test to check if the same argument is provided twice
	@Test
	void testDoubleArgument() throws Exception {
	    String[] arg3 = {"--population=population.csv", "--log=events.log", 
	            "--covid=covid_data.csv", "--population=population.csv"};
	    Main.validArguments = new HashMap<String, String>();
	    Exception e = assertThrows(Exception.class, () -> {
	        Main.setInputs(arg3);
	    });
	    assertEquals("Provided invalid argument. Program exiting...", e.getMessage());
	}
	
	// Test to check if all arguments are valid
	@Test
	void testValidArguments() {
	    String[] arg4 = {"--population=population.csv", "--log=events.log", 
	            "--covid=covid_data.csv", "--properties=downsampled_properties.csv"};
	    Main.validArguments = new HashMap<String, String>();
	    try {
			Main.setInputs(arg4);
		} catch (Exception e) {
			System.out.println(e);
		}
	    HashMap<String, String> validArguments = Main.getValidArguments();
	    assertTrue(validArguments.containsKey("populationFilename"));
	    assertTrue(validArguments.containsKey("covidFilename"));
	    assertTrue(validArguments.containsKey("logFilename"));
	    assertTrue(validArguments.containsKey("propertyFilename"));
	}
}
