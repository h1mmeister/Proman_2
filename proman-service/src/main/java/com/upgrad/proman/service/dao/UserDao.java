package com.upgrad.proman.service.dao;

import com.upgrad.proman.service.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    // persist the user entity
    public UserEntity createUser(UserEntity userEntity) {
        entityManager.persist(userEntity);
        return userEntity;
    }

    // get the user by uuid
    public UserEntity getUser(final String userUuid) {
        return entityManager.createNamedQuery("userByUuid", UserEntity.class).setParameter("uuid", userUuid).getSingleResult();
    }
}
