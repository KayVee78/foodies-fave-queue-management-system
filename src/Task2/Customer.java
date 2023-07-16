package Task2;

public class Customer {
    private String firstName;
    private String secondName;
    private int noOfBurgers;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getNoOfBurgers() {
        return noOfBurgers;
    }

    public void setNoOfBurgers(int noOfBurgers) {
        this.noOfBurgers = noOfBurgers;
    }

    public String getFullName() {
        if (firstName != null && secondName != null) {
            return firstName + " " + secondName;
        } else {
            return null;
        }
    }

    // Creation of the customer object (with firstName, secondName, noOfBurgers)
    public static Customer setNewCustomer(String[] customerDetails) {
        Customer customer = new Customer();
        customer.setFirstName(customerDetails[0].toLowerCase());
        customer.setSecondName(customerDetails[1].toLowerCase());
        customer.setNoOfBurgers(Integer.parseInt(customerDetails[3]));
        return customer;
    }
}
