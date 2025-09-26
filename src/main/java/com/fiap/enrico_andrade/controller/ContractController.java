package com.fiap.enrico_andrade.controller;

import com.fiap.enrico_andrade.dto.AddressDTO;
import com.fiap.enrico_andrade.dto.ContractDTO;
import com.fiap.enrico_andrade.dto.ContractUpdateDTO;
import com.fiap.enrico_andrade.dto.MotorcycleDTO;
import com.fiap.enrico_andrade.dto.TenantDTO;
import com.fiap.enrico_andrade.service.ContractService;
import com.fiap.enrico_andrade.service.MotorcycleService;
import com.fiap.enrico_andrade.service.StatusService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContractController {

    private final ContractService contractService;
    private final MotorcycleService motorcycleService;
    private final StatusService statusService;

    public ContractController(
            ContractService contractService,
            MotorcycleService motorcycleService,
            StatusService statusService
    ) {
        this.contractService = contractService;
        this.motorcycleService = motorcycleService;
        this.statusService = statusService;
    }

    @GetMapping("/list/contracts")
    public String listContracts(Model model) {
        List<ContractDTO> contracts = contractService.listAll();
        model.addAttribute("contracts", contracts);
        return "fragments/contracts-list :: contracts-list";
    }

    @GetMapping("/contract/{id}/details")
    public String getContractDetails(@PathVariable Integer id, Model model) {
        ContractDTO dto = contractService.findDetailById(id)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado"));
        model.addAttribute("contract", dto);
        return "fragments/contract-dialog :: contract-dialog";
    }

    @GetMapping("/contract/{id}/edit")
    public String editContract(@PathVariable Integer id, Model model) {
        ContractUpdateDTO dto = contractService.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado"));

        model.addAttribute("contract", dto);
        model.addAttribute("contractId", id);
        model.addAttribute("motorcycles", motorcycleService.findAvailable());
        model.addAttribute("statuses", statusService.findAllDescriptions());

        return "fragments/contract-edit-form :: contract-edit-form";
    }

    @PostMapping("/contract/{id}/update")
    @ResponseBody
    public String updateContract(
            @PathVariable Integer id,
            @ModelAttribute("contract") ContractUpdateDTO contractDTO,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("motorcycles", motorcycleService.findAvailable());
            model.addAttribute("statuses", statusService.findAll());
            return "fragments/contract-edit-form :: contract-edit-form";
        }

        contractService.updateContract(id, contractDTO);

        ContractUpdateDTO updated = contractService.findById(id).orElseThrow();
        model.addAttribute("contract", updated);
        return "OK";
    }

    @GetMapping("/contract/new")
    public String newContractForm(Model model) {
        ContractUpdateDTO dto = new ContractUpdateDTO();
        dto.setTenant(new TenantDTO());
        dto.setMotorcycle(new MotorcycleDTO());
        dto.getTenant().setAddress(new AddressDTO());

        model.addAttribute("contract", dto);
        model.addAttribute("motorcycles", motorcycleService.findAvailable());
        model.addAttribute("statuses", statusService.findAll());

        return "fragments/contract-edit-form :: contract-edit-form";
    }

    @PostMapping("/contract/new")
    @ResponseBody
    public ResponseEntity<Void> createContract(
            @Valid @ModelAttribute("contract") ContractUpdateDTO contractDTO,
            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        contractService.createContract(contractDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/contract/{id}/finalize")
    public String finalizeContract(@PathVariable Integer id, Model model) {
        contractService.finalizeContract(id);

        ContractUpdateDTO updated = contractService.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado"));

        model.addAttribute("contract", updated);
        return "fragments/contract-dialog :: contract-dialog";
    }
}
