package edu.upenn.cit594.util;

/**
 * This class represents the Covid cases in a given day. It stores the date,
 * cumulative partially and fully vaccinated residents in a given day
 *
 */

public class Covid {
	
	// Private attributes of the class
	private String date;
	private int partiallyVaccinated = 0;
	private int fullyVaccinated = 0;
	
	// Constructor method. Requires the date, partially and fully vaccinated
    public Covid(String date) {
        this.date = date;
    }
    
    // Getter method: Once set, date cannot be changed
    public String getDate() {
    	return date;
    }
    
    // Getter method for partially vaccinated residents
    public int getPartiallyVaccinated() {
    	return partiallyVaccinated;
    }
    
    // Setter method for partially vaccinated residents
    public void setPartiallyVaccinated(int total) {
    	partiallyVaccinated = total;
    }
    
    // Getter method for fully vaccinated residents
    public int getFullyVaccinated() {
    	return fullyVaccinated;
    }
    
    // Setter method for fully vaccinated residents
    public void setFullyVaccinated(int total) {
    	fullyVaccinated = total;
    }
}
