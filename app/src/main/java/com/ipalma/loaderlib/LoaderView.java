package com.ipalma.loaderlib;

import android.graphics.Paint;

interface LoaderView {
    void setRectColor(Paint rectPaint);

    void invalidate();

    boolean valueSet();
}

