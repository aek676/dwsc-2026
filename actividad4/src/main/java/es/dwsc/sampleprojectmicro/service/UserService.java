package es.dwsc.sampleprojectmicro.service;

import es.dwsc.sampleprojectmicro.domain.User;
import es.dwsc.sampleprojectmicro.domain.Users;

public interface UserService {
  public Users getUsersFromDB();
  public User getUserFromDB(String username);
  public boolean insertUser(User user);
}
