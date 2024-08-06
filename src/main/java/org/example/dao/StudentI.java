package org.example.dao;

import org.example.models.Student;
import org.example.models.Course;

import java.util.List;

public interface StudentI {
    void createStudent(Student student);
    List<Student> getAllStudents();
    Student getStudentByEmail(String email);
    boolean validateStudent(String email, String password);
    void registerStudentToCourse(String email, int courseId);
    List<Course> getStudentCourses(String email);
}
