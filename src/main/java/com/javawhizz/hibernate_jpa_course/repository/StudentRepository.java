package com.javawhizz.hibernate_jpa_course.repository;


import com.javawhizz.hibernate_jpa_course.entity.Course;
import com.javawhizz.hibernate_jpa_course.entity.Passport;
import com.javawhizz.hibernate_jpa_course.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class StudentRepository {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EntityManager em;

    public Student findById(Long id){
        return em.find(Student.class, id);
    }

    public Student saveOrUpdate(Student student){
        if (student.getId() == null){
            em.persist(student);
        }else {
            em.merge(student);
        }
        return student;
    }

    public void deleteById(Long id){
        Student student = findById(id);
        em.remove(student);
    }

    public void saveStudentWithPassport(){
        Passport passport = new Passport("Z123456");
        em.persist(passport);

        Student student = new Student("Mike");
        student.setPassport(passport);
        em.persist(student);

    }

    public void someDummyOperation() {
        Student student = em.find(Student.class, 20001L);

        Passport passport = student.getPassport();

        passport.setNumber("E123457");

        student.setName("Ranga - updated");
    }


    public void insertHardCodedStudentAndCourse(){
        Student student = new Student("Jack");
        Course course = new Course("Microservices in 100 Steps");

        em.persist(student);
        em.persist(course);

        student.addCourse(course);
        course.addStudent(student);

        em.persist(student);
    }

    public void insertStudentAndCourse(Student student, Course course){
//        Student student = new Student("Jack");
//        Course course = new Course("Microservices in 100 Steps");

        student.addCourse(course);
        course.addStudent(student);

        em.persist(student);
        em.persist(course);

    }
}































