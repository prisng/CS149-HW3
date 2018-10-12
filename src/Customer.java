import java.util.Random;

public class Customer implements Comparable<Customer> {

	Random random = new Random(); // random number generator for arrival time
	private int arrivalTime; // time that the customer arrives
	private int customerID;
	private int seatNum;
	private int time;
	private String ticket;

	/**
	 * Constructor for Customer
	 */
	public Customer(int customerID) {
		arrivalTime = random.nextInt(60);
		this.customerID = customerID;
		seatNum = -1;
	}

	/**
	 * Getter for time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * Setter for time
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * Getter for ticket
	 */
	public String getTicket() {
		return ticket;
	}

	/**
	 * Setter for ticket
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	/**
	 * Getter method to access customer's id
	 */
	public int customerID() {
		return this.customerID;
	}

	/**
	 * Getter method to arrival time
	 */
	public int getArrivalTime() {
		return this.arrivalTime;
	}

	/**
	 * Checks to see if a seat number has been assigned to a customer
	 */
	public boolean isSigned() {
		if (seatNum == -1)
			return false;
		else
			return true;
	}

	/**
	 * Getter method to access seat number
	 */
	public int getSeatNum() {
		return this.seatNum;
	}

	/**
	 * Setter method for seat number
	 */
	public void setSeatNum(int seatNum) {
		this.seatNum = seatNum;
	}

	/**
	 * Compares arrival time of two customers
	 */
	@Override
	public int compareTo(Customer customer) {
		if (this.arrivalTime < customer.arrivalTime)
			return -1;
		else if (this.arrivalTime > customer.arrivalTime)
			return 1;
		else
			return 0;
	}
}