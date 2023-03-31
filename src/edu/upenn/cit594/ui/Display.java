package edu.upenn.cit594.ui;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;

import java.util.Locale;
import java.util.Scanner;
import java.util.TreeMap;

public class Display {
    Scanner scanner = new Scanner(System.in);
    private Processor analyzer;
    private boolean activeUser = true;

    public Display(Processor analyzer) {
        this.analyzer = analyzer;
    }
    public void menuOptions() {
        System.out.println("Please choose from the list of options: \n0: Exit the program \n1: Show available options " +
                "\n2: Show total population for all ZIP codes " +
                "\n3: Show the total vaccinations per capita for each ZIP code for the specified date" +
                "\n4: Show the average market value for properties in a specified ZIP code" +
                "\n5: Show the average total livable area for properties in a specified ZIP code" +
                "\n6: Show the total market value of properties, per capita, for a specified ZIP code" +
                "\n7: This is a placeholder for our custom feature");

        arrowPrinter();
    }
    Logger log = Logger.getInstance();

    public void displayData() {

        while(activeUser) {
            menuOptions();
            int firstInput = scanner.nextInt();
            while (firstInput < 0 || firstInput > 7) {
                System.out.println("Please input a number between 0 and 7.");
                arrowPrinter();
                firstInput = scanner.nextInt();
            }
            switch (firstInput) {
                case 0:
                    activeUser = false;
                    System.out.println("Thank you for using our program.");
                    return;
                case 1:
                    System.out.println("BEGIN OUTPUT");
                    System.out.println("END OUTPUT");
                    displayData();
                    return;
                case 2:
                    System.out.println(analyzer.getTotalPopulation());
                    displayData();
                    return;
                case 3:
                    seeTotalVaccinationsPerCapita();
                    displayData();
                    return;
                case 4:
                    System.out.println("Please specify the ZIP code you would like to see the average property value");
                    String zipCode = scanner.next();
                    arrowPrinter();
                    System.out.println(analyzer.getAveragePropertyValue(zipCode));
                    displayData();
                    return;
                case 5:
                    System.out.println("Please specify the ZIP code you would like to see average livable area");
                    String zip = scanner.next();
                    arrowPrinter();
                    System.out.println(analyzer.getAverageTotalLivableArea(zip));
                    displayData();
                    return;
                case 6:
                    System.out.println("Please specify the ZIP code you would like to see total market value per capita");
                    String nextZip = scanner.next();
                    arrowPrinter();
                    System.out.println(analyzer.getTotalMarketValuePerCapita(nextZip));
                    displayData();
                    return;
                case 7:
                    System.out.println("This will be our custom feature");
                    return;
            }
            scanner.close();
        }
    }
    public void arrowPrinter() {
        System.out.flush();
        System.out.print("> ");
    }
    public boolean validateUserInput(Object input) {
        if(input.getClass() == String.class) {
            if(((String) input).matches("^\\d{4}-\\d{2}-\\d{2}$")) return true;
        }
       return false;
    }

    public void seeTotalVaccinationsPerCapita() {
        System.out.println("partial or full");
        arrowPrinter();
        String next = scanner.next().toLowerCase(Locale.ROOT);
        while(!next.equals("partial") && !next.equals("full")) {
            System.out.println("Please enter either partial or full.");
            arrowPrinter();
            next = scanner.next();
        }
        System.out.println("Please enter the date in YYYY-MM-DD format");
        arrowPrinter();
        String date = scanner.next();
        while (!validateUserInput(date)) {
            System.out.println("Please try again: (format YYYY-MM-DD)");
            arrowPrinter();
            date = scanner.next();
        }
        TreeMap<Integer, Double> map = analyzer.getVaccinationsPerCapita(next, date);
        System.out.println("BEGIN OUTPUT");
        for (int i : map.keySet()) {
            System.out.println(i + " " + map.get(i));
        }
        System.out.println("END OUTPUT");
        }
    }
