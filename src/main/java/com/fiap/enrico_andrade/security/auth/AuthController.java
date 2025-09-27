package com.fiap.enrico_andrade.security.auth;

import com.fiap.enrico_andrade.security.entity.AppUser;
import com.fiap.enrico_andrade.security.entity.Role;
import com.fiap.enrico_andrade.security.repository.UserRepository;
import com.fiap.enrico_andrade.security.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "auth/login :: login-form";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "auth/signup :: register-form";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") AppUser user, Model model) {
        userService.register(user);

        model.addAttribute("success", "Usuário cadastrado com sucesso!");
        return "auth/login :: login-form";
    }
}

