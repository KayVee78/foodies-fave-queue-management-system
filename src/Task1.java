import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Task1 {
    // creation of constants whose values can't be changed, can be accessed only within the class,
    // shared among all the instances of the class
    /* Declaring constants according to the naming conventions mentioned in the Oracle Docs */
    private static final int TOTAL_OF_QUEUE1 = 2;
    private static final int TOTAL_OF_QUEUE2 = 3;
    private static final int TOTAL_OF_QUEUE3 = 5;
    private static final int TOTAL_BURGER_COUNT = 50;
    // creation of variables which can be accessed only within the class and
    // shared among all the instances of the class
    private static int stock;
    private static String[] queue1;
    private static String[] queue2;
    private static String[] queue3;
    private static String[] queue1Visualization;
    private static String[] queue2Visualization;
    private static String[] queue3Visualization;

    // Declaring element count of queues
    private static int elementCountOfQueue1;
    private static int elementCountOfQueue2;
    private static int elementCountOfQueue3;

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
    }

    public static void viewAllQueues(String[] queue1Visualization, String[] queue2Visualization, String[] queue3Visualization) {
        int maxLength = Math.max(Math.max(queue1Visualization.length, queue2Visualization.length), queue3Visualization.length);
        System.out.println("*****************");
        System.out.println("*   Cashiers    *");
        System.out.println("*****************");
        for (int i = 0; i < maxLength; i++) {
            if (i < queue1Visualization.length) {
                System.out.print(queue1Visualization[i] + "\t\t");
            } else {
                System.out.print("\t\t");
            }

            if (i < queue2Visualization.length) {
                System.out.print(queue2Visualization[i] + "\t\t");
            } else {
                System.out.print("\t\t");
            }

            if (i < queue3Visualization.length) {
                System.out.print(queue3Visualization[i]);
            }

            System.out.println();
        }
        System.out.println();
    }

    public static void viewEmptyQueues() {
        System.out.println("*********************");
        System.out.println("*   Empty Queues    *");
        System.out.println("*********************");
        if (elementCountOfQueue1 < TOTAL_OF_QUEUE1) {
            System.out.println("Queue 1\t: EMPTY");
        } else {
            System.out.println("Queue 1\t: OCCUPIED");
        }

        if (elementCountOfQueue2 < TOTAL_OF_QUEUE2) {
            System.out.println("Queue 2\t: EMPTY");
        } else {
            System.out.println("Queue 2\t: OCCUPIED");
        }

        if (elementCountOfQueue3 < TOTAL_OF_QUEUE3) {
            System.out.println("Queue 3\t: EMPTY");
        } else {
            System.out.println("Queue 3\t: OCCUPIED");
        }
    }

    public static void addCustomer() {
        Scanner input = new Scanner(System.in);
        System.out.print("Choose a queue to proceed (1, 2 or 3) : ");
        String queueNo = input.next();
        if (!(queueNo.equals("1") || queueNo.equals("2") || queueNo.equals("3"))) {
            System.out.println("Please insert a valid queue number!");
        } else {
            switch (queueNo) {
                case "1":
                    if (elementCountOfQueue1 < TOTAL_OF_QUEUE1) {
                        repeatingCodesOfAddCustomer(elementCountOfQueue1, queue1Visualization, queue1, queueNo);
                        elementCountOfQueue1++;
                    } else {
                        System.out.println("Queue 1 is full, please try another queue!");
                    }
                    break;
                case "2":
                    if (elementCountOfQueue2 < TOTAL_OF_QUEUE2) {
                        repeatingCodesOfAddCustomer(elementCountOfQueue2, queue2Visualization, queue2, queueNo);
                        elementCountOfQueue2++;
                    } else {
                        System.out.println("Queue 2 is full, please try another queue!");
                    }
                    break;
                case "3":
                    if (elementCountOfQueue3 < TOTAL_OF_QUEUE3) {
                        repeatingCodesOfAddCustomer(elementCountOfQueue3, queue3Visualization, queue3, queueNo);
                        elementCountOfQueue3++;
                    } else {
                        System.out.println("Queue 3 is full, please try another queue!");
                    }
                    break;
                default:
                    System.out.println("Please insert a valid queue number!");
            }
        }
    }

    public static void removeCustomer() {
        Scanner input = new Scanner(System.in);
        System.out.print("Choose a queue to proceed (1, 2 or 3) : ");
        int queueNo = input.nextInt();
        System.out.print("Choose a customer's position : ");
        int position = input.nextInt();

        switch (queueNo) {
            case 1:
                if (position >= 1 && position <= elementCountOfQueue1) {
                    String customer = queue1[position - 1];
                    shiftElementToLeftInQueue(queue1, queue1Visualization, elementCountOfQueue1, position);
                    elementCountOfQueue1--;
                    System.out.println(customer + " is removed from queue 1 ");
                } else {
                    System.out.println("Please enter a valid position in queue1!");
                }
                break;
            case 2:
                if (position >= 1 && position <= elementCountOfQueue2) {
                    String customer = queue2[position - 1];
                    shiftElementToLeftInQueue(queue2, queue2Visualization, elementCountOfQueue2, position);
                    elementCountOfQueue2--;
                    System.out.println(customer + " is removed from queue 2 ");
                } else {
                    System.out.println("Please enter a valid position in queue1!");
                }
                break;
            case 3:
                if (position >= 1 && position <= elementCountOfQueue3) {
                    String customer = queue3[position - 1];
                    shiftElementToLeftInQueue(queue3, queue3Visualization, elementCountOfQueue3, position);
                    elementCountOfQueue3--;
                    System.out.println(customer + " is removed from queue 3 ");
                } else {
                    System.out.println("Please enter a valid position in queue1!");
                }
                break;
            default:
                System.out.println("Please insert a valid queue number!");
        }
    }

    public static void removeServedCustomer() {
        Scanner input = new Scanner(System.in);
        System.out.print("Choose a queue to proceed (1, 2 or 3) : ");
        int queueNo = input.nextInt();

        switch (queueNo) {
            case 1:
                if (elementCountOfQueue1 > 0) {
                    if (stock > 10) {
                        String customer = queue1[0];
                        shiftElementToLeftInQueue(queue1, queue1Visualization, elementCountOfQueue1, 1);
                        elementCountOfQueue1--;
                        stock -= 5;
                        System.out.println("Served customer " + customer + " is removed from queue1");
                    } else {
                        System.out.println("WARNING!! Low burger count, please re-stock burgers to serve customers");
                    }

                } else {
                    System.out.println("Queue1 is empty! Please try another one");
                }
                break;
            case 2:
                if (elementCountOfQueue2 > 0) {
                    if (stock > 10) {
                        String customer = queue2[0];
                        shiftElementToLeftInQueue(queue2, queue2Visualization, elementCountOfQueue2, 1);
                        elementCountOfQueue2--;
                        stock -= 5;
                        System.out.println("Served customer " + customer + " is removed from queue2");
                    } else {
                        System.out.println("WARNING!! Low burger count, please re-stock burgers to serve customers");
                    }

                } else {
                    System.out.println("Queue2 is empty! Please try another one");
                }
                break;
            case 3:
                if (elementCountOfQueue3 > 0) {
                    if (stock > 10) {
                        String customer = queue3[0];
                        shiftElementToLeftInQueue(queue3, queue3Visualization, elementCountOfQueue3, 1);
                        elementCountOfQueue3--;
                        stock -= 5;
                        System.out.println("Served customer " + customer + " is removed from queue3");
                    } else {
                        System.out.println("WARNING!! Low burger count, please re-stock burgers to serve customers");
                    }

                } else {
                    System.out.println("Queue3 is empty! Please try another one");
                }
                break;
            default:
                System.out.println("Please insert a valid queue number!");
        }
    }

    public static void sortCustomersAlphabetically() {
        String[] customersFromAll3Queues = mergeAll3arrays(queue1, queue2, queue3);
        sort(customersFromAll3Queues);

        // prints the sorted names
        for (String customersFromAll3Queue : customersFromAll3Queues) {
            if (customersFromAll3Queue != null) {
                System.out.println(customersFromAll3Queue);
            }
        }
    }

    public static void storeProgramDataIntoFile() {
        try {
            FileWriter fileWriter = new FileWriter("store.txt");
            // Write stock data
            fileWriter.write("Stock   : " + stock + "\n");
            // Write Queue 1 data
            fileWriter.write("Queue 1 :" + "\n");
            for (int i = 0; i < elementCountOfQueue1; i++) {
                fileWriter.write(queue1[i] + "\n");
            }
            // Write Queue 2 data
            fileWriter.write("\nQueue 2 :\n");
            for (int i = 0; i < elementCountOfQueue2; i++) {
                fileWriter.write(queue2[i] + "\n");
            }
            // Write Queue 3 data
            fileWriter.write("\nQueue 3 :\n");
            for (int i = 0; i < elementCountOfQueue3; i++) {
                fileWriter.write(queue3[i] + "\n");
            }
            fileWriter.flush();
            fileWriter.close();
            System.out.println("Program data stored successfully in the text file!");
            // Reload the data from the file
            //loadProgramDataFromFile();
        } catch (IOException e) {
            System.out.println("Failed to process request: " + e.getMessage());
        }
    }

    public static void loadProgramDataFromFile() {
        try (FileReader fileReader = new FileReader("store.txt");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String stockLine = bufferedReader.readLine();
            if (stockLine != null) {
                String stockValue = stockLine.split(":")[1].trim();
                stock = Integer.parseInt(stockValue);
                System.out.println("Stock: " + stock);
            }
            String queue1Title = bufferedReader.readLine(); // Read the title line for Queue 1
            if (queue1Title != null && queue1Title.equals("Queue 1 :")) {
                // Read the elements of Queue 1
                int queue1Size = 0;
                String line;
                while ((line = bufferedReader.readLine()) != null && !line.equals("")) {
                    queue1[queue1Size++] = line;

                }
                System.out.println("Queue 1: ");
                for (int i = 0; i < queue1Size; i++) {
                    if (queue1[i] != null) {
                        System.out.println(queue1[i]);
                        queue1Visualization[i] = "0";
                    }
                }
                System.out.println();

            }
            String queue2Title = bufferedReader.readLine(); // Read the title line for Queue 2
            if (queue2Title != null && queue2Title.equals("Queue 2 :")) {
                // Read the elements of Queue 2
                int queue2Size = 0;
                String line;
                while ((line = bufferedReader.readLine()) != null && !line.equals("")) {
                    queue2[queue2Size++] = line;
                }
                System.out.println("Queue 2: ");
                for (int i = 0; i < queue2Size; i++) {
                    if (queue2[i] != null) {
                        System.out.println(queue2[i]);
                        queue2Visualization[i] = "0";
                    }
                }
                System.out.println();
            }
            String queue3Title = bufferedReader.readLine(); // Read the title line for Queue 3
            if (queue3Title != null && queue3Title.equals("Queue 3 :")) {
                // Read the elements of Queue 3
                int queue3Size = 0;
                String line;
                while ((line = bufferedReader.readLine()) != null && !line.equals("")) {
                    queue3[queue3Size++] = line;
                }
                System.out.println("Queue 3: ");
                for (int i = 0; i < queue3Size; i++) {
                    if (queue3[i] != null) {
                        System.out.println(queue3[i]);
                        queue3Visualization[i] = "0";
                    }
                }
                System.out.println();
            }
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

    public static void repeatingCodesOfAddCustomer(int elementCountOfQueue, String[] queueVisualization, String[] queue, String queueNo) {
        Scanner input = new Scanner(System.in);
        System.out.print("Insert customer's name : ");
        String customerName = input.next();
        queueVisualization[elementCountOfQueue] = "O";
        queue[elementCountOfQueue] = customerName;
        System.out.println(customerName + " is added to Queue " + queueNo);
    }

    /* This function shifts elements to left, the customer at the specified location
    would be removed and the size of the queue will be reduced by 1 */
    // At the end, last element of the queue is set to null
    public static void shiftElementToLeftInQueue(String[] queue, String[] visualisingQueue, int size, int position) {
        for (int i = position; i < size; i++) {
            queue[i - 1] = queue[i];
            visualisingQueue[i - 1] = visualisingQueue[i];
        }
        queue[size - 1] = null;
        visualisingQueue[size - 1] = "X";
    }

    // Variable arguments - this allows to accept variable no. of arguments of the String[] type to the method mergeAll3arrays()
    // This will be treated as array of arrays String[][] internally
    public static String[] mergeAll3arrays(String[]... arrays) {
        //flattens a 2D array by iterating over its elements and merging them into a 1D array
        int arrayLength = 0;
        for (String[] array : arrays) {
            arrayLength += array.length;
        }
        String[] mergedArray = new String[arrayLength];
        int currentIndex = 0; //keeps track of the current position of the mergedArray

        for (String[] array : arrays) {
            for (String element : array) {
                if (element != null) {
                    mergedArray[currentIndex] = element;
                    currentIndex++;
                }
            }
        }
        return mergedArray;
    }

    // using bubble sort to sort out the names alphabetically
    private static void sort(String[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] != null && array[j + 1] != null && array[j].compareTo(array[j + 1]) > 0) {
                    // elements are swapped
                    String temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        stock = TOTAL_BURGER_COUNT;
        queue1 = new String[TOTAL_OF_QUEUE1];
        queue2 = new String[TOTAL_OF_QUEUE2];
        queue3 = new String[TOTAL_OF_QUEUE3];
        queue1Visualization = new String[TOTAL_OF_QUEUE1];
        queue2Visualization = new String[TOTAL_OF_QUEUE2];
        queue3Visualization = new String[TOTAL_OF_QUEUE3];
        // Initialize visualization array with X for the visualization
        Arrays.fill(queue1Visualization, "X");
        Arrays.fill(queue2Visualization, "X");
        Arrays.fill(queue3Visualization, "X");
        // Initializing element count of arrays to 0
        elementCountOfQueue1 = 0;
        elementCountOfQueue2 = 0;
        elementCountOfQueue3 = 0;
        String option;

        do {
            viewMenu();
            Scanner input = new Scanner(System.in);
            System.out.print("Select an option from the above : ");
            option = input.next();

            switch (option) {
                case "100", "VFQ" -> viewAllQueues(queue1Visualization, queue2Visualization, queue3Visualization);
                case "101", "VEQ" -> viewEmptyQueues();
                case "102", "ACQ" -> addCustomer();
                case "103", "RCQ" -> removeCustomer();
                case "104", "PCQ" -> removeServedCustomer();
                case "105", "VCS" -> sortCustomersAlphabetically();
                case "106", "SPD" -> storeProgramDataIntoFile();
                case "107", "LPD" -> loadProgramDataFromFile();
                case "108", "STK" -> System.out.println("Remaining stock count : " + stock);
                case "109", "AFS" -> restockBurgerCount();
                case "999", "EXT" -> System.out.println("Program's exiting....");
            }
            System.out.println();
        } while (!(option.equals("999") || option.equals("EXT")));
    }
}
