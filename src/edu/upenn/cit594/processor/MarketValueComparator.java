package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.Zip;

import java.util.List;

public class MarketValueComparator implements PropertyComparator{

    public List<Float> getList(Zip zip) {
        return zip.getMarketValue();
    }

}
