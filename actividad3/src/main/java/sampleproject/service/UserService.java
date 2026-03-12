package sampleproject.service;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.*;
import sampleproject.domain.User;
import sampleproject.domain.Users;

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

  @POST
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  @Produces(MediaType.APPLICATION_JSON)
  public Response createUser(User user) {
    if (user.getUsername() == null || user.getDni() == null) {
      return Response.status(400).entity("{\"error\": \"Username and DNI are required\"}").build();
    }

    if (this.existsUsername(user.getUsername())) {
      return Response.status(409).entity("{\"error\": \"Username already exists\"}").build();
    }

    if (this.existsDni(user.getDni())) {
      return Response.status(409).entity("{\"error\": \"DNI already exists\"}").build();
    }

    boolean inserted = this.insertUser(user);
    if (inserted) {
      return Response.status(201).entity(user).build();
    } else {
      return Response.status(500).entity("{\"error\": \"Failed to insert user\"}").build();
    }
  }

  private boolean existsUsername(String username) {
    Connection conn = this.connect2DB();
    try {
      PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM sampleusers WHERE username = ?");
      ps.setString(1, username);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        return rs.getInt(1) > 0;
      }
      rs.close();
      ps.close();
      conn.close();
    } catch (SQLException e) {
      System.err.println("[UserService - existsUsername] SQLException: " + e.getMessage());
    }
    return false;
  }

  private boolean existsDni(String dni) {
    Connection conn = this.connect2DB();
    try {
      PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM sampleusers WHERE dni = ?");
      ps.setString(1, dni);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        return rs.getInt(1) > 0;
      }
      rs.close();
      ps.close();
      conn.close();
    } catch (SQLException e) {
      System.err.println("[UserService - existsDni] SQLException: " + e.getMessage());
    }
    return false;
  }

  private boolean insertUser(User user) {
    Connection conn = this.connect2DB();
    try {
      PreparedStatement ps = conn.prepareStatement(
          "INSERT INTO sampleusers (username, password, dni, name, surnames, age) VALUES (?, ?, ?, ?, ?, ?)");
      ps.setString(1, user.getUsername());
      ps.setString(2, user.getPassword());
      ps.setString(3, user.getDni());
      ps.setString(4, user.getName());
      ps.setString(5, user.getSurnames());
      ps.setInt(6, user.getAge());
      int result = ps.executeUpdate();
      ps.close();
      conn.close();
      return result > 0;
    } catch (SQLException e) {
      System.err.println("[UserService - insertUser] SQLException: " + e.getMessage());
    }
    return false;
  }

  private Connection connect2DB() {
    Connection conn = null;
    try {
      Class.forName("org.postgresql.Driver");
      String url = "jdbc:postgresql://postgres:5432/dwsc";
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
