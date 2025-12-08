package systemagenda.com.dev.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import systemagenda.com.dev.entity.Cliente;
import systemagenda.com.dev.entity.FichaAvaliacao;

public interface FichaAvaliacaoRepository extends JpaRepository<FichaAvaliacao, UUID> {

    Optional<FichaAvaliacao> findByCliente(Cliente cliente);
}
