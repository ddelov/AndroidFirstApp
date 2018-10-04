package com.estafet.dev.firstapp.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.estafet.dev.firstapp.FirstApp;
import com.estafet.dev.firstapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.estafet.dev.firstapp.Utils.YYYY_MM_DD_HHMMSS;
import static com.estafet.dev.firstapp.activity.MainActivity.PHOTOS;

/**
 * Created by Delcho Delov <delcho.delov@estafet.com>
 * on 25.09.18
 */
public class ImagePickerActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private ImageView imageView;
    private String baseDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);
        baseDir = ((FirstApp) getApplication()).basePath;

        imageView = (ImageView) findViewById(R.id.image_view);

//        Button selectGalleryImgBtn = (Button) findViewById(R.id.pick_image_button);
//        selectGalleryImgBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openGallery();
//            }
//        });

        final View takePictureBtn = findViewById(R.id.pick_image_button);
        takePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
    }


    private void takePhoto() {
        final boolean feature = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
        if(feature) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }else{
            Toast.makeText(ImagePickerActivity.this, "This device does not have a camera",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void savePhoto(Bitmap bitmap){
        final File photosDir = new File(baseDir, PHOTOS);
        SimpleDateFormat name_format = new SimpleDateFormat(YYYY_MM_DD_HHMMSS);
        Date now = new Date();
        final Long nameId = Long.parseLong(name_format.format(now));
        final File photoFile = new File(photosDir, "ph"+nameId+".jpg");
        try {
            checkStorageLocation(photoFile.getAbsolutePath());
        } catch (IOException e) {
            Toast.makeText(ImagePickerActivity.this, "Storage location unavailable",
                    Toast.LENGTH_LONG).show();
        }
        try(FileOutputStream fos = new FileOutputStream(photosDir + "ph"+nameId+".jpg")) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos);
            fos.flush();
        } catch (Exception e) {
            Toast.makeText(ImagePickerActivity.this, "Could not store file",
                    Toast.LENGTH_LONG).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // If the user finished the action
        // and the request code is your request code
        if (resultCode == RESULT_OK) {
            if(requestCode==PICK_IMAGE){
                Uri imageUri = data.getData();
                imageView.setImageURI(imageUri);
            }else {
                if (requestCode == REQUEST_IMAGE_CAPTURE) {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imageView.setImageBitmap(imageBitmap);
                    savePhoto(imageBitmap);
                }
            }
        }
    }
}