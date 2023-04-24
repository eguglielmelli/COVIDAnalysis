package edu.upenn.cit594.datamanagement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import edu.upenn.cit594.Main;

class ReaderTest {

	// Test to check valid inputs, including some inputs missing
	@Test
	void testValidSetInputs() throws Exception {
		// All valid inputs
		String[] arg1 = {"--population=population.csv", "--log=events.log", 
				"--covid=covid_data.csv", "--properties=downsampled_properties.csv"};
	    Main.resetValidArguments();
		Main.setInputs(arg1);
		Reader reader1 = new Reader(Main.getValidArguments());
		boolean[] validated1 = reader1.getReadFiles();
		assertTrue(validated1[0] == true);
		assertTrue(validated1[1] == true);
		assertTrue(validated1[2] == true);
		assertTrue(validated1[3] == true);

		// Population Missing
		String[] arg2 = {"--log=events.log", 
				"--covid=covid_data.csv", "--properties=downsampled_properties.csv"};
	    Main.resetValidArguments();
		Main.setInputs(arg2);
		Reader reader2 = new Reader(Main.getValidArguments());
		boolean[] validated2 = reader2.getReadFiles();
		assertTrue(validated2[0] == true);
		assertTrue(validated2[1] == false);
		assertTrue(validated2[2] == true);
		assertTrue(validated2[3] == true);
		
		// Properties Missing
		String[] arg3 = {"--population=population.csv", "--log=events.log", 
				"--covid=covid_data.csv"};
	    Main.resetValidArguments();
		Main.setInputs(arg3);
		Reader reader3 = new Reader(Main.getValidArguments());
		boolean[] validated3 = reader3.getReadFiles();
		assertTrue(validated3[0] == true);
		assertTrue(validated3[1] == true);
		assertTrue(validated3[2] == true);
		assertTrue(validated3[3] == false);
		
		// Covid Missing
		String[] arg4 = {"--population=population.csv", "--log=events.log", 
				"--properties=downsampled_properties.csv"};
	    Main.resetValidArguments();
		Main.setInputs(arg4);
		Reader reader4 = new Reader(Main.getValidArguments());
		boolean[] validated4 = reader4.getReadFiles();
		assertTrue(validated4[0] == true);
		assertTrue(validated4[1] == true);
		assertTrue(validated4[2] == false);
		assertTrue(validated4[3] == true);
	}
}
