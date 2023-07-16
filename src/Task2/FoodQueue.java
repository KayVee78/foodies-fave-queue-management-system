package Task2;

import java.util.InputMismatchException;

public class FoodQueue {
    private Customer[] customerQueue;

    //constructor
    public FoodQueue(int size) {
        customerQueue = new Customer[size]; //created an array of Customer objects
    }

    public static void addCustomer() {
        boolean detailLoop = true; //outer loop which takes the customer names
        boolean burgerLoop = true; //inner loop which takes the burger amount needed

        while (detailLoop) {
            Customer customer = new Customer();
            System.out.print("Insert customer's first name : ");
            customer.setFirstName(Main.input.next());
            System.out.print("Insert customer's second name : ");
            customer.setSecondName(Main.input.next());
            System.out.print("Insert the burger amount : ");
            customer.setNoOfBurgers(Main.input.nextInt());

            System.out.println(customer.getFullName());
            System.out.println(customer.getNoOfBurgers());

            if (Main.stock > 10) {
                System.out.print("Enter name : ");
                String name = Main.input.next();


                if (name.matches("[a-zA-Z]+")) {
                    detailLoop = false;

                    while (burgerLoop) {
                        try {
                            Main.getQueue();

                            //Checking whether the queue number inserted is valid
                            if (Main.queueNo >= 1 && Main.queueNo <= 3) {
                                Main.currentQueueNo = Main.queueNo - 1;
                                int occupiedQueuePositionCount = 0;

                                for (int i = 0; i < Main.cashiers[Main.currentQueueNo].length; i++) {
                                    //Adding customer to the queue by check whether the position is an empty slot or not
                                    if (Main.cashiers[Main.currentQueueNo][i] == null) {
                                        Main.cashiers[Main.currentQueueNo][i] = name;
                                        System.out.println(name + " added to the queue " + Main.queueNo + " successfully\n");

                                        //Burger reservation
                                        Main.stock -= 5;
                                        Main.reservedBurgersCount += 5;
                                        Main.emptySlots--;

                                        Main.lowBurgerCountWarning();
                                        burgerLoop = false;
                                        Main.viewMenu();
                                        break;

                                    } else {
                                        occupiedQueuePositionCount++;

                                        if (occupiedQueuePositionCount == Main.cashiers[Main.currentQueueNo].length) {
                                            System.out.println("Queue is full! Please try another one");
                                        }
                                    }
                                }
                            } else {
                                System.out.println("Please insert a valid queue number!");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Integer required!");
                            Main.input.nextInt();
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


}
