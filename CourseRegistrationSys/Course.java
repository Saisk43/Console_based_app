public class Course {
    private final String code;
    private final String instructor;
    private final String title;
    private final int maxCapacity;
    private int enrolledStudents;

    public Course(String code, String instructor, String title, int maxCapacity) {
        this.code = code;
        this.instructor = instructor;
        this.title = title;
        this.maxCapacity = maxCapacity;
    }

    public String getCode() {
        return code;
    }

    public String getInstructor() {
        return instructor;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    @Override
    public String toString() {
        return "Course [code=" + code + ", instructor=" + instructor + ", title=" + title + ", maxCapacity="
                + maxCapacity + ", enrolledStudents=" + enrolledStudents + "]";
    }

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    public String getTitle() {
        return title;
    }

    public void addStudent(int enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }
}
