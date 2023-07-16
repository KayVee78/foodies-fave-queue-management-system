package Task2;

public class WaitingQueue {
    private static Customer[] waitingList;
    public static int customerCount; //to keep in track of customer count
    public static int size = 10; //maximum amount of slots
    private static int startIndex; //to track the first item
    private static int endIndex; //to track the last item

    //Constructor
    public WaitingQueue(int size) {
        this.size = size;
        waitingList = new Customer[size];
        startIndex = 0;
        endIndex = -1;
        customerCount = 0;
    }

    //removing and returning a Customer object from the front of the waiting queue
    public static Customer remove() {
        if (customerCount == 0) {
            System.out.println("Waiting Queue is already empty");
            return null;
        } else {
            Customer temp = waitingList[startIndex];
            waitingList[startIndex] = null;
            startIndex++;

            if (startIndex == size) startIndex = 0;
            customerCount--;
            return temp;
        }
    }

    //insert a Customer object into the waiting queue
    public static void insert(Customer customer) {
        if (customerCount == size) System.out.println("Waiting Queue is full!");
        else {
            if (endIndex == size - 1) endIndex = -1;

            waitingList[++endIndex] = customer;
            customerCount++;
        }
    }

    public static void getCustomersOfWaitingQueue() {
        Main.fileInput.print("Waiting Queue Customers : ");
        for (int i = 0; i < waitingList.length; i++) {
            if (waitingList[i] != null) {
                Main.capitalizeName(waitingList[i].getFullName().split(" "));
                if (i + 1 != waitingList.length) {
                    if (waitingList[i + 1] != null) Main.fileInput.print(", ");
                }
            }
        }
        Main.fileInput.println("\n");
    }
}
