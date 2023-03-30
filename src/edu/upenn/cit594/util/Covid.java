package edu.upenn.cit594.util;

import java.util.Date;

public class Covid {
	
	private Date date;
	private int partiallyVaccinated;
	private int fullyVaccinated;
	
    public Covid(Date date) {
        this.date = date;
    }
    
    public Date getDate() {
    	return date;
    }
    
    public int getPartiallyVaccinated() {
    	return partiallyVaccinated;
    }
    
    public void setPartiallyVaccinated(int total) {
    	partiallyVaccinated = total;
    }
    
    public int getFullyVaccinated() {
    	return fullyVaccinated;
    }
    
    public void setFullyVaccinated(int total) {
    	fullyVaccinated = total;
    }
}
