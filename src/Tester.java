import java.util.Scanner;

/**
 * Tester for the project - creates a 2D array of seats and distributes
 * tickets based on numCustomers, the command-line input for the number of
 * customers per hour that arrive
 */
public class Tester {
	static int countL = 0;		// number of L customers that got seats
	static int countM = 0;		// number of M customers that got seats
	static int countH = 0;		// number of H customers that got seats
	static int ticketsSold = 0;	// total number of tickets sold
	static int turnedAway = 0;	// number of customers turned away
	
    public static void main(String[] args) {
    	
        // number of customers per hour -- command line argument
        int numCustomers = 0;
        
        // This takes in the number of customer from the command line
        // ex. java Tester.java 5
        numCustomers = Integer.parseInt(args[0]);
        final Object lock = new Object();

        // 2D array to represent the seating matrix
        int maxRows = 10;
        int maxCols = 10;
        Seat[][] theSeating = createSeating(maxRows, maxCols);

        // 10 threads to represent 10 Sellers
        Seller[] sellers = new Seller[10];
        for (int numSeller = 0; numSeller < 10; numSeller++) {
            if (numSeller == 0)
                sellers[numSeller] = new SellerHigh(theSeating, "H" + (numSeller + 1), lock);
            else if (numSeller >= 1 && numSeller < 4)
                sellers[numSeller] = new SellerMed(theSeating, "M" + (numSeller), lock);
            else if (numSeller >= 4 && numSeller < 10)
                sellers[numSeller] = new SellerLow(theSeating, "L" + (numSeller - 3), lock);
        }
        sellers = addCustomers(sellers, numCustomers);

        // start seller threads to run in parallel
        Thread[] threads = new Thread[sellers.length];

        for (int numSellers = 0; numSellers < sellers.length; numSellers++) {
            threads[numSellers] = new Thread(sellers[numSellers]);
            threads[numSellers].start();
        }

        for (int numSellers = 0; numSellers < sellers.length; numSellers++) {
            try {
                threads[numSellers].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        //***** END OUTPUT *****//
        
        System.out.println("\n");
        System.out.println("Total number of tickets sold: " + Tester.ticketsSold);
        System.out.println("Number of customers turned away: " + Tester.turnedAway);
		System.out.println("Number of H tickets sold: " + Tester.countH);
		System.out.println("Number of M tickets sold: " + Tester.countM);
		System.out.println("Number of L tickets sold: " + Tester.countL);
    }


    /**
     * Creates a seating arrangement with rows x cols
	**/
    public static Seat[][] createSeating(int maxRows, int maxCols) {
        // 10 x 10 matrix for concert
        Seat[][] seatingArrangement = new Seat[maxRows][maxCols];
        int numSeat = 1;
        for (int row = 0; row < maxRows; row++) {
            for (int column = 0; column < maxCols; column++) {
            	// create a new seat in each spot
            	seatingArrangement[row][column] = new Seat(numSeat);
                numSeat++;
            }
        }
        return seatingArrangement;
    }

    /**
     * Prints the current seating arrangement
     */
    public static void printSeats(Seat[][] seating, int maxRows, int maxCols) {
        System.out.println("__________________________");
        for (int row = 0; row < maxRows; row++) {
            for (int col = 0; col < maxCols; col++) {
                if (seating[row][col].isSeatEmpty()) {
                    System.out.printf("%7s ", "O");	
                }
                else {
                    System.out.printf("%7s ", seating[row][col].getCustomer().getTicket());	
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Adds new customer to the seller's queues
     */
    public static Seller[] addCustomers(Seller[] sellers, int num) {
        for (int i = 0; i < sellers.length; i++) {
            for (int count = 0; count < num; count++) {
                Customer c = new Customer(i);
                sellers[i].addCustomer(c);
            }
            // sort the queue based on arrival time
            sellers[i].sortQueue();
        }
        return sellers;
    }

}