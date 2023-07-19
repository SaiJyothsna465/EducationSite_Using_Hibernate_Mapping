package com.assignment.sai.controller;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.assignment.sai.exception.ResourceNotFoundException;
import com.assignment.sai.model.Student;
import com.assignment.sai.repository.StudentRepository;



@RestController
@RequestMapping("/api")
public class StudentController {

  @Autowired
  StudentRepository studentRepository;
  
  //return all students with courses
  @GetMapping("/students")
  public ResponseEntity<List<Student>> getAllStudents(@RequestParam(required = false) String name) {
    List<Student> students = new ArrayList<Student>();

    if (name == null)
      studentRepository.findAll().forEach(students::add);
    else
      studentRepository.findByNameContaining(name).forEach(students::add);

    if (students.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(students, HttpStatus.OK);
  }

  @GetMapping("/students/{id}")
  public ResponseEntity<Student> getStudentById(@PathVariable("id") long id) {
    Student student = studentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Student with id = " + id));

    return new ResponseEntity<>(student, HttpStatus.OK);
  }

  @PostMapping("/students")
  public ResponseEntity<Student> createStudent(@RequestBody Student student) {
    Student _student = studentRepository.save(new Student(student.getName()));
    return new ResponseEntity<>(_student, HttpStatus.CREATED);
  }


}

