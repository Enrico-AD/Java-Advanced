package com.fiap.enrico_andrade.controller;

import com.fiap.enrico_andrade.dto.ModelDTO;
import com.fiap.enrico_andrade.service.ModelService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/models")
public class ModelController {

    private final ModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("models", modelService.findAll());
        return "motorcycle/model-list :: model-list";
    }

    @GetMapping("/new")
    public String newForm(ModelMap map) {
        map.addAttribute("model", new ModelDTO());
        map.addAttribute("formAction", "/models/new");

        return "motorcycle/model-form :: model-form";
    }

    @PostMapping("/new")
    public ResponseEntity<Void> save(@ModelAttribute("model") ModelDTO dto, ModelMap map) {
        modelService.save(dto);
        map.addAttribute("models", modelService.findAll());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, ModelMap map) {
        ModelDTO m = modelService.findAll()
                .stream()
                .filter(mm -> mm.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Modelo não encontrado"));
        ModelDTO dto = new ModelDTO();
        dto.setName(m.getName());
        map.addAttribute("model", dto);
        map.addAttribute("id", id);
        map.addAttribute("formAction", "/model/" + id + "/update");

        return "motorcycle/model-form :: model-form";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Integer id, @ModelAttribute("modelDTO") ModelDTO dto, ModelMap map) {
        modelService.update(id, dto);
        map.addAttribute("models", modelService.findAll());
        return "motorcycle/model-list :: model-list";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id, ModelMap map) {
        try {
            modelService.delete(id);
            map.addAttribute("models", modelService.findAll());
        } catch (IllegalStateException e) {
            map.addAttribute("models", modelService.findAll());
            map.addAttribute("errorMessage", e.getMessage());
        }

        return "motorcycle/model-list :: model-list";
    }
}
