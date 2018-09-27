package com.estafet.dev.firstapp.entity;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.estafet.dev.firstapp.Utils.DD_MM_YYYY;


/**
 * Created by Delcho Delov <delcho.delov@estafet.com>
 * on 25.09.18
 */
public class PersonalInfo implements Serializable {
    private final Long id;
    private String name;
    private Date birthDate;
    private Double weight;
    private Double height;
    private String email;
    private String notes;

    public PersonalInfo(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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

    @TargetApi(Build.VERSION_CODES.KITKAT)
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void writeJsonStream(OutputStream out) throws IOException {
        SimpleDateFormat birthDate_format = new SimpleDateFormat(DD_MM_YYYY);

        try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"))) {
            writer.beginObject();
            writer.name("id").value(getId());
            if (getName() != null) {
                writer.name("name").value(getName());
            }
            if (getBirthDate() != null) {
                writer.name("birthDate").value(birthDate_format.format(getBirthDate()));
            }
            if (getWeight() != null) {
                writer.name("weight").value(getWeight());
            }
            if (getHeight() != null) {
                writer.name("height").value(getHeight());
            }
            if (getEmail() != null) {
                writer.name("email").value(getEmail());
            }
            if (getNotes() != null) {
                writer.name("notes").value(getNotes());
            }
            writer.endObject();
        }
    }

//    public static void main(String[] args) throws IOException {
//        final PersonalInfo info = new PersonalInfo(22L);
//        info.setEmail("forget@about.it");
//        final Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.YEAR, 1928);
//        cal.set(Calendar.MONTH, Calendar.AUGUST);
//        cal.set(Calendar.DAY_OF_MONTH, 14);
//        info.setBirthDate(new Date(cal.getTimeInMillis()));
//        info.setName("Unknown rabbit");
//        info.setHeight(23.44454);
//        info.setWeight(456.890);
//        info.setNotes("нямам представа какво да очаквам");
//    }
}
