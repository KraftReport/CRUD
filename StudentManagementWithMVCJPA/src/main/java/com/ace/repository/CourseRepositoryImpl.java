package com.ace.repository;

import com.ace.entity.Course;
import com.ace.entity.Status;
import com.ace.entity.Student;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseRepositoryImpl implements CourseRepository {
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Override
    public void addCourse(Course course) {
        try {
            var em = entityManagerFactory.createEntityManager();
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
            var em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            var found = em.find(Course.class,course.getId());
            em.detach(found);
            found.setName(course.getName());
            found.setStudents(course.getStudents());
            found.setStatus(course.getStatus());
            em.merge(found);
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCourse(int id) {
        try {
            var em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            var found = em.find(Course.class,id);
            em.detach(found);
            found.setStatus(Status.UNAVAILABLE);
            em.merge(found);
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Course> getAllCourse() {
        try {
            var em = entityManagerFactory.createEntityManager();
            var jpql = "select c from course c ";
            var query = em.createQuery(jpql,Course.class);
            return query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Course findByName(String name) {
        try {
            var em = entityManagerFactory.createEntityManager();
            var jpql = "select c from course c where c.name like :name";
            var query = em.createQuery(jpql,Course.class).setParameter("name",name);
            return query.getResultList().get(0);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Course> findByNameLike(String name) {
        try {
            var em = entityManagerFactory.createEntityManager();
            var jpql = "select c from course c where c.name like :name";
            var query = em.createQuery(jpql,Course.class).setParameter("name","%"+name+"%");
            return query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Course findById(int id) {
        try {
            var em = entityManagerFactory.createEntityManager();
            var jpql = "select c from course c where c.id = :id";
            var query = em.createQuery(jpql,Course.class).setParameter("id",id);
            return query.getResultList().get(0);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Course> findByStatus(String status) {
        try {
            var em = entityManagerFactory.createEntityManager();
            var jpql = "select c from course c where c.status = :status";
            var query = em.createQuery(jpql,Course.class).setParameter("status",status);
            return query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Course> findByStudent(Student student) {
        try {
            var em = entityManagerFactory.createEntityManager();
            var jpql = "select c from course c inner join c.students s inner join s.courses r on c.id = r.id where element(r.students).id = :id ";
            var query = em.createQuery(jpql,Course.class).setParameter("id",student.getId());
            return query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
