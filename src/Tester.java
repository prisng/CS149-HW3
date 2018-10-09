import java.util.Scanner;

public class Tester
{
	static int countL = 0;		// number of L customers that got seats
	static int countM = 0;		// number of M customers that got seats
	static int countH = 0;		// number of H customers that got seats
	static int ticketsSold = 0;	// total number of tickets sold
	static int turnedAway = 0;	// number of customers turned away
	
    public static void main(String[] args) {
    	
        // number of customers per seller per hour -- command line argument
        int numCustomers = 0;
        
        // This takes in the number of customer from the command line
        // ex. java Tester.java 5
        // make sure to change to args[0] before submission; I just manually put "5" for testing
        numCustomers = Integer.parseInt("15");
        final Object lock = new Object();

        // create 2d array that represents the seating
        int maxRows = 10;
        int maxCols = 10;
        Seat[][] theSeating = createSeating(maxRows, maxCols);

        //create 10 threads representing 10 sellers
        Seller[] allSellers = new Seller[10];
        for (int numSeller = 0; numSeller < 10; numSeller++) {
            if (numSeller == 0)
                allSellers[numSeller] = new SellerH(theSeating, "H" + (numSeller + 1), lock);
            else if (numSeller >= 1 && numSeller < 4)
                allSellers[numSeller] = new SellerM(theSeating, "M" + (numSeller), lock);
            else if (numSeller >= 4 && numSeller < 10)
                allSellers[numSeller] = new SellerL(theSeating, "L" + (numSeller - 3), lock);
        }


        //add numOfCustomers for each seller for each hour, initially add numOfCustomers for each seller's queue
        allSellers = addNewCustomers(allSellers, numCustomers);

        // start() all seller threads so they may run in parallel
        Thread[] threads = new Thread[allSellers.length];

        for(int numSellers = 0; numSellers < allSellers.length; numSellers++) {
            threads[numSellers] = new Thread(allSellers[numSellers]);
            threads[numSellers].start();
        }


        for(int numSellers = 0; numSellers < allSellers.length; numSellers++) {
            try {
                threads[numSellers].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        //*** END OUTPUT ***//
        
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
    public static Seat[][] createSeating(int maxRows, int maxCols)
    {
        // 10 x 10 matrix for concert
        Seat[][] theSeating = new Seat[maxRows][maxCols];
        int numSeat = 1;
        for (int row = 0; row < maxRows; row++) {
            for (int column = 0; column < maxCols; column++) {
                theSeating[row][column] = new Seat(numSeat);
                numSeat++;
            }
        }
        return theSeating;
    }

    /**
     * Adds new customer to the seller's queues
     */
    public static Seller[] addNewCustomers(Seller[] allSellers, int numAdd) {
        for (int numSeller = 0; numSeller < allSellers.length; numSeller++) {
            for (int count = 0; count < numAdd; count++) {
                Customer c = new Customer(numSeller);
                allSellers[numSeller].addCustomer(c);
            }
            allSellers[numSeller].sortQueue();
        }
        return allSellers;
    }

    /**
     * Prints the current seating arrangement
     */
    public static void prtSeating(Seat[][] seating, int maxRows, int maxCols) {
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

}