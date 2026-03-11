package sampleproject.domain;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement
@XmlType(propOrder = { "username", "password", "dni", "name", "surnames", "age" })
public class User {

  private String username;
  private String password;

  private String dni;
  private String name;
  private String surnames;

  private int age;

}
