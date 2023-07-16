package Task2;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static PrintWriter fileInput;
    public static int priceOfBurger = 650;
    public static int stock = 50;
    public static int emptySlots = 10; //initially 10 empty slots of 3 cashiers
    public static int queueNo;
    public static int currentQueueNo;
    public static int newBurgers;
    public static int lastElement;
    public static int soldBurgers = 0; //initially sold burger count is 0
    public static int servedCustomersCount = 0; //initially served customer count is 0
    public static int reservedBurgersCount = 50 + newBurgers - soldBurgers - stock;

    public static int[] queueIncome = new int[3];
    //An array to sort customer names
    public static String[] names = new String[10];

    //2D array to store ASCII values of customer names
    public static int[][] asciiVal = new int[][]{
            new int[10], new int[10]
    };

    public static FoodQueue[] foodQueue = new FoodQueue[3];

    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        foodQueue[0] = new FoodQueue(2);
        foodQueue[1] = new FoodQueue(3);
        foodQueue[2] = new FoodQueue(5);
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
        System.out.println("110 or IFQ:\tView the income of each queue.");
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
            case "102", "ACQ" -> {
                addCustomer();
                viewMenu();
            }
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
            case "110", "IFQ" -> {
                viewQueueIncome();
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
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < foodQueue.length; j++) {
                if (i >= foodQueue[j].getCustomerQueue().length) {
                    System.out.print("      ");
                    continue;
                }
                if (foodQueue[j].getCustomerQueue()[i] == null) {
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
        for (int i = 0; i < foodQueue.length; i++) {
            boolean isEmpty = false;
            for (Customer customer : foodQueue[i].getCustomerQueue()) {
                if (customer == null) {
                    isEmpty = true;
                    break;
                }
            }
            if (isEmpty) {
                System.out.println("Queue " + (i + 1) + "\t: EMPTY");
            } else {
                System.out.println("Queue " + (i + 1) + "\t: OCCUPIED");
            }
        }
    }

    public static void addCustomer() {
        boolean detailLoop = true; //outer loop which takes the customer names
        boolean burgerLoop = true; //inner loop which takes the burger amount needed

        while (detailLoop) {
            Customer customer = new Customer();

            if (stock > 10) {
                System.out.print("Insert customer's first name : ");
                customer.setFirstName(Main.input.next());
                System.out.print("Insert customer's second name : ");
                customer.setSecondName(Main.input.next());

                //Validating customer names
                if (customer.getFirstName().matches("[a-zA-Z]+") && customer.getSecondName().matches("[a-zA-Z]+")) {
                    detailLoop = false;
                    while (burgerLoop) {
                        //Checking whether the burger amount is an integer
                        try {
                            //Asking for the burger requirement
                            System.out.print("Insert the burger amount : ");
                            customer.setNoOfBurgers(Main.input.nextInt());

                            //Checking whether we can serve the required burger amount
                            if (customer.getNoOfBurgers() <= stock && customer.getNoOfBurgers() > 0) {
                                //placing the customer in the shortest possible queue
                                for (int i = 0; i < 5; i++) {
                                    for (int j = 0; j < foodQueue.length; j++) {
                                        if (i >= foodQueue[j].getCustomerQueue().length) continue;
                                        if (foodQueue[j].getCustomerQueue()[i] == null) {
                                            foodQueue[j].getCustomerQueue()[i] = customer;

                                            //income of Queue
                                            queueIncome[j] += foodQueue[j].getCustomerQueue()[i].getNoOfBurgers() * priceOfBurger;
                                            i = 5;
                                            break;
                                        }
                                    }
                                }

                                //If all the queues are full adding the rest of the customers to waiting queue
                                if (emptySlots == 0) {
                                    WaitingQueue.insert(customer);
                                } else {
                                    //reserve 5 burgers to the customer and empty slots are updated afterwards
                                    stock -= customer.getNoOfBurgers();
                                    reservedBurgersCount += customer.getNoOfBurgers();
                                    emptySlots--;
                                }
                                //showing the low burger count warning
                                lowBurgerCountWarning();

                                burgerLoop = false;
                                System.out.println();
                                viewMenu();
                                break;
                            } else {
                                System.out.println("Sorry! We are unable to supply your requirement, Please Try Again Later!");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Integer required");
                            input.nextInt();
                        }
                    }
                } else {
                    System.out.println("Please check the spellings of your name!");
                }
            } else {
                System.out.println("WARNING!! Low burger count, please re-stock burgers to serve customers");
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
                            if (position >= 1 && position <= foodQueue[currentQueueNo].getCustomerQueue().length) {
                                int currentPosition = position - 1;
                                lastElement = foodQueue[currentQueueNo].getCustomerQueue().length - 1;
                                Customer removedCustomer = foodQueue[currentQueueNo].getCustomerQueue()[currentPosition];

                                //updating the rest of the positions after the removal
                                if (removedCustomer != null) {
                                    //updating the burger stock as the customer is not been served
                                    stock += removedCustomer.getNoOfBurgers();
                                    reservedBurgersCount -= removedCustomer.getNoOfBurgers();
                                    emptySlots++;

                                    //updating the queueIncome as the customer is removed without serving
                                    queueIncome[currentQueueNo] -= removedCustomer.getNoOfBurgers() * priceOfBurger;

                                    for (int i = currentPosition; i < lastElement; i++) {
                                        foodQueue[currentQueueNo].getCustomerQueue()[i] = foodQueue[currentQueueNo].getCustomerQueue()[i + 1];
                                    }

                                    //Adding customers from the waiting queue to the food queue
                                    if (WaitingQueue.customerCount == 0) {
                                        foodQueue[currentQueueNo].getCustomerQueue()[lastElement] = null;
                                    } else {
                                        foodQueue[currentQueueNo].getCustomerQueue()[lastElement] = WaitingQueue.remove();

                                        //income of the newly added customer (Update)
                                        queueIncome[currentQueueNo] += foodQueue[currentQueueNo].getCustomerQueue()[lastElement].getNoOfBurgers() * priceOfBurger;
                                        emptySlots--;
                                    }

                                    System.out.println("Customer is removed from queue " + queueNo);
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
                    int lastElement = foodQueue[currentQueueNo].getCustomerQueue().length - 1;


                    if (foodQueue[currentQueueNo].getCustomerQueue()[0] != null) {
                        soldBurgers += foodQueue[currentQueueNo].getCustomerQueue()[0].getNoOfBurgers();
                        servedCustomersCount += 1;
                        emptySlots++;
                        reservedBurgersCount -= foodQueue[currentQueueNo].getCustomerQueue()[0].getNoOfBurgers();

                        // removing first customer in line and updating the rest
                        for (int i = 0; i < lastElement; i++) {
                            foodQueue[currentQueueNo].getCustomerQueue()[i] = foodQueue[currentQueueNo].getCustomerQueue()[i + 1];
                        }

                        //Adding customers from the waiting queue to the food queue
                        if (WaitingQueue.customerCount == 0) {
                            foodQueue[currentQueueNo].getCustomerQueue()[lastElement] = null;
                        } else {
                            foodQueue[currentQueueNo].getCustomerQueue()[lastElement] = WaitingQueue.remove();

                            //income update
                            queueIncome[currentQueueNo] += foodQueue[currentQueueNo].getCustomerQueue()[lastElement].getNoOfBurgers() * priceOfBurger;
                            emptySlots--;
                        }
                        System.out.println("Served customer is removed from queue " + queueNo);

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

    public static void viewQueueIncome() {
        System.out.println("*********************");
        System.out.println("*   Queue Income    *");
        System.out.println("*********************");
        for (int i = 0; i < queueIncome.length; i++) {
            System.out.println("Queue " + (i + 1) + " : Rs." + queueIncome[i]);
        }
    }

    //Referred a Tutorial for sorting
    public static void sortCustomersAlphabetically() {
        // adding names of customers to an array named names
        int count = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < foodQueue.length; j++) {
                // adding ascii values of first 2 characters to asciiValues array
                if (i >= foodQueue[j].getCustomerQueue().length) continue;
                if (foodQueue[j].getCustomerQueue()[i] != null) {
                    names[count] = foodQueue[j].getCustomerQueue()[i].getFullName().toLowerCase();
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
        int number = 1;
        for (String name : names) {
            if (name != null) {
                String fName = name.split(" ")[0].substring(0, 1).toUpperCase() + name.split(" ")[0].substring(1);
                String sName = name.split(" ")[1].substring(0, 1).toUpperCase() + name.split(" ")[1].substring(1);
                System.out.println(number + ". " + fName + " " + sName);
                number++;
            }
        }
    }

    public static void storeProgramDataIntoFile() {
        int size = 10;
        try {
            // creating and writing the file
            fileInput = new PrintWriter("task2.txt");

            //Main Data
            String mainData = String.format("""
                    Sold Burger Count      : %s \n
                    Reserved Burger Count  : %s
                    Remaining Burger Count : %s \n
                    """, soldBurgers, reservedBurgersCount, stock);

            fileInput.write(mainData);

            //Queue data
            for (int i = 0; i < 3; i++) {
                fileInput.write(String.format("Cashier %s   -   [Rs. %05d]    : ", (i + 1), queueIncome[i]));
                for (int j = 0; j < 5; j++) {
                    if (j >= foodQueue[i].getCustomerQueue().length) continue;
                    if (foodQueue[i].getCustomerQueue()[j] != null) {
                        capitalizeName(foodQueue[i].getCustomerQueue()[j].getFullName().split(" "));
                        if (j + 1 != foodQueue[i].getCustomerQueue().length) {
                            if (foodQueue[i].getCustomerQueue()[j + 1] != null) fileInput.print(", ");
                        }
                    }
                }
                fileInput.println(" ");
            }

            //other Data
            String otherData = String.format("""
                    \nNo.of Empty Slots              : %s \n
                    No.of Waiting Queue Customers  : %s\n
                    """, emptySlots, WaitingQueue.customerCount);
            fileInput.write(otherData);

            WaitingQueue waitingQueue = new WaitingQueue(size);
            waitingQueue.getCustomersOfWaitingQueue();

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
                    queueIncome[queueIndex] = Integer.parseInt(line.split(">")[1].trim().split("\\s+")[1].trim());
                    for (int i = 0; i < queueTokens.length; i++) {
                        if (queueTokens[i] == "") break;
                        String[] customerAttributes = queueTokens[i].split("\\s+");
                        foodQueue[queueIndex].getCustomerQueue()[i] = setCustomerLoaded(customerAttributes);
                    }
                    queueIndex++;
                } else if (line.startsWith("Customers in Waiting Queue")) {
                    String[] waitingQueueTokens = line.split(":")[1].trim().split(", ");
                    for (int i = 0; i < waitingQueueTokens.length; i++) {
                        if (waitingQueueTokens[i] == "") break;
                        String[] waitingCustomerAttributes = waitingQueueTokens[i].split("\\s+");
                        WaitingQueue.insert(setCustomerLoaded(waitingCustomerAttributes));
                        WaitingQueue.customerCount--;
                    }
                    break;
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
                    """, soldBurgers, reservedBurgersCount, stock, emptySlots, servedCustomersCount);
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

    public static void capitalizeName(String[] nameParts) {
        String fName = nameParts[0].substring(0, 1).toUpperCase() + nameParts[0].substring(1);
        String sName = nameParts[1].substring(0, 1).toUpperCase() + nameParts[1].substring(1);
        fileInput.print(fName + " " + sName);
    }

    public static Customer setCustomerLoaded(String[] customerAttributes) {
        Customer customer = new Customer();
        customer.setFirstName(customerAttributes[0].toLowerCase());
        customer.setSecondName(customerAttributes[1].toLowerCase());
        customer.setNoOfBurgers(Integer.parseInt(customerAttributes[3]));

        return customer;
    }
}