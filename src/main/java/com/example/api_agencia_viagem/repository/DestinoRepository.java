package com.example.api_agencia_viagem.repository;

import com.example.api_agencia_viagem.dominio.entity.DestinoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinoRepository extends JpaRepository<DestinoEntity, Long> {
    List<DestinoEntity> findByNomeContainingIgnoreCaseOrLocalizacaoContainingIgnoreCase(String nome, String localizacao);
}
