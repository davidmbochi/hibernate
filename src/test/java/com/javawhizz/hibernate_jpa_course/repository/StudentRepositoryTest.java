package com.javawhizz.hibernate_jpa_course.repository;

import com.javawhizz.hibernate_jpa_course.entity.Address;
import com.javawhizz.hibernate_jpa_course.entity.Course;
import com.javawhizz.hibernate_jpa_course.entity.Passport;
import com.javawhizz.hibernate_jpa_course.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudentRepositoryTest {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	EntityManager em;

	@Test
	public void someTest(){
		studentRepository.someDummyOperation();
	}



	@Test
	@Transactional
	void retrieveStudentAndPassportDetails() {
		Student student = em.find(Student.class, 20001L);
		logger.info("student -> {}", student);
		logger.info("passport -> {}", student.getPassport());
	}

	@Test
	@Transactional
	void setAddressDetails() {
		Student student = em.find(Student.class, 20001L);
		student.setAddress(
				new Address(
						"No 101",
						"Some Street",
						"Hyderabad"
				)
		);
		em.flush();
		logger.info("student -> {}", student);
		logger.info("passport -> {}", student.getPassport());
	}


	@Test
	@Transactional
	void retrieveStudentAndAssociatedPassport() {
		Passport passport = em.find(Passport.class, 40001L);
		logger.info("passport -> {}", passport);
		logger.info("student -> {}",passport.getStudent());
	}


	@Test
	@Transactional
	void retrieveStudentAndCourses() {
		Student student = em.find(Student.class, 20001L);
		logger.info("student -> {}", student);
		logger.info("student -> {}", student.getCourses());
	}

	@Test
	@Transactional
	void retrieveCourseStudents() {
		Course course = em.find(Course.class, 10001L);
		logger.info("course -> {}", course);
		logger.info("students -> {}", course.getStudents());
	}

}



































