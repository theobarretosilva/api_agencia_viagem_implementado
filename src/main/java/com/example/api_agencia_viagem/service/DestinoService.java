package com.example.api_agencia_viagem.service;

import java.util.List;

import com.example.api_agencia_viagem.dominio.dto.DestinoDTO;

public interface DestinoService {
    DestinoDTO cadastrarDestino(DestinoDTO destino);
    List<DestinoDTO> listarDestinos();
    List<DestinoDTO> pesquisarDestinos(String nome, String localizacao);
    DestinoDTO obterDetalhes(Long id);
    DestinoDTO avaliarDestino(Long id, int nota);
    void excluirDestino(Long id);
}
