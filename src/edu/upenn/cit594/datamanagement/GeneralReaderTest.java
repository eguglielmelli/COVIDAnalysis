package edu.upenn.cit594.datamanagement;

import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import org.junit.jupiter.api.Test;

class GeneralReaderTest {

	// Test to check logic of helper function
	@Test
    void testGetIndexes() throws Exception {
        String[] header = {"a", "b", "c", "d"};
        String[] parameters1 = {"a", "c"};
        String[] parameters2 = {"a", "b", "c", "d"};
        String[] parameters3 = {"a", "b", "c", "e"};
        
        int[] expected1 = {0, 2};
        int[] expected2 = {0, 1, 2, 3};
        
        GeneralReader reader = new GeneralReader();
        
        assertArrayEquals(expected1, reader.getIndexes(header, parameters1));
        assertArrayEquals(expected2, reader.getIndexes(header, parameters2));
        assertThrows(Exception.class, () -> reader.getIndexes(header, parameters3));
    }
	
	// Test to check logic of helper function
	@Test
	void testValidateZip() {
        GeneralReader reader = new GeneralReader();
        assertEquals("19104", reader.validateZip("19104", "\\d{5}"));
        assertNull(reader.validateZip("19104-", "\\d{5}$"));
        assertNull(reader.validateZip("19104", "\\d{6}"));
	}
	
	// Test to check logic of helper function
	@Test
	void testValidateInteger() {
        GeneralReader reader = new GeneralReader();
	    assertEquals(123, reader.validateInteger("123", "^\\d+$"));
	    assertEquals(-1, reader.validateInteger("abc", "^\\d+$"));
	    assertEquals(-123, reader.validateInteger("-123", "^-?\\d+$"));
	    assertEquals(0, reader.validateInteger("0", "^-?\\d+$"));
	}

	// Test to check logic of helper function
	@Test
	void testValidateFloat() {
        GeneralReader reader = new GeneralReader();
	    assertEquals(3.14, reader.validateFloat("3.14", "\\d+(\\.\\d+)?"), 0.001f);
	    assertEquals(-1.0, reader.validateFloat("abc", "\\d+(\\.\\d+)?"), 0.001f);
	}
	
	// Test to check logic of helper function
	@Test
	void testValidateDate() {
        GeneralReader reader = new GeneralReader();
	    assertEquals("2022-05-01", reader.validateDate("2022-05-01 12:00:00"));
	    assertNull(reader.validateDate("2022/05/01 12:00:00"));
	}
	// Test to check CSV lexer using file easy1.csv from HW7 (well formatted)
	@Test
	void testReadRowEasy1() throws Exception {
        GeneralReader reader = new GeneralReader();
        reader.reader = new BufferedReader(new FileReader("./testFiles/easy1.csv"));
        LinkedList<String[]> results = new LinkedList<String[]>();
        String[] row;
        while ((row = reader.readRow()) != null) {
        	results.add(row);
        }
        String[] expected0 = {"name", "age"};
		assertTrue(Arrays.equals(results.get(0), expected0));
        String[] expected1 = {"hannah", "20"};
		assertTrue(Arrays.equals(results.get(1), expected1));
        String[] expected2 = {"harry", "40"};
		assertTrue(Arrays.equals(results.get(2), expected2));
        String[] expected3 = {"mary", "56"};
		assertTrue(Arrays.equals(results.get(3), expected3));
        String[] expected4 = {"john", "18"};
		assertTrue(Arrays.equals(results.get(4), expected4));
        String[] expected5 = {"yule", "25"};
		assertTrue(Arrays.equals(results.get(5), expected5));
        String[] expected6 = {"jiexi", "32"};
		assertTrue(Arrays.equals(results.get(6), expected6));
        String[] expected7 = {"lauren", "35"};
		assertTrue(Arrays.equals(results.get(7), expected7));
	}
	
	// Test to check CSV lexer using file easy2.csv from HW7 (embedded new line)
	@Test
	void testReadRowEasy2() throws Exception {
        GeneralReader reader = new GeneralReader();
        reader.reader = new BufferedReader(new FileReader("./testFiles/easy2.csv"));
        LinkedList<String[]> results = new LinkedList<String[]>();
        String[] row;
        while ((row = reader.readRow()) != null) {
        	results.add(row);
        }
        String[] expected0 = {"from","to","sequence_number","message"};
		assertTrue(Arrays.equals(results.get(0), expected0));
        String[] expected1 = {"alice","bob","1","hello", " "};
		assertTrue(Arrays.equals(results.get(1), expected1));
        String[] expected2 = {"gus"};
		assertTrue(Arrays.equals(results.get(2), expected2));
        String[] expected3 = {""};
		assertTrue(Arrays.equals(results.get(3), expected3));
        String[] expected4 = {"bob","alice","2","hello to you too"};
		assertTrue(Arrays.equals(results.get(4), expected4));
        String[] expected5 = {"alice","bob","3","do you like newlines?\nlike this?"};
		assertTrue(Arrays.equals(results.get(5), expected5));
        String[] expected6 = {"bob","alice","4","meh", ""};
		assertTrue(Arrays.equals(results.get(6), expected6));
	}
	
	// Test to check CSV lexer using file easy3.csv from HW7 (commas in the middle of field)
	@Test
	void testReadRowEasy3() throws Exception {
        GeneralReader reader = new GeneralReader();
        reader.reader = new BufferedReader(new FileReader("./testFiles/easy3.csv"));
        LinkedList<String[]> results = new LinkedList<String[]>();
        String[] row;
        while ((row = reader.readRow()) != null) {
        	results.add(row);
        }
        String[] expected0 = {"example of using \" in a field","1"};
		assertTrue(Arrays.equals(results.get(0), expected0));
	}
	
	// Test to check CSV lexer using file medium1.csv from HW7 (quotes in a field)
	@Test
	void testReadRowMedium1() throws Exception {
        GeneralReader reader = new GeneralReader();
        reader.reader = new BufferedReader(new FileReader("./testFiles/medium1.csv"));
        LinkedList<String[]> results = new LinkedList<String[]>();
        String[] row;
        while ((row = reader.readRow()) != null) {
        	results.add(row);
        }
        String[] expected0 = {"from","to","sequence_number","message"};
		assertTrue(Arrays.equals(results.get(0), expected0));
        String[] expected1 = {"alice","bob","5","Thoughts on so-called \"quotes\"?"};
		assertTrue(Arrays.equals(results.get(1), expected1));
        String[] expected2 = {"bob","alice","6","only if they say something interesting"};
		assertTrue(Arrays.equals(results.get(2), expected2));
	}
	
	// Test to check CSV lexer using file tricky.csv (quotes, new lines, etc.)
	@Test
	void testReadRowTricky() throws Exception {
        GeneralReader reader = new GeneralReader();
        reader.reader = new BufferedReader(new FileReader("./testFiles/tricky.csv"));
        LinkedList<String[]> results = new LinkedList<String[]>();
        String[] row;
        while ((row = reader.readRow()) != null) {
        	results.add(row);
        }
        String[] expected0 = {"Header field 0","\",\"\",\"\"\",,\",fun, right?","\nStill a header field (2 to be specific"};
		assertTrue(Arrays.equals(results.get(0), expected0));
        String[] expected1 = {"0.0", "0.1", "0.2"};
		assertTrue(Arrays.equals(results.get(1), expected1));
        String[] expected2 = {"1.0", "1.1", "1.2"};
		assertTrue(Arrays.equals(results.get(2), expected2));
        String[] expected3 = {"2.0, but only because it's zero indexed\nI think", "2.1", "\"2.2\""};
		assertTrue(Arrays.equals(results.get(3), expected3));
	}
}
