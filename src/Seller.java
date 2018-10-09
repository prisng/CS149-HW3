import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * A class representing a ticket-seller
 */
public abstract class Seller implements Runnable {
	
	public static Random randomVariable = new Random();
	public Queue<Customer> customerQueue;
	public String sellerID;
	public int serviceTime;
	public int ticketNum = 1;
	public int time = 0;
	public long pastTime;
	public long currentTime;
	public Seat[][] seating;
	//private Object lock;

	public Seller(Seat[][] seating, int serviceTime, String sellerID, Object lock, long pastTime) {
		customerQueue = new LinkedList<Customer>();
		this.serviceTime = serviceTime;
		this.seating = seating;
		//this.lock = lock;
		this.sellerID = sellerID;
		this.pastTime = pastTime;

	}
	
	protected void callTime(Customer customer) {
		time = (int) (currentTime + serviceTime); //+ elapse_time;
		System.out.println("Customer arrival time: " + customer.getArrivalTime() + " min");
		System.out.println("Time it takes to be served: " + this.serviceTime + " min");
		System.out.println("Seller ID: " + this.sellerID);
		customer.setTime(time);
	}

	protected void assignSeat(Customer customer, Seat seat, int i, int j) {
		if (ticketNum < 10) {
			customer.setTicket(sellerID + "0" + ticketNum);	
		}
		else {
			customer.setTicket(sellerID + ticketNum);	
		}
		callTime(customer);
		ticketNum++;
		seat.assignSeat(customer);
		seating[i][j] = seat;
		
		// Keep track of how many M / H / L tickets were sold
		if (sellerID.substring(0, 1).equals("M")) {
			Tester.countM++;
		}
		if (sellerID.substring(0, 1).equals("H")) {
			Tester.countH++;
		}
		if (sellerID.substring(0, 1).equals("L")) {
			Tester.countL++; 
		}
		
		// Increment the total number of tickets sold
		Tester.ticketsSold++;
	}

	protected void update() {
		currentTime = System.currentTimeMillis() - this.pastTime;
		if (currentTime < 1000) {
			currentTime = 0;	
		}
		else {
			currentTime /= 1000;	
		}
	}

	public void addCustomer(Customer c) {
		customerQueue.add(c);
	}

	public void sortQueue() {
		Customer[] temp = customerQueue.toArray(new Customer[customerQueue.size()]);
		customerQueue.clear();
		Arrays.sort(temp);
		for (Customer c : temp) {
			customerQueue.add(c);	
		}
	}

	protected void printMsg(Customer customer, Seat seat) {
		int hour = customer.getTime() / 60;
		int min = customer.getTime() % 60;
		String time = "";
		if (min < 10) time = hour + ":0" + min;
		else time = hour + ":" + min;
		if (hour > 0 || seat == null || Tester.ticketsSold > 100) {
			System.out.println("Time: " + time + "  " + "Seller ID: " + sellerID + " - Sorry, The tickets aren't available anymore!");
			Tester.turnedAway++;	// increment the number of customers turned away
		}
		else  {
			System.out.println("Time: " + time + "  " + "Your seat is " + seat.getSeatNum() + ". Enjoy the concert!");
			printSeating(this.seating, 10, 10);
		}
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println();

	}

	// thread to serve one time "quanta" - 1 min
	public abstract void sellTicket();

	@Override
	public void run() {
		sellTicket();
	}

	public static void printSeating(Seat[][] seating, int maxRows, int maxCols) {
		System.out.println("--------------------------------------------------------------------------------");
		for (int row = 0; row < maxRows; row++) {
			for (int col = 0; col < maxCols; col++) {
				if (seating[row][col].isSeatEmpty()) {
					System.out.printf("%7s ", "x");	
				}
				else {
					System.out.printf("%7s ", seating[row][col].getCustomer().getTicket());	
				}
			}
			System.out.println();
		}
	}
	
} // end of class Seller
