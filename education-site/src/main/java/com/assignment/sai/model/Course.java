package com.assignment.sai.model;



import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "courses")
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "title")
  private String title;

  @ManyToMany(fetch = FetchType.LAZY,
      cascade = {
          CascadeType.PERSIST,
          CascadeType.MERGE
      },
      mappedBy = "courses")
  @JsonIgnore
  private Set<Student> students = new HashSet<>();

  public Course() {

  }
  
  public Course(String title) {
	  this.title=title;
  }
  
  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Set<Student> getStudents() {
	return students;
  }

  public void setStudents(Set<Student> students) {
	this.students = students;
  }

  public void setId(long id) {
	this.id = id;
  }

  
  
}
