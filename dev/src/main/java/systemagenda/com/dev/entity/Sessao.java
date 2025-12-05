package systemagenda.com.dev.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sessoes")
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "data_sessao")
    private LocalDate dataSessao;

    private String protocolo;

    private double valor;

    @Column(name = "eh_reavaliacao")
    private boolean ehReavaliacao;

    @ManyToOne
    @JoinColumn(name = "tratamento_id")
    private Tratamento tratamento;

    public Sessao() {
    }

    public Sessao(LocalDate dataSessao, String protocolo, double valor, boolean ehReavaliacao, Tratamento tratamento) {
        this.dataSessao = dataSessao;
        this.protocolo = protocolo;
        this.valor = valor;
        this.ehReavaliacao = ehReavaliacao;
        this.tratamento = tratamento;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDataSessao() {
        return dataSessao;
    }

    public void setDataSessao(LocalDate dataSessao) {
        this.dataSessao = dataSessao;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public boolean isEhReavaliacao() {
        return ehReavaliacao;
    }

    public void setEhReavaliacao(boolean ehReavaliacao) {
        this.ehReavaliacao = ehReavaliacao;
    }

    public Tratamento getTratamento() {
        return tratamento;
    }

    public void setTratamento(Tratamento tratamento) {
        this.tratamento = tratamento;
    }
}
