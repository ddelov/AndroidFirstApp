package com.estafet.dev.firstapp.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.estafet.dev.firstapp.FirstApp;
import com.estafet.dev.firstapp.R;
import com.estafet.dev.firstapp.entity.PersonalInfo;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.estafet.dev.firstapp.Utils.DD_MM_YYYY;

/**
 *
 * Created by Delcho Delov <delcho.delov@estafet.com>
 * on 25.09.18
 */
public class PersonInfoActivity extends AppCompatActivity {
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
        if (this.personalInfo != null) {// should be!
            toView();
        }
    }

    private void fromView() {
        final SimpleDateFormat birthDate_format = new SimpleDateFormat(DD_MM_YYYY);
        try {
            final Date date = birthDate_format.parse(birthDate.getText().toString());
            personalInfo.setBirthDate(date);
        } catch (ParseException ignored) {
        }
        personalInfo.setEmail(email.getText().toString());
        final String heightS = this.height.getText().toString();
        if (heightS != null) {
            personalInfo.setHeight(Double.parseDouble(heightS));
        }
        final String weightS = weight.getText().toString();
        if (weightS != null) {
            personalInfo.setWeight(Double.parseDouble(weightS));
        }
        personalInfo.setName(personName.getText().toString());
        personalInfo.setNotes(notes.getText().toString());
    }

    private void toView() {
        if (personalInfo.getName() != null) {
            personName.setText(this.personalInfo.getName());
        }
        if (personalInfo.getBirthDate() != null) {
            birthDate.setText(this.personalInfo.getBirthDateText());
        }
        if (personalInfo.getWeight() != null) {
            weight.setText(this.personalInfo.getWeight().toString());
        }
        if (personalInfo.getHeight() != null) {
            height.setText(this.personalInfo.getHeight().toString());
        }
        if (personalInfo.getEmail() != null) {
            email.setText(this.personalInfo.getEmail());
        }
        if (personalInfo.getNotes() != null) {
            notes.setText(this.personalInfo.getNotes());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void savePersonalInfo(View view) {
        if (isValid()) {
            fromView();
            ((FirstApp) getApplication()).personalInfo = personalInfo;
            //save patients data as file
            try {
                personalInfo.save(baseDir);
            } catch (IOException e) {
                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

//            //TODO for test only -  remove
//            final File baseDir = new File(getFilesDir(), personalInfo.getId().toString());
//            final File infoFile = new File(baseDir, personalInfo.getName() != null ? personalInfo.getName() : personalInfo.getId().toString() + ".json");
//
//            try {
//                PersonalInfo load = PersonalInfo.readFromFile(infoFile);
//            } catch (IOException e) {
//                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
            //TODO go to the next screen
            //personalInfo.writeJsonStream();
            Toast.makeText(getBaseContext(), "Personal info saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(), "Please correct personal information", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isValid() {
        final Context baseContext = getBaseContext();
        if (personName.getText().toString().isEmpty()) {
            Toast.makeText(baseContext, "Please enter person name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (birthDate.getText().toString().isEmpty()) {
            Toast.makeText(baseContext, "Please enter valid birth date name", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            SimpleDateFormat birthDate_format = new SimpleDateFormat(DD_MM_YYYY);
            try {
                birthDate_format.parse(birthDate.getText().toString());
            } catch (ParseException e) {
                Toast.makeText(baseContext, "Please enter birth date in format " + DD_MM_YYYY, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        final String heightS = height.getText().toString();
        if (heightS != null) {
            try {
                Double.parseDouble(heightS);
            } catch (NumberFormatException e) {
                Toast.makeText(baseContext, "Please enter valid decimal number for height", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        final String weightS = weight.getText().toString();
        if (weightS != null) {
            try {
                Double.parseDouble(weightS);
            } catch (NumberFormatException e) {
                Toast.makeText(baseContext, "Please enter valid decimal number for weight", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }
}
