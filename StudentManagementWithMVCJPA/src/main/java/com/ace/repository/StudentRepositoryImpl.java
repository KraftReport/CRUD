package com.ace.repository;

import com.ace.entity.Course;
import com.ace.entity.Status;
import com.ace.entity.Student;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepositoryImpl implements StudentRepository {
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private CourseRepository courseRepository;
    @Override
    public void registerStudent(Student student) {
        try{
            var em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
//            student.getCourses().get(0).setId(1);
//            System.err.println(student.getCourses().get(0).getId());
//            var student1 = student;
//            em.persist(student1);
            em.persist(student);
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateStudent(Student student) {
        try {
            var em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            var found = em.find(Student.class,student.getId());
            em.detach(found);
            System.err.println(student.getName());
            found.setName(student.getName());
            found.setDob(student.getDob());
            found.setPhone(student.getPhone());
            found.setEducation(student.getEducation());
            found.setGender(student.getGender());
            found.setStatus(student.getStatus());
            found.setCourses(student.getCourses());
            found.setPhoto(student.getPhoto());
            em.merge(found);
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteStudent(int id) {
        try {
            var em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            var found = em.find(Student.class,id);
            em.detach(found);
            found.setStatus(Status.UNAVAILABLE);
            em.merge(found);
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> getAllStudents() {
        try {
            var em = entityManagerFactory.createEntityManager();
            var jpql = "select s from student s";
            var query = em.createQuery(jpql,Student.class);
            return query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Student findById(int id) {
        try {
            var em = entityManagerFactory.createEntityManager();
            var jpql = "select s from student s where s.id = :id";
            var query = em.createQuery(jpql,Student.class).setParameter("id",id);
            return query.getResultList().get(0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Student> findByCourse(int id) {
        try {
            var em = entityManagerFactory.createEntityManager();
            var jpql = "select s from student s inner join s.courses c inner join c.students  r on s.id = r.id where element(r.courses).id = :id";
            var query = em.createQuery(jpql,Student.class).setParameter("id",id);
            return query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Student> findByName(String name) {
        try {
            var em = entityManagerFactory.createEntityManager();
            var jpql = "select s from student s where name = :name";
            var query = em.createQuery(jpql,Student.class).setParameter("name",name);
            return query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Student> findByCriteria(Student student) {
        var em = entityManagerFactory.createEntityManager();
        var criteriaBuilder = em.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Student.class);
        List<Predicate> predicates = new ArrayList<>();
        var root = criteriaQuery.from(Student.class);
        if(null != Long.valueOf(student.getId())){
            var id = criteriaBuilder.equal(root.get("id"),student.getId());
            predicates.add(id);
        }
        if(null != student.getName() && !student.getName().isEmpty() && !student.getName().isBlank()){
            var name = criteriaBuilder.like(root.get("name"),"%"+student.getName()+"%");
            predicates.add(name);
        }

        criteriaQuery.where(criteriaBuilder.or(predicates.toArray(new Predicate[0])));
        var query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }


    @Override
    public List<Student> findByStatus(String status) {
        try {
            var em = entityManagerFactory.createEntityManager();
            var jpql = "select s from student s where s.status = :status";
            var query = em.createQuery(jpql,Student.class).setParameter("status",Status.valueOf(status));
            return query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Student> findByNameLike(String name) {
        try{
            var em = entityManagerFactory.createEntityManager();
            var jpql = "select s from student s where s.name like :name";
            var query = em.createQuery(jpql,Student.class).setParameter("name","%"+name+"%");
            return query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
