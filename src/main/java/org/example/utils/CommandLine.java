package org.example.utils;

import org.example.models.Course;
import org.example.models.Student;
import org.example.services.CourseService;
import org.example.services.StudentService;

import java.util.List;
import java.util.Scanner;

public class CommandLine {
    // Create instances of services to interact with the database and handle user input
    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("Select # from menu:");
            System.out.println("1.Student");
            System.out.println("2.Quit");

            int choice = Integer.parseInt(scanner.nextLine()); // Get user choice

            switch (choice) { // Get user choice
                case 1: // If choice is 1, handle student operations'
                    handleStudent();
                    break;
                case 2: // If choice is 2, exit the program
                    System.out.println("session ended!");
                    return;
                default: // If choice is neither 1 nor 2, display an error message and ask for a valid
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Extract the first name from the email address
    private String getFirstNameFromEmail(String email) {
        String localPart = email.split("@")[0]; // Get the part before the '@'
        StringBuilder firstName = new StringBuilder();

        for (int i = 0; i < localPart.length(); i++) {
            if (i == 0 || Character.isLowerCase(localPart.charAt(i))) {
                firstName.append(localPart.charAt(i));
            } else {
                break;
            }
        }

        if (firstName.length() > 0) {
            firstName.setCharAt(0, Character.toUpperCase(firstName.charAt(0)));
        }

        return firstName.toString();
    }

    private void handleStudent() {
        System.out.print("Enter student email: ");
        String email = scanner.nextLine();
        String firstName = getFirstNameFromEmail(email); // Get the first name from the email address
        System.out.print("Enter " + firstName + "'s password: ");
        String password = scanner.nextLine();

        // Validate student credentials
        if (studentService.validateStudent(email, password)) {
            // Proceed with displaying and managing courses if credentials are valid
            while (true) {
                System.out.println(email + " courses:");
                List<Course> courses = studentService.getStudentCourses(email);
                if (courses.isEmpty()) {
                    System.out.println("No courses to view");
                } else {
                    courses.forEach(course ->
                            System.out.println(course.getId() + " | " + course.getName() + " | " + course.getInstructor())
                    );
                }

                System.out.println("Select # from menu:");
                System.out.println("1.Register " + email + " to class:");
                System.out.println("2.Logout");

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        System.out.println("All courses:");
                        System.out.println("-------------------------");
                        System.out.printf("%-4s | %-20s | %-20s%n", "ID", "Course", "Instructor");
                        System.out.println("-------------------------");

                        List<Course> allCourses = courseService.getAllCourses();
                        allCourses.forEach(course ->
                                System.out.printf("%-4d | %-20s | %-20s%n",
                                        course.getId(),
                                        course.getName(),
                                        course.getInstructor())
                        );

                        System.out.print("Select course #: ");
                        int courseId = Integer.parseInt(scanner.nextLine());
                        studentService.registerStudentToCourse(email, courseId);
                        System.out.println("Successfully registered " + email + " to " + courseService.getCourseById(courseId).getName());
                        break;
                    case 2:
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } else {

            System.out.println("Invalid email or password.");
        }
    }
}
