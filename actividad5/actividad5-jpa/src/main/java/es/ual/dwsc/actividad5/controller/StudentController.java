package es.ual.dwsc.actividad5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import es.ual.dwsc.actividad5.domain.Student;
import es.ual.dwsc.actividad5.repository.StudentRepository;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class StudentController {

  @Autowired
  StudentRepository studentRepo;

  @GetMapping("/students")
  public ResponseEntity<Iterable<Student>> getStudents() {
    return ResponseEntity.ok(studentRepo.findAll());
  }

}
