package edu.upenn.cit594.datamanagement;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ReaderTest {

	// Test to check valid inputs, including some inputs missing
	@Test
	void testValidSetInputs() throws Exception {
		// All valid inputs
		String[] arg1 = {"--population=population.csv", "--log=events.log", 
				"--covid=covid_data.csv", "--properties=downsampled_properties.csv"};
		Reader reader1 = new Reader(arg1);
		reader1.setInputs();
		boolean[] validated1 = reader1.getReadFiles();
		assertTrue(validated1[0] == true);
		assertTrue(validated1[1] == true);
		assertTrue(validated1[2] == true);
		assertTrue(validated1[3] == true);

		// Population Missing
		String[] arg2 = {"--log=events.log", 
				"--covid=covid_data.csv", "--properties=downsampled_properties.csv"};
		Reader reader2 = new Reader(arg2);
		reader2.setInputs();
		boolean[] validated2 = reader2.getReadFiles();
		assertTrue(validated2[0] == true);
		assertTrue(validated2[1] == false);
		assertTrue(validated2[2] == true);
		assertTrue(validated2[3] == true);
		
		// Properties Missing
		String[] arg3 = {"--population=population.csv", "--log=events.log", 
				"--covid=covid_data.csv"};
		Reader reader3 = new Reader(arg3);
		reader3.setInputs();
		boolean[] validated3 = reader3.getReadFiles();
		assertTrue(validated3[0] == true);
		assertTrue(validated3[1] == true);
		assertTrue(validated3[2] == true);
		assertTrue(validated3[3] == false);
		
		// Covid Missing
		String[] arg4 = {"--population=population.csv", "--log=events.log", 
				"--properties=downsampled_properties.csv"};
		Reader reader4 = new Reader(arg4);
		reader4.setInputs();
		boolean[] validated4 = reader4.getReadFiles();
		assertTrue(validated4[0] == true);
		assertTrue(validated4[1] == true);
		assertTrue(validated4[2] == false);
		assertTrue(validated4[3] == true);
	}
	
	// Test to check one invalid argument
	@Test
	void testInvalidArgument() throws Exception {
	    String[] arg1 = {"--population=population.csv", "--log=events.log", 
	            "--covid=covid_data.csv", "invalidargument"};
	    Reader reader1 = new Reader(arg1);
	    Exception e = assertThrows(Exception.class, () -> {
	        reader1.setInputs();
	    });
	    assertEquals("Argument not of form '--name=value'", e.getMessage());
	}
	
	// Test to check one unknown argument
	@Test
	void testUnknownArgument() throws Exception {
	    String[] arg1 = {"--population=population.csv", "--log=events.log", 
	            "--covid=covid_data.csv", "--valuesamples=downsampled_properties.csv"};
	    Reader reader1 = new Reader(arg1);
	    Exception e = assertThrows(Exception.class, () -> {
	        reader1.setInputs();
	    });
	    assertEquals("Provided invalid argument. Program exiting...", e.getMessage());
	}
	
	// Test to check if the same argument is provided twice
	@Test
	void testDoubleArgument() throws Exception {
	    String[] arg1 = {"--population=population.csv", "--log=events.log", 
	            "--covid=covid_data.csv", "--population=population.csv"};
	    Reader reader1 = new Reader(arg1);
	    Exception e = assertThrows(Exception.class, () -> {
	        reader1.setInputs();
	    });
	    assertEquals("Provided invalid argument. Program exiting...", e.getMessage());
	}
}
