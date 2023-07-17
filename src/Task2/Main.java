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

    public static void getQueue() {
        System.out.print("Choose a queue to proceed (1, 2 or 3) : ");
        queueNo = input.nextInt();
    }

    public static void capitalizeName(String[] nameParts) {
        String fName = nameParts[0].substring(0, 1).toUpperCase() + nameParts[0].substring(1);
        String sName = nameParts[1].substring(0, 1).toUpperCase() + nameParts[1].substring(1);
        fileInput.print(fName + " " + sName);
    }

    public static void lowBurgerCountWarning() {
        if (stock <= 10) {
            System.out.println("WARNING!! Low burger count, please re-stock burgers to serve customers");
        }
    }

    //OPTION-100 Visual representation of the 3 queues O-occupied X-empty
    public static void viewAllQueues() {
        System.out.println("*****************");
        System.out.println("*   Cashiers    *");
        System.out.println("*****************");
        for (int i = 0; i < 5; i++) { //outer loop iterates for 5 (maximum no of customers that can be in a col)
            //inner loop iterates through the elements of foodQueue array
            for (FoodQueue queue : foodQueue) {
                //checks if the current position getting iterated is greater than the size of elements in that particular foodQueue array
                //if so it will print an empty space
                if (i >= queue.getCustomerQueue().length) {
                    System.out.print("      ");
                    continue;
                }
                //checking for the availability of customer elements in the array
                if (queue.getCustomerQueue()[i] == null) {
                    System.out.print("  X   ");
                } else {
                    System.out.print("  O   ");
                }
            }
            System.out.println(); //hops  to a new line after each iteration
        }
    }

    //OPTION-101 Visualising OCCUPIED if all the slots are taken ad EMPTY if at least one empty slot is available
    public static void viewEmptyQueues() {
        System.out.println("*********************");
        System.out.println("*   Empty Queues    *");
        System.out.println("*********************");
        //iterates through the foodQueue array
        for (int i = 0; i < foodQueue.length; i++) {
            boolean isEmpty = false; //variable that checks whether the slot is empty or not
            //iterates through the customer objects of the current queue
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

    //OPTION-102 Adding customer to queue
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
                            System.out.println("Please insert a numeric value");
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

    //OPTION-103 Remove a customer (without serving)
    public static void removeCustomer() {
        boolean removeCustomerLoop1 = true;
        boolean removeCustomerLoop2 = true;

        while (removeCustomerLoop1) {
            //Checking if the queue number entered is valid
            try {
                getQueue();
                currentQueueNo = queueNo - 1;

                //Checking if the queue number entered is valid
                if (queueNo >= 1 && queueNo <= 3) {
                    removeCustomerLoop1 = false;
                    while (removeCustomerLoop2) {
                        try {
                            System.out.print("Choose a customer's position : ");
                            int position = input.nextInt();

                            //Validating the position of the customer that need to be removed
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
                            System.out.println("Please insert a numeric value");
                            input.nextInt();
                        }
                    }
                } else {
                    System.out.println("Please insert a valid queue number!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please insert a numeric value");
                input.nextInt();
            }
        }
    }

    //OPTION-104 Remove a customer (after serving)
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
                        //tracking burgers sold
                        soldBurgers += foodQueue[currentQueueNo].getCustomerQueue()[0].getNoOfBurgers();
                        servedCustomersCount += 1; //tracking served customer count
                        emptySlots++;
                        //tracking the burgers reserved
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

                            //tracking the income update of the served customer
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
                System.out.println("Please insert a numeric value");
                input.nextInt();
            }
        }
    }

    //OPTION-105 View Customers Sorted in alphabetical order
    public static void sortCustomersAlphabetically() {
        //followed a documentation for ascii value sorting
        int count = 0;
        for (int i = 0; i < 5; i++) {
            for (FoodQueue queue : foodQueue) {
                // adding ascii values of first 2 characters to asciiValues array
                if (i >= queue.getCustomerQueue().length) continue;
                if (queue.getCustomerQueue()[i] != null) {
                    names[count] = queue.getCustomerQueue()[i].getFullName().toLowerCase();
                    asciiVal[0][count] = names[count].charAt(0);
                    if (names[count].length() > 1) {
                        asciiVal[1][count] = names[count].charAt(1);
                    }
                    count++;
                }
            }
        }
        //first two characters are swapped (using bubble sorting)
        for (int i = 0; i < count; i++) {
            if (names[i] == null || asciiVal[0][i] == 0) continue;
            for (int j = 0; j < count; j++) {
                if (asciiVal[0][i] < asciiVal[0][j] || (asciiVal[0][i] == asciiVal[0][j] && asciiVal[1][i] < asciiVal[1][j])) {
                    //names are swapped
                    String temp = names[j];
                    names[j] = names[i];
                    names[i] = temp;

                    //first character is swapped
                    int tempVal1 = asciiVal[0][j];
                    asciiVal[0][j] = asciiVal[0][i];
                    asciiVal[0][i] = tempVal1;

                    //second character is swapped
                    int tempVal2 = asciiVal[1][j];
                    asciiVal[1][j] = asciiVal[1][i];
                    asciiVal[1][i] = tempVal2;
                }
            }
        }
        //sorting done
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

    //OPTION-106 Necessary program data are stored in a file
    public static void storeProgramDataIntoFile() {
        int size = 10;
        try {
            // creating and writing the file
            fileInput = new PrintWriter("task2.txt");
            //Main Data
            String mainData = String.format("""
                    Sold Burger Count      : %s\040
                                    
                    Reserved Burger Count  : %s
                    
                    Remaining Burger Count : %s\040
                                        
                    """, soldBurgers, reservedBurgersCount, stock);
            fileInput.write(mainData);
            //other Data
            String otherData = String.format("""                     
                    No.of Empty Slots              : %s\040
                                        
                    No.of Waiting Queue Customers  : %s
                                        
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

    //OPTION-107 Loading stored data from the text file
    public static void loadProgramDataFromFile() {
        try {
            File file = new File("task2.txt");
            Scanner fileReader = new Scanner(file);

            //needed local variables for this method
            String line;

            //reading data
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
            }
            fileReader.close();

            //loading stored data to the console
            String fileOutput = String.format("""
                    Sold Burger Count      : %s
                                        
                    Reserved Burger Count  : %s
                    
                    Remaining Burger Count : %s\040
                                        
                    No. of Empty slots    : %s\040
                    
                    No.of Waiting Queue Customers  : %s
                                        
                    """, soldBurgers, reservedBurgersCount, stock, emptySlots, WaitingQueue.customerCount);
            System.out.println(fileOutput);
        } catch (IOException e) {
            System.out.println("Failed to load data: " + e.getMessage());
        }
    }

    //OPTION-109 Restock burger count (always stock must be restocked to 50 whenever the burger count is low)
    public static void restockBurgerCount() {
        while (stock < 50) {
            stock += 5;
        }
        System.out.println("Burgers Restocked! Stock includes " + stock + " burgers");
    }

    //OPTION-110 View income of the particular queues
    public static void viewQueueIncome() {
        System.out.println("*********************");
        System.out.println("*   Queue Income    *");
        System.out.println("*********************");
        for (int i = 0; i < queueIncome.length; i++) {
            System.out.println("Queue " + (i + 1) + " : Rs." + queueIncome[i]);
        }
    }



}