package com.dusja.hw3;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.dusja.hw3.MainActivity.FILE_NAME;

/**
 * Created by Дарья on 30.11.2016.
 */

public class PicLoader extends Service implements Runnable {
    private static final String TAG = SimpleService.class.getSimpleName();
    private final String PIC_URL = "https://pp.vk.me/c638120/v638120162/ecda/DF_DewHl2Uc.jpg";

    private final File file;

    PicLoader(File file){
        this.file = file;
    }


    @Override
    public void run() {
        Log.d(TAG, "Start loading");
        InputStream in = null;
        FileOutputStream out = null;
        try {
            URL url = new URL(PIC_URL);
            in = new BufferedInputStream(url.openStream());
            out = new FileOutputStream(file);
            int cnt;
            byte[] buff = new byte[1024];
            while ((cnt = in.read(buff)) != -1) {
                out.write(buff, 0, cnt);
            }
            sendBroadcast(new Intent(MainActivity.BROADCAST));
        } catch (IOException e) {
            e.printStackTrace();
            file.delete();
        } finally {
            try {
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "Finnish loading");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
