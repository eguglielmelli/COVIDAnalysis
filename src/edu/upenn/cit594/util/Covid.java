package edu.upenn.cit594.util;

public class Covid {
	
	private String date;
	private int partiallyVaccinated;
	private int fullyVaccinated;
	
    public Covid(String date, int partiallyVaccinated, int fullyVaccinated) {
        this.date = date;
        this.partiallyVaccinated = partiallyVaccinated;
        this.fullyVaccinated = fullyVaccinated;
    }
    
    public String getDate() {
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
