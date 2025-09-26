package com.fiap.enrico_andrade.controller;

import com.fiap.enrico_andrade.dto.MotorcycleDTO;
import com.fiap.enrico_andrade.service.MotorcycleService;
import com.fiap.enrico_andrade.service.StatusService;
import com.fiap.enrico_andrade.service.YardService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/motorcycle")
public class MotorcycleController {

    private final MotorcycleService motorcycleService;
    private final YardService yardService;
    private final StatusService statusService;

    public MotorcycleController(
            MotorcycleService motorcycleService,
            YardService yardService,
            StatusService statusService
    ) {
        this.motorcycleService = motorcycleService;
        this.yardService = yardService;
        this.statusService = statusService;
    }

    @GetMapping("/list")
    public String listMotorcycles(Model model) {
        model.addAttribute("motorcycles", motorcycleService.findAll());
        return "motorcycle/motorcycle-list :: motorcycle-list";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute MotorcycleDTO dto, Model model) {
        motorcycleService.save(dto);
        model.addAttribute("motorcycles", motorcycleService.findAll());
        return "motorcycle/motorcycle-list :: motorcycle-list";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Integer id, @ModelAttribute MotorcycleDTO dto, Model model) {
        motorcycleService.updateDTO(id, dto);
        model.addAttribute("motorcycles", motorcycleService.findAll());
        return "motorcycle/motorcycle-list :: motorcycle-list";
    }

    @GetMapping("/{id}/edit")
    public String editMotorcycle(@PathVariable Integer id, Model model) {
        MotorcycleDTO motorcycle = motorcycleService.findById(id);
        model.addAttribute("motorcycle", motorcycle);
        model.addAttribute("yards", yardService.findAll());
        model.addAttribute("statuses", statusService.findAllDescriptions());
        return "motorcycle/motorcycle-form :: motorcycle-form";
    }

    @GetMapping("/{id}/details")
    public String detailsMotorcycle(@PathVariable Integer id, Model model) {
        MotorcycleDTO dto = motorcycleService.findById(id);
        model.addAttribute("motorcycle", dto);
        return "motorcycle/motorcycle-detail :: motorcycle-detail";
    }

    @DeleteMapping("/{id}")
    public String deleteMotorcycle(@PathVariable Integer id, Model model) {
        motorcycleService.deleteById(id);
        model.addAttribute("motorcycles", motorcycleService.findAvailable());
        return "motorcycle/motorcycle-list :: motorcycle-list";
    }
}
