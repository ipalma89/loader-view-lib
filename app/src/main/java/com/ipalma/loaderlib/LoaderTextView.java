package com.ipalma.loaderlib;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;


public class LoaderTextView extends TextView implements LoaderView {

    private LoaderController loaderController;
    private ColorStateList color;

    public LoaderTextView(Context context) {
        super(context);
        init(null);
    }

    public LoaderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LoaderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoaderTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LoaderView, 0, 0);
        color = typedArray.getColorStateList(R.styleable.LoaderView_loader_color);
        loaderController = new LoaderController(this);
        setWidthWeight(typedArray.getFloat(R.styleable.LoaderView_width_weight, LoaderConstant.MAX_WEIGHT));
        setHeightWeight(typedArray.getFloat(R.styleable.LoaderView_height_weight, LoaderConstant.MAX_WEIGHT));
        loaderController.setUseGradient(typedArray.getBoolean(R.styleable.LoaderView_use_gradient, LoaderConstant.USE_GRADIENT_DEFAULT));
        typedArray.recycle();
    }

    protected void setWidthWeight(float widthWeight) {
        loaderController.setWidthWeight(widthWeight);
    }

    protected void setHeightWeight(float heightWeight) {
        loaderController.setHeightWeight(heightWeight);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        loaderController.onSizeChanged();
    }

    public void resetLoader() {
        if (!TextUtils.isEmpty(getText())) {
            super.setText(null);
            loaderController.startLoading();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        loaderController.onDraw(canvas);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (loaderController != null) {
            loaderController.stopLoading();
        }
    }

    @Override
    public void setRectColor(Paint rectPaint) {
        final Typeface typeface = getTypeface();

        rectPaint.setColor(color != null ? color.getDefaultColor() :
                typeface != null && typeface.getStyle() == Typeface.BOLD ?
                        LoaderConstant.COLOR_DARKER_GREY : LoaderConstant.COLOR_DEFAULT_GREY);
    }

    @Override
    public boolean valueSet() {
        return !TextUtils.isEmpty(getText());
    }
}

