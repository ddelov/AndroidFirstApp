package com.estafet.dev.firstapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.estafet.dev.firstapp.entity.PersonalInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PersonInfoActivity extends AppCompatActivity {
    public static final String DD_MM_YYYY = "dd/MM/yyyy";
    public static final String YYYY_M_MDD_H_HMMSS = "yyyyMMddHHmmss";
    private PersonalInfo personalInfo;
    private EditText personName, birthDate, weight, height, email, notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        personalInfo = ((FirstApp) getApplication()).personalInfo;
        personName = findViewById(R.id.editPersonName);
        birthDate = findViewById(R.id.editPersonBirthDate);
        weight = findViewById(R.id.editPersonWeight);
        height = findViewById(R.id.editPersonHeight);
        email = findViewById(R.id.editPersonEmail);
        notes = findViewById(R.id.editPersonNotes);
        if(this.personalInfo !=null){
            personName.setText(this.personalInfo.getName());
            birthDate.setText(this.personalInfo.getName());
            weight.setText(this.personalInfo.getWeight().toString());
            height.setText(this.personalInfo.getHeight().toString());
            email.setText(this.personalInfo.getEmail());
            notes.setText(this.personalInfo.getNotes());
        }
    }

    public void savePersonalInfo(View view) {
        if(isValid()){
            if(personalInfo==null){
                SimpleDateFormat id_format = new SimpleDateFormat(YYYY_M_MDD_H_HMMSS);
                Date now = new Date();
                SimpleDateFormat birthDate_format = new SimpleDateFormat(DD_MM_YYYY);
                Date bd = new Date();
                try {
                    bd = birthDate_format.parse(birthDate.getText().toString());
                } catch (ParseException ignored) {
                }
                final Long id = Long.parseLong(id_format.format(now));
                personalInfo = new PersonalInfo(id, bd);
            }
            personalInfo.setEmail(email.getText().toString());
            final String heightS = this.height.getText().toString();
            if(heightS!=null) {
                personalInfo.setHeight(Double.parseDouble(heightS));
            }
            final String weightS = weight.getText().toString();
            if(weightS!=null){
                personalInfo.setWeight(Double.parseDouble(weightS));
            }
            personalInfo.setName(personName.getText().toString());
            personalInfo.setNotes(notes.getText().toString());
            ((FirstApp) getApplication()).personalInfo = personalInfo;
            Toast.makeText(getBaseContext(), "Personal info saved", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getBaseContext(), "Please correct personal information", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isValid() {
        final Context baseContext = getBaseContext();
        if(personName.getText().toString().isEmpty()){
            Toast.makeText(baseContext, "Please enter person name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(birthDate.getText().toString().isEmpty()){
            Toast.makeText(baseContext, "Please enter valid birth date name", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            SimpleDateFormat birthDate_format = new SimpleDateFormat(DD_MM_YYYY);
            try {
                birthDate_format.parse(birthDate.getText().toString());
            } catch (ParseException e) {
                Toast.makeText(baseContext, "Please enter birth date in format "+DD_MM_YYYY, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        final String heightS = height.getText().toString();
        if(heightS!=null) {
            try{
                Double.parseDouble(heightS);
            }catch (NumberFormatException e){
                Toast.makeText(baseContext, "Please enter valid decimal number for height", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        final String weightS = weight.getText().toString();
        if(weightS!=null) {
            try{
                Double.parseDouble(weightS);
            }catch (NumberFormatException e){
                Toast.makeText(baseContext, "Please enter valid decimal number for weight", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }
}
