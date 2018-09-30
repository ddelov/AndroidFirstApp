package com.estafet.dev.firstapp;

/**
 * Created by Delcho Delov <delcho.delov@estafet.com>
 * on 27.09.18
 */
public class Utils {
    public static final String YYYY_MM_DD_HHMMSS = "yyyyMMddHHmmss";
    public static final String DD_MM_YYYY = "dd/MM/yyyy";

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public static void savePhoto(Long id, Bitmap bitmap){
//        final String baseDirPath = Environment.getExternalStorageDirectory() + BASE_DIR +id.toString();
//        final File baseDir = new File(baseDirPath);
//        final File photosDir = new File(baseDir, PHOTOS);
//        SimpleDateFormat name_format = new SimpleDateFormat(YYYY_MM_DD_HHMMSS);
//        Date now = new Date();
//        final Long nameId = Long.parseLong(name_format.format(now));
//
//        try(FileOutputStream fos = new FileOutputStream(photosDir + "ph"+nameId+".jpg")) {
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos);
//            fos.flush();
//        } catch (Exception e) {
//            Log.e("MyLog", e.toString());
//        }
//    }

}
