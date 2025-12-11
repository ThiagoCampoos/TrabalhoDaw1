package systemagenda.com.dev.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import systemagenda.com.dev.entity.Cliente;
import systemagenda.com.dev.service.ClienteService;

@Controller
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/clientes")
    public String listarClientes(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "busca", required = false) String busca,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Cliente> pagina = clienteService.listar(busca, pageable);

        model.addAttribute("pagina", pagina);
        model.addAttribute("busca", busca);
        model.addAttribute("clienteForm", new Cliente()); // usado no form de novo cliente

        return "clientes/lista";
    }

    @PostMapping("/clientes")
    public String salvarCliente(
            @Valid @ModelAttribute("clienteForm") Cliente clienteForm,
            BindingResult bindingResult,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "busca", required = false) String busca,
            Model model) {

        if (bindingResult.hasErrors()) {
            Pageable pageable = PageRequest.of(page, size);
            Page<Cliente> pagina = clienteService.listar(busca, pageable);

            model.addAttribute("pagina", pagina);
            model.addAttribute("busca", busca);
            // clienteForm já vem preenchido com os erros de validação

            return "clientes/lista";
        }

        clienteService.salvar(clienteForm);
        return "redirect:/clientes";
    }

    @PostMapping("/clientes/{id}/excluir")
    public String excluirCliente(@PathVariable UUID id) {
        clienteService.excluir(id);
        return "redirect:/clientes";
    }
}