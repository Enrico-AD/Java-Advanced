package com.fiap.enrico_andrade.security.auth;

import com.fiap.enrico_andrade.security.entity.AppUser;
import com.fiap.enrico_andrade.security.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Usuário ou senha inválidos.");
        }
        return "auth/login :: login-form";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "auth/signup :: register-form";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") AppUser user, Model model) {
        try {
            userService.register(user);
            model.addAttribute("success", "Usuário cadastrado com sucesso!");
            return "auth/login :: login-form";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", user);
            return "auth/signup :: register-form";
        }
    }
}
