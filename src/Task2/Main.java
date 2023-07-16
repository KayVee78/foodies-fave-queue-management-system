package Task2;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    /* Declaring constants according to the naming conventions mentioned in the Oracle Docs */
    private static final int TOTAL_OF_QUEUE1 = 2;
    private static final int TOTAL_OF_QUEUE2 = 3;
    private static final int TOTAL_OF_QUEUE3 = 5;
    public static int stock = 50;
    public static int emptySlots = 10; //initially 10 empty slots of 3 cashiers
    public static int queueNo;
    public static int currentQueueNo;
    public static int newBurgers;
    public static int lastElement;
    public static int soldBurgers = 0; //initially sold burger count is 0
    public static int servedCustomersCount = 0; //initially served customer count is 0
    public static int reservedBurgersCount = 50 + newBurgers - soldBurgers - stock;

    //storing queue data
    public static String[][] cashiers = new String[][]{
            new String[2], new String[3], new String[5]
    };
    //An array to sort customer names
    public static String[] names = new String[10];

    //2D array to store ASCII values of customer names
    public static int[][] asciiVal = new int[][]{
            new int[10], new int[10]
    };

    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        viewMenu();
    }

    public static void viewMenu() {
        System.out.println("* * * * * * * * * * * * * *");
        System.out.println(" Foodies Fave Food Center ");
        System.out.println("* * * * * * * * * * * * * *");
        System.out.println();

        System.out.println("---------");
        System.out.println(" M E N U");
        System.out.println("---------");
        System.out.println("100 or VFQ:\tView all Queues.");
        System.out.println("101 or VEQ:\tView all Empty Queues.");
        System.out.println("102 or ACQ:\tAdd customer to a Queue.");
        System.out.println("103 or RCQ:\tRemove a customer from a Queue.");
        System.out.println("104 or PCQ:\tRemove a served customer.");
        System.out.println("105 or VCS:\tView Customers Sorted in alphabetical order");
        System.out.println("106 or SPD:\tStore Program Data into file.");
        System.out.println("107 or LPD:\tLoad Program Data from file.");
        System.out.println("108 or STK:\tView Remaining burgers Stock.");
        System.out.println("109 or AFS:\tAdd burgers to Stock.");
        System.out.println("999 or EXT:\tExit the Program.");
        System.out.println();
        Scanner input = new Scanner(System.in);
        System.out.print("Select an option from the above : ");
        String option = input.next();

        switch (option.toLowerCase()) {
            case "100", "VFQ" -> {
                viewAllQueues();
                System.out.println();
                viewMenu();
            }
            case "101", "VEQ" -> {
                viewEmptyQueues();
                System.out.println();
                viewMenu();
            }
            case "102", "ACQ" -> FoodQueue.addCustomer();
            case "103", "RCQ" -> {
                removeCustomer();
            }
            case "104", "PCQ" -> {
                removeServedCustomer();
            }
            case "105", "VCS" -> {
                sortCustomersAlphabetically();
                System.out.println();
                viewMenu();
            }
            case "106", "SPD" -> {
                storeProgramDataIntoFile();
                System.out.println();
                viewMenu();
            }
            case "107", "LPD" -> {
                loadProgramDataFromFile();
                System.out.println();
                viewMenu();
            }
            case "108", "STK" -> {
                System.out.println("Remaining stock count : " + stock);
                lowBurgerCountWarning();
                System.out.println();
                viewMenu();
            }
            case "109", "AFS" -> {
                restockBurgerCount();
                System.out.println();
                viewMenu();
            }
            case "999", "EXT" -> {
                System.out.println("Program's exiting....");
                System.exit(0);
            }
            default -> {
                System.out.println("Please enter a valid option!");
                viewMenu();
            }
        }
    }

    static void getQueue() {
        System.out.print("Choose a queue to proceed (1, 2 or 3) : ");
        queueNo = input.nextInt();
    }

    static void lowBurgerCountWarning() {
        if (stock <= 10) {
            System.out.println("WARNING!! Low burger count, please re-stock burgers to serve customers");
        }
    }

    static void viewAllQueues() {
        System.out.println("*****************");
        System.out.println("*   Cashiers    *");
        System.out.println("*****************");
        for (int i = 0; i < cashiers[2].length; i++) {
            for (int j = 0; j < cashiers.length; j++) {
                if (i >= cashiers[j].length) {
                    System.out.print("      ");
                    continue;
                }
                if (cashiers[j][i] == null) {
                    System.out.print("  X   ");
                } else {
                    System.out.print("  O   ");
                }
            }
            System.out.println("");
        }
    }

    public static void viewEmptyQueues() {
        System.out.println("*********************");
        System.out.println("*   Empty Queues    *");
        System.out.println("*********************");
        for (int i = 0; i < cashiers.length; i++) {
            boolean isEmpty = true;
            for (int j = 0; j < cashiers[i].length; j++) {
                if (cashiers[i][j] == null) {
                    isEmpty = true;
                    break;
                } else {
                    isEmpty = false;
                }
            }
            if (isEmpty) {
                System.out.println("Queue " + (i + 1) + "\t: EMPTY");
            } else {
                System.out.println("Queue " + (i + 1) + "\t: OCCUPIED");
            }
        }
    }

    public static void removeCustomer() {
        boolean removeCustomerLoop1 = true;
        boolean removeCustomerLoop2 = true;

        while (removeCustomerLoop1) {
            //Checking if the queue number entered is valid
            try {
                getQueue();
                currentQueueNo = queueNo - 1;

                //Checking if the queue number entered is a cashier no.
                if (queueNo >= 1 && queueNo <= 3) {
                    removeCustomerLoop1 = false;
                    while (removeCustomerLoop2) {
                        try {
                            System.out.print("Choose a customer's position : ");
                            int position = input.nextInt();

                            //Validating the position
                            if (position >= 1 && position <= cashiers[currentQueueNo].length) {
                                int currentPosition = position - 1;
                                lastElement = cashiers[currentPosition].length - 1;

                                //updating the rest of the positions after the removal
                                if (cashiers[currentQueueNo][currentPosition] != null) {
                                    for (int i = currentPosition; i < lastElement; i++) {
                                        cashiers[currentQueueNo][i] = cashiers[currentQueueNo][i + 1];
                                    }
                                    cashiers[currentQueueNo][lastElement] = null;

                                    System.out.println("Customer is removed from queue " + queueNo);

                                    // increasing the burger count as the customer had not been served
                                    stock += 5;
                                    reservedBurgersCount -= 5;
                                    emptySlots++;
                                    removeCustomerLoop2 = false;

                                } else {
                                    System.out.println("This slot is already empty!\n");
                                }
                                viewMenu();

                            } else {
                                System.out.println("Please enter a valid position!");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Integer required");
                            input.nextInt();
                        }
                    }
                } else {
                    System.out.println("Please insert a valid queue number!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Integer required");
                input.nextInt();
            }
        }
    }

    static void removeServedCustomer() {
        boolean removeServedCustomerLoop = true;
        while (removeServedCustomerLoop) {
            try {
                getQueue();
                //Checking if the queue number entered is a cashier no.
                if (queueNo >= 1 && queueNo <= 3) {
                    currentQueueNo = queueNo - 1;
                    int lastElement = cashiers[currentQueueNo].length - 1;

                    // removing first customer in line and updating the rest
                    if (cashiers[currentQueueNo][0] != null) {
                        for (int i = 0; i < lastElement; i++) {
                            cashiers[currentQueueNo][i] = cashiers[currentQueueNo][i + 1];
                        }
                        cashiers[currentQueueNo][lastElement] = null;
                        System.out.println("Served customer is removed from queue " + queueNo);

                        // updating soldBurgers and servedCustomersCount
                        soldBurgers += 5;
                        servedCustomersCount += 1;
                        emptySlots++;
                        reservedBurgersCount -= 5;

                        removeServedCustomerLoop = false;
                        System.out.println();
                        viewMenu();
                    } else {
                        System.out.println("The selected queue is already empty");
                    }
                } else {
                    System.out.println("Please insert a valid queue number!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Integer required");
                input.nextInt();
            }
        }
    }

    //Referred a Tutorial for sorting
    public static void sortCustomersAlphabetically() {
        // adding names of customers to an array named names
        int count = 0;
        for (int i = 0; i < cashiers[2].length; i++) {
            for (int j = 0; j < cashiers.length; j++) {
                // adding ascii values of first 2 characters to asciiValues array
                if (i >= cashiers[j].length) continue;
                if (cashiers[j][i] != null) {
                    names[count] = cashiers[j][i].toLowerCase();
                    asciiVal[0][count] = names[count].charAt(0);
                    if (names[count].length() > 1) {
                        asciiVal[1][count] = names[count].charAt(1);
                    }
                    count++;
                }
            }
        }

        // bubble sorting the first 2 characters of each name
        for (int i = 0; i < count; i++) {
            if (names[i] == null || asciiVal[0][i] == 0) continue;
            for (int j = 0; j < count; j++) {
                if (asciiVal[0][i] < asciiVal[0][j] || (asciiVal[0][i] == asciiVal[0][j] && asciiVal[1][i] < asciiVal[1][j])) {
                    // Swapping names
                    String temp = names[j];
                    names[j] = names[i];
                    names[i] = temp;

                    // Swapping first character values
                    int temp1 = asciiVal[0][j];
                    asciiVal[0][j] = asciiVal[0][i];
                    asciiVal[0][i] = temp1;

                    // Swapping second character values
                    int temp2 = asciiVal[1][j];
                    asciiVal[1][j] = asciiVal[1][i];
                    asciiVal[1][i] = temp2;
                }
            }
        }

        //sorted name list
        int rank = 1;
        for (String name : names) {
            if (name != null) {
                System.out.println(rank + ". " + name);
                rank++;
            }
        }
    }

    public static void storeProgramDataIntoFile() {
        try {
            // creating and writing the file
            PrintWriter fileInput = new PrintWriter("task2.txt");

            //Main Data
            String mainData = String.format("""
                    Sold Burger Count      : %s
                    Reserved Burger Count  : %s
                    Remaining Burger Count : %s \n
                    """, soldBurgers, reservedBurgersCount, stock);

            fileInput.write(mainData);

            //Queue data
            for (int i = 0; i < 3; i++) {
                fileInput.write("Queue " + (i + 1) + "              : ");
                for (int j = 0; j < 5; j++) {
                    if (j >= cashiers[i].length) continue;
                    fileInput.print(cashiers[i][j] + "  ");
                }
                fileInput.println(" ");
            }

            //other Data
            String otherData = String.format("""
                    No. of Empty slots     : %s \n
                    Served Customers Count : %s \n \n \n
                    """, emptySlots, servedCustomersCount);
            fileInput.write(otherData);

            fileInput.close();

            System.out.println("Program data stored successfully in the text file!\n");
            viewMenu();
        } catch (IOException e) {
            System.out.println("Failed to process request: " + e.getMessage());
        }

    }

    public static void loadProgramDataFromFile() {
        try {
            File file = new File("task2.txt");
            Scanner fileReader = new Scanner(file);

            //needed local variables for this method
            String line;
            int queueIndex = 0;

            // reading and adding values to all variables
            while (fileReader.hasNextLine()) {
                line = fileReader.nextLine();

                if (line.startsWith("Sold Burger Count")) {
                    soldBurgers = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("Reserved Burger Count")) {
                    reservedBurgersCount = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("Remaining Burger Count")) {
                    stock = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("No. of Empty slots")) {
                    emptySlots = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("Served Customers Count")) {
                    servedCustomersCount = Integer.parseInt(line.split(":")[1].trim());
                }
                //Queue part
                else if (line.startsWith("Queue")) {
                    String[] queueTokens = line.split(":")[1].trim().split("\\s+");
                    for (int i = 0; i < queueTokens.length; i++) {
                        if (!queueTokens[i].equals("null")) {
                            cashiers[queueIndex][i] = queueTokens[i];
                        }
                    }
                    queueIndex++;
                }
            }
            fileReader.close();

            // printing saved data into the console
            String fileOutput = String.format("""
                            Sold Burger Count      : %s\n
                            Reserved Burger Count  : %s
                            Remaining Burger Count : %s \n
                            No. of Empty Queues    : %s
                            Served Customers Count : %s \n
                            Cashier 1              : %s
                            Cashier 2              : %s
                            Cashier 3              : %s
                            """, soldBurgers, reservedBurgersCount, stock, emptySlots, servedCustomersCount,
                    Arrays.toString(cashiers[0]), Arrays.toString(cashiers[1]), Arrays.toString(cashiers[2]));
            System.out.println(fileOutput);
        } catch (IOException e) {
            System.out.println("Failed to load data: " + e.getMessage());
        }
    }

    public static void restockBurgerCount() {
        while (stock < 50) {
            stock += 5;
        }
        System.out.println("Burgers Restocked! Stock includes " + stock + " burgers");
    }
}
