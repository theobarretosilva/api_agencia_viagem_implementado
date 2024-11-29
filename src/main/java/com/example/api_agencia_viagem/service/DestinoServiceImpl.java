package com.example.api_agencia_viagem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.api_agencia_viagem.dominio.dto.Destino;

@Service
public class DestinoServiceImpl implements DestinoService {
    private List<Destino> destinos = new ArrayList<>();

    @Override
    public Destino cadastrarDestino(Destino destino) {
        destinos.add(destino);
        return destino;
    }

    @Override
    public List<Destino> listarDestinos() {
        return destinos;
    }

    @Override
    public List<Destino> pesquisarDestinos(String nome, String localizacao) {
        List<Destino> resultados = new ArrayList<>();

        for (Destino destino : destinos) {
            boolean nomeMatch = nome != null && destino.getNome().toLowerCase().contains(nome.toLowerCase());
            boolean localizacaoMatch = localizacao != null && destino.getLocalizacao().toLowerCase().contains(localizacao.toLowerCase());

            if (nomeMatch || localizacaoMatch) {
                resultados.add(destino);
            }
        }

        return resultados;
    }

    @Override
    public Destino obterDetalhes(Long id) {
        Optional<Destino> destino = destinos.stream().filter(d -> d.getId().equals(id)).findFirst();
        return destino.orElse(null);
    }

    @Override
    public Destino avaliarDestino(Long id, int nota) {
        Destino destino = obterDetalhes(id);
        if (destino == null) {
            return null;
        }

        double totalNotas = destino.getMediaAvaliacoes() * destino.getQuantidadeAvaliacoes();
        totalNotas += nota;
        destino.setQuantidadeAvaliacoes(destino.getQuantidadeAvaliacoes() + 1);
        destino.setMediaAvaliacoes(totalNotas / destino.getQuantidadeAvaliacoes());

        return destino;
    }

    @Override
    public void excluirDestino(Long id) {
        destinos.removeIf(d -> d.getId().equals(id));
    }
}
