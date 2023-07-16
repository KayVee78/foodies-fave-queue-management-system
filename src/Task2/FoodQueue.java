package Task2;

public class FoodQueue {
    private Customer[] customerQueue;

    //constructor
    public FoodQueue(int size) {
        customerQueue = new Customer[size]; //created an array of Customer objects
    }

    public Customer[] getCustomerQueue() {
        return customerQueue;
    }

}
