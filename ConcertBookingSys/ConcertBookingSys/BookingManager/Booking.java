package ConcertBookingSys.BookingManager;

import java.io.ObjectInputFilter.Status;
import java.util.List;

import ConcertBookingSys.*;
import ConcertBookingSys.SeatManager.*;

public class Booking {
    private final String id;
    private final User user;
    private final Concert concert;
    private final List<Seat> seats;
    private final Double totalPrice;
    private BookingStatus status;

    public Booking(String id, User user, Concert concert, List<Seat> seats) {
        this.id = id;
        this.user = user;
        this.concert = concert;
        this.seats = seats;
        this.totalPrice = calculatePrice();
        this.status = BookingStatus.PENDING;
    }

    private Double calculatePrice() {
        Double price = 0.0;
        for (Seat seat : seats) {
            price += seat.getPrice();
        }
        return price;
    }

    public void confirmBooking() {
        if (status == BookingStatus.PENDING)
            status = BookingStatus.CONFIRMED;
    }

    public void cancelBooking() {
        if (status == BookingStatus.CONFIRMED) {
            status = BookingStatus.CANCELED;

            for (Seat seat : seats) {
                seat.setStatus();
            }
            System.out.println("Canceled");
        }
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Concert getConcert() {
        return concert;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public BookingStatus getStatus() {
        return status;
    }

}
