package com.javawhizz.hibernate_jpa_course.repository;

import com.javawhizz.hibernate_jpa_course.entity.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class NativeQueriesTest {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager entityManager;

	@Test
	void native_queries_basic() {
		Query query = entityManager.createNativeQuery("SELECT * FROM COURSE", Course.class); //Retrieve all non deleted courses
		List resultList = query.getResultList();
		logger.info("SELECT * FROM COURSE -> {}", resultList);
	}

	@Test
	void native_queries_with_parameter() {
		Query query = entityManager.createNativeQuery("SELECT * FROM COURSE WHERE id = ?", Course.class);
		query.setParameter(1, 10001L);
		List resultList = query.getResultList();
		logger.info("SELECT * FROM COURSE where id = ? -> {}", resultList);
	}

	@Test
	void native_queries_with_named_parameter() {
		Query query = entityManager.createNativeQuery("SELECT * FROM COURSE WHERE id = :id", Course.class);
		query.setParameter("id", 10001L);
		List resultList = query.getResultList();
		logger.info("SELECT * FROM COURSE where id = :id -> {}", resultList);
	}

	@Test
	@Transactional
	void native_queries_to_update() {
		Query query = entityManager.createNativeQuery("UPDATE COURSE set last_updated_date=CURRENT_TIMESTAMP", Course.class);
		int numberOfRowsUpdated = query.executeUpdate();
		logger.info("numberOfRowsUpdated -> {}", numberOfRowsUpdated);
	}
}
