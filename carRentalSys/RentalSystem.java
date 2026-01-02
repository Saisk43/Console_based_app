import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RentalSystem {
    private static RentalSystem instance;
    private final Map<String, Car> cars;
    private final Map<String, Reservation> reservations;
    private final Payment payment;

    public RentalSystem() {
        this.cars = new HashMap<>();
        this.reservations = new HashMap<>();
        this.payment = new Payment();

    }

    public static synchronized RentalSystem getInstance() {
        if (instance == null) {
            instance = new RentalSystem();
        }
        return instance;

    }

    public void addCar(Car car) {
        cars.put(car.getLicensePlateNumber(), car);
    }

    public void removeCar(String licensePlateNumber) {
        cars.remove(licensePlateNumber);
    }

    public List<Car> searchCars(String brand, String model, LocalDate starDate, LocalDate enDate) {
        List<Car> availableCars = new ArrayList<>();
        for (Car car : cars.values()) {
            if (car.getBrand().equals(brand) && car.getModel().equals(model) && isAvailable(car, starDate, enDate)) {

                if (car.isAvailable()) {
                    availableCars.add(car);
                }
            }
        }

        return availableCars;

    }

    private boolean isAvailable(Car car, LocalDate starDate, LocalDate enDate) {
        for (Reservation reserv : reservations.values()) {
            if (reserv.getCar().equals(car)) {
                if (reserv.getEndDate().isAfter(starDate) && reserv.getStartDate().isBefore(enDate)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Reservation makeReservation(Customer customer, Car car, LocalDate starDate, LocalDate enDate) {
        if (isAvailable(car, starDate, enDate)) {
            String reserveId = generateId();
            Reservation reservation = new Reservation(reserveId, customer, car, enDate, enDate);
            reservations.put(reserveId, reservation);
            car.setAvailable(false);
            return reservation;
        }
        return null;
    }

    public void cancelReservation(String reserveId) {
        Reservation reservation = reservations.remove(reserveId);
        if (reservation != null) {
            reservation.getCar().setAvailable(true);
        }
    }

    private String generateId() {
        return "RES" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public Map<String, Reservation> getReservations() {
        return reservations;
    }

    public boolean processPayment(Reservation reservation) {
        return payment.processPayment(reservation);
    }
}
