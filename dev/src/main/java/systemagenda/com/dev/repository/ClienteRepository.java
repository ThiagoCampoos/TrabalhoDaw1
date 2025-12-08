package systemagenda.com.dev.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import systemagenda.com.dev.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
 
    Page<Cliente> findByNomeContainingIgnoreCase (String nome, Pageable pageable);
}
