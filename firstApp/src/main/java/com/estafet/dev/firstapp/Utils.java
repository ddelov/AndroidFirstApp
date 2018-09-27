package com.estafet.dev.firstapp;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Delcho Delov <delcho.delov@estafet.com>
 * on 27.09.18
 */
public class Utils {
    public static final String YYYY_MM_DD_HHMMSS = "yyyyMMddHHmmss";
    public static final String DD_MM_YYYY = "dd/MM/yyyy";
    public static final String BASE_DIR = "/medicalData/";
    public static final String PHOTOS = "photos/";
    public static final String VOICE = "voice/";

    public static boolean createDirectoryStructure(Long id) {
        final String baseDirPath = Environment.getExternalStorageDirectory() + BASE_DIR +id.toString();
        final File baseDir = new File(baseDirPath);
        final File photosDir = new File(baseDir, PHOTOS);
        final File voiceDir = new File(baseDir, VOICE);
        if(!photosDir.mkdirs()){
            return false;
        }
        if(!voiceDir.mkdirs()){
            return false;
        }
        return true;
    }
    public static File getInfoFile(Long id){
        final String baseDirPath = Environment.getExternalStorageDirectory() + BASE_DIR +id.toString();
        final File baseDir = new File(baseDirPath);
        return new File(baseDir, "pi" + id.toString()+".json");
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void savePhoto(Long id, Bitmap bitmap){
        final String baseDirPath = Environment.getExternalStorageDirectory() + BASE_DIR +id.toString();
        final File baseDir = new File(baseDirPath);
        final File photosDir = new File(baseDir, PHOTOS);
        SimpleDateFormat name_format = new SimpleDateFormat(YYYY_MM_DD_HHMMSS);
        Date now = new Date();
        final Long nameId = Long.parseLong(name_format.format(now));

        try(FileOutputStream fos = new FileOutputStream(photosDir + "ph"+nameId+".jpg")) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos);
            fos.flush();
        } catch (Exception e) {
            Log.e("MyLog", e.toString());
        }
    }

}
