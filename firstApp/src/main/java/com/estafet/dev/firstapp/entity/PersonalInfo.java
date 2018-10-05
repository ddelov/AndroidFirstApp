package com.estafet.dev.firstapp.entity;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

//    @TargetApi(Build.VERSION_CODES.KITKAT)
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
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static PersonalInfo readFromFile(File file) throws IOException {
        Long id = null;
        String name=null;
        Date birthDate = null;
        Double weight=null;
        Double height=null;
        String email=null;
        String notes=null;
        SimpleDateFormat birthDate_format = new SimpleDateFormat(DD_MM_YYYY);

        try(BufferedReader br = new BufferedReader(new FileReader(file));
            JsonReader reader = new JsonReader(br)) {
            reader.beginObject();
            while (reader.hasNext()){
                String nameToRead = reader.nextName();
                switch (nameToRead){
                    case "id":
                        id = reader.nextLong();break;
                    case "name":
                        name = reader.nextString();break;
                    case "birthDate":
                        try {
                            birthDate = birthDate_format.parse(reader.nextString());
                        } catch (ParseException ignored) {}
                        break;
                    case "weight":
                        weight = reader.nextDouble();break;
                    case "height":
                        height = reader.nextDouble();break;
                    case "email":
                        email = reader.nextString();break;
                    case "notes":
                        notes = reader.nextString();break;
                        default: reader.skipValue();
                }
            }
            reader.endObject();
        }
        if(id!=null){
            final PersonalInfo res = new PersonalInfo(id);
            if(name!=null) {
                res.setName(name);
            }
            if(birthDate!=null) {
                res.setBirthDate(birthDate);
            }
            if(weight!=null) {
                res.setWeight(weight);
            }
            if(height!=null) {
                res.setHeight(height);
            }
            if(email!=null) {
                res.setEmail(email);
            }
            if(notes!=null) {
                res.setNotes(notes);
            }
            return res;
        }
        return null;
    }


    public String getBirthDateText() {
        if(birthDate!=null){
            SimpleDateFormat birthDate_format = new SimpleDateFormat(DD_MM_YYYY);
            return birthDate_format.format(birthDate);
        }
        return null;
    }

    public void save(final File infoFile) throws IOException {
        try (FileOutputStream out = new FileOutputStream(infoFile)) {

            writeJsonStream(out);
        }

    }

}
