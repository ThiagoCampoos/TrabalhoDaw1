package systemagenda.com.dev.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import systemagenda.com.dev.entity.Cliente;
import systemagenda.com.dev.entity.FichaAvaliacao;
import systemagenda.com.dev.repository.ClienteRepository;
import systemagenda.com.dev.repository.FichaAvaliacaoRepository;


@Service
public class FichaAvaliacaoService {
    private final FichaAvaliacaoRepository fichaAvaliacaoRepository;
    private final ClienteRepository clienteRepository;

    public FichaAvaliacaoService(FichaAvaliacaoRepository fichaAvaliacaoRepository,
            ClienteRepository clienteRepository) {
        this.fichaAvaliacaoRepository = fichaAvaliacaoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Transactional(readOnly = true)
    public FichaAvaliacao buscarPorId(UUID id) {
        return fichaAvaliacaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ficha de Avaliação não encontrada"));
    }

    @Transactional(readOnly = true)
    public FichaAvaliacao buscarPorCliente(UUID clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        return fichaAvaliacaoRepository.findByCliente(cliente)
                .orElseGet(() -> {
                    FichaAvaliacao ficha = new FichaAvaliacao();
                    ficha.setCliente(cliente);
                    ficha.setConsentimento(false);
                    return ficha;
                });
    }

    @Transactional
    public FichaAvaliacao salvar(UUID clienteId, FichaAvaliacao fichaForm) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        fichaForm.setCliente(cliente);
        return fichaAvaliacaoRepository.save(fichaForm);
    }
}
