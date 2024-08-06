package org.example.dao;

import org.example.models.Course;

import java.util.List;

// Create an interface for interacting with the Course table in the database
public interface CourseI {
    void createCourse(Course course);
    List<Course> getAllCourses();
    Course getCourseById(int courseId);
}