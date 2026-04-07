package es.ual.dwsc.actividad5_rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import es.ual.dwsc.actividad5_rest.domain.Degree;

@RestResource(path = "degrees", rel = "degrees")
public interface DegreeRepository extends CrudRepository<Degree, Long> {
}
