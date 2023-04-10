package edu.upenn.cit594;

import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.ui.Display;

public class Main {
	
	public static void main(String[] args) {	
		try {
			Reader reader = new Reader(args);
			Processor processor = new Processor(reader);
			Display display = new Display(processor);
			display.startProgram();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
