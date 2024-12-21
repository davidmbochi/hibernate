package com.javawhizz.hibernate_jpa_course.repository;

import com.javawhizz.hibernate_jpa_course.entity.Course;
import com.javawhizz.hibernate_jpa_course.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class JPQLTest {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager entityManager;

	@Test
	void jpql_basic() {
		Query query = entityManager.createNamedQuery("query_get_all_courses");
		List resultList = query.getResultList();
		logger.info("Select c From Course c -> {}", resultList);
	}

	@Test
	void jpql_typed() {
		TypedQuery<Course> query = entityManager.createNamedQuery("query_get_all_courses", Course.class);
		List<Course> resultList = query.getResultList();
		logger.info("Select c From Course c -> {}", resultList);
	}

	@Test
	void jpql_where() {
		TypedQuery<Course> query = entityManager.createNamedQuery("query_get_100_step_courses", Course.class);
		List<Course> resultList = query.getResultList();
		logger.info("Select c From Course c where name like '%100 Steps' -> {}", resultList);
	}

	@Test
	void jpql_courses_without_students() {
		TypedQuery<Course> query = entityManager.createQuery("Select c from Course c where c.students is empty ", Course.class);
		List<Course> resultList = query.getResultList();
		logger.info("Results -> {}", resultList);
	}

	@Test
	void jpql_courses_with_atleast_2_students() {
		TypedQuery<Course> query = entityManager.createQuery("Select c from Course c where size(c.students) >= 2 ", Course.class);
		List<Course> resultList = query.getResultList();
		logger.info("Results -> {}", resultList);
	}

	@Test
	void jpql_courses_ordered_by_students() {
		TypedQuery<Course> query = entityManager.createQuery("Select c from Course c order by size(c.students) desc ", Course.class);
		List<Course> resultList = query.getResultList();
		logger.info("Results -> {}", resultList);
	}

	@Test
	void jpql_students_with_passports_in_a_certain_pattern() {
		TypedQuery<Student> query = entityManager.createQuery("Select s from Student s where s.passport.number like '%1234%'", Student.class);
		List<Student> resultList = query.getResultList();
		logger.info("Results -> {}", resultList);
	}

	//like
	//BETWEEN 100 AND 1000
	//IS NULL
	//upper, lower, trim, length

	//JOIN => Select c, s from Course c JOIN c.students s
	//LEFT JOIN => Select c, s from Course c LEFT JOIN c.students s
	//CROSS JOIN => Select c, s from Course c, Student s

	@Test
	public void join(){
		Query query = entityManager.createQuery("select c, s from Course c JOIN c.students s");
		List<Object[]> resultList = query.getResultList();

		logger.info("Results Size -> {}", resultList.size());

		for (Object[] result : resultList) {
			logger.info("Course{} Student{}", result[0], result[1]);
		}
	}

	@Test
	public void left_join(){
		Query query = entityManager.createQuery("select c, s from Course c LEFT JOIN c.students s");
		List<Object[]> resultList = query.getResultList();

		logger.info("Results Size -> {}", resultList.size());

		for (Object[] result : resultList) {
			logger.info("Course{} Student{}", result[0], result[1]);
		}
	}

	@Test
	public void cross_join(){
		Query query = entityManager.createQuery("select c, s from Course c, Student s");
		List<Object[]> resultList = query.getResultList();

		logger.info("Results Size -> {}", resultList.size());

		for (Object[] result : resultList) {
			logger.info("Course{} Student{}", result[0], result[1]);
		}
	}
}
