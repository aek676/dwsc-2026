package es.ual.dwsc.actividad5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.ual.dwsc.actividad5.domain.Degree;
import es.ual.dwsc.actividad5.repository.DegreeRepository;

@RestController
@RequestMapping("/degrees")
public class DegreeController {

  @Autowired
  DegreeRepository degreeRepo;

  @GetMapping
  public ResponseEntity<Iterable<Degree>> getAllDegrees() {
    return ResponseEntity.ok(degreeRepo.findAll());
  }

  @GetMapping("/{code}")
  public ResponseEntity<Degree> getDegreeByCode(@PathVariable String code) {
    Degree degree = degreeRepo.findByCode(code);
    if (degree == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(degree);
  }

  @PutMapping("/{code}")
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
  public ResponseEntity<Void> deleteDegree(@PathVariable String code) {
    Degree degree = degreeRepo.findByCode(code);
    if (degree == null) {
      return ResponseEntity.notFound().build();
    }
    degreeRepo.delete(degree);
    return ResponseEntity.noContent().build();
  }

}
