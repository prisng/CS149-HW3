/**
 * A class representing a medium-priced ticket-seller
 */
public class SellerMed extends Seller {
    private Object lock;

    public SellerMed(Seat[][] seat, String sellerID, Object lock) {
    	// medium-price tickets: 2, 3, 4 min to complete a sale
        super(seat, randomVariable.nextInt(3) + 2, sellerID, lock, System.currentTimeMillis());
        this.lock = lock;
    }

    public void sellTicket() {
        while (!customerQueue.isEmpty()) {
            Customer customer;
            if (customerQueue.isEmpty()) {
                return;	
            }


            update(); // get the current time
            if(currentTime <= 59) {
                customer = customerQueue.peek();	
            }
            else {
                return;	
            }

            boolean flag = true;
            int counter = 1;

            Seat seat = null;

            synchronized(lock) {
                update();
                if(currentTime  >= (customer.getArrivalTime()))
                {
                    find_seat:
                    for(int i = 5; i >= 0 && i < seating.length;)
                    {
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
                        if(flag == true)
                        {
                            i += counter;
                            flag = false;
                        }
                        else
                            {
                            i -= counter;
                            flag = true;
                        }
                        counter++;
                    }
                }
            }
            if (seat != null) {
                try {
                    Thread.sleep(serviceTime * 1000);
                    update();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }
    }

} // end of class SellerMed