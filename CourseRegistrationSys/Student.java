import java.util.ArrayList;
import java.util.List;

public class Student {
    private final String id;
    private final String name;
    private final String email;
    private List<Course> RegisteredCourses;

    public Student(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.RegisteredCourses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        RegisteredCourses.add(course);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Student [id=" + id + ", name=" + name + ", email=" + email + ", RegisteredCourses="
                + RegisteredCourses.size()
                + "]";
    }

    public String getEmail() {
        return email;
    }

    public List<Course> getRegisteredCourses() {
        return RegisteredCourses;
    }
}
