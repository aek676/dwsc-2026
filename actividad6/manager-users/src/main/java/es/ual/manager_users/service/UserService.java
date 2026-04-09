package es.ual.manager_users.service;

import es.ual.manager_users.domain.User;
import es.ual.manager_users.domain.Users;

public interface UserService {
  public Users getUsersFromDB();
  public User getUserFromDB(String username);
  public boolean insertUser(User user);
}