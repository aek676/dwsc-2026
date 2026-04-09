package es.ual.manager_students.repository;

import org.springframework.data.repository.CrudRepository;

import es.ual.manager_students.domain.Degree;

public interface DegreeRepository extends CrudRepository<Degree, Long> {

  Degree findByCode(String code);
}