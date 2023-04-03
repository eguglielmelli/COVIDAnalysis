package edu.upenn.cit594.ui;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;

import java.util.*;

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
    }

   // Logger log = Logger.getInstance();

    public void displayData() {
        while (activeUser) {
            menuOptions();
            arrowPrinter();
            int firstInput;
            try {
                String s = scanner.next();
                firstInput = Integer.parseInt(s);
            }catch (NumberFormatException e){
                System.out.println("Please enter a number between 0 and 7.");
                continue;
            }
            if(firstInput < 0 || firstInput > 7) {
                System.out.println("Please enter a number between 0 and 7.");
                continue;
            }
            switch (firstInput) {
                case 0:
                    activeUser = false;
                    System.out.println("Thank you for using our program.");
                    break;
                case 1:
                    availableActionsMenu();
                    break;
                case 2:
                    printTotalPopulation();
                    break;
                case 3:
                    seeTotalVaccinationsPerCapita();
                    break;
                case 4:
                    printAveragePropertyValue();
                    break;
                case 5:
                    printAverageLivableArea();
                    break;
                case 6:
                    printTotalMarketValuePerCapita();
                    break;
                case 7:
                    System.out.println("This will be our custom feature");
                    break;
            }
        }
        scanner.close();
    }
    public void arrowPrinter() {
        System.out.flush();
        System.out.print("> ");
    }

    public boolean validateDateInfo(String date) {
        if(date.matches("^\\d{4}-\\d{2}-\\d{2}$")) return true;
        return false;
    }

    public void seeTotalVaccinationsPerCapita() {
        System.out.println("partial or full");
        arrowPrinter();
        String next = scanner.next().toLowerCase(Locale.ROOT);
        while (!next.equals("partial") && !next.equals("full")) {
            System.out.println("Please enter either partial or full.");
            arrowPrinter();
            next = scanner.next();
        }
        System.out.println("Please enter the date in YYYY-MM-DD format");
        arrowPrinter();
        String date = scanner.next();
        while (!validateDateInfo(date)) {
            System.out.println("Please try again: (format YYYY-MM-DD)");
            arrowPrinter();
            date = scanner.next();
        }
        if(date.compareTo("2022-07-25") > 0) {
            System.out.println("BEGIN OUTPUT");
            System.out.println("0");
            System.out.println("END OUTPUT");
            return;
        }
        TreeMap<Integer, Double> map = analyzer.getVaccinationsPerCapita(next, date);
        if (map != null) {
            System.out.println("BEGIN OUTPUT");
              for (int i : map.keySet()) {
                    System.out.println(i + " " + map.get(i));
                }
            System.out.println("END OUTPUT");

        }
    }
    public void availableActionsMenu() {
        List<Integer> list = analyzer.getAvailableActions();
        System.out.println("BEGIN OUTPUT");
        for(int i : list) {
            System.out.println(i);
        }
        System.out.println("END OUTPUT");
        System.out.println("Please selection one of the available options: ");
        int input;
        while(true) {
            try {
                arrowPrinter();
                String s = scanner.next();
                input = Integer.parseInt(s);
            }catch (NumberFormatException e){
                System.out.println("Please enter a number between 0 and 7.");
                continue;
            }
            if(input < 0 || input > 7) {
                System.out.println("Please enter a number between 0 and 7.");
                continue;
            }
            break;
        }
        switch(input) {
            case 0:
                activeUser = false;
                System.out.println("Thank you for using our program.");
                break;
            case 1:
                availableActionsMenu();
                break;
            case 2:
                printTotalPopulation();
                break;
            case 3:
                seeTotalVaccinationsPerCapita();
                break;
            case 4:
                printAveragePropertyValue();
                break;
            case 5:
                printAverageLivableArea();
                break;
            case 6:
                printTotalMarketValuePerCapita();
                break;
        }
    }
    public void printAveragePropertyValue() {
        System.out.println("Please specify the ZIP code you would like to see the average property value");
        arrowPrinter();
        String zipCode = scanner.next();
        System.out.println("BEGIN OUTPUT");
        System.out.println(analyzer.getAveragePropertyValue(zipCode));
        System.out.println("END OUTPUT");
    }
    public void printAverageLivableArea() {
        System.out.println("Please specify the ZIP code you would like to see average livable area");
        arrowPrinter();
        String zip = scanner.next();
        System.out.println("BEGIN OUTPUT");
        System.out.println(analyzer.getAverageTotalLivableArea(zip));
        System.out.println("END OUTPUT");
    }
    public void printTotalMarketValuePerCapita() {
        System.out.println("Please specify the ZIP code you would like to see total market value per capita");
        arrowPrinter();
        String nextZip = scanner.next();
        System.out.println("BEGIN OUTPUT");
        System.out.println(analyzer.getTotalMarketValuePerCapita(nextZip));
        System.out.println("END OUTPUT");
    }
    public void printTotalPopulation() {
        System.out.println("BEGIN OUTPUT");
        System.out.println(analyzer.getTotalPopulation());
        System.out.println("END OUTPUT");
    }
}