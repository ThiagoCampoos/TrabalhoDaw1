package systemagenda.com.dev.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        // Se a requisição vier via htmx (hx-target na tabela), retornamos só o fragmento
        return "clientes/lista";
    }
}