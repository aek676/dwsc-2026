package es.ual.dwsc.actividad5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

}
