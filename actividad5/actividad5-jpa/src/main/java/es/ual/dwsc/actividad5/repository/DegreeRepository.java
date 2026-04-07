package es.ual.dwsc.actividad5.repository;

import org.springframework.data.repository.CrudRepository;

import es.ual.dwsc.actividad5.domain.Degree;

public interface DegreeRepository extends CrudRepository<Degree, Long> {
}
