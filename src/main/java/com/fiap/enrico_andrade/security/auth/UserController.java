package com.fiap.enrico_andrade.security.auth;

import com.fiap.enrico_andrade.security.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/user-list-dialog :: user-list-dialog";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Integer id, Model model) {
        userService.deleteById(id);
        model.addAttribute("users", userService.findAll());
        return "user/user-list-dialog :: user-list-dialog";
    }

    @PostMapping("/{id}/promote")
    public String promoteUser(@PathVariable Integer id, Model model) {
        userService.promoteToAdmin(id);
        model.addAttribute("users", userService.findAll());
        return "user/user-list-dialog :: user-list-dialog";
    }
}