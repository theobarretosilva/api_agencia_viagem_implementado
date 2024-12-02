package com.example.api_agencia_viagem.service;

import com.example.api_agencia_viagem.dominio.dto.DestinoDTO;
import com.example.api_agencia_viagem.dominio.entity.DestinoEntity;
import com.example.api_agencia_viagem.repository.DestinoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DestinoServiceImpl implements DestinoService {

    @Autowired
    private DestinoRepository destinoRepository;

    @Override
    public DestinoDTO cadastrarDestino(DestinoDTO destinoDTO) {
        DestinoEntity destinoEntity = toEntity(destinoDTO);
        destinoEntity = destinoRepository.save(destinoEntity);
        return toDTO(destinoEntity);
    }

    @Override
    public List<DestinoDTO> listarDestinos() {
        return destinoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DestinoDTO> pesquisarDestinos(String nome, String localizacao) {
        return destinoRepository.findByNomeContainingIgnoreCaseOrLocalizacaoContainingIgnoreCase(nome, localizacao)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DestinoDTO obterDetalhes(Long id) {
        DestinoEntity destinoEntity = destinoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Destino não encontrado"));
        return toDTO(destinoEntity);
    }

    @Override
    public DestinoDTO avaliarDestino(Long id, int nota) {
        DestinoEntity destinoEntity = destinoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Destino não encontrado"));

        double totalNotas = destinoEntity.getMediaAvaliacoes() * destinoEntity.getQuantidadeAvaliacoes();
        totalNotas += nota;
        destinoEntity.setQuantidadeAvaliacoes(destinoEntity.getQuantidadeAvaliacoes() + 1);
        destinoEntity.setMediaAvaliacoes(totalNotas / destinoEntity.getQuantidadeAvaliacoes());

        destinoEntity = destinoRepository.save(destinoEntity);
        return toDTO(destinoEntity);
    }

    @Override
    public void excluirDestino(Long id) {
        if (!destinoRepository.existsById(id)) {
            throw new EntityNotFoundException("Destino não encontrado");
        }
        destinoRepository.deleteById(id);
    }

    // Métodos utilitários para conversão entre DTO e Entity
    private DestinoDTO toDTO(DestinoEntity destinoEntity) {
        DestinoDTO dto = new DestinoDTO();
        dto.setId(destinoEntity.getId());
        dto.setNome(destinoEntity.getNome());
        dto.setLocalizacao(destinoEntity.getLocalizacao());
        dto.setDescricao(destinoEntity.getDescricao());
        dto.setMediaAvaliacoes(destinoEntity.getMediaAvaliacoes());
        dto.setQuantidadeAvaliacoes(destinoEntity.getQuantidadeAvaliacoes());
        return dto;
    }

    private DestinoEntity toEntity(DestinoDTO destinoDTO) {
        DestinoEntity entity = new DestinoEntity();
        entity.setId(destinoDTO.getId());
        entity.setNome(destinoDTO.getNome());
        entity.setLocalizacao(destinoDTO.getLocalizacao());
        entity.setDescricao(destinoDTO.getDescricao());
        entity.setMediaAvaliacoes(destinoDTO.getMediaAvaliacoes());
        entity.setQuantidadeAvaliacoes(destinoDTO.getQuantidadeAvaliacoes());
        return entity;
    }
}
