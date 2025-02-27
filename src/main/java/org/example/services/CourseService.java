package org.example.services;

import org.example.dao.CourseI;
import org.example.models.Course;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.example.utils.HibernateUtil;

import java.util.List;

public class CourseService implements CourseI {

    @Override
    public void createCourse(Course course) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(course);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Course> getAllCourses() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Course", Course.class).list();
        }
    }

    @Override
    public Course getCourseById(int courseId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Course.class, courseId);
        }
    }
}