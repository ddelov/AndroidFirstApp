package com.estafet.dev.firstapp.entity;

import java.util.Date;

/**
 *
 * Created by Delcho Delov <delcho.delov@estafet.com>
 * on ${DATE}
 */
public class PersonalInfo {
    private final Long id;
    private String name;
    private final Date birthDate;
    private Double weight;
    private Double height;
    private String email;
    private String notes;

    public PersonalInfo(Long id, Date birthDate) {
        this.id = id;
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
