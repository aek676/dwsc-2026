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

import es.ual.manager_students.domain.Degree;
import es.ual.manager_students.repository.DegreeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/degrees")
@Tag(name = "Degrees", description = "API for managing degrees (JPA)")
public class DegreeController {

  @Autowired
  DegreeRepository degreeRepo;

  @GetMapping
  @Operation(summary = "Get all degrees", description = "Retrieves all degrees from the database")
  public ResponseEntity<Iterable<Degree>> getAllDegrees() {
    return ResponseEntity.ok(degreeRepo.findAll());
  }

  @GetMapping("/{code}")
  @Operation(summary = "Get degree by code", description = "Retrieves a degree by code")
  public ResponseEntity<Degree> getDegreeByCode(@PathVariable String code) {
    Degree degree = degreeRepo.findByCode(code);
    if (degree == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(degree);
  }

  @PutMapping("/{code}")
  @Operation(summary = "Update degree", description = "Updates an existing degree by code")
  public ResponseEntity<Degree> updateDegree(@PathVariable String code, @RequestBody Degree degree) {
    Degree existing = degreeRepo.findByCode(code);
    if (existing == null) {
      return ResponseEntity.notFound().build();
    }
    existing.setName(degree.getName());
    existing.setProgramme(degree.getProgramme());
    return ResponseEntity.ok(degreeRepo.save(existing));
  }

  @DeleteMapping("/{code}")
  @Operation(summary = "Delete degree", description = "Deletes a degree by code")
  public ResponseEntity<Void> deleteDegree(@PathVariable String code) {
    Degree degree = degreeRepo.findByCode(code);
    if (degree == null) {
      return ResponseEntity.notFound().build();
    }
    degreeRepo.delete(degree);
    return ResponseEntity.noContent().build();
  }

}