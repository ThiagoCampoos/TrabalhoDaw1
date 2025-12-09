package systemagenda.com.dev.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import systemagenda.com.dev.entity.Sessao;
import systemagenda.com.dev.entity.Tratamento;

public interface SessaoRepository extends JpaRepository<Sessao, UUID> {

    List<Sessao> findByTratamentoOrderByDataSessaoAsc(Tratamento tratamento);

    boolean existsByTratamentoAndDataSessao(Tratamento tratamento, LocalDate dataSessao);

    @Query("""

                select s from Sessao s
                where s.tratamento = :tratamento
                    and s.dataSessao >= :dataMinima
                order by s.dataSessao asc
                    """)
    Optional<Sessao> findProximaDataPorTratamento(Tratamento tratamento, LocalDate dataMinima);
}