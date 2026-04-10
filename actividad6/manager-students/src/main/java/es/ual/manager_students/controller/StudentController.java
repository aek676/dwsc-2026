package es.ual.manager_students.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.ual.manager_students.domain.Student;
import es.ual.manager_students.repository.StudentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/students")
@Tag(name = "Students", description = "API for managing students (JPA)")
public class StudentController {

  @Autowired
  StudentRepository studentRepo;

  @GetMapping
  @Operation(summary = "Get all students", description = "Retrieves all students from the database")
  public ResponseEntity<Iterable<Student>> getStudents() {
    return ResponseEntity.ok(studentRepo.findAll());
  }

  @GetMapping("/{name}")
  @Operation(summary = "Get student by name", description = "Retrieves a student by name")
  public ResponseEntity<Student> getStudentByName(@PathVariable String name) {
    Student student = studentRepo.findByName(name);
    if (student == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(student);
  }

  @GetMapping("/dni/{dni}")
  @Operation(summary = "Get student by DNI", description = "Retrieves a student by DNI")
  public ResponseEntity<Student> getStudentByDni(@PathVariable String dni) {
    Student student = studentRepo.findByDni(dni);
    if (student == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(student);
  }

  @PutMapping("/{dni}")
  @Operation(summary = "Update student", description = "Updates an existing student by DNI")
  public ResponseEntity<Student> updateStudent(@PathVariable String dni, @RequestBody Student student) {
    Student existing = studentRepo.findByDni(dni);
    if (existing == null) {
      return ResponseEntity.notFound().build();
    }
    existing.setName(student.getName());
    existing.setSurnames(student.getSurnames());
    return ResponseEntity.ok(studentRepo.save(existing));
  }

  @DeleteMapping("/{dni}")
  @Operation(summary = "Delete student", description = "Deletes a student by DNI")
  public ResponseEntity<Void> deleteStudent(@PathVariable String dni) {
    Student student = studentRepo.findByDni(dni);
    if (student == null) {
      return ResponseEntity.notFound().build();
    }
    studentRepo.delete(student);
    return ResponseEntity.noContent().build();
  }

}