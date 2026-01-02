package ConcertBookingSys.SeatManager;

public class Seat {
    private final String seatNumber;
    private final SeatType type;
    private final Double price;
    private SeatStatus status;

    public Seat(String seatNumber, SeatType type, Double price) {
        this.seatNumber = seatNumber;
        this.type = type;
        this.price = price;
        this.status = SeatStatus.AVAILABLE;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public SeatType getType() {
        return type;
    }

    public Double getPrice() {
        return price;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus() {
        if (status == SeatStatus.AVAILABLE)
            status = SeatStatus.OCCUPIED;
        else
            status = SeatStatus.AVAILABLE;
    }

}
