package com.estafet.dev.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class SendMessageActivity extends AppCompatActivity {

//    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.MESSAGE_TO_SEND);
        TextView textView = findViewById(R.id.displayedMessage);
        textView.setText(message);
//        mTextMessage = (TextView) findViewById(R.id.message);
    }

}