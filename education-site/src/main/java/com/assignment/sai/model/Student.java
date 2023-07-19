package com.assignment.sai.model;



import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "students")
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name")
  private String name;


  @ManyToMany(fetch = FetchType.LAZY,
      cascade = {
          CascadeType.PERSIST,
          CascadeType.MERGE
      })
  @JoinTable(name = "student_course",
        joinColumns = { @JoinColumn(name = "student_id") },
        inverseJoinColumns = { @JoinColumn(name = "course_id") })
  private Set<Course> courses = new HashSet<>();
  
  public Student() {

  }

  public Student(String name) {
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  

  public Set<Course> getCourses() {
    return courses;
  }

  public void setCourses(Set<Course> courses) {
    this.courses = courses;
  }
  
  public void addCourse(Course course) {
    this.courses.add(course);
    course.getStudents().add(this);
  }
  


  @Override
  public String toString() {
    return "Student [id=" + id + ", name=" + name + "]";
  }

}
