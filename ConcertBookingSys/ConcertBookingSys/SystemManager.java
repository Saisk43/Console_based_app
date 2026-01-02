package ConcertBookingSys;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ConcertBookingSys.BookingManager.Booking;
import ConcertBookingSys.SeatManager.Seat;
import ConcertBookingSys.SeatManager.SeatStatus;
import ConcertBookingSys.SeatManager.SeatType;

public class SystemManager {
    private static SystemManager instance;
    private Map<String, Concert> concerts;
    private Map<String, Booking> bookings;

    public SystemManager() {
        this.concerts = new HashMap<>();
        this.bookings = new HashMap<>();
    }

    public static SystemManager getInstance() {
        if (instance == null)
            instance = new SystemManager();
        return instance;
    }

    public void addConcert(Concert concert, int numberOfSeats) {
        concert.setSeats(generateSeats(numberOfSeats));
        concerts.put(concert.getId(), concert);
    }

    private List<Seat> generateSeats(int numberOfSeats) {
        List<Seat> allSeats = new ArrayList<>();
        for (int i = 1; i <= numberOfSeats; i++) {
            String seatNum = "S" + i;
            SeatType type = (i < 10) ? SeatType.VIP : (i < 50) ? SeatType.PREMIUM : SeatType.ECONOMIC;
            Double price = (type == SeatType.VIP) ? 5 : (type == SeatType.PREMIUM) ? 3.5 : 2.5;
            allSeats.add(new Seat(seatNum, type, price));
        }
        return allSeats;
    }

    public List<Concert> allConcert() {
        return new ArrayList<>(concerts.values());
    }

    public Concert getConcert(String id) {
        return concerts.get(id);
    }

    public List<Concert> searchConcert(String artist, String venue, LocalDateTime date) {
        List<Concert> match = new ArrayList<>();
        for (Concert concert : concerts.values()) {
            if (concert.getArtist().equals(artist) && concert.getVenue().equals(venue)
                    && concert.getDateTime().equals(date))
                match.add(concert);
        }
        return match;

    }

    public Booking bookConcert(User user, Concert concert, List<Seat> seats) {
        for (Seat seat : seats) {
            if (seat.getStatus().equals(SeatStatus.OCCUPIED)) {
                System.out.println("Seat not available");
                return null;
            }
        }
        seats.forEach(Seat::setStatus); // book

        String bookingId = generateBookingId();
        Booking booking = new Booking(bookingId, user, concert, seats);
        bookings.put(bookingId, booking);

        if (processPayment(booking))
            booking.confirmBooking();

        System.out.println("Booking successfull");
        return booking;
    }

    public List<Seat> selectSeats(Concert concert, int numberOfSeats) {
        List<Seat> availableSeats = new ArrayList<>();
        for (Seat seat : concert.getSeats()) {
            if (availableSeats.size() < numberOfSeats)
                if (seat.getStatus() == SeatStatus.AVAILABLE)
                    availableSeats.add(seat);
                else
                    break;
        }
        if (availableSeats.size() < numberOfSeats)
            return null;
        return availableSeats;
    }

    private boolean processPayment(Booking booking) {

        return true;
    }

    public void cancelBooking(String bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking != null) {
            booking.cancelBooking();
            bookings.remove(bookingId);
            System.out.println("Cancelation Successfull");
        }
    }

    private String generateBookingId() {
        return "BKG" + UUID.randomUUID();
    }
}
