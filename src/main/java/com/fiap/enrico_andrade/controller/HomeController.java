package com.fiap.enrico_andrade.controller;

import com.fiap.enrico_andrade.repository.MotorcycleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final MotorcycleRepository motorcycleRepository;

    public HomeController(MotorcycleRepository motorcycleRepository) {
        this.motorcycleRepository = motorcycleRepository;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }


}
