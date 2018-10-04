package com.estafet.dev.firstapp.activity;

import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.estafet.dev.firstapp.FirstApp;
import com.estafet.dev.firstapp.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.estafet.dev.firstapp.Utils.YYYY_MM_DD_HHMMSS;
import static com.estafet.dev.firstapp.activity.MainActivity.VOICE;

/**
 * Created by Delcho Delov <delcho.delov@estafet.com>
 * on 25.09.18
 */
public class VoiceMemoActivity extends AppCompatActivity {
    private ImageButton buttonStart, buttonStop ;
    private MediaRecorder mediaRecorder ;
    private String baseDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_memo);
        baseDir = ((FirstApp) getApplication()).basePath;

        buttonStart =  findViewById(R.id.startRecordingBtn);
        buttonStop =  findViewById(R.id.stopRecordingBtn);

        buttonStop.setEnabled(false);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final boolean feature = getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
                if(feature) {
//                    final File baseDir = Environment.getExternalStorageDirectory();
                    final File voiceDir = new File(baseDir, VOICE);
                    SimpleDateFormat name_format = new SimpleDateFormat(YYYY_MM_DD_HHMMSS);
                    Date now = new Date();
                    final Long nameId = Long.parseLong(name_format.format(now));
                    final File voiceFile = new File(voiceDir, "v"+nameId.toString()+".3gp");

                    try {
                        mediaRecorder = new MediaRecorder();
                        start(voiceFile.getAbsolutePath());
                    } catch (IOException e) {
                        Toast.makeText(VoiceMemoActivity.this, "Recording failed",
                                Toast.LENGTH_LONG).show();
                    }

                    buttonStart.setEnabled(false);
                    buttonStop.setEnabled(true);

                    Toast.makeText(VoiceMemoActivity.this, "Recording started",
                            Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(VoiceMemoActivity.this, "This device does not have a microphone",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop();
                buttonStop.setEnabled(false);
                buttonStart.setEnabled(true);

                Toast.makeText(VoiceMemoActivity.this, "Recording Completed",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
    /**
     * Starts a new recording.
     */
    public void start(final String outputFile) throws IOException {
        checkStorageLocation(outputFile);

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(outputFile);
        mediaRecorder.prepare();
        mediaRecorder.start();
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

    /**
     * Stops a recording that has been previously started.
     */
    public void stop() {
        mediaRecorder.stop();
        mediaRecorder.reset();
        mediaRecorder.release();
    }
}