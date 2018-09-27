package com.estafet.dev.firstapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.estafet.dev.firstapp.FirstApp;
import com.estafet.dev.firstapp.R;
import com.estafet.dev.firstapp.entity.PersonalInfo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.estafet.dev.firstapp.Utils.YYYY_MM_DD_HHMMSS;
import static com.estafet.dev.firstapp.Utils.createDirectoryStructure;

/**
 * Created by Delcho Delov <delcho.delov@estafet.com>
 * on 25.09.18
 */
public class MainActivity extends AppCompatActivity {
    final static String MESSAGE_TO_SEND = "com.estafet.dev.firstapp.messageToSend";

    private PersonalInfo personalInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        personalInfo = ((FirstApp) getApplication()).personalInfo;
    }

    public void createNewPatient(View view) {
        //TODO check if there is unsaved data on current (if any) patient

        //1 create new patient ID
        SimpleDateFormat id_format = new SimpleDateFormat(YYYY_MM_DD_HHMMSS);
        Date now = new Date();
        final Long id = Long.parseLong(id_format.format(now));
        PersonalInfo personalInfo = new PersonalInfo(id);
        //2. create base directory structure
        if(!createDirectoryStructure(id)){
            Toast.makeText(MainActivity.this, "Could not create directory structure for the application", Toast.LENGTH_SHORT).show();
            return;
        }
        ((FirstApp) getApplication()).personalInfo = personalInfo;

        //navigate to next screen
        final Intent intent = new Intent(this, PersonInfoActivity.class);
        startActivity(intent);

    }


    public void sendMessage(View view) {
        final TextView textView = findViewById(R.id.messageText);
        final Intent intent = new Intent(this, SendMessageActivity.class);
        intent.putExtra(MESSAGE_TO_SEND, textView.getText().toString());
        startActivity(intent);
    }
}
