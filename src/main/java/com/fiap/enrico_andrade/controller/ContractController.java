package com.fiap.enrico_andrade.controller;

import com.fiap.enrico_andrade.dto.AddressDTO;
import com.fiap.enrico_andrade.dto.ContractDTO;
import com.fiap.enrico_andrade.dto.ContractUpdateDTO;
import com.fiap.enrico_andrade.dto.MotorcycleDTO;
import com.fiap.enrico_andrade.dto.StatusDTO;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/contract")
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

    @GetMapping("/list")
    public String listContracts(Model model) {
        List<ContractDTO> contracts = contractService.listAll();
        model.addAttribute("contracts", contracts);
        return "contract/contracts-list :: contracts-list";
    }

    @GetMapping("/{id}/details")
    public String getContractDetails(@PathVariable Integer id, Model model) {
        ContractDTO dto = contractService.findDetailById(id)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado"));
        model.addAttribute("contract", dto);
        return "contract/contract-detail :: contract-detail";
    }

    @GetMapping("/{id}/edit")
    public String editContract(@PathVariable Integer id, Model model) {
        ContractUpdateDTO dto = contractService.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado"));

        model.addAttribute("contract", dto);
        model.addAttribute("motorcycles", motorcycleService.findAvailable());

        List<StatusDTO> statuses = statusService.findAll()
                .stream()
                .map(s -> new StatusDTO(s.getId(), s.getDescription()))
                .toList();

        model.addAttribute("statuses", statuses);
        model.addAttribute("formAction", "/contract/" + id + "/update");

        return "contract/contract-form :: contract-form";
    }

    @PostMapping("/{id}/update")
    public String updateContract(
            @ModelAttribute("contract") ContractUpdateDTO contractDTO,
            Model model) {

        contractService.updateContract(contractDTO.getId(), contractDTO);

        model.addAttribute("contracts", contractService.listAll());
        return "contract/contracts-list :: contracts-list";
    }

    @GetMapping("/new")
    public String newContractForm(Model model) {
        ContractUpdateDTO dto = new ContractUpdateDTO();
        dto.setTenant(new TenantDTO());
        dto.setMotorcycle(new MotorcycleDTO());
        dto.getTenant().setAddress(new AddressDTO());

        model.addAttribute("contract", dto);
        model.addAttribute("motorcycles", motorcycleService.findAvailable());
        model.addAttribute("statuses", statusService.findAllDescriptions());
        model.addAttribute("formAction", "/contract/new");

        return "contract/contract-form :: contract-form";
    }

    @PostMapping("/new")
    public String createContract(
            @ModelAttribute("contract") ContractUpdateDTO contractDTO,
            Model model) {

        contractService.createContract(contractDTO);

        model.addAttribute("contracts", contractService.listAll());
        return "contract/contracts-list :: contracts-list";
    }

    @PostMapping("/{id}/finalize")
    public String finalizeContract(@PathVariable Integer id, Model model) {
        contractService.finalizeContract(id);

        ContractUpdateDTO updated = contractService.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado"));

        model.addAttribute("contract", updated);
        return "contract/contracts-list :: contracts-list";
    }
}
