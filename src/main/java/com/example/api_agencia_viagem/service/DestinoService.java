package com.example.api_agencia_viagem.service;

import java.util.List;

import com.example.api_agencia_viagem.entity.Destino;

public interface DestinoService {
    Destino cadastrarDestino(Destino destino);
    List<Destino> listarDestinos();
    List<Destino> pesquisarDestinos(String nome, String localizacao);
    Destino obterDetalhes(Long id);
    Destino avaliarDestino(Long id, int nota);
    void excluirDestino(Long id);
}
