package com.dusja.hw3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static final String FILE_NAME = "pic.jpg";
    public static final String BROADCAST = "BROADCAST";
    private ImageView image;
    private TextView error;
    BroadcastReceiver broadcastReceiver, myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.picture);
        error = (TextView) findViewById(R.id.error_txt);
        showImage();
        myReceiver = new MyReceiver();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                showImage();
            }
        };

        registerReceiver(myReceiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
        registerReceiver(broadcastReceiver, new IntentFilter(BROADCAST));

    }

    private void showImage() {
        File f = new File(getApplicationContext().getFilesDir(), FILE_NAME);
        boolean isError = true;

        if (f.exists()) {
            image.setImageBitmap(BitmapFactory.decodeFile(f.getAbsolutePath()));
            isError = false;
        }

        image.setVisibility(View.INVISIBLE);
        error.setVisibility(View.INVISIBLE);

        if (!isError)
            image.setVisibility(View.VISIBLE);
        else
            error.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
        unregisterReceiver(broadcastReceiver);
    }

}
