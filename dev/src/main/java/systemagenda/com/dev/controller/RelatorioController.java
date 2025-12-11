package systemagenda.com.dev.controller;

import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import systemagenda.com.dev.service.RelatorioService;

@Controller
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/relatorios/clientes/{clienteId}/tratamentos")
    public ResponseEntity<byte[]> relatorioTratamentosPorCliente(@PathVariable UUID clienteId) {
        byte[] pdf = relatorioService.gerarRelatorioTratamentosPorCliente(clienteId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(
                "attachment",
                "tratamentos-cliente-" + clienteId + ".pdf"
        );

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(pdf);
    }
}