/**
 * A class representing a concert seat
 *
 */
public class Seat {
	
    private int seatNum;
    private Customer seatTaken;

    /**
     * Constructor
     * @param int num	the seat number to be set
     */
    public Seat(int num) {
    		seatNum = num;
        seatTaken = null;
    }

    /**
     * Returns true if Seat is empty, false if not
     */
    public boolean isSeatEmpty() {
        return seatTaken == null;
    }
    
    /**
     * Assigns a Customer c to this Seat
     */
    public void assignSeat(Customer c) {
        seatTaken = c;
    }
    
    /**
     * Getter for Customer assigned to this Seat
     */
    public Customer getCustomer(){
        return seatTaken;
    }

    /**
     * Getter for seat number
     */
    public int getSeatNum() {
        return seatNum;
    }
}