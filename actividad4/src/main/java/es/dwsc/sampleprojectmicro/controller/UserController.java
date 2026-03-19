package es.dwsc.sampleprojectmicro.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import es.dwsc.sampleprojectmicro.domain.Users;
import es.dwsc.sampleprojectmicro.service.UserService;

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
}
