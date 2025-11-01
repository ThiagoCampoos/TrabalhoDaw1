package com.stardepiller.entity;

//import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tratamentos")
public class Tratamento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String areaTratamento;
    private int sessoesRecomendadas;
    private String status;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "tratamento")
    private List<Sessao> sessoes;

    // getters e setters

    public Tratamento(String areaTratamento, Cliente cliente, UUID id, List<Sessao> sessoes, int sessoesRecomendadas, String status) {
        this.areaTratamento = areaTratamento;
        this.cliente = cliente;
        this.id = id;
        this.sessoes = sessoes;
        this.sessoesRecomendadas = sessoesRecomendadas;
        this.status = status;
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
        if (cliente != null) {
            cliente.getTratamentos().add(this);
        }
    }
    public List<Sessao> getSessoes() {
        return sessoes;
    }
    public void setSessoes(List<Sessao> sessoes) {
        this.sessoes = sessoes;
        for (Sessao sessao : sessoes) {
            sessao.setTratamento(this);
        }
    }
    
}