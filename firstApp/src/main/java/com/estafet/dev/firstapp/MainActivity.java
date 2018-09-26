package com.estafet.dev.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.estafet.dev.firstapp.entity.PersonalInfo;

public class MainActivity extends AppCompatActivity {
    final static String MESSAGE_TO_SEND = "com.estafet.dev.firstapp.messageToSend";
    private PersonalInfo personalInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        personalInfo = ((FirstApp) getApplication()).personalInfo;
    }

    public void sendMessage(View view) {
//        final TextView textView = findViewById(R.id.messageText);
//        final Intent intent = new Intent(this, SendMessageActivity.class);
//        intent.putExtra(MESSAGE_TO_SEND, textView.getText().toString());
//        startActivity(intent);
        final Intent intent = new Intent(this, PersonInfoActivity.class);
        startActivity(intent);
    }
}
