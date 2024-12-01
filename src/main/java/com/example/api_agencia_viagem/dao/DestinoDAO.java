package com.example.api_agencia_viagem.dao;

import com.example.api_agencia_viagem.dominio.entity.DestinoEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class DestinoDAO {
    private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;

    public DestinoDAO() {
        entityManagerFactory = Persistence.createEntityManagerFactory("agencia_viagem_implementado");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public DestinoEntity createDestino(DestinoEntity destino) {
        entityManager.getTransaction().begin();
        entityManager.persist(destino);
        entityManager.getTransaction().commit();
        return destino;
    }

    public List<DestinoEntity> getAllDestinos() {
        return entityManager.createQuery("SELECT d FROM DestinoEntity d", DestinoEntity.class).getResultList();
    }

    public List<DestinoEntity> getDestinoByNameOrLocation(String nome, String localizacao) {
        String query = "SELECT d FROM DestinoEntity d WHERE " +
                "(LOWER(d.nome) LIKE :nome OR LOWER(d.localizacao) LIKE :localizacao)";
        return entityManager.createQuery(query, DestinoEntity.class)
                .setParameter("nome", nome != null ? "%" + nome.toLowerCase() + "%" : "%")
                .setParameter("localizacao", localizacao != null ? "%" + localizacao.toLowerCase() + "%" : "%")
                .getResultList();
    }

    public DestinoEntity getDestinoById(Long id) {
        return entityManager.find(DestinoEntity.class, id);
    }

    public DestinoEntity rateDestino(Long id, int nota) {
        DestinoEntity destino = entityManager.find(DestinoEntity.class, id);
        if (destino != null) {
            entityManager.getTransaction().begin();
            double totalNotas = destino.getMediaAvaliacoes() * destino.getQuantidadeAvaliacoes();
            totalNotas += nota;
            destino.setQuantidadeAvaliacoes(destino.getQuantidadeAvaliacoes() + 1);
            destino.setMediaAvaliacoes(totalNotas / destino.getQuantidadeAvaliacoes());
            entityManager.merge(destino);
            entityManager.getTransaction().commit();
        }
        return destino;
    }

    public void deleteDestino(Long id) {
        entityManager.getTransaction().begin();
        DestinoEntity destino = entityManager.find(DestinoEntity.class, id);
        if (destino != null) {
            entityManager.remove(destino);
        }
        entityManager.getTransaction().commit();
    }

    public DestinoEntity updateDestino(DestinoEntity destino) {
        entityManager.getTransaction().begin();
        entityManager.merge(destino);
        entityManager.getTransaction().commit();
        return destino;
    }

    public void close() {
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
