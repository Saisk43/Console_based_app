public class Customer {
    private final String name;
    private final String contactInfo;
    private final String drivingLicenseNumber;

    public Customer(String name, String contactInfo, String drivingLicenseNumber) {
        this.name = name;
        this.contactInfo = contactInfo;
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contactInfo;
    }

    public String getLicenseNumber() {
        return drivingLicenseNumber;
    }
}
