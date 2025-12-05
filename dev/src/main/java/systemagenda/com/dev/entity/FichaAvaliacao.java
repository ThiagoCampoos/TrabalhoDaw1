package systemagenda.com.dev.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "fichas_avaliacao")
public class FichaAvaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 50)
    private String fototipo;

    @Column(length = 1000)
    private String alergias;

    @Column(nullable = false)
    private boolean consentimento;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public FichaAvaliacao() {
    }

    public FichaAvaliacao(String fototipo, String alergias, boolean consentimento, Cliente cliente) {
        this.fototipo = fototipo;
        this.alergias = alergias;
        this.consentimento = consentimento;
        this.cliente = cliente;
    }

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
