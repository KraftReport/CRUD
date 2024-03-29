package com.ace.boot.repository;

import com.ace.boot.entity.Course;
import com.ace.boot.entity.Enroll;
import com.ace.boot.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollRepository extends JpaRepository<Enroll,Long>, JpaSpecificationExecutor<Enroll> {
    List<Enroll> findByStudentId(Long id);
}
