package com.javawhizz.hibernate_jpa_course.repository;

import com.javawhizz.hibernate_jpa_course.entity.Course;
import com.javawhizz.hibernate_jpa_course.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CriteriaQueryTest {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager entityManager;

	@Test
	void all_courses() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class);

		Root<Course> courseRoot = cq.from(Course.class);

		TypedQuery<Course> query = entityManager.createQuery(cq.select(courseRoot));

		List<Course> resultList = query.getResultList();
		logger.info("Typed Query -> {}", resultList);
	}


	@Test
	void all_courses_having_100_steps() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class);

		Root<Course> courseRoot = cq.from(Course.class);

		Predicate like100Steps = cb.like(courseRoot.get("name"), "%100 Steps");

		cq.where(like100Steps);

		TypedQuery<Course> query = entityManager.createQuery(cq.select(courseRoot));

		List<Course> resultList = query.getResultList();
		logger.info("Typed Query -> {}", resultList);
	}

	@Test
	void all_courses_without_students() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class);

		Root<Course> courseRoot = cq.from(Course.class);

		Predicate studentsIsEmpty = cb.isEmpty(courseRoot.get("students"));

		cq.where(studentsIsEmpty);

		TypedQuery<Course> query = entityManager.createQuery(cq.select(courseRoot));

		List<Course> resultList = query.getResultList();
		logger.info("Typed Query -> {}", resultList);
	}

	@Test
	void join() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class);

		Root<Course> courseRoot = cq.from(Course.class);

		Join<Object, Object> join = courseRoot.join("students");

		TypedQuery<Course> query = entityManager.createQuery(cq.select(courseRoot));

		List<Course> resultList = query.getResultList();
		logger.info("Typed Query -> {}", resultList);
	}

	@Test
	void left_join() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class);

		Root<Course> courseRoot = cq.from(Course.class);

		Join<Object, Object> join = courseRoot.join("students", JoinType.LEFT);

		TypedQuery<Course> query = entityManager.createQuery(cq.select(courseRoot));

		List<Course> resultList = query.getResultList();
		logger.info("Typed Query -> {}", resultList);
	}


}
