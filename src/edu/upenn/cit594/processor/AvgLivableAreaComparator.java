package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.Zip;
import java.util.List;

public class AvgLivableAreaComparator implements PropertyComparator {

    @Override
	public List<Float> getList(Zip zip) {
        return zip.getTotalArea();
    }
}
