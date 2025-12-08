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

    public SessaoService(SessaoRepository sessaoRepository, TratamentoRepository tratamentoRepository){
        this.sessaoRepository = sessaoRepository;
        this.tratamentoRepository = tratamentoRepository;
    }

    @Transactional(readOnly = true)
    public List<Sessao> listarPorTratamento(UUID tratamentoId){
        Tratamento tratamento = tratamentoRepository.findById(tratamentoId)
            .orElseThrow(() -> new IllegalArgumentException("Tratamento não encontrado"));
        return sessaoRepository.findByTratamentoOrderByDataSessaoAsc(tratamento);
    }
    
    @Transactional
    public Sessao CriarSessao (UUID tratamentoId, LocalDate data, String protocolo, double valor, boolean ehReavaliacao){
        Tratamento tratamento = tratamentoRepository.findById(tratamentoId)
            .orElseThrow(() -> new IllegalArgumentException("Tratamento não encontrado"));

        Sessao sessao = new Sessao();
        sessao.setTratamento(tratamento);
        sessao.setDataSessao(data);
        sessao.setProtocolo(protocolo);
        sessao.setValor(valor);
        sessao.setEhReavaliacao(ehReavaliacao);

        return sessaoRepository.save(sessao);
    }
    
    @Transactional
    public void excluir (UUID sessaoId){
        sessaoRepository.deleteById(sessaoId);
    }
}
