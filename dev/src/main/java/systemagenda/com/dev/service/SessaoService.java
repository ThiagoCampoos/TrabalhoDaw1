package systemagenda.com.dev.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import systemagenda.com.dev.entity.Sessao;
import systemagenda.com.dev.entity.Tratamento;
import systemagenda.com.dev.repository.SessaoRepository;
import systemagenda.com.dev.repository.TratamentoRepository;

@Service
public class SessaoService {
    private final SessaoRepository sessaoRepository;
    private final TratamentoRepository tratamentoRepository;

    public SessaoService(SessaoRepository sessaoRepository, TratamentoRepository tratamentoRepository) {
        this.sessaoRepository = sessaoRepository;
        this.tratamentoRepository = tratamentoRepository;
    }

    @Transactional(readOnly = true)
    public List<Sessao> listarPorTratamento(UUID tratamentoId) {
        Tratamento tratamento = tratamentoRepository.findById(tratamentoId)
                .orElseThrow(() -> new IllegalArgumentException("Tratamento não encontrado"));
        return sessaoRepository.findByTratamentoOrderByDataSessaoAsc(tratamento);
    }

    @Transactional
    public Sessao criarSessao(UUID tratamentoId, LocalDate data, String protocolo, double valor,
            boolean ehReavaliacao) {
        Tratamento tratamento = tratamentoRepository.findById(tratamentoId)
                .orElseThrow(() -> new IllegalArgumentException("Tratamento não encontrado"));

        if (data.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data da sessão não pode ser no passado");
        }
        
        boolean jaExisteNaData = sessaoRepository.existsByTratamentoAndDataSessao(tratamento, data);
        
        if (jaExisteNaData) {
            throw new IllegalArgumentException("Já existe uma sessão agendada para essa data");
        }

        if (tratamento.getDataInicio() == null) {
            tratamento.setDataInicio(data);
        }
        
        Sessao sessao = new Sessao(data, protocolo, valor, ehReavaliacao, tratamento);
        Sessao salva = sessaoRepository.save(sessao);

        int realizadas = tratamento.getSessoesRealizadas() + 1;
        tratamento.setSessoesRealizadas(realizadas);

        atualizarStatusTratamento(tratamento);

        tratamentoRepository.save(tratamento);

        return salva;
    }

    private void atualizarStatusTratamento(Tratamento tratamento) {
        int realizadas = tratamento.getSessoesRealizadas();
        int recomendadas = tratamento.getSessoesRecomendadas();

        if (realizadas == 0) {
            tratamento.setStatus("AGENDADO");
        } else if (realizadas < recomendadas) {
            tratamento.setStatus("EM ANDAMENTO");
        } else {
            tratamento.setStatus("CONCLUÍDO");
        }
    }

    @Transactional
    public void excluir(UUID sessaoId) {
        Sessao sessao = sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new IllegalArgumentException("Sessão não encontrada"));
        Tratamento tratamento = sessao.getTratamento();

        sessaoRepository.delete(sessao);

        int realizadas = Math.max(0, tratamento.getSessoesRealizadas() - 1);
        tratamento.setSessoesRealizadas(realizadas);
        atualizarStatusTratamento(tratamento);
        tratamentoRepository.save(tratamento);
    }
}
