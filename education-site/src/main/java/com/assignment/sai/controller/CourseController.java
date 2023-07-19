package com.assignment.sai.controller;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.assignment.sai.exception.ResourceNotFoundException;
import com.assignment.sai.model.Course;
import com.assignment.sai.model.Student;
import com.assignment.sai.repository.CourseRepository;
import com.assignment.sai.repository.StudentRepository;



@RestController
@RequestMapping("/api")
public class CourseController {

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private StudentRepository studentRepository;

  @GetMapping("/courses")
  public ResponseEntity<List<Course>> getAllCourses() {
    List<Course> courses = new ArrayList<Course>();

    courseRepository.findAll().forEach(courses::add);

    if (courses.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(courses, HttpStatus.OK);
  }
  
  @GetMapping("/students/{studentId}/courses")
  public ResponseEntity<List<Course>> getAllCoursesByStudentId(@PathVariable(value = "studentId") Long studentId) {
    if (!studentRepository.existsById(studentId)) {
      throw new ResourceNotFoundException("Not found Student with id = " + studentId);
    }

    List<Course> courses = courseRepository.findCoursesByStudentsId(studentId);
    return new ResponseEntity<>(courses, HttpStatus.OK);
  }

  @GetMapping("/courses/{id}")
  public ResponseEntity<Course> getCoursesById(@PathVariable(value = "id") Long id) {
    Course course = courseRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Course with id = " + id));

    return new ResponseEntity<>(course, HttpStatus.OK);
  }
  
  @GetMapping("/courses/{courseId}/students")
  public ResponseEntity<List<Student>> getAllStudentsByCourseId(@PathVariable(value = "courseId") Long courseId) {
    if (!courseRepository.existsById(courseId)) {
      throw new ResourceNotFoundException("Not found Course  with id = " + courseId);
    }

    List<Student> students = studentRepository.findStudentsByCoursesId(courseId);
    return new ResponseEntity<>(students, HttpStatus.OK);
  }

  @PostMapping("/students/{studentId}/courses")
  public ResponseEntity<Course> addCourse(@PathVariable(value = "studentId") Long studentId, @RequestBody Course courseRequest) {
    Course course = studentRepository.findById(studentId).map(student -> {
      long courseId = courseRequest.getId();
      
      // course exist
      if (courseId != 0L) {
        Course _course =courseRepository.findById(courseId)
            .orElseThrow(() -> new ResourceNotFoundException("Not found Course with id = " + courseId));
        student.addCourse(_course);
        studentRepository.save(student);
        return _course;
      }
      
      // add and create new course
      student.addCourse(courseRequest);
      return courseRepository.save(courseRequest);
    }).orElseThrow(() -> new ResourceNotFoundException("Not found Student with id = " + studentId));

    return new ResponseEntity<>(course, HttpStatus.CREATED);
  }

 

}

