package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.util.Zip;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class PopulationProcessorTest {

    @Test
    void totalPopulation() throws Exception {
        //valid original case from file
        String[] arguments = {"--population=population.csv"};
        Reader reader = new Reader(arguments);
        TreeMap<String, Zip> data = reader.getData();
        PopulationProcessor popProcessor = new PopulationProcessor(data);
        int population = popProcessor.totalPopulation();
        assertTrue(population == 1603797);

        //valid case from our own test file
        String[] arguments1 ={"--population=./testFiles/population2.csv"};
        Reader reader1 = new Reader(arguments1);
        TreeMap<String,Zip> data1 = reader1.getData();
        PopulationProcessor pProcessor = new PopulationProcessor(data1);
        population = pProcessor.totalPopulation();
        assertTrue(population == 60);

    }
}