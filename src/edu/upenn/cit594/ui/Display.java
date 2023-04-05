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
                "\n7: Show the total increase in full vaccinations percentage between two dates + ZIP codes market value per capita");

        arrowPrinter();
    }

    public void displayData() throws Exception {
        Logger log = Logger.getInstance();
        menuOptions();
        while (activeUser) {
            int firstInput;
            try {
                String s = scanner.next();
                log.writeToLog(System.currentTimeMillis() + " " + s);
                firstInput = Integer.parseInt(s);
            }catch (NumberFormatException e){
                System.out.println("Please enter a number between 0 and 7.");
                arrowPrinter();
                continue;
            }
            if(firstInput < 0 || firstInput > 7) {
                System.out.println("Please enter a number between 0 and 7.");
                arrowPrinter();
                continue;
            }
            switch (firstInput) {
                case 0:
                    activeUser = false;
                    System.out.println("Thank you for using our program.");
                    return;
                case 1:
                    availableActionsMenu(log);
                    break;
                case 2:
                    if(!availableActionCheck(firstInput)) {
                        System.out.println("Action not available. Please choose another.");
                        menuOptions();
                        break;
                    }
                    printTotalPopulation();
                    break;
                case 3:
                    if(!availableActionCheck(firstInput)) {
                        System.out.println("Action not available. Please choose another.");
                        menuOptions();
                        break;
                    }
                    seeTotalVaccinationsPerCapita(log);
                    break;
                case 4:
                    if(!availableActionCheck(firstInput)) {
                        System.out.println("Action not available. Please choose another.");
                        menuOptions();
                        break;
                    }
                    printAveragePropertyValue(log);
                    break;
                case 5:
                    if(!availableActionCheck(firstInput)) {
                        System.out.println("Action not available. Please choose another.");
                        menuOptions();
                        break;
                    }
                    printAverageLivableArea(log);
                    break;
                case 6:
                    if(!availableActionCheck(firstInput)) {
                        System.out.println("Action not available. Please choose another.");
                        menuOptions();
                        break;
                    }
                    printTotalMarketValuePerCapita(log);
                    break;
                case 7:
                    if(!availableActionCheck(firstInput)) {
                        System.out.println("Action not available. Please choose another.");
                        menuOptions();
                        break;
                    }
                    increaseInFullVaccinations(log);
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

    public void seeTotalVaccinationsPerCapita(Logger log) {
        System.out.println("partial or full");
        arrowPrinter();
        String next = scanner.next().toLowerCase(Locale.ROOT);
        log.writeToLog(System.currentTimeMillis() + " " + next);
        while (!next.equals("partial") && !next.equals("full")) {
            System.out.println("Please enter either partial or full.");
            arrowPrinter();
            next = scanner.next();
            log.writeToLog(System.currentTimeMillis() + " " + next);
        }
        System.out.println("Please enter the date in YYYY-MM-DD format");
        arrowPrinter();
        String date = scanner.next();
        log.writeToLog(System.currentTimeMillis() + " " + date);
        while (!validateDateInfo(date)) {
            System.out.println("Please try again: (format YYYY-MM-DD)");
            arrowPrinter();
            date = scanner.next();
            log.writeToLog(System.currentTimeMillis() + " " + date);
        }
        TreeMap<Integer, Double> map = analyzer.getVaccinationsPerCapita(next, date);
        if(map.isEmpty()) {
            System.out.println("BEGIN OUTPUT");
            System.out.println("0");
            System.out.println("END OUTPUT");
            menuOptions();
            return;
        }
        System.out.println("BEGIN OUTPUT");
        for (int i : map.keySet()) {
            System.out.println(i + " " + map.get(i));
        }
        System.out.println("END OUTPUT");
        menuOptions();
    }
    public void availableActionsMenu(Logger log) {
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
                log.writeToLog(System.currentTimeMillis() + " " + s);
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
                return;
            case 1:
                availableActionsMenu(log);
                break;
            case 2:
                if(!availableActionCheck(input)) {
                    System.out.println("Action not available. Please choose another.");
                    menuOptions();
                    break;
                }
                printTotalPopulation();
                break;
            case 3:
                if(!availableActionCheck(input)) {
                    System.out.println("Action not available. Please choose another.");
                    menuOptions();
                    break;
                }
                seeTotalVaccinationsPerCapita(log);
                break;
            case 4:
                if(!availableActionCheck(input)) {
                    System.out.println("Action not available. Please choose another.");
                    menuOptions();
                    break;
                }
                printAveragePropertyValue(log);
                break;
            case 5:
                if(!availableActionCheck(input)) {
                    System.out.println("Action not available. Please choose another.");
                    menuOptions();
                    break;
                }
                printAverageLivableArea(log);
                break;
            case 6:
                if(!availableActionCheck(input)) {
                    System.out.println("Action not available. Please choose another.");
                    menuOptions();
                    break;
                }
                printTotalMarketValuePerCapita(log);
                break;
            case 7:
                if(!availableActionCheck(input)) {
                    System.out.println("Action not available. Please choose another.");
                    menuOptions();
                    break;
                }
                increaseInFullVaccinations(log);
                break;
        }
    }
    public void printAveragePropertyValue(Logger log) {
        System.out.println("Please specify the 5 digit ZIP code for which you would like to see the average property value");
        arrowPrinter();
        String zipCode = scanner.next();
        log.writeToLog(System.currentTimeMillis() + " " + zipCode);
        while(!zipCode.matches("^\\d{5}$")) {
            System.out.println("Please enter a 5 digit ZIP code");
            arrowPrinter();
            zipCode = scanner.next();
            log.writeToLog(System.currentTimeMillis() + " " + zipCode);
        }
        System.out.println("BEGIN OUTPUT");
        System.out.println(analyzer.getAveragePropertyValue(zipCode));
        System.out.println("END OUTPUT");
        menuOptions();
    }
    public void printAverageLivableArea(Logger log) {
        System.out.println("Please specify the 5 digit ZIP code for which you would like to see average livable area");
        arrowPrinter();
        String zip = scanner.next();
        log.writeToLog(System.currentTimeMillis() + " " + zip);
        while(!zip.matches("^\\d{5}$")) {
            System.out.println("Please enter a 5 digit ZIP code");
            arrowPrinter();
            zip = scanner.next();
            log.writeToLog(System.currentTimeMillis() + " " + zip);
        }
        System.out.println("BEGIN OUTPUT");
        System.out.println(analyzer.getAverageTotalLivableArea(zip));
        System.out.println("END OUTPUT");
        menuOptions();
    }
    public void printTotalMarketValuePerCapita(Logger log) {
        System.out.println("Please specify the 5 digit ZIP code for which you would like to see total market value per capita");
        arrowPrinter();
        String nextZip = scanner.next();
        log.writeToLog(System.currentTimeMillis() + " " + nextZip);
        while(!nextZip.matches("^\\d{5}$")) {
            System.out.println("Please enter a 5 digit ZIP code");
            arrowPrinter();
            nextZip = scanner.next();
            log.writeToLog(System.currentTimeMillis() + " " + nextZip);
        }
        System.out.println("BEGIN OUTPUT");
        System.out.println(analyzer.getTotalMarketValuePerCapita(nextZip));
        System.out.println("END OUTPUT");
        menuOptions();
    }
    public void printTotalPopulation() {
        System.out.println("BEGIN OUTPUT");
        System.out.println(analyzer.getTotalPopulation());
        System.out.println("END OUTPUT");
        menuOptions();
    }
    public boolean availableActionCheck(int input) {
        if(!analyzer.getAvailableActions().contains(input)) return false;
        return true;
    }
    public void increaseInFullVaccinations(Logger log) {
        System.out.println("Please enter the start date in YYYY-MM-DD format");
        String startDate = scanner.next();
        log.writeToLog(System.currentTimeMillis() + " " + startDate);
        while(!validateDateInfo(startDate)) {
            System.out.println("Please try again: (format YYYY-MM-DD)");
            startDate = scanner.next();
            log.writeToLog(System.currentTimeMillis() + " " + startDate);
        }
        System.out.println("Please enter the end date in YYYY-MM-DD format");
        String endDate = scanner.next();
        log.writeToLog(System.currentTimeMillis() + " " + endDate);
        while(!validateDateInfo(startDate)) {
            System.out.println("Please try again: (format YYYY-MM-DD)");
            endDate = scanner.next();
            log.writeToLog(System.currentTimeMillis() + " " + endDate);
        }
        TreeMap<String,HashMap<Integer,Double>> map = analyzer.getVaccinationIncreaseForDate(startDate,endDate);
        if(map.isEmpty()) {
            System.out.println("BEGIN OUTPUT");
            System.out.println("0");
            System.out.println("END OUTPUT");
            menuOptions();
            return;
        }else {
            System.out.println("BEGIN OUTPUT");
            System.out.println("ZIP " + "\t" + "MVPC" + "\t" + "Percent Change");
            for (String i : map.keySet()) {
                for (int j : map.get(i).keySet()) {
                    System.out.println(i + "\t" + j + "\t" + "\t" + map.get(i).get(j) + "%");
                }
            }
            System.out.println("END OUTPUT");
        }
        menuOptions();
    }
}