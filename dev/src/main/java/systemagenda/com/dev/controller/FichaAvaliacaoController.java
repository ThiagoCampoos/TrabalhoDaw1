package systemagenda.com.dev.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import systemagenda.com.dev.entity.FichaAvaliacao;
import systemagenda.com.dev.service.FichaAvaliacaoService;

@Controller
public class FichaAvaliacaoController {
    private final FichaAvaliacaoService fichaAvaliacaoService;

    public FichaAvaliacaoController(FichaAvaliacaoService fichaAvaliacaoService) {
        this.fichaAvaliacaoService = fichaAvaliacaoService;
    }

    @GetMapping("/clientes/{clienteId}/ficha")
    public String exibirFormulario(@PathVariable UUID  clienteId, Model model) {
        FichaAvaliacao ficha = fichaAvaliacaoService.buscarPorCliente(clienteId);
        model.addAttribute("ficha", ficha);
        model.addAttribute("clienteId", clienteId);
        return "fichas/form";
    }
    @PostMapping ("/clientes/{clienteId}/ficha")
    public String salvarFicha(@PathVariable UUID clienteId, @Valid @ModelAttribute("ficha") FichaAvaliacao ficha,
    BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("clienteId", clienteId);
            return "fichas/form";
        }

        fichaAvaliacaoService.salvar(clienteId, ficha);
        return "redirect:/clientes";
    }
}
