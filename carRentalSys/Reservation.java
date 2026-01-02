import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation {
    private final String resevId;
    private final Customer customer;
    private final Car car;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final double totalAmount;

    public Reservation(String reservId, Customer customer, Car car, LocalDate startDate, LocalDate endDate) {
        this.resevId = reservId;
        this.customer = customer;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalAmount = calculateAmount();

    }

    public String getResevId() {
        return resevId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Car getCar() {
        return car;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    private double calculateAmount() {
        long daysRented = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        return car.getRentalPricePerDay() * daysRented;
    }

}
