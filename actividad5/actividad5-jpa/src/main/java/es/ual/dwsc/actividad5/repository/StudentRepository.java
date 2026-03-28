package es.ual.dwsc.actividad5.repository;

import org.springframework.data.repository.CrudRepository;

import es.ual.dwsc.actividad5.domain.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {

  Student findByName(String name);

  Student findByDni(String dni);
}
