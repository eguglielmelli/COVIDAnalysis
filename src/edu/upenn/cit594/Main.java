package edu.upenn.cit594;

import java.util.HashMap;
import java.util.TreeMap;

import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.util.Covid;
import edu.upenn.cit594.util.Zip;

public class Main {
	
	public static void main(String[] args) {
		
		// FOR TESTING ONLY :)
		String[] arguments = {"--population=population.csv", "--log=events.log", 
				"--covid=covid_data.csv", "--properties=downsampled_properties.csv"};
		try {
			Reader reader = new Reader(arguments);
			TreeMap<String, Zip> data = reader.getData();
			Zip sample = data.get("19119");
			
			System.out.println("Sample for ZIP 19119 on 2021-03-25");
			System.out.println("Population: " + sample.getTotalPopulation());
			System.out.println("Market Value: " + sample.getMarketValue());
			System.out.println("Area: " + sample.getTotalArea());
			
			HashMap<String, Covid> cases = sample.getCovidCases();
			System.out.println("Partially Vax: " + cases.get("2021-03-25").getPartiallyVaccinated());
			System.out.println("Fully Vax: " + cases.get("2021-03-25").getFullyVaccinated());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
