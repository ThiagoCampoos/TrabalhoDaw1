package systemagenda.com.dev.dto;

import java.time.LocalDate;
import java.util.UUID;

public class TratamentoResumoDTO {
    private UUID tratamentoId;
    private String areaTratamento;
    private int sessaoRecomendadas;
    private int sessoesRealizadas;
    private int sessoesPendentes;
    private LocalDate proximaSessao;

    public TratamentoResumoDTO(UUID tratamentoId, String areaTratamento, int sessaoRecomendadas, int sessoesRealizadas, int sessoesPendentes, LocalDate proximaSessao) {
        this.tratamentoId = tratamentoId;
        this.areaTratamento = areaTratamento;
        this.sessaoRecomendadas = sessaoRecomendadas;
        this.sessoesRealizadas = sessoesRealizadas;
        this.sessoesPendentes = sessoesPendentes;
        this.proximaSessao = proximaSessao;
    }

    public UUID getTratamentoId() {
        return tratamentoId;
    }

    public String getAreaTratamento() {
        return areaTratamento;
    }

    public int getSessaoRecomendadas() {
        return sessaoRecomendadas;
    }

    public int getSessoesRealizadas() {
        return sessoesRealizadas;
    }

    public int getSessoesPendentes() {
        return sessoesPendentes;
    }

    public LocalDate getProximaSessao() {
        return proximaSessao;
}
}