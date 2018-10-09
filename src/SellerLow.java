/**
 * A class representing a low-priced ticket-seller
 */
public class SellerLow extends Seller {

    private Object lock;
    
    public SellerLow(Seat[][] seat, String sellerID, Object lock) {
    	// low-priced tickets: 4, 5, 6, 7 min to complete a sale
        super(seat, randomVariable.nextInt(4) + 4, sellerID, lock, System.currentTimeMillis());
        this.lock = lock;

    }

    public void sellTicket() {
        while (!customerQueue.isEmpty()) {
            Customer customer;
            if (customerQueue.isEmpty()) {
            	return;
            }
            // Get customer in queue that is ready
            update();
            if (currentTime <= 59) {
                customer = customerQueue.peek();	
            }
            else {
                return;	
            }

            // create a seat for the customer
            Seat seat = null;

            synchronized(lock) {
                update();
                
                if(currentTime  >= (customer.getArrivalTime())) {
                    find_seat:
                    for (int i = seating.length - 1; i >= 0; i--) {
                        for (int j = 0; j < seating[0].length; j++) {
                            if (seating[i][j].isSeatEmpty()) {
                                // seat assignment to customer
                                int seatNum = (i * 10) + j + 1;	// (row x 10) + (col + 1)
                                seat = new Seat(seatNum);
                                super.assignSeat(customer, seat, i, j);
                                printMsg(customer, seat);
                                customerQueue.remove();
                                break find_seat;
                            }
                        }
                    }
                }
            }
            
            if(seat != null) {
                try {
                    Thread.sleep(serviceTime * 1000);
                    update();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
} // end of class SellerLow