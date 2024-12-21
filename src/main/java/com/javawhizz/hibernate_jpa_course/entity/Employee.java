package com.javawhizz.hibernate_jpa_course.entity;

import jakarta.persistence.*;

//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) -> contains nulls but fast -> Good when performance is a concern
//@DiscriminatorColumn(name = "EmployeeType")
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // -> contains repeated columns (name)
//@Inheritance(strategy = InheritanceType.JOINED) -> Good in terms of database design but not fast -> Good for data integrity and quality (preferred)
@MappedSuperclass // Employee is only used for mapping and not an Entity -> Remove @Entity -> No inheritance
public abstract class Employee {

    @Id
    @GeneratedValue
    private Long id;


    private String name;

    protected Employee() {
    }

    public Employee(String name) {
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

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                '}';
    }
}
