package org.example.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "student_courses",
            joinColumns = { @JoinColumn(name = "student_email") },
            inverseJoinColumns = { @JoinColumn(name = "courses_id") }
    )
    private Set<Course> courses = new HashSet<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
