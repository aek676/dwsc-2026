package es.ual.dwsc.actividad5.domain;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String dni;
  private String name;
  private String surnames;

  @ManyToMany
  @JoinTable(name = "student_degree", joinColumns = { @JoinColumn(name = "student_id") }, inverseJoinColumns = {
      @JoinColumn(name = "degree_id") })
  private Set<Degree> degrees;

}
