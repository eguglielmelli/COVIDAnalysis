package edu.upenn.cit594.logging;

import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * The Logger class provides a simple logging utility for writing messages to a file.
 * It implements the Singleton design pattern.
 */
public class Logger {

    private static PrintWriter out = null;
    private static Logger instance = new Logger();

    /**
     * Private constructor
     */
    private Logger() {}

    /**
     * Returns the instance of the Logger class
     * @return The instance of the Logger class
     */
    public static Logger getInstance() throws Exception {
        return instance;
    }

	/**
	 * Sets or changes the filename of the logger file
	 * If an instance of the Logger class already exists, it is closed
	 * @param filename The name of the new logger file
	 */
    public void setDestination(String filename) throws Exception {
        if(out != null) {out.close();}
        try {
        	out = new PrintWriter(new FileWriter(filename, true));
        } catch (Exception e) {
			throw new Exception("Could not open logger file. Program exiting...");
        }
    }
    
    /**
     * Writes a message to the logger file
     * @param message The message to write to the logger file
     * @return True if the message was successfully written to the logger file, false otherwise
     */
	public boolean writeToLog (String message) {
		String logged = System.currentTimeMillis() + " " + message;
		if(out == null) {
			System.err.println(logged);
		} else { 
			out.println(logged);
			out.flush();
		}
		return true;
	}
	
	/**
	 * Closes the logger file
	 */
	public void close() {
		if (out != null) {out.close();}
	}
}
