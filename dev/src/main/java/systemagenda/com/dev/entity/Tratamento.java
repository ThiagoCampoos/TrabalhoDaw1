package systemagenda.com.dev.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tratamentos")
public class Tratamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "area_tratamento", length = 255)
    private String areaTratamento;

    @Column(name = "sessoes_recomendadas")
    private int sessoesRecomendadas;

    @Column(length = 50)
    private String status;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "tratamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sessao> sessoes = new ArrayList<>();

    public Tratamento() {
    }

    public Tratamento(String areaTratamento, int sessoesRecomendadas, String status, Cliente cliente) {
        this.areaTratamento = areaTratamento;
        this.sessoesRecomendadas = sessoesRecomendadas;
        this.status = status;
        this.cliente = cliente;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAreaTratamento() {
        return areaTratamento;
    }

    public void setAreaTratamento(String areaTratamento) {
        this.areaTratamento = areaTratamento;
    }

    public int getSessoesRecomendadas() {
        return sessoesRecomendadas;
    }

    public void setSessoesRecomendadas(int sessoesRecomendadas) {
        this.sessoesRecomendadas = sessoesRecomendadas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Sessao> getSessoes() {
        return sessoes;
    }

    public void setSessoes(List<Sessao> sessoes) {
        this.sessoes = sessoes;
    }
}
