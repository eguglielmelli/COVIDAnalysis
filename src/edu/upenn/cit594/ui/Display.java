package edu.upenn.cit594.ui;
import edu.upenn.cit594.processor.Analyzer;

import java.util.Locale;
import java.util.Scanner;

public class Display {

    private Analyzer analyzer;

    public Display(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    public void displayData() throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please choose from the list of options: \n0: Exit the program \n1: Show available options " +
                "\n2: Show total population for all ZIP codes " +
                "\n3: Show the total vaccinations per capita for each ZIP code for the specified date" +
                "\n4: Show the average market value for properties in a specified ZIP code" +
                "\n5: Show the average total livable area for properties in a specified ZIP code" +
                "\n6: Show the total market value of properties, per capita, for a specified ZIP code" +
                "\n7: This is a placeholder for our custom feature");
        int firstInput = scanner.nextInt();
        switch (firstInput) {
            case 0:
                System.out.println("Thank you for using our program.");
                return;
            case 1:
                return;
            case 2:
                System.out.println(analyzer.totalPopulation());
                return;
            case 3:
                System.out.println("partial or full");
                String next = scanner.next().toLowerCase(Locale.ROOT);
                if(next.equals("partial") || next.equals("full")) {
                    System.out.println("Please enter the date in YYYY-MM-DD format");
                    String date = scanner.next();
                    analyzer.vaccinationsPerCapita(next,date);
                    return;
                }
            case 4:
                System.out.println("Please specify the ZIP code you would like to see the average property value");
                String zipCode = scanner.next();
                analyzer.averagePropertyValue(zipCode);
                return;
            case 5:
                System.out.println("Please specify the ZIP code you would like to see average livable area");
                String zip = scanner.next();
                analyzer.averageTotalLivableArea(zip);
                return;
            case 6:
                System.out.println("Please specify the ZIP code you would like to see total market value per capita");
                String nextZip = scanner.next();
                analyzer.totalMarketValuePerCapita(nextZip);
                return;
            case 7:
                System.out.println("This will be our custom feature");


        }
        scanner.close();
    }

}
