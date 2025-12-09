package systemagenda.com.dev.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import systemagenda.com.dev.entity.Sessao;
import systemagenda.com.dev.service.SessaoService;

@Controller
public class SessaoController {

    private final SessaoService sessaoService;

    public SessaoController(SessaoService sessaoService) {
        this.sessaoService = sessaoService;
    }

    @GetMapping("/tratamentos/{tratamentoId}/sessoes")
    public String listarSessoes(@PathVariable UUID tratamentoId, Model model) {
        List<Sessao> sessoes = sessaoService.listarPorTratamento(tratamentoId);
        model.addAttribute("sessoes", sessoes);
        model.addAttribute("tratamentoId", tratamentoId);
        return "sessoes/lista";
    }

    @PostMapping("/tratamentos/{tratamentoId}/sessoes")
    public String criarSessao(@PathVariable UUID tratamentoId,
                              @RequestParam("dataSessao")
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataSessao,
                              @RequestParam("protocolo") String protocolo,
                              @RequestParam("valor") double valor,
                              @RequestParam(name = "ehReavaliacao", defaultValue = "false") boolean ehReavaliacao) {

        sessaoService.criarSessao(tratamentoId, dataSessao, protocolo, valor, ehReavaliacao);
        return "redirect:/tratamentos/" + tratamentoId + "/sessoes";
    }

    @PostMapping("/tratamentos/{tratamentoId}/sessoes/{sessaoId}/excluir")
    public String excluirSessao(@PathVariable UUID tratamentoId,
                                @PathVariable UUID sessaoId) {
        sessaoService.excluir(sessaoId);
        return "redirect:/tratamentos/" + tratamentoId + "/sessoes";
    }
}