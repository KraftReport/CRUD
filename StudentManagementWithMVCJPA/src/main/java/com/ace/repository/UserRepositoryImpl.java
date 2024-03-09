package com.ace.repository;

import com.ace.entity.User;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Override
    public void addUser(User user) {
    try{
        var em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }catch (Exception e){
        e.printStackTrace();
    }
    }

    @Override
    public void updateUser(User user) {
        try {
            var em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            var found =  em.find(User.class,user.getId());
            em.detach(found);
            found.setName(user.getName());
            found.setEmail(user.getEmail());
            found.setPassword(user.getPassword());
            found.setRole(user.getRole());
            found.setPhoto(user.getPhoto());
            em.merge(found);
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(int id) {
        try {
            var em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            em.remove(em.find(User.class,id));
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public User findById(int id) {
        try {
            var em = entityManagerFactory.createEntityManager();
            return em.find(User.class,id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        try{
            var em = entityManagerFactory.createEntityManager();
            var jpql = "select u from user u where u.email = :email";
            var query = em.createQuery(jpql,User.class).setParameter("email",email);
            return query.getResultList().get(0);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> getAllUser() {
        try {
            var em = entityManagerFactory.createEntityManager();
            var jpql = "select u from user u";
            var query = em.createQuery(jpql,User.class);
            return query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
