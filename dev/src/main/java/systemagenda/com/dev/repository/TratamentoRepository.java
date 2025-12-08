package systemagenda.com.dev.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import systemagenda.com.dev.entity.Tratamento;

public interface TratamentoRepository extends JpaRepository<Tratamento, UUID> {
}