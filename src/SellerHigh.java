/**
 * A class representing a high-priced ticket-seller
 */
public class SellerHigh extends Seller {
    private Object lock;
    
    public SellerHigh(Seat[][] seat, String sellerID, Object lock) {
        // high-priced tickets: 1 or 2 minutes to complete ticket sale
        super(seat, randomVariable.nextInt(2) + 1, sellerID, lock, System.currentTimeMillis());
        this.lock = lock;
    }

    public void sellTicket() {
        while (!customerQueue.isEmpty()) {
            //Object lock = new Object();

            Customer customer = null;
            if (customerQueue.isEmpty())
                return;
            // Get customer in queue that is ready
            update();

            if(currentTime <= 59)
                customer = customerQueue.peek();
            else
                return;
            // Find seat for the customer
            // Case for Seller H
            Seat seat = null;

            //System.out.println(currentTime);

            synchronized(lock)
            {

                update();
                if(currentTime  >= (customer.getArrivalTime()))
                {
                    find_seat:
                    for (int i = 0; i < seating.length; i++) {
                        for (int j = 0; j < seating[0].length; j++) {
                            if (seating[i][j].isSeatEmpty()) {
                                // seat assignment to customer
                                int seatNum = (i * 10) + j + 1;	// (row x 10) + (col + 1)
                                seat = new Seat(seatNum);
                                super.assignSeat(customer, seat, i, j);
                                //update();
                                printMsg(customer, seat);
                                customerQueue.remove();
                                break find_seat;
                            }
                        }
                    }
                }
            }
            if(seat != null){
                try {
                    Thread.sleep(serviceTime * 1000);
                    update();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }


        }
    }

}