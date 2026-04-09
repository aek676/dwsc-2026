package es.ual.manager_users.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import es.ual.manager_users.domain.User;
import es.ual.manager_users.domain.Users;
import es.ual.manager_users.service.UserService;

@Controller
public class UserController {

  @Autowired
  private UserService userService;

  @RequestMapping("/userstable")
  public String getUsersTab(Map<String, Users> model) {

    Users users = userService.getUsersFromDB();
    model.put("users", users);

    return "usertemplate";
  }

  @RequestMapping("/users")
  public @ResponseBody Users getUsers() {
    return userService.getUsersFromDB();
  }

  @GetMapping("/users/{username}")
  public ResponseEntity<User> getUser(@PathVariable String username) {
    User user = userService.getUserFromDB(username);
    if (user != null) {
      return ResponseEntity.ok(user);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/users")
  public ResponseEntity<Map<String, String>> insertUser(@RequestBody User user) {
    boolean inserted = userService.insertUser(user);
    if (inserted) {
      return ResponseEntity.ok(Map.of("message", "User created successfully"));
    } else {
      return ResponseEntity.badRequest().body(Map.of("error", "Username or DNI already exists"));
    }
  }

  @GetMapping("/usertable/{username}")
  public String getUserHtml(@PathVariable String username, Map<String, User> model) {
    User user = userService.getUserFromDB(username);

    if (user != null) {
      model.put("user", user);
      return "userview";
    }

    return "error";
  }
}