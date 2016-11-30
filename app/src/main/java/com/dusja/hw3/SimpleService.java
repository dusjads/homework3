package com.dusja.hw3;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;


/**
 * Created by Дарья on 30.11.2016.
 */

public class SimpleService extends Service {

    private static final String TAG = SimpleService.class.getSimpleName();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        File f = new File(getFilesDir(), MainActivity.FILE_NAME);
        if (!f.exists()) {
            PicLoader picLoader = new PicLoader(f);
            new Thread(picLoader).start();
        }
        return START_STICKY;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}