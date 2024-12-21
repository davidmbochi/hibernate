package com.javawhizz.hibernate_jpa_course.repository;

import com.javawhizz.hibernate_jpa_course.entity.Course;
import com.javawhizz.hibernate_jpa_course.entity.Review;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class CourseRepositoryTest {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CourseRepository repository;

	@Autowired
	EntityManager em;

	@Test
	void findById_basic() {
		Course course = repository.findById(10001L);
		assertEquals("JPA in 50 Steps", course.getName());
	}

	@Test
	@Transactional //First Level Cache is active by default within a transaction
	void findById_firstLevelCacheDemo() {
		Course course = repository.findById(10001L);
		logger.info("First Course Retrieved {}", course);
		Course course1 = repository.findById(10001L);
		logger.info("First Course Retrieved again {}", course1);
		assertEquals("JPA in 50 Steps", course.getName());
		assertEquals("JPA in 50 Steps", course1.getName());
	}

	@Test
	@DirtiesContext
	void deleteById_basic() {
		repository.deleteById(10002L);
		assertNull(repository.findById(10002L));
	}


	@Test
	@DirtiesContext
	void saveOrUpdate_basic() {
		Course course = repository.findById(10001L);
		assertEquals("JPA in 50 Steps", course.getName());

		course.setName("JPA in 50 Steps - Updated");
		repository.saveOrUpdate(course);

		Course course1 = repository.findById(10001L);
		assertEquals("JPA in 50 Steps - Updated", course1.getName());
	}

	@Test
	@DirtiesContext
	public void playWithEntityManager(){
		repository.playWithEntityManager();
	}

	@Test
	@Transactional
	public void retrieveReviewsForCourse(){
		Course course = repository.findById(10001L);
		logger.info("{}", course.getReviews());
	}


	@Test
	@Transactional
	public void retrieveCourseForReview(){
		Review review = em.find(Review.class, 50001L);
		logger.info("{}", review.getCourse());
	}

}



























