package com.ipalma.loaderlib;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int WAIT_DURATION = 3500;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();
        loadText();
    }

    private void loadText() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((TextView)findViewById(R.id.txt_name)).setText("Test Name text");
                ((TextView)findViewById(R.id.txt_name2)).setText("Test Name text 2");
                ((TextView)findViewById(R.id.txt_name3)).setText("Test Name text 3");
                ((ImageView)findViewById(R.id.image_icon)).setImageResource(R.mipmap.ic_launcher);
                ((ImageView)findViewById(R.id.image_icon2)).setImageResource(R.mipmap.ic_launcher);
            }
        }, WAIT_DURATION);

    }

    public void reset(View v) {
        handler.removeCallbacksAndMessages(null);
        ((LoaderTextView)findViewById(R.id.txt_name)).resetLoader();
        ((LoaderTextView)findViewById(R.id.txt_name2)).resetLoader();
        ((LoaderTextView)findViewById(R.id.txt_name3)).resetLoader();
        ((LoaderImageView)findViewById(R.id.image_icon)).resetLoader();
        ((LoaderCircleImageView)findViewById(R.id.image_icon2)).resetLoader();
        loadText();
    }
}
