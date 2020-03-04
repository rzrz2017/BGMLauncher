package com.dsn.andy.bgmlauncher.util;

/**
 * Created by dell on 2017/10/11.
 */

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.dsn.andy.bgmlauncher.DSNApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * User: Picasso
 * Date: 2015-11-30
 * Time: 15:43
 * FIXME
 */
public class FileUtil {
    /**
     * 拍照路径
     */

    private static String FILE_NAME = "userIcon.jpg";
    public static String PATH_PHOTOGRAPH = "DSN";

    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static File getAvailableCacheDir() {
        if (isExternalStorageWritable()) {
            return DSNApplication.app.getExternalCacheDir();
        } else {
            return DSNApplication.app.getCacheDir();
        }
    }

    public static String getAvatarCropPath() {
        return new File(getAvailableCacheDir(), FILE_NAME).getAbsolutePath();
    }

    public static String getPublishPath(String fileName) {
        return new File(getAvailableCacheDir(), fileName).getAbsolutePath();
    }


    /**
     * 保存图片
     *
     * @param bitmap
     * @param filePath
     */
    public static void saveBitmap(Bitmap bitmap, String filePath) {
        FileOutputStream bos = null;
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            bos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.flush();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void deleteDir(File directory) {
        if (directory != null){
            if (directory.isFile()) {
                directory.delete();
                return;
            }

            if (directory.isDirectory()) {
                File[] childFiles = directory.listFiles();
                if (childFiles == null || childFiles.length == 0) {
                    directory.delete();
                    return;
                }

                for (int i = 0; i < childFiles.length; i++) {
                    deleteDir(childFiles[i]);
                }
                directory.delete();
            }
        }
    }



    public static File getDCIMFile(String filePath, String imageName) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) { // 文件可用
            File dirs = new File("/mnt/sdcard/dsn/");
            if (!dirs.exists()) {
               // Log.e("andy","not exsit="+dirs.getAbsolutePath());
                boolean f = dirs.mkdirs();
                Log.e("andy","mkdir="+f);
            }

            File file = new File(dirs.getAbsolutePath(),
                    imageName);
            if (!file.exists()) {
               // Log.e("andy","not exsit222="+file.getAbsolutePath());
                try {
                    //在指定的文件夹中创建文件
                    file.createNewFile();
                } catch (Exception e) {
                  //  Log.e("andy","eee="+e.getMessage());
                }
            }
            return file;
        } else {
            return null;
        }

    }



    public static File saveBitmap2(Bitmap bitmap, String fileName, File baseFile) {
        FileOutputStream bos = null;
        File imgFile = new File(baseFile, "/" + fileName);
        try {
            bos = new FileOutputStream(imgFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.flush();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imgFile;
    }

    public static File getBaseFile(String filePath) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) { // 文件可用
            File f = new File(Environment.getExternalStorageDirectory(),
                    filePath);
            if (!f.exists())
                f.mkdirs();
            return f;
        } else {
            return null;
        }
    }

    public static String getFileName() {
        String fileName = FILE_NAME ;
        return fileName;
    }

    /**
     * 由指定的路径和文件名创建文件
     */
    public static File createFile(String path, String name) throws IOException {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = new File(path + name);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

}
