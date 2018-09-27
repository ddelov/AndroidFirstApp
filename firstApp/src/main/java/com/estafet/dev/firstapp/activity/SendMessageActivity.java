package com.estafet.dev.firstapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.estafet.dev.firstapp.R;

/**
 * Created by Delcho Delov <delcho.delov@estafet.com>
 * on 25.09.18
 */
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
