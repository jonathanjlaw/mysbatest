package org.example.services;

import org.example.dao.StudentI;
import org.example.models.Course;
import org.example.models.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.example.utils.HibernateUtil;

import java.util.List;

public class StudentService implements StudentI {

    @Override
    public void createStudent(Student student) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> getAllStudents() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Student", Student.class).list();
        }
    }

    @Override
    public Student getStudentByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Student.class, email);
        }
    }

    @Override
    public boolean validateStudent(String email, String password) {
        Student student = getStudentByEmail(email);
        return student != null && student.getPassword().equals(password);
    }

    @Override
    public void registerStudentToCourse(String email, int courseId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Student student = session.get(Student.class, email);
            Course course = session.get(Course.class, courseId);
            student.getCourses().add(course);
            course.getStudents().add(student);
            session.saveOrUpdate(student);
            session.saveOrUpdate(course);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Course> getStudentCourses(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createNativeQuery("SELECT * FROM course c JOIN student_courses sc ON c.id = sc.courses_id WHERE sc.student_email = :email", Course.class)
                    .setParameter("email", email)
                    .list();
        }
    }
}
