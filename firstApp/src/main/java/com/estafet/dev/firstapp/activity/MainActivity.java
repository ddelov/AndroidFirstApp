package com.estafet.dev.firstapp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.estafet.dev.firstapp.FirstApp;
import com.estafet.dev.firstapp.R;
import com.estafet.dev.firstapp.entity.PersonalInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    public void editProfile(View view){
        if(((FirstApp) getApplication()).personalInfo!=null) {
            final Intent intent = new Intent(this, PersonInfoActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(MainActivity.this, "Please choose patient profile first", Toast.LENGTH_SHORT).show();
        }
    }
    public void loadProfile(View view) {
//        final File baseDir = new File(getFilesDir(), personalInfo.getId().toString());
//        //final File infoFile = new File(baseDir, personalInfo.getName()!=null?personalInfo.getName():personalInfo.getId().toString() + ".json");
//        final String[] list = baseDir.list(new FilenameFilter() {
//            @Override
//            public boolean accept(File dir, String name) {
//                if (name.endsWith(".json")) {
//                    return true;
//                }
//                return false;
//            }
//        });
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                final List<Path> paths = Files.walk(Paths.get(getFilesDir().getPath()))
                        .filter(/*name -> name.endsWith(".json")*/Files::isRegularFile)
                        .collect(Collectors.toList());
                for (Path path : paths) {
                    System.out.println("path = " + path);
                }
                final PersonalInfo personalInfo = PersonalInfo.readFromFile(paths.get(0).toFile());
                ((FirstApp) getApplication()).personalInfo = personalInfo;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        editProfile(view);
        final Intent intent = new Intent(this, PersonInfoActivity.class);
        startActivity(intent);
//            //create a dialog to show a list of files and directories
//            AlertDialog.Builder builder = new AlertDialog.Builder(/*context*/this);
//            builder.setCancelable(true);
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    //TODO
//
//                    dialog.cancel();
//                }
//            });
//            builder.setNeutralButton("Up",null);
//            builder.setView(getList(path));
//            AlertDialog dialog = builder.create();
//            dialog.show();
//            //override the default dialog default behavior when the Up button is pressed
//            dialog.getButton(Dialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
//
//                public void onClick(View arg0) {
//                    upOneLevel();
//                }
//            });
    }

    public void sendMessage(View view) {
        final TextView textView = findViewById(R.id.messageText);
        final Intent intent = new Intent(this, SendMessageActivity.class);
        intent.putExtra(MESSAGE_TO_SEND, textView.getText().toString());
        startActivity(intent);
    }

    private boolean createDirectoryStructure(Long id) {
        final File appplicationDir = getFilesDir();
        final File baseDir = new File(appplicationDir, id.toString());
        final File photosDir = new File(baseDir, PHOTOS);
        final File voiceDir = new File(baseDir, VOICE);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            try {
//                Files.createDirectory(baseDir.toPath());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        if(!photosDir.mkdirs()){
            return false;
        }
        if(!voiceDir.mkdirs()){
            return false;
        }
        return true;
    }

}
