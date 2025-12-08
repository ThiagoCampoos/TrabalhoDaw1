package systemagenda.com.dev.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import systemagenda.com.dev.entity.Cliente;
import systemagenda.com.dev.repository.ClienteRepository;

@Service

public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Page<Cliente> listar(String busca, Pageable pageable) {
        if (busca == null || busca.isBlank()) {
            return clienteRepository.findAll(pageable);
        }
        return clienteRepository.findByNomeContainingIgnoreCase(busca.trim(), pageable);
    }

    public Cliente buscarPorId(UUID id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
    }
    public Cliente salvar (Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    public void excluir (UUID id){
        clienteRepository.deleteById(id);
    }
}
