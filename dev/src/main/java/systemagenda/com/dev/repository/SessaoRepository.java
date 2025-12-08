package systemagenda.com.dev.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import systemagenda.com.dev.entity.Sessao;
import systemagenda.com.dev.entity.Tratamento;

public interface SessaoRepository extends JpaRepository<Sessao, UUID> {
    List<Sessao> findByTratamentoOrderByDataSessaoAsc(Tratamento tratamento);
}
