package com.ipalma.loaderlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text);
        loadText();
    }

    private void loadText() {
        textView.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("Hello Loading!!!");
            }
        }, 3500);
    }

    public void reset(View v) {
        ((LoaderTextView) textView).resetLoader();
        loadText();
    }
}
