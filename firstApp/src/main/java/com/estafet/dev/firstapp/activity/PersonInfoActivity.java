package com.estafet.dev.firstapp.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.estafet.dev.firstapp.FirstApp;
import com.estafet.dev.firstapp.R;
import com.estafet.dev.firstapp.entity.Gender;
import com.estafet.dev.firstapp.entity.PersonalInfo;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
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
    private EditText personName;
    private EditText birthDate;
    private EditText nationality;
    private EditText address;
    private EditText county;
    private EditText telephone;
    private EditText age;
    private RadioGroup gender;
    private RadioGroup interpreterUsed;
    private EditText examLocation;
    private EditText examDate;
    private EditText examStartTime;
    private EditText examEndTime, interpreterName, languageUsed, interpreterPhone;


    private String baseDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        personalInfo = ((FirstApp) getApplication()).personalInfo;
        baseDir = ((FirstApp) getApplication()).basePath;

        if (this.personalInfo != null) {// should be!
            toView();
        }
        interpreterUsed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                EditText[] interpeterEditControls = {interpreterName, languageUsed, interpreterPhone};
                if(checkedId==findViewById(R.id.noInterpreterUsed).getId()){
                    disableInterpreter(interpeterEditControls);
                }else{
                    enableInterpreter(interpeterEditControls);
                }
            }
        });
    }

    private void disableInterpreter(EditText[] controls){
        for (EditText editText : controls) {
            editText.setText("");
            editText.setVisibility(View.GONE);
//            editText.setFocusable(false);
//            editText.setEnabled(false);
//            editText.setInputType(InputType.TYPE_NULL);
        }
    }
    private void enableInterpreter(EditText[] controls){
        for (EditText editText : controls) {
            editText.setFocusable(true);
//            editText.setEnabled(true);
            editText.setVisibility(View.VISIBLE);
        }
//        interpreterName.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
//        languageUsed.setInputType(InputType.TYPE_CLASS_TEXT);
//        interpreterPhone.setInputType(InputType.TYPE_CLASS_PHONE);
    }

    private void fromView() {
        personalInfo.setName(personName.getText().toString());
        final SimpleDateFormat birthDate_format = new SimpleDateFormat(DD_MM_YYYY);
        try {
            final Date date = birthDate_format.parse(birthDate.getText().toString());
            personalInfo.setBirthDate(date);
        } catch (ParseException ignored) {
        }
        personalInfo.setNationality(nationality.getText().toString());
        personalInfo.setAddress(address.getText().toString());
        personalInfo.setCounty(county.getText().toString());
        personalInfo.setTelephone(telephone.getText().toString());
        final String ageS = this.age.getText().toString();
        if (ageS != null && !ageS.isEmpty()) {
            personalInfo.setAge(Integer.parseInt(ageS));
        }

        final int checkedRadioButtonId = gender.getCheckedRadioButtonId();
        if(checkedRadioButtonId==findViewById(R.id.male).getId()){
            personalInfo.setGender(Gender.MALE);
        }else{
            personalInfo.setGender(Gender.FEMALE);
        }
        if(interpreterUsed.getCheckedRadioButtonId()==findViewById(R.id.yesInterpreterUsed).getId()){
            personalInfo.setInterpreterUsed(Boolean.TRUE);
        }else{
            personalInfo.setInterpreterUsed(Boolean.FALSE);
        }

        personalInfo.setExamLocation(examLocation.getText().toString());
        try {
            final Date date = birthDate_format.parse(examDate.getText().toString());
            personalInfo.setExamDate(date);
        } catch (ParseException ignored) {
        }
        personalInfo.setExamStartTime(examStartTime.getText().toString());
        personalInfo.setExamEndTime(examEndTime.getText().toString());
        personalInfo.setInterpreterName(interpreterName.getText().toString());
        personalInfo.setInterpreterPhone(interpreterPhone.getText().toString());
        personalInfo.setLanguageUsed(languageUsed.getText().toString());
    }

    private void toView() {
        personName = findViewById(R.id.editPersonName);
        birthDate = findViewById(R.id.editPersonBirthDate);
        nationality = findViewById(R.id.editPersonNationality);
        address = findViewById(R.id.editPersonAddress);
        county = findViewById(R.id.editPersonCounty);
        telephone = findViewById(R.id.editPersonTelephone);

        age = findViewById(R.id.editPersonAge);
        gender = (RadioGroup)findViewById(R.id.editPersonGender);
        examLocation = findViewById(R.id.editExamLocation);
        examDate = findViewById(R.id.editExamDate);
        examStartTime = findViewById(R.id.editExamStartTime);
        examEndTime = findViewById(R.id.editExamEndTime);
        interpreterUsed = findViewById(R.id.editInterpreterUsed);
        interpreterName = findViewById(R.id.editInterpreterName);
        languageUsed = findViewById(R.id.editLanguage);
        interpreterPhone = findViewById(R.id.editInterpreterPhone);
        // section A - default date - today
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DD_MM_YYYY);
        final String now = dateFormat.format(new Date());
        final EditText secADate = findViewById(R.id.secADate);
        final EditText secBDate = findViewById(R.id.secBDate);
        secADate.setText(now);
        secBDate.setText(now);
        //section B - name of the practitioner
        final EditText secBPrintName = findViewById(R.id.secBPrintName);
        secBPrintName.setText("Това съм аз - Льоклер!");

        if (personalInfo.getName() != null) {
            personName.setText(this.personalInfo.getName());
            final EditText secAName = findViewById(R.id.secAPrintName);
            // section A - default print name - today
            secAName.setText(personalInfo.getName());
        }
        if (personalInfo.getNationality() != null) {
            nationality.setText(this.personalInfo.getNationality());
        }
        if (personalInfo.getAddress() != null) {
            address.setText(this.personalInfo.getAddress());
        }
        if (personalInfo.getCounty() != null) {
            county.setText(this.personalInfo.getCounty());
        }
        if (personalInfo.getTelephone() != null) {
            telephone.setText(this.personalInfo.getTelephone());
        }
        if (personalInfo.getAge() != null) {
            age.setText(this.personalInfo.getAge().toString());
        }
        if (personalInfo.getBirthDate() != null) {
            birthDate.setText(this.personalInfo.getBirthDateText());
        }
        {
            final RadioButton maleBtn = findViewById(R.id.male);
            final RadioButton femaleBtn = findViewById(R.id.female);
            maleBtn.setSelected(false);
            femaleBtn.setSelected(false);
            if (personalInfo.getGender() != null) {
                if (personalInfo.getGender() == Gender.MALE) {
                    maleBtn.setChecked(true);
                } else {
                    femaleBtn.setChecked(true);
                }
            }
        }
        if (personalInfo.getExamLocation() != null) {
            examLocation.setText(this.personalInfo.getExamLocation());
        }
        if (personalInfo.getExamDate() != null) {
            examDate.setText(this.personalInfo.getExamDateText());
        }
        if (personalInfo.getExamStartTime() != null) {
            examStartTime.setText(this.personalInfo.getExamStartTime());
        }
        if (personalInfo.getExamEndTime() != null) {
            examEndTime.setText(this.personalInfo.getExamEndTime());
        }

        if (personalInfo.getInterpreterName() != null) {
            interpreterName.setText(this.personalInfo.getInterpreterName());
        }
        if (personalInfo.getLanguageUsed() != null) {
            languageUsed.setText(this.personalInfo.getLanguageUsed());
        }
        if (personalInfo.getInterpreterPhone() != null) {
            interpreterPhone.setText(this.personalInfo.getInterpreterPhone());
        }
        {
            final RadioButton yesIntrptBtn = findViewById(R.id.yesInterpreterUsed);
            final RadioButton noIntrptBtn = findViewById(R.id.noInterpreterUsed);
            yesIntrptBtn.setChecked(false);
            noIntrptBtn.setChecked(true);
            if (personalInfo.getInterpreterUsed()) {
                    yesIntrptBtn.setChecked(true);
                    noIntrptBtn.setChecked(false);
            }else{
                yesIntrptBtn.setChecked(false);
                noIntrptBtn.setChecked(true);
                EditText[] interpeterEditControls = {interpreterName, languageUsed, interpreterPhone};
                disableInterpreter(interpeterEditControls);
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void savePersonalInfo(View view) {
        if (isValid()) {
            fromView();
            ((FirstApp) getApplication()).personalInfo = personalInfo;
//            final File applicationDir = /*getFilesDir();//*/Environment.getExternalStorageDirectory();
//            final File baseDir = new File(applicationDir, id.toString());
//            basePath = baseDir.getAbsolutePath();
//            ((FirstApp) getApplication()).basePath = basePath;

            //save patients data as file
            String infoFileName = personalInfo.getId().toString() + ".json";
            if(personalInfo.getName() !=null && !personalInfo.getName().isEmpty()){
                infoFileName = personalInfo.getName() + ".json";
            }
//            File profile = new File(baseDir, "profile");
            File infoFile = new File(baseDir, infoFileName);
            try {
                checkStorageLocation(infoFile.getAbsolutePath());
            } catch (IOException e) {
                Toast.makeText(PersonInfoActivity.this, "Storage location unavailable",
                        Toast.LENGTH_LONG).show();
                return;
            }
            try {
                personalInfo.save(infoFile);
            } catch (IOException e) {
                final ByteArrayOutputStream out = new ByteArrayOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(out);

                PrintStream printStream = new PrintStream(bos);
                e.printStackTrace(printStream);
                final String txt = new String(out.toByteArray());

                Toast.makeText(getBaseContext(), txt, Toast.LENGTH_LONG).show();
                return;
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
    private void checkStorageLocation(String outputFile) throws IOException {
        String state = Environment.getExternalStorageState();
        if(!state.equals(Environment.MEDIA_MOUNTED))  {
            throw new IOException("SD Card is not mounted.  It is " + state + ".");
        }

        // make sure the directory we plan to store the recording in exists
        File directory = new File(outputFile).getParentFile();
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Path to file could not be created.");
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
//        final String heightS = height.getText().toString();
//        if (heightS != null) {
//            try {
//                Double.parseDouble(heightS);
//            } catch (NumberFormatException e) {
//                Toast.makeText(baseContext, "Please enter valid decimal number for height", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        }
//        final String weightS = weight.getText().toString();
//        if (weightS != null) {
//            try {
//                Double.parseDouble(weightS);
//            } catch (NumberFormatException e) {
//                Toast.makeText(baseContext, "Please enter valid decimal number for weight", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        }

        return true;
    }
}
