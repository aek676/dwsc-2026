package es.ual.manager_users.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Service;

import es.ual.manager_users.domain.User;
import es.ual.manager_users.domain.Users;

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

  @Override
  public boolean insertUser(User user) {
    Connection conn = this.connect2DB();

    try {
      PreparedStatement checkUsername = conn.prepareStatement("SELECT * FROM sampleusers WHERE username = ?");
      checkUsername.setString(1, user.getUsername());
      ResultSet rsUsername = checkUsername.executeQuery();
      boolean usernameExists = rsUsername.next();
      rsUsername.close();
      checkUsername.close();

      if (usernameExists) {
        conn.close();
        return false;
      }

      PreparedStatement checkDni = conn.prepareStatement("SELECT * FROM sampleusers WHERE dni = ?");
      checkDni.setString(1, user.getDni());
      ResultSet rsDni = checkDni.executeQuery();
      boolean dniExists = rsDni.next();
      rsDni.close();
      checkDni.close();

      if (dniExists) {
        conn.close();
        return false;
      }

      PreparedStatement insert = conn.prepareStatement("INSERT INTO sampleusers (username, password, dni, name, surnames, age) VALUES (?, ?, ?, ?, ?, ?)");
      insert.setString(1, user.getUsername());
      insert.setString(2, user.getPassword());
      insert.setString(3, user.getDni());
      insert.setString(4, user.getName());
      insert.setString(5, user.getSurnames());
      insert.setInt(6, user.getAge());
      insert.executeUpdate();
      insert.close();
      conn.close();

      return true;
    } catch (Exception e) {
      System.err.println("[UserService - insertUser] Error inserting user: " + user.getUsername());
      System.err.println(e.getMessage());
      return false;
    }
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