package com.stardepiller.entity;

//import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "fichas_avaliacao")
public class FichaAvaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String fototipo;
    private String alergias;
    private boolean consentimento;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

  

    public FichaAvaliacao(String alergias, Cliente cliente, boolean consentimento, String fototipo, UUID id) {
        this.alergias = alergias;
        this.cliente = cliente;
        this.consentimento = consentimento;
        this.fototipo = fototipo;
        this.id = id;
    }

      // getters e setters
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getFototipo() {
        return fototipo;
    }
    public void setFototipo(String fototipo) {
        this.fototipo = fototipo;
    }
    public String getAlergias() {
        return alergias;
    }
    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }
    public boolean isConsentimento() {
        return consentimento;
    }
    public void setConsentimento(boolean consentimento) {
        this.consentimento = consentimento;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}