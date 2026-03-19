package es.dwsc.sampleprojectmicro.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

  private String username;
  private String password;
  private String dni;
  private String name;
  private String surnames;
  private int age;
}
