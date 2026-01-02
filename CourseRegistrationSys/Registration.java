import java.time.LocalDateTime;

public class Registration {

    private final Student student;
    private final Course course;
    private final LocalDateTime registrationTime;

    public Registration(Course course, Student student, LocalDateTime registrationTime) {
        this.student = student;
        this.course = course;
        this.registrationTime = registrationTime;

    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }
}