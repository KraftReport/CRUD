package com.ace.repository;

import com.ace.entity.Student;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepositoryImpl implements StudentRepository {
    @Autowired
    private EntityManagerFactory emf;
    @Override
    public void registerStudent(Student student) {
        try {
            var em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateStudent(Student student) {
        try {
            var em = emf.createEntityManager();
            em.getTransaction().begin();
            var found = em.find(Student.class,student.getId());
            em.detach(found);
            found.setName(student.getName());
            found.setDob(student.getDob());
            found.setGender(student.getGender());
            found.setPhone(student.getPhone());
            found.setEducation(student.getEducation());
            found.setPhoto(student.getPhoto());
            found.setCourses(student.getCourses());
            em.merge(found);
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteStudent(int id) {
        try {
            var em = emf.createEntityManager();
            em.getTransaction().begin();
            em.remove(em.find(Student.class,id));
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> getAllStudents() {
        try {
            var em = emf.createEntityManager();
            var jpql = "select s from student s ";
            var query = em.createQuery(jpql,Student.class);
            return query.getResultList();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Student> getById(int id) {
       var em = emf.createEntityManager();
       var jpql = "select s from student s where s.id = :id";
       var query = em.createQuery(jpql,Student.class).setParameter("id",id);
       return query.getResultList();
    }

    @Override
    public List<Student> getByName(String name) {
        var em = emf.createEntityManager();
        var jpql = "select s from student s where s.name = :name";
        var query = em.createQuery(jpql,Student.class).setParameter("name",name);
        return query.getResultList();
    }
}
