package com.assignment.sai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.sai.model.Course;
import com.assignment.sai.model.Student;



public interface CourseRepository extends JpaRepository<Course, Long> {
  List<Course> findByTitleContaining(String title);
  List<Course> findCoursesByStudentsId(Long studentId);
}
