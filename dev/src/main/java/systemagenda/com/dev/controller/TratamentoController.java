package systemagenda.com.dev.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import systemagenda.com.dev.entity.Tratamento;
import systemagenda.com.dev.service.TratamentoService;

@Controller
public class TratamentoController {
    private final TratamentoService tratamentoService;

    public TratamentoController(TratamentoService tratamentoService) {
        this.tratamentoService = tratamentoService;
    }

    @GetMapping("/clientes/{clienteId}/tratamentos")
    public String listarTratamento(@PathVariable UUID clienteId, Model model) {
        List<Tratamento> tratamentos = tratamentoService.listarPorCliente(clienteId);
        model.addAttribute("tratamentos", tratamentos);
        model.addAttribute("clienteId", clienteId);
        model.addAttribute("tratamentos", new Tratamento());
        return "tratamentos/lista";
    }

    @PostMapping("/clientes/{clienteId}/tratamentos")
    public String SalvarTratamento(@PathVariable UUID clienteId,
            @Valid @ModelAttribute("tratamento") Tratamento tratamento,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Tratamento> tratamentos = tratamentoService.listarPorCliente(clienteId);
            model.addAttribute("tratamentos", tratamentos);
            model.addAttribute("clienteId", clienteId);
            return "tratamentos/lista";
        }
        tratamentoService.salvar(clienteId, tratamento);
        return "redirect:/clientes/" + clienteId + "/tratamentos";
    }

    @PostMapping("/clientes/{clienteId}/tratamentos/{tratamentoId}/excluir")
    public String excluirTratamento(@PathVariable UUID clienteId,
            @PathVariable UUID tratamentoId) {
        tratamentoService.excluir(tratamentoId);
        return "redirect:/clientes/" + clienteId + "/tratamentos";
    }
}
