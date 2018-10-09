public class Seat {
    private int seatNumber;
    private Customer seatTaken;

    /**
     * Constructor
     * @param int num	the seat number to be set
     */
    public Seat(int num) {
        seatNumber = num;
        seatTaken = null;
    }

    /**
     * Returns true if seat is empty, false if not
     */
    public boolean isSeatEmpty() {
        return seatTaken == null;
    }
    
    /**
     * Assigns a Customer to this Seat
     */
    public void assignSeat(Customer c) {
        seatTaken = c;
    }
    
    /**
     * Getter for Customer assigned to this Saet
     */
    public Customer getCustomer(){
        return seatTaken;
    }

    /**
     * Getter for seat number
     */
    public int getSeatNumber() {
        return seatNumber;
    }
}