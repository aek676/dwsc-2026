package es.ual.dwsc.actividad5_rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import es.ual.dwsc.actividad5_rest.domain.Student;

@RestResource(path = "students", rel = "students")
public interface StudentRepository extends CrudRepository<Student, Long> {

  Student findByName(@Param("name") String name);

  Student findByDni(@Param("dni") String dni);

}
