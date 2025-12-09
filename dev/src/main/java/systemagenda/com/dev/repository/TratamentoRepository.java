package systemagenda.com.dev.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import systemagenda.com.dev.entity.Cliente;
import systemagenda.com.dev.entity.Tratamento;

public interface TratamentoRepository extends JpaRepository<Tratamento, UUID> {

    List<Tratamento> findByClienteOrderByAreaTratamentoAsc(Cliente cliente);
    
}