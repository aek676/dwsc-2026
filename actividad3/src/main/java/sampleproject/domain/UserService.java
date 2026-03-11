package sampleproject.domain;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.sql.*;

@Path("/users")
public class UserService {

  @GET
  @Produces({ "application/json", "application/xml" })
  public Response getUsers() {
    Users users = this.getUsersFromDB();

    return Response.status(200).entity(users).build();
  }

  @Path("xml")
  @GET
  @Produces("application/xml")
  public Response getUsersXML() {
    Users users = this.getUsersFromDB();
    return Response.status(200).entity(users).build();
  }

  @Path("json")
  @GET
  @Produces("application/json")
  public Response getUsersJSON() {
    Users users = this.getUsersFromDB();
    return Response.status(200).entity(users).build();
  }

  @Path("{username}")
  @GET
  @Produces("application/json")
  public Response getExampleUser(@PathParam("username") String username) {
    User user = this.getUserFromDB(username);
    if (user != null) {
      return Response.status(200).entity(user).build();
    } else {
      return Response.status(400).build();
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

  private Users getUsersFromDB() {
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
      conn.close();
    } catch (SQLException e) {
      System.err.println("[UserService - getUsersFromDB] SQLException while querying the users");
      System.err.println(e.getMessage());
    }
    return users;
  }

  private User getUserFromDB(String username) {
    User user = null;
    Connection conn = this.connect2DB();
    try {
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery("SELECT * FROM sampleusers WHERE username = '" + username + "'");
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
      st.close();
      conn.close();
    } catch (SQLException e) {
      System.err.println("[UserService - getUserFromDB] SQLException while querying a user");
      System.err.println(e.getMessage());
    }
    return user;
  }

}
