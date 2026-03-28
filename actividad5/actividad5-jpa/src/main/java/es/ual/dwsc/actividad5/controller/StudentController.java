package es.ual.dwsc.actividad5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.ual.dwsc.actividad5.domain.Student;
import es.ual.dwsc.actividad5.repository.StudentRepository;

@RestController
@RequestMapping("/students")
public class StudentController {

  @Autowired
  StudentRepository studentRepo;

  @GetMapping
  public ResponseEntity<Iterable<Student>> getStudents() {
    return ResponseEntity.ok(studentRepo.findAll());
  }

  @GetMapping("/{name}")
  public ResponseEntity<Student> getStudentByName(@PathVariable String name) {
    Student student = studentRepo.findByName(name);
    if (student == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(student);
  }

  @GetMapping("/dni/{dni}")
  public ResponseEntity<Student> getStudentByDni(@PathVariable String dni) {
    Student student = studentRepo.findByDni(dni);
    if (student == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(student);
  }

}
