package com.fiap.enrico_andrade.security.auth;

import com.fiap.enrico_andrade.security.entity.AppUser;
import com.fiap.enrico_andrade.security.entity.Role;
import com.fiap.enrico_andrade.security.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") AppUser user, Model model) {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "Nome de usuário indisponível!");
            return "auth/register";
        }

        if (user.getPassword().length() < 5) {
            model.addAttribute("error", "A senha deve ter pelo menos 5 caracteres.");
            return "auth/register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_ADMIN);
        userRepository.save(user);

        model.addAttribute("success", "Cadastro realizado com sucesso! Faça login.");
        return "auth/login";
    }
}
