package edu.upenn.cit594.logging;

import static org.junit.Assert.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.*;

public class LoggerTest {
    
	// Test 1 - Logger throws Exception when filename is not set
    @Test(expected = Exception.class)
    public void getInstanceNullTest() throws Exception {
    	Logger.setDestination(null);
        Logger.getInstance();
    }
    
    // Test 2 - Logger returns the same instance when called multiple times
    @Test
    public void getInstanceSameInstanceTest() throws Exception {
        Logger.setDestination("./TestFiles/test.log");
        Logger instance1 = Logger.getInstance();
        Logger instance2 = Logger.getInstance();
        assertNotNull(instance1);
        assertNotNull(instance2);
        assertSame(instance1, instance2);
    }
    
    // Test 3 - Changing the name of the file does not change the instance
    @Test
    public void setFilenameNewFileTest() throws Exception {
        String filename1 = "./TestFiles/test1.log";
        String filename2 = "./TestFiles/test2.log";
        Logger.setDestination(filename1);
        Logger instance1 = Logger.getInstance();
        Logger.setDestination(filename2);
        Logger instance2 = Logger.getInstance();
        assertSame(instance1, instance2);
        assertNotNull(instance2);
        Logger.getInstance().close();
    }
    
    // Test 4 - New text can be written to log and it returns true.
    @Test
    public void writeToLogTest() throws Exception {
        String filename = "./TestFiles/test.log";
        Logger.setDestination(filename);
        Logger logger = Logger.getInstance();
        assertTrue(logger.writeToLog("test"));
        logger.close();
        assertTrue(new String(Files.readAllBytes(Paths.get(filename))).contains("test"));
    }
}
