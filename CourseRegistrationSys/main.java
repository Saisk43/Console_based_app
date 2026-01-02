import java.time.LocalDateTime;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        System.out.println("Welcome");
        Scanner scanner = new Scanner(System.in);
        boolean condition = true;
        SystemManager manager = SystemManager.getInstance();
        while (condition) {
            System.out.println(
                    "\n1- Register student\n2- Register Course\n3- Enroll Course\n4- Search student\n5- Search courses\n6- Student Enrolled Courses\n7- Exit");
            String option = scanner.next();
            switch (option) {
                case "1":
                    System.out.println("Enter your unique id");
                    String id = scanner.next();
                    System.out.println("Enter your name");
                    String name = scanner.next();
                    System.out.println("Enter your email");
                    String email = scanner.next();
                    Student std = new Student(id, name, email);
                    manager.addStudent(std);
                    System.out.println("Student successfully added");
                    break;
                case "2":
                    System.out.println("Enter your unique code");
                    String code = scanner.next();
                    System.out.println("Enter your name");
                    String instructor = scanner.next();
                    System.out.println("Enter your course title");
                    String title = scanner.next();
                    System.out.println("Enter your course Max Capacity");
                    int Capacity = scanner.nextInt();
                    Course course = new Course(code, instructor, title, Capacity);
                    manager.addCourse(course);
                    System.out.println("Course successfully added");
                    break;
                case "3":
                    System.out.println("Enter student unique id");
                    String stdId = scanner.next();
                    System.out.println("Enter course code");
                    String courseCode = scanner.next();
                    if (manager.registerCourse(stdId, courseCode, LocalDateTime.now()))
                        System.out.println("Course successfully enrolled");
                    else
                        System.out.println("Course enrollment failed");
                    break;
                case "4":
                    System.out.println("Enter student unique id");
                    String ID = scanner.next();
                    manager.searchStudent(ID);
                    break;
                case "5":
                    manager.searchCourses();
                    break;
                case "6":
                    System.out.println("Enter student unique id");
                    String studentId = scanner.next();
                    manager.getEnrolledCourses(studentId);
                    break;
                case "7":
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input try again");
                    break;
            }
        }
    }
}
