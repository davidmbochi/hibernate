package com.javawhizz.hibernate_jpa_course.repository;

import com.javawhizz.hibernate_jpa_course.entity.Course;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CourseSpringDataRepositoryTest {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CourseSpringDataRepository repository;

	@Test
	public void findById_CoursePresent(){
		Optional<Course> courseOptional = repository.findById(10001L);
		assertTrue(courseOptional.isPresent());
	}

	@Test
	public void findById_CourseNotPresent(){
		Optional<Course> courseOptional = repository.findById(20001L);
		assertFalse(courseOptional.isPresent());
	}

	@Test
	public void playingAroundWithSpringDataRepository(){
//		Course course = new Course("Microservices in 100 Steps");
//		repository.save(course);
//		course.setName("Microservices in 100 Steps - Updated");
//		repository.save(course);

		logger.info(" Courses -> {}", repository.findAll());
		logger.info(" Count -> {}", repository.count());
	}

	@Test
	public void sort(){
		Sort sort = Sort.by(Sort.Direction.ASC, "name");

		logger.info("Sorted Courses -> {}", repository.findAll(sort));
		logger.info(" Count -> {}", repository.count());
	}

	@Test
	public void pagination(){

		PageRequest pageRequest = PageRequest.of(0, 3);

		Page<Course> firstPage = repository.findAll(pageRequest);
		logger.info("First Page -> {}", firstPage.getContent());

		Pageable secondPageable = firstPage.nextPageable();
		Page<Course> secondPage = repository.findAll(secondPageable);
		logger.info("Second Page -> {}", secondPage.getContent());

	}

	@Test
	public void findUsingName(){
		logger.info("FIndByName -> {}", repository.findByName("JPA in 50 Steps"));
	}

}



























