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
    private String nationality;
    private String address;
    private String county;
    private String telephone;
    private Integer age;
    private Date birthDate;
    private Gender gender;
    private String examLocation;
    private Date examDate = new Date();
    private String examStartTime;
    private String examEndTime;
    private Boolean interpreterUsed = false;
    private String interpreterName;
    private String languageUsed;
    private String interpreterPhone;

    public PersonalInfo(Long id) {
        this.id = id;
        name = id.toString();
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getExamLocation() {
        return examLocation;
    }

    public void setExamLocation(String examLocation) {
        this.examLocation = examLocation;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public String getExamStartTime() {
        return examStartTime;
    }

    public void setExamStartTime(String examStartTime) {
        this.examStartTime = examStartTime;
    }

    public String getExamEndTime() {
        return examEndTime;
    }

    public void setExamEndTime(String examEndTime) {
        this.examEndTime = examEndTime;
    }

    public Boolean getInterpreterUsed() {
        return interpreterUsed;
    }

    public void setInterpreterUsed(Boolean interpreterUsed) {
        this.interpreterUsed = interpreterUsed;
    }

    public String getInterpreterName() {
        return interpreterName;
    }

    public void setInterpreterName(String interpreterName) {
        this.interpreterName = interpreterName;
    }

    public String getLanguageUsed() {
        return languageUsed;
    }

    public void setLanguageUsed(String languageUsed) {
        this.languageUsed = languageUsed;
    }

    public String getInterpreterPhone() {
        return interpreterPhone;
    }

    public void setInterpreterPhone(String interpreterPhone) {
        this.interpreterPhone = interpreterPhone;
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

            if (getNationality() != null) {
                writer.name("nationality").value(getNationality());
            }
            if (getAddress() != null) {
                writer.name("address").value(getAddress());
            }
            if (getCounty() != null) {
                writer.name("county").value(getCounty());
            }
            if (getTelephone() != null) {
                writer.name("telephone").value(getTelephone());
            }

            if (getAge() != null) {
                writer.name("age").value(getAge());
            }
            if (getBirthDateText() != null) {
                writer.name("birthDate").value(getBirthDateText());
            }
            if (getGender() != null) {
                writer.name("gender").value(getGender().name());
            }
            if (getExamLocation() != null) {
                writer.name("examLocation").value(getExamLocation());
            }

            if (getExamDate() != null) {
                writer.name("examDate").value(getExamDateText());
            }
            if (getExamStartTime() != null) {
                writer.name("examStartTime").value(getExamStartTime());
            }
            if (getExamEndTime() != null) {
                writer.name("examEndTime").value(getExamEndTime());
            }
            if (getInterpreterUsed() != null) {
                writer.name("interpreterUsed").value(getInterpreterUsed());
            }
            if (getInterpreterName() != null) {
                writer.name("interpreterName").value(getInterpreterName());
            }
            if (getLanguageUsed() != null) {
                writer.name("languageUsed").value(getLanguageUsed());
            }
            if (getInterpreterPhone() != null) {
                writer.name("interpreterPhone").value(getInterpreterPhone());
            }


            writer.endObject();
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static PersonalInfo readFromFile(File file) throws IOException {
        Long id = null;
        String name = null;
        Date birthDate = null;
        String nationality = null;
        String address = null;
        String county = null;
        String telephone = null;
        Integer age = null;
        Gender gender = null;
        String examLocation = null;
        Date examDate = null;
        String examStartTime = null;
        String examEndTime = null;
        Boolean interpreterUsed = null;
        String interpreterName = null;
        String languageUsed = null;
        String interpreterPhone = null;
        SimpleDateFormat birthDate_format = new SimpleDateFormat(DD_MM_YYYY);

        try (BufferedReader br = new BufferedReader(new FileReader(file));
             JsonReader reader = new JsonReader(br)) {
            reader.beginObject();
            while (reader.hasNext()) {
                String nameToRead = reader.nextName();
                switch (nameToRead) {
                    case "id":
                        id = reader.nextLong();
                        break;
                    case "name":
                        name = reader.nextString();
                        break;
                    case "birthDate":
                        try {
                            birthDate = birthDate_format.parse(reader.nextString());
                        } catch (ParseException ignored) {
                        }
                        break;
                    case "nationality":
                        nationality = reader.nextString();
                        break;
                    case "address":
                        address = reader.nextString();
                        break;
                    case "county":
                        county = reader.nextString();
                        break;
                    case "telephone":
                        telephone = reader.nextString();
                        break;
                    case "age":
                        age = reader.nextInt();
                        break;
                    case "gender":
                        gender = Gender.valueOf(reader.nextString());
                        break;
                    case "examLocation":
                        examLocation = reader.nextString();
                        break;
                    case "examDate":
                        try {
                            examDate = birthDate_format.parse(reader.nextString());
                        } catch (ParseException ignored) {
                        }
                        break;
                    case "examStartTime":
                        examStartTime = reader.nextString();
                        break;
                    case "examEndTime":
                        examEndTime = reader.nextString();
                        break;
                    case "interpreterUsed":
                        interpreterUsed = reader.nextBoolean();
                        break;
                    case "interpreterName":
                        interpreterName = reader.nextString();
                        break;
                    case "languageUsed":
                        languageUsed = reader.nextString();
                        break;
                    case "interpreterPhone":
                        interpreterPhone = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                }
            }
            reader.endObject();
        }
        if (id != null) {
            final PersonalInfo res = new PersonalInfo(id);
            if (name != null) {
                res.setName(name);
            }
            if (birthDate != null) {
                res.setBirthDate(birthDate);
            }
            if (nationality != null) {
                res.setNationality(nationality);
            }
            if (address != null) {
                res.setAddress(address);
            }
            if (county != null) {
                res.setCounty(county);
            }
            if (telephone != null) {
                res.setTelephone(telephone);
            }
            if (age != null) {
                res.setAge(age);
            }
            if (gender != null) {
                res.setGender(gender);
            }
            if (examLocation != null) {
                res.setExamLocation(examLocation);
            }
            if (examDate != null) {
                res.setExamDate(examDate);
            }
            if (examStartTime != null) {
                res.setExamStartTime(examStartTime);
            }
            if (examEndTime != null) {
                res.setExamEndTime(examEndTime);
            }
            if (interpreterUsed != null) {
                res.setInterpreterUsed(interpreterUsed);
            }
            if (interpreterName != null) {
                res.setInterpreterName(interpreterName);
            }
            if (languageUsed != null) {
                res.setLanguageUsed(languageUsed);
            }
            if (interpreterPhone != null) {
                res.setInterpreterPhone(interpreterPhone);
            }
            return res;
        }
        return null;
    }


    public String getBirthDateText() {
        if (birthDate != null) {
            SimpleDateFormat birthDate_format = new SimpleDateFormat(DD_MM_YYYY);
            return birthDate_format.format(birthDate);
        }
        return null;
    }

    public String getExamDateText() {
        if (examDate != null) {
            SimpleDateFormat date_format = new SimpleDateFormat(DD_MM_YYYY);
            return date_format.format(examDate);
        }
        return null;
    }

    public void save(final File infoFile) throws IOException {
        try (FileOutputStream out = new FileOutputStream(infoFile)) {

            writeJsonStream(out);
        }

    }

}
