package com.example.api_agencia_viagem.dao;

import com.example.api_agencia_viagem.dominio.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

import java.util.List;

public class UserDAO {

    private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;

    public UserDAO() {
        entityManagerFactory = Persistence.createEntityManagerFactory("agencia_viagem_implementado");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public UserEntity createUser(UserEntity user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        return user;
    }

    public List<UserEntity> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM UserEntity u", UserEntity.class).getResultList();
    }

    public UserEntity getUserById(Long id) {
        return entityManager.find(UserEntity.class, id);
    }

    public UserEntity getUserByEmail(String email) {
        try {
            return entityManager.createQuery("SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public UserEntity updateUser(UserEntity user) {
        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
        return user;
    }

    public void deleteUser(Long id) {
        entityManager.getTransaction().begin();
        UserEntity user = entityManager.find(UserEntity.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
        entityManager.getTransaction().commit();
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
