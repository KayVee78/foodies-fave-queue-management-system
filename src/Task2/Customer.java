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
}
