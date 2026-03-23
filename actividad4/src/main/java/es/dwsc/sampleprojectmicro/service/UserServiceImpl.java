package es.dwsc.sampleprojectmicro.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

      rs.close();
      st.close();
    } catch (Exception e) {
      System.err.println("[UserService - getUsersFromDB] SQLException while querying the users");
      System.err.println(e.getMessage());
    }

    return users;
  }

  @Override
  public User getUserFromDB(String username) {
    User user = null;
    Connection conn = this.connect2DB();

    try {
      PreparedStatement pst = conn.prepareStatement("SELECT * FROM sampleusers WHERE username = ?");
      pst.setString(1, username);
      ResultSet rs = pst.executeQuery();

      if (rs.next()) {
        user = new User();
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setDni(rs.getString("dni"));
        user.setName(rs.getString("name"));
        user.setSurnames(rs.getString("surnames"));
        user.setAge(rs.getInt("age"));
      }

      rs.close();
      pst.close();
    } catch (Exception e) {
      System.err.println("[UserService - getUserFromDB] SQLException while querying user: " + username);
      System.err.println(e.getMessage());
    }

    return user;
  }

  private Connection connect2DB() {
    Connection conn = null;
    try {
      Class.forName("org.postgresql.Driver");
      String url = "jdbc:postgresql://localhost:5432/dwsc";
      conn = DriverManager.getConnection(url, "estudiante", "estudiante");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return conn;
  }
}
