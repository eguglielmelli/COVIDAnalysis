package edu.upenn.cit594.ui;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;
import java.util.*;


public class Display {
    //create scanner to be used in many different methods
    Scanner scanner = new Scanner(System.in);
    //we will pass the processor in order to make call the computation methods
    private Processor analyzer;

    //boolean that denotes the user is still actively using the program
    private boolean activeUser = true;

    public Display(Processor analyzer) {
        this.analyzer = analyzer;
    }

    //display menu that will be shown to the user
    public void menuOptions() {
        System.out.println("Please choose from the list of options: \n0: Exit the program \n1: Show available options " +
                "\n2: Show total population for all ZIP codes " +
                "\n3: Show the total vaccinations per capita (either partial or full) for each ZIP code for the specified date" +
                "\n4: Show the average market value for properties in a specified ZIP code" +
                "\n5: Show the average total livable area for properties in a specified ZIP code" +
                "\n6: Show the total market value of properties, per capita, for a specified ZIP code" +
                "\n7: Show the total increase in full vaccinations percentage between two dates + ZIP codes market value per capita");

        arrowPrinter();
    }

    /**
     * The display method for our program that will be called by main and continuously takes input
     * until the user enters "0"
     * @throws Exception
     */
    public void startProgram() throws Exception {

        Logger log = Logger.getInstance();
        menuOptions();
        while (activeUser) {
            //try to parse first input as int, if not then re-prompt
            int firstInput;
            try {
                String s = scanner.next();
                //log our input each time the user types something
                log.writeToLog(s);
                System.out.println();
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
                    //for each input, we will check if its the list, if not immediate reprompt
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
    //quick check to match the date format (YYYY-MM-DD) given in the files
    public boolean validateDateInfo(String date) {
        if(date.matches("^\\d{4}-\\d{2}-\\d{2}$")) return true;
        return false;
    }

    /**
     * This method takes the inputs and processes the vaccinations per capita
     * an empty map denotes either dates are out of range or there is no data
     * @param log logger to log user input
     */
    public void seeTotalVaccinationsPerCapita(Logger log) {

        System.out.println("partial or full");
        arrowPrinter();
        String next = scanner.next();
        log.writeToLog(next);
        System.out.println();

        while (!next.equals("partial") && !next.equals("full")) {
            System.out.println("Please enter either partial or full.");
            arrowPrinter();
            next = scanner.next();
            log.writeToLog(next);
            System.out.println();
        }

        System.out.println("Please enter the date in YYYY-MM-DD format");
        arrowPrinter();
        String date = scanner.next();
        log.writeToLog(date);
        System.out.println();

        while (!validateDateInfo(date)) {
            System.out.println("Please try again: (format YYYY-MM-DD)");
            arrowPrinter();
            date = scanner.next();
            log.writeToLog(date);
            System.out.println();
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

    /**
     * Helper method represents the "available actions" menu
     * will reprompt user to main menu if an unavailable action is inputted
     * @param log logger to log user input
     */
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
                log.writeToLog(s);
                System.out.println();
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
                //for each input, we will check if its the list, if not immediate reprompt
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

    /**
     * prints the average property value of the zip code given
     * @param log logger to log user input
     */
    public void printAveragePropertyValue(Logger log) {

        System.out.println("Please specify the 5 digit ZIP code for which you would like to see the average property value");
        arrowPrinter();
        String zipCode = scanner.next();
        log.writeToLog(zipCode);
        System.out.println();
        //we're looking for 5 digit pattern, otherwise user will be reprompted
        while(!zipCode.matches("^\\d{5}$")) {
            System.out.println("Please enter a 5 digit ZIP code");
            arrowPrinter();
            zipCode = scanner.next();
            log.writeToLog(zipCode);
            System.out.println();
        }

        System.out.println("BEGIN OUTPUT");
        System.out.println(analyzer.getAveragePropertyValue(zipCode));
        System.out.println("END OUTPUT");
        menuOptions();
    }

    /**
     * Prints the average livable area for the zip code given
     * @param log logger to log inputs
     */
    public void printAverageLivableArea(Logger log) {

        System.out.println("Please specify the 5 digit ZIP code for which you would like to see average livable area");
        arrowPrinter();
        String zip = scanner.next();
        log.writeToLog(zip);
        System.out.println();
        //looking for 5 digit pattern
        while(!zip.matches("^\\d{5}$")) {
            System.out.println("Please enter a 5 digit ZIP code");
            arrowPrinter();
            zip = scanner.next();
            log.writeToLog(zip);
            System.out.println();
        }

        System.out.println("BEGIN OUTPUT");
        System.out.println(analyzer.getAverageTotalLivableArea(zip));
        System.out.println("END OUTPUT");
        menuOptions();
    }

    /**
     * Prints the total market value per capita of a given zip code
     * @param log logger to log inputs
     */
    public void printTotalMarketValuePerCapita(Logger log) {
        System.out.println("Please specify the 5 digit ZIP code for which you would like to see total market value per capita");
        arrowPrinter();
        String nextZip = scanner.next();
        log.writeToLog(nextZip);
        System.out.println();
        //also looking for 5 digit pattern
        while(!nextZip.matches("^\\d{5}$")) {
            System.out.println("Please enter a 5 digit ZIP code");
            arrowPrinter();
            nextZip = scanner.next();
            log.writeToLog(nextZip);
            System.out.println();
        }

        System.out.println("BEGIN OUTPUT");
        System.out.println(analyzer.getTotalMarketValuePerCapita(nextZip));
        System.out.println("END OUTPUT");
        menuOptions();
    }
    //prints the total population of all zip codes
    public void printTotalPopulation() {
        System.out.println("BEGIN OUTPUT");
        System.out.println(analyzer.getTotalPopulation());
        System.out.println("END OUTPUT");
        menuOptions();
    }
    //keeps track of the actions that are available to the user
    public boolean availableActionCheck(int input) {
        if(!analyzer.getAvailableActions().contains(input)) return false;
        return true;
    }

    /**
     * custom feature that shows each zips percentage increase in full vaccinations over two given dates
     * and also displays the market value per capita in each respective zip code
     * @param log logger to log user input
     */
    public void increaseInFullVaccinations(Logger log) {
        System.out.println("This feature will immediately return 0 under any of the following conditions: \n1.) The end date occurs before the start date " +
                "\n2.) The dates are either both before or after the range of our data (2021-03-25 - 2022-11-07)" +
                "\n3.) The start date and the end date are the same" +
                "\n4.) You put in an invalid date (ie 2022-13-40)" +
                "\nIf a particular ZIP code has no data for the dates you give, we will retrieve the data from the closest dates");
        System.out.println();
        System.out.println("Please enter the start date in YYYY-MM-DD format");
        arrowPrinter();
        String startDate = scanner.next();
        log.writeToLog(startDate);
        System.out.println();

        while(!validateDateInfo(startDate)) {
            System.out.println("Please try again: (format YYYY-MM-DD)");
            arrowPrinter();
            startDate = scanner.next();
            log.writeToLog(startDate);
            System.out.println();
        }

        System.out.println("Please enter the end date in YYYY-MM-DD format");
        arrowPrinter();
        String endDate = scanner.next();
        log.writeToLog(endDate);
        System.out.println();

        while(!validateDateInfo(endDate)) {
            System.out.println("Please try again: (format YYYY-MM-DD)");
            arrowPrinter();
            endDate = scanner.next();
            log.writeToLog(endDate);
            System.out.println();
        }
        //call our analyzer method to get access to the treemap for printing
        TreeMap<String,HashMap<Integer,Double>> map = analyzer.getVaccinationIncreaseForDate(startDate,endDate);
        //an empty map denotes either invalid dates or no data is available for one or both of the dates
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
                    System.out.printf("%-7s %-6d %12s\n", i, j, map.get(i).get(j) + "%");
                }
            }
            System.out.println("END OUTPUT");
        }
        menuOptions();
    }
}