package es.dwsc.sampleprojectmicro.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.stereotype.Service;

import es.dwsc.sampleprojectmicro.domain.User;
import es.dwsc.sampleprojectmicro.domain.Users;

@Service
public class UserServiceImpl implements UserService {
  @Override
  public Users getUsersFromDB() {
    Users users = new Users();

    Connection conn = this.connect2DB();

    try {
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery("SELECT * FROM sampleusers ORDER BY age");
      while (rs.next()) {
        User user = new User();
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setDni(rs.getString("dni"));
        user.setName(rs.getString("name"));
        user.setSurnames(rs.getString("surnames"));
        user.setAge(rs.getInt("age"));
        users.add(user);
      }
    } catch (Exception e) {
      // TODO: handle exception
    }
  }

}
