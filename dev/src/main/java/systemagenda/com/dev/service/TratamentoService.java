package systemagenda.com.dev.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import systemagenda.com.dev.entity.Cliente;
import systemagenda.com.dev.entity.Sessao;
import systemagenda.com.dev.entity.Tratamento;
import systemagenda.com.dev.repository.ClienteRepository;
import systemagenda.com.dev.repository.SessaoRepository;
import systemagenda.com.dev.repository.TratamentoRepository;
import systemagenda.com.dev.dto.TratamentoResumoDTO;

@Service
public class TratamentoService {
    private final TratamentoRepository tratamentoRepository;
    private final ClienteRepository clienteRepository;
    private final SessaoRepository sessaoRepository;

    public TratamentoService(TratamentoRepository tratamentoRepository, ClienteRepository clienteRepository,
            SessaoRepository sessaoRepository) {
        this.tratamentoRepository = tratamentoRepository;
        this.clienteRepository = clienteRepository;
        this.sessaoRepository = sessaoRepository;
    }

    @Transactional(readOnly = true)
    public List<Tratamento> listarPorCliente(UUID clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return tratamentoRepository.findByClienteOrderByAreaTratamentoAsc(cliente);
    }

    @Transactional(readOnly = true)
    public Tratamento buscarPorId(UUID id) {
        return tratamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tratamento não encontrado"));
    }

    @Transactional
    public Tratamento salvar(UUID clienteId, Tratamento tratamentoForm) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        tratamentoForm.setCliente(cliente);
        return tratamentoRepository.save(tratamentoForm);
    }

    @Transactional
    public void excluir(UUID id) {
        tratamentoRepository.deleteById(id);
    }

    @Transactional
    public List<TratamentoResumoDTO> resumoPorCliente(UUID clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        List<Tratamento> tratamentos = tratamentoRepository.findByClienteOrderByAreaTratamentoAsc(cliente);
        return tratamentos.stream()
                .map(t -> {
                    int recomendadas = t.getSessoesRecomendadas();
                    int realizadas = t.getSessoesRealizadas();
                    int pendentes = Math.max(0, recomendadas - realizadas);

                    LocalDate hoje = LocalDate.now();
                    LocalDate proximaData = sessaoRepository.findProximaDataPorTratamento(t, hoje.minusDays(1))
                            .map(Sessao::getDataSessao)
                            .orElse(null);

                    return new TratamentoResumoDTO(
                            t.getId(),
                            t.getAreaTratamento(),
                            recomendadas,
                            realizadas,
                            pendentes,
                            proximaData);

                })
                .collect(Collectors.toList());
    }
}
