package com.javawhizz.hibernate_jpa_course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import org.hibernate.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries(
        value = {
                @NamedQuery(name = "query_get_all_courses", query = "Select c From Course c"),
                @NamedQuery(name = "query_get_all_courses_join_fetch", query = "Select c From Course c JOIN FETCH c.students"),
                @NamedQuery(name = "query_get_100_step_courses", query = "select c from Course c where name like '%100 Steps'")
        }
)
@Cacheable
@SQLDelete(sql = "update course set is_deleted=true where id=?")
@SQLRestriction(value = "is_deleted = false")/* Since the course is already set to true
 retrieving it with false returns null (fixing delete test for null) => The annotation does not apply to native queries
 hence you must specify the predicate explicitly on the native query*/
public class Course {
    private static Logger LOGGER = LoggerFactory.getLogger(Course.class);

    @Id
    @GeneratedValue
    private Long id;


    private String name;


    @OneToMany(mappedBy = "course")
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    private List<Student> students = new ArrayList<>();

    @UpdateTimestamp
    private LocalDateTime lastUpdatedDate;


    @CreationTimestamp
    private LocalDateTime createdDate;

    private boolean isDeleted;

    @PreRemove //Updating the entity in the cache since hibernate does not know about the update done by the @SQLDelete annotation
    private void preRemove(){
        LOGGER.info("Setting isDeleted to true");
        this.isDeleted = true;
    }

    protected Course() {
    }

    public Course(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public void removeReview(Review review) {
        this.reviews.remove(review);
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student){
        this.students.add(student);
    }

    public void removeStudent(Student student){
        this.students.remove(student);
    }

    @Override
    public String  toString() {
        return "Course{" +
                "name='" + name + '\'' +
                '}';
    }
}
