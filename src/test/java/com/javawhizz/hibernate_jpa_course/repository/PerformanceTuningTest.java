package com.javawhizz.hibernate_jpa_course.repository;

import com.javawhizz.hibernate_jpa_course.entity.Course;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Subgraph;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PerformanceTuningTest {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CourseRepository repository;

	@Autowired
	EntityManager em;

	@Test
	@Transactional
	void creatingNPlusOneProblem() {
		List<Course> courses = em.createNamedQuery("query_get_all_courses", Course.class)
				.getResultList();
		for (Course course : courses) {
			logger.info("Course -> {} Students -> {}", course, course.getStudents());
		}
	}

	@Test
	@Transactional
	void solvingNPlusOneProblem_EntityGraph() {
		EntityGraph<Course> entityGraph = em.createEntityGraph(Course.class);
		Subgraph<Object> subGraph = entityGraph.addSubgraph("students");
		List<Course> courses = em.createNamedQuery("query_get_all_courses", Course.class)
				.setHint("javax.persistence.loadgraph", entityGraph)
				.getResultList();
		for (Course course : courses) {
			logger.info("Course -> {} Students -> {}", course, course.getStudents());
		}
	}

	@Test
	@Transactional
	void solvingNPlusOneProblem_JoinFetch() {
		EntityGraph<Course> entityGraph = em.createEntityGraph(Course.class);
		Subgraph<Object> subGraph = entityGraph.addSubgraph("students");
		List<Course> courses = em.createNamedQuery("query_get_all_courses_join_fetch", Course.class)
				.setHint("javax.persistence.loadgraph", entityGraph)
				.getResultList();
		for (Course course : courses) {
			logger.info("Course -> {} Students -> {}", course, course.getStudents());
		}
	}

}



























