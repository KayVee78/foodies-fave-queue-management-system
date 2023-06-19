import java.util.Scanner;

public class Task1 {
    // creation of constants whose values can't be changed, can be accessed only within the class,
    // shared among all the instances of the class
    /*Declaring constants according to the naming conventions mentioned in the Oracle Docs*/
    private static final int TOTAL_OF_QUEUE1 = 2;
    private static final int TOTAL_OF_QUEUE2 = 3;
    private static final int TOTAL_OF_QUEUE3 = 5;
    private static final int TOTAL_BURGER_COUNT = 50;
    // creation of variables which can be accessed only within the class and
    // shared among all the instances of the class
    private static int stock;
    private static String queue1[];
    private static String queue2[];
    private static String queue3[];
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
                        System.out.print("Insert customer's name : ");
                        String customerName = input.next();
                        queue1[elementCountOfQueue1++] = customerName;
                        stock -= 5;
                        System.out.println(customerName + " is added to Queue 1");
                    } else {
                        System.out.println("Queue 1 is full, please try another queue!");
                    }
                    break;
                case "2":
                    if (elementCountOfQueue2 < TOTAL_OF_QUEUE2) {
                        System.out.print("Insert customer's name : ");
                        String customerName = input.next();
                        queue2[elementCountOfQueue2++] = customerName;
                        stock -= 5;
                        System.out.println(customerName + " is added to Queue 2");
                    } else {
                        System.out.println("Queue 2 is full, please try another queue!");
                    }
                    break;
                case "3":
                    if (elementCountOfQueue3 < TOTAL_OF_QUEUE3) {
                        System.out.print("Insert customer's name : ");
                        String customerName = input.next();
                        queue3[elementCountOfQueue3++] = customerName;
                        stock -= 5;
                        System.out.println(customerName + " is added to Queue 3");
                    } else {
                        System.out.println("Queue 3 is full, please try another queue!");
                    }
                    break;
                default:
                    System.out.println("Please insert a valid queue number!");
            }
            if (stock <= 10) {
                System.out.println("WARNING!! Low burger count, please re-stock burgers");
            }
        }
    }

    public static void main(String[] args) {
        stock = TOTAL_BURGER_COUNT;
        queue1 = new String[TOTAL_OF_QUEUE1];
        queue2 = new String[TOTAL_OF_QUEUE2];
        queue3 = new String[TOTAL_OF_QUEUE3];
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
                case "102":
                case "ACQ":
                    addCustomer();
                    break;
                case "999":
                case "EXT":
                    System.out.println("Program's exiting....");
                    break;

            }
            System.out.println();
        } while (!(option.equals("999") || option.equals("EXT")));

    }
}
