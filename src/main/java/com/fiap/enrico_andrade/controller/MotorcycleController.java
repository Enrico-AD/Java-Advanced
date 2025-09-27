package com.fiap.enrico_andrade.controller;

import com.fiap.enrico_andrade.dto.MotorcycleDTO;
import com.fiap.enrico_andrade.service.ModelService;
import com.fiap.enrico_andrade.service.MotorcycleService;
import com.fiap.enrico_andrade.service.StatusService;
import com.fiap.enrico_andrade.service.YardService;
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
    private final ModelService modelService;

    public MotorcycleController(
            MotorcycleService motorcycleService,
            YardService yardService,
            StatusService statusService,
            ModelService modelService
    ) {
        this.motorcycleService = motorcycleService;
        this.yardService = yardService;
        this.statusService = statusService;
        this.modelService = modelService;
    }

    @GetMapping("/list")
    public String listMotorcycles(Model model) {
        model.addAttribute("motorcycles", motorcycleService.findAll());
        return "motorcycle/motorcycle-list :: motorcycle-list";
    }

    @GetMapping("/new")
    public String newMotorcycleForm(Model model) {
        MotorcycleDTO dto = new MotorcycleDTO();
        model.addAttribute("motorcycle", dto);
        model.addAttribute("yards", yardService.findAll());
        model.addAttribute("statuses", statusService.findAllDescriptions());
        model.addAttribute("models", modelService.findAll());
        model.addAttribute("formAction", "/motorcycle/new");

        return "motorcycle/motorcycle-form :: motorcycle-form";
    }

    @GetMapping("/{id}/edit")
    public String editMotorcycle(@PathVariable Integer id, Model model) {
        MotorcycleDTO motorcycle = motorcycleService.findById(id);
        model.addAttribute("motorcycle", motorcycle);
        model.addAttribute("yards", yardService.findAll());
        model.addAttribute("statuses", statusService.findAllDescriptions());
        model.addAttribute("models", modelService.findAll());
        model.addAttribute("formAction", "/motorcycle/" + id + "/update");

        return "motorcycle/motorcycle-form :: motorcycle-form";
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

    @GetMapping("/{id}/details")
    public String detailsMotorcycle(@PathVariable Integer id, Model model) {
        MotorcycleDTO dto = motorcycleService.findById(id);
        model.addAttribute("motorcycle", dto);
        return "motorcycle/motorcycle-detail :: motorcycle-detail";
    }

    @DeleteMapping("/{id}")
    public String deleteMotorcycle(@PathVariable Integer id, Model model) {
        try {
            motorcycleService.deleteById(id);
            model.addAttribute("motorcycles", motorcycleService.findAll());
        } catch (IllegalStateException e) {
            model.addAttribute("motorcycles", motorcycleService.findAll());
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "motorcycle/motorcycle-list :: motorcycle-list";
    }
}
