import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemManager {
    private static SystemManager instance;
    private Map<String, Student> allStudents;
    private Map<String, Course> allCourses;
    private List<Registration> registraions;

    public SystemManager() {
        this.allStudents = new HashMap<>();
        this.allCourses = new HashMap<>();
        this.registraions = new ArrayList<>();
    }

    public static SystemManager getInstance() {
        if (instance == null)
            instance = new SystemManager();
        return instance;
    }

    public void addStudent(Student student) {
        allStudents.put(student.getId(), student);
    }

    public void searchStudent(String id) {
        System.out.println(allStudents.get(id).toString());
    }

    public void addCourse(Course course) {
        allCourses.put(course.getCode(), course);
    }

    public void searchCourses() {
        for (Course course : allCourses.values()) {
            System.out.println(course.toString());
        }
    }

    public boolean registerCourse(String stdId, String courseCode, LocalDateTime date) {
        Student std = allStudents.get(stdId);
        Course course = allCourses.get(courseCode);
        if (std == null || course == null) {
            return false;
        }
        if (course.getEnrolledStudents() < course.getMaxCapacity()) {
            registraions.add(new Registration(course, std, date));
            std.addCourse(course);
            course.addStudent(course.getEnrolledStudents() + 1);
            return true;
        }
        return false;

    }

    public void getEnrolledCourses(String id) {
        Student std = allStudents.get(id);
        for (Course course : std.getRegisteredCourses()) {
            System.out.println(course.toString());
        }

    }
}
