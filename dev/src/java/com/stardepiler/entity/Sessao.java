package com.stardepiller.entity;

//import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "sessoes")
public class Sessao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate dataSessao;
    private String protocolo;
    private double valor;
    private boolean ehReavaliacao;

    @ManyToOne
    @JoinColumn(name = "tratamento_id")
    private Tratamento tratamento;

    // getters e setters

    public Sessao(LocalDate dataSessao, boolean ehReavaliacao, UUID id, String protocolo, Tratamento tratamento, double valor) {
        this.dataSessao = dataSessao;
        this.ehReavaliacao = ehReavaliacao;
        this.id = id;
        this.protocolo = protocolo;
        this.tratamento = tratamento;
        this.valor = valor;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
        if (tratamento != null) {
            tratamento.getSessoes().add(this);
        }
    }
    public LocalDate getDataSessao() {
        return dataSessao;
    }
    public void setDataSessao(LocalDate dataSessao) {
        this.dataSessao = dataSessao;
        if (tratamento != null) {
            tratamento.getSessoes().add(this);
        }
    }
    public String getProtocolo() {
        return protocolo;
    }
    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
        if (tratamento != null) {
            tratamento.getSessoes().add(this);
            this.tratamento = tratamento;
        }
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
        if (tratamento != null) {
            tratamento.getSessoes().add(this);
            this.tratamento = tratamento;
        }
    }
    public boolean isEhReavaliacao() {
        return ehReavaliacao;
    }
    public void setEhReavaliacao(boolean ehReavaliacao) {
        this.ehReavaliacao = ehReavaliacao;
        if (tratamento != null) {
            tratamento.getSessoes().add(this);
            this.tratamento = tratamento;
        }

    }
    public Tratamento getTratamento() {
        return tratamento;
    }
    public void setTratamento(Tratamento tratamento) {
        this.tratamento = tratamento;
        if (tratamento != null) {
            tratamento.getSessoes().add(this);
        }
    }
}