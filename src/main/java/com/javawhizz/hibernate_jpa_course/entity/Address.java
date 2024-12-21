package com.javawhizz.hibernate_jpa_course.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String line1;
    private String line2;
    private String city;

    public Address() {
    }

    public Address(String city, String line2, String line1) {
        this.city = city;
        this.line2 = line2;
        this.line1 = line1;
    }

}
