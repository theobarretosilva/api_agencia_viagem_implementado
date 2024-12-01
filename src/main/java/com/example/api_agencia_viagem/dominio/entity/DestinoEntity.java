package com.example.api_agencia_viagem.dominio.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "destino")
public class DestinoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String localizacao;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private double mediaAvaliacoes;

    @Column(nullable = false)
    private int quantidadeAvaliacoes;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }
    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getMediaAvaliacoes() {
        return mediaAvaliacoes;
    }
    public void setMediaAvaliacoes(double mediaAvaliacoes) {
        this.mediaAvaliacoes = mediaAvaliacoes;
    }

    public int getQuantidadeAvaliacoes() {
        return quantidadeAvaliacoes;
    }
    public void setQuantidadeAvaliacoes(int quantidadeAvaliacoes) {
        this.quantidadeAvaliacoes = quantidadeAvaliacoes;
    }
}
