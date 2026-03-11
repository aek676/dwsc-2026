package sampleproject.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement
public class Users extends ArrayList<User> {

  @XmlElement(name = "user")
  public List<User> getUsers() {
    return this;
  }

}
