package com.estafet.dev.firstapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.estafet.dev.firstapp.FirstApp;
import com.estafet.dev.firstapp.R;
import com.estafet.dev.firstapp.entity.PersonalInfo;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.estafet.dev.firstapp.Utils.YYYY_MM_DD_HHMMSS;

/**
 * Created by Delcho Delov <delcho.delov@estafet.com>
 * on 25.09.18
 */
public class MainActivity extends AppCompatActivity {
    final static String MESSAGE_TO_SEND = "com.estafet.dev.firstapp.messageToSend";
    public static final String PHOTOS = "photos/";
    public static final String VOICE = "voice/";

    private PersonalInfo personalInfo;
    private String basePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        personalInfo = ((FirstApp) getApplication()).personalInfo;
        basePath = ((FirstApp) getApplication()).basePath;
    }

    public void createNewPatient(View view) {
        //TODO check if there is unsaved data on current (if any) patient

        //1 create new patient ID
        SimpleDateFormat id_format = new SimpleDateFormat(YYYY_MM_DD_HHMMSS);
        Date now = new Date();
        final Long id = Long.parseLong(id_format.format(now));
        PersonalInfo personalInfo = new PersonalInfo(id);
        //2. create base directory structure
        final File applicationDir = /*getFilesDir();//*/Environment.getExternalStorageDirectory();
        final File baseDir = new File(applicationDir, id.toString());
        basePath = baseDir.getAbsolutePath();
        ((FirstApp) getApplication()).basePath = basePath;

        if (!createDirectoryStructure(id)) {
            Toast.makeText(MainActivity.this, "Could not create directory structure for the application", Toast.LENGTH_SHORT).show();
            return;
        }
        ((FirstApp) getApplication()).personalInfo = personalInfo;

        //navigate to next screen
        final Intent intent = new Intent(this, PersonInfoActivity.class);
        startActivity(intent);
    }

    public void editProfile(View view) {
        if (((FirstApp) getApplication()).personalInfo != null) {
            final Intent intent = new Intent(this, PersonInfoActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "Please choose patient profile first", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadProfile(View view) {
            final File applicationDir = /*getFilesDir();//*/Environment.getExternalStorageDirectory();
            final String[] profileDirectories = applicationDir.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    if(dir.isDirectory() && name.matches("\\d+")){
                        return true;
                    }
                    return false;
                }
            });

        if (profileDirectories.length > 0) {
            List<String> paths = new ArrayList<>();
            for (String directory : profileDirectories) {
                File prDir = new File(applicationDir, directory);
                final String[] list = prDir.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        if (name.endsWith(".json")) {
                            File f = new File(prDir, name);
                            paths.add(f.getAbsolutePath());
                            return true;
                        }
                        return false;
                    }
                });
//                if(list!=null && list.length>0){
//                    File f = new File()
//                    paths.add(list[0]);
//                }
            }
            if(!paths.isEmpty()) {
                try {
                    File f = new File(paths.get(0));
                    final PersonalInfo personalInfo = PersonalInfo.readFromFile(f);
                    ((FirstApp) getApplication()).personalInfo = personalInfo;
                    ((FirstApp) getApplication()).basePath = f.getParent();
                    editProfile(view);
                    final Intent intent = new Intent(this, PersonInfoActivity.class);
                    startActivity(intent);
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, "Could not open file", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

//    public void sendMessage(View view) {
//        final TextView textView = findViewById(R.id.messageText);
//        final Intent intent = new Intent(this, SendMessageActivity.class);
//        intent.putExtra(MESSAGE_TO_SEND, textView.getText().toString());
//        startActivity(intent);
//    }

    private boolean createDirectoryStructure(Long id) {
//        final File appplicationDir = getFilesDir();
//        final File baseDir = new File(appplicationDir, id.toString());
        final File photosDir = new File(basePath, PHOTOS);
        final File voiceDir = new File(basePath, VOICE);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            try {
//                Files.createDirectory(baseDir.toPath());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        if (!photosDir.mkdirs()) {
            return false;
        }
        if (!voiceDir.mkdirs()) {
            return false;
        }
        return true;
    }

    public void takePicture(View view) {
        final Intent intent = new Intent(this, ImagePickerActivity.class);
        startActivity(intent);
    }

    public void startVoiceRecording(View view) {
        final Intent intent = new Intent(this, VoiceMemoActivity.class);
        startActivity(intent);
    }

    public void newTextNote(View view) {
        Toast.makeText(MainActivity.this, "This functionality is not ready", Toast.LENGTH_SHORT).show();
    }
}
