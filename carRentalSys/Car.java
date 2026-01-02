public class Car {
    private final String brand;
    private final String model;
    private final int year;
    private final String licensePlateNumber;
    private final double rentalPricePerDay;
    private boolean Available;

    public Car(String brand, String model, int year, String licensePlateNumber, double rentalPricePerDay) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.licensePlateNumber = licensePlateNumber;
        this.rentalPricePerDay = rentalPricePerDay;
        this.Available = true;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public double getRentalPricePerDay() {
        return rentalPricePerDay;
    }

    public boolean isAvailable() {
        return Available;
    }

    public void setAvailable(boolean available) {
        Available = available;
    }
}