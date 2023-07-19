package com.assignment.sai;



import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.assignment.sai.model.Course;
import com.assignment.sai.model.Student;
import com.assignment.sai.repository.CourseRepository;
import com.assignment.sai.repository.StudentRepository;



@DataJpaTest
public class JPAUnitTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  StudentRepository repository;
  
  @Autowired
  CourseRepository courseRepo;

  @Test
  public void should_find_no_students_if_repository_is_empty() {
    Iterable<Student> students = repository.findAll();

    assertThat(students).isEmpty();
  }

  @Test
  public void should_store_a_student() {
    Student student = repository.save(new Student("Student name"));

    assertThat(student).hasFieldOrPropertyWithValue("name", "Student name");
  }

  @Test
  public void should_find_all_students() {
    Student s1 = new Student("sai");
    entityManager.persist(s1);

    Student s2 = new Student("jyothsna");
    entityManager.persist(s2);
    
    Student s3 = new Student("padma");
    entityManager.persist(s3);

    Iterable<Student> students = repository.findAll();

    assertThat(students).hasSize(3).contains(s1,s2,s3);
  }

  @Test
  public void should_find_student_by_id() {
	Student s1 = new Student("sai");
    entityManager.persist(s1);

	Student s2 = new Student("jyothsna");
	entityManager.persist(s2);

    Student foundStudent = repository.findById(s2.getId()).get();

    assertThat(foundStudent).isEqualTo(s2);
  }


  @Test
  public void should_find_students_by_name_containing_string() {
	Student s1 = new Student("sai");
	entityManager.persist(s1);

	Student s2 = new Student("sai jyothsna");
	entityManager.persist(s2);
	    
	Student s3 = new Student("padma");
	entityManager.persist(s3);


    Iterable<Student> students = repository.findByNameContaining("sai");

    assertThat(students).hasSize(2).contains(s1,s2);
  }
  
  
  @Test
  public void should_find_no_courses_if_repository_is_empty() {
    Iterable<Course> courses = courseRepo.findAll();

    assertThat(courses).isEmpty();
  }
  
  @Test
  public void should_store_a_course() {
    Course course = courseRepo.save(new Course("Course title"));

    assertThat(course).hasFieldOrPropertyWithValue("title", "Course title");
  }

  @Test
  public void should_find_all_courses() {
    Course c1 = new Course("Spring");
    entityManager.persist(c1);

    Course c2=new Course("SpringBoot");
    entityManager.persist(c2);
    
    Course c3=new Course("Java");
    entityManager.persist(c3);
    
    Iterable<Course> courses = courseRepo.findAll();

    assertThat(courses).hasSize(3).contains(c1,c2,c3);
  }
}
