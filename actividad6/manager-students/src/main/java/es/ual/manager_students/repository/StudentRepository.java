package es.ual.manager_students.repository;

import org.springframework.data.repository.CrudRepository;

import es.ual.manager_students.domain.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {

  Student findByName(String name);

  Student findByDni(String dni);
}