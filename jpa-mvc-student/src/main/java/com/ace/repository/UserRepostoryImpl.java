package com.ace.repository;

import com.ace.entity.User;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepostoryImpl implements UserRepository {

    @Autowired
    private EntityManagerFactory emf;
    @Override
    public boolean registerUser(User user) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUser(User user) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            var updateUser = em.find(User.class, user.getId());
            em.detach(updateUser);
            updateUser.setName(user.getName());
            updateUser.setEmail(user.getEmail());
            updateUser.setPassword(user.getPassword());
            updateUser.setRole(user.getRole());
            em.merge(updateUser);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUser(int id) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.remove(em.find(User.class, id));
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> getAllUsers() {
        var em = emf.createEntityManager();
        var jpql = "select u from user u";
        var query = em.createQuery(jpql,User.class);
        return query.getResultList();
    }

    @Override
    public List<User> getById(int id) {
        var em = emf.createEntityManager();
        List<User> userList = new ArrayList<>();
        userList.add(em.find(User.class,id));
        return userList;
    }

    @Override
    public List<User> getByEmail(String email) {
        var em = emf.createEntityManager();
        var jpql = "select u from user u where u.email = :email";
        var query = em.createQuery(jpql,User.class).setParameter("email",email);
        return query.getResultList();
    }

    @Override
    public List<User> getByName(String name) {
        var em = emf.createEntityManager();
        var jpql = "select u from user u where u.name = :name";
        var query = em.createQuery(jpql,User.class).setParameter("name",name);
        return query.getResultList();
    }
}
