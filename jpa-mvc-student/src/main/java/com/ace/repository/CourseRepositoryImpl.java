package com.ace.repository;

import com.ace.entity.Course;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseRepositoryImpl implements CourseRepository {

    @Autowired
    private EntityManagerFactory emf;
    @Override
    public void addCourse(Course course) {
        try{
            var em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(course);
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateCourse(Course course) {
        try {
            var em = emf.createEntityManager();
            em.getTransaction().begin();
            var found = em.find(Course.class,course.getId());
            em.detach(found);
            found.setName(course.getName());
            found.setStatus(course.getStatus());
            found.setStudents(course.getStudents());
            em.merge(found);
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCourse(int id) {
    try {
        var em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(Course.class,id));
        em.getTransaction().commit();
    }catch (Exception e){
        e.printStackTrace();
    }
    }

    @Override
    public List<Course> getAllCourse() {
        var em = emf.createEntityManager();
        em.getTransaction().begin();
        var jpql = "select c from course c";
        var query = em.createQuery(jpql,Course.class);
        return query.getResultList();
    }

    @Override
    public List<Course> getById(int id) {
        var em = emf.createEntityManager();
        var jpql = "select c from course c where c.id = :id";
        var query = em.createQuery(jpql,Course.class).setParameter("id",id);
        return query.getResultList();
    }

    @Override
    public List<Course> getByName(String name) {
        var em = emf.createEntityManager();
        var jpql = "select c from course c where c.name = :name";
        var query = em.createQuery(jpql,Course.class).setParameter("name",name);
        return query.getResultList();
    }
}
