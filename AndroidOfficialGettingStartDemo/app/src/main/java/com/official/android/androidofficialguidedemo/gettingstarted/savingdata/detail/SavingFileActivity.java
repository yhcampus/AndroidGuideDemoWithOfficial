package com.official.android.androidofficialguidedemo.gettingstarted.savingdata.detail;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.official.android.androidofficialguidedemo.R;

import java.io.File;
import java.io.FileOutputStream;

public class SavingFileActivity extends Activity {

    private static final String LOG_TAG = "SavingFileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_file);
    }

    public void getInternalStoragePath(View v){
        TextView tv_internal_storage = (TextView)findViewById(R.id.tv_internal_storage);
        tv_internal_storage.setText(getFilesDir().toString());
    }

    public void getCachePath(View v){
        TextView tv_cache_storage = (TextView)findViewById(R.id.tv_cache_storage);
        tv_cache_storage.setText(getCacheDir().toString());
    }
    public void getExternalStoragePath(View v){
        TextView tv_external_storage = (TextView)findViewById(R.id.tv_external_storage);
        //DIRECTORY_RINGTONES 表示文件就会被系统的media scanner认为是ringtone而不是音乐
        tv_external_storage.setText(getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString());
    }
    public void createFile(View v){
        String filename = "test";
        File file = new File(getFilesDir(), filename);
    }
    public void writeInternalStorageFile(View v){
        String filename = "myfile";
        String string = "Hello world!";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(View v){
        String filename = "myfile";
        //方法一
//        File myFile = new File(getFilesDir(), filename);
//        myFile.delete();
        //方法二
        deleteFile(getFilesDir().toString() + File.separator + filename);
    }

    public void getExternalStorageState(View v){
        isExternalStorageWritable();
        isExternalStorageReadable();
    }

    public void savePublicFile(View v){
        getAlbumStorageDir("test");
    }

    public void savePrivateFile(View v){
        getAlbumStorageDir(this, "test");
    }

    public void getSpace(View v){
        File file = new File(Environment.getRootDirectory().toString());
        long freeSpace = file.getFreeSpace();
        long totalSpace = file.getTotalSpace();
        Log.i(LOG_TAG, "totalSpace = " + totalSpace + ";" + "freeSpace = " + freeSpace);
    }

    //获取常用路径，不同品牌手机的路径可能不会一样
    public void getPath(View v){
        Log.i(LOG_TAG, "getFilesDir = " + getFilesDir());
        Log.i(LOG_TAG, "getExternalFilesDir = " + getExternalFilesDir("exter_test").getAbsolutePath());
        Log.i(LOG_TAG, "getDownloadCacheDirectory = " + Environment.getDownloadCacheDirectory().getAbsolutePath());
        Log.i(LOG_TAG, "getDataDirectory = " + Environment.getDataDirectory().getAbsolutePath());
        Log.i(LOG_TAG, "getExternalStorageDirectory = " + Environment.getExternalStorageDirectory().getAbsolutePath());
        Log.i(LOG_TAG, "getExternalStoragePublicDirectory = " + Environment.getExternalStoragePublicDirectory("pub_test"));
        Log.i(LOG_TAG, "getRootDirectory = " + Environment.getRootDirectory().toString());
    }


    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }

    public File getAlbumStorageDir(Context context, String albumName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }

}
