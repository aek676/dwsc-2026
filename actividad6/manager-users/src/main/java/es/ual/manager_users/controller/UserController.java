package es.ual.manager_users.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.ual.manager_users.domain.User;
import es.ual.manager_users.domain.Users;
import es.ual.manager_users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "API for managing users")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping
  @Operation(summary = "Get all users", description = "Retrieves all users from the database")
  public Users getUsers() {
    return userService.getUsersFromDB();
  }

  @GetMapping("/{username}")
  @Operation(summary = "Get user by username", description = "Retrieves a user by username")
  public ResponseEntity<User> getUser(@PathVariable String username) {
    User user = userService.getUserFromDB(username);
    if (user != null) {
      return ResponseEntity.ok(user);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping
  @Operation(summary = "Create new user", description = "Creates a new user in the database")
  public ResponseEntity<Map<String, String>> insertUser(@RequestBody User user) {
    boolean inserted = userService.insertUser(user);
    if (inserted) {
      return ResponseEntity.ok(Map.of("message", "User created successfully"));
    } else {
      return ResponseEntity.badRequest().body(Map.of("error", "Username or DNI already exists"));
    }
  }
}