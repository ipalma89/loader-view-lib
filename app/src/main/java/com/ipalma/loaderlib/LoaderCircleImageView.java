package com.ipalma.loaderlib;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

public class LoaderCircleImageView extends LoaderImageView {

    public LoaderCircleImageView(Context context) {
        super(context);
    }

    public LoaderCircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoaderCircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoaderCircleImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}

