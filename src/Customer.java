import java.util.Random;

public class Customer implements Comparable<Customer> {
	
    Random random = new Random();	// random number generator for arrival time
    private int arrivalTime;		// time that the customer arrives
    private int customerID;
    private int seatNum;
    private int time;
    private String ticket;

    /**
     * Constructor for Customer
     */
    public Customer(int customerID){
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
     * Setter method ticket
     */
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    /**
     * Getter method to arrival time
     * @return int arrivalTime
     */
    public int getArrivalTime() {
        return this.arrivalTime;
    }
    
    /**
     * Setter method for seat number
     * @param int seatNum to set this.seatNum
     */
    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

    /**
     * Checks to see if a seat number is has been assigned to a customer
     * @return false if seat number is not assigned, else true
     */
    public boolean isSigned()
    {
        if(seatNum== -1)  return false;
        else return true;
    }
    
    /**
     * Getter method to access seat number
     * @return int seatNum
     */
    public int getSeatNum() {
        return this.seatNum;
    }

    /**
     * Getter method to access customer's id number
     * @return int customerID
     */
    public int customerID() {
        return this.customerID;
    }
    
    /**
     * Compares arrival time of 2 customers
     */
    @Override
    public int compareTo(Customer customer) {
        if(this.arrivalTime < customer.arrivalTime)
            return -1;
        else if(this.arrivalTime > customer.arrivalTime)
            return 1;
        else
            return 0;
    }
}