package com.ipalma.loaderlib;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;


public class LoaderImageView extends ImageView implements LoaderView {

    private LoaderController loaderController;
    private ColorStateList color;

    public LoaderImageView(Context context) {
        super(context);
        init(null);
    }

    public LoaderImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LoaderImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoaderImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LoaderView, 0, 0);
        color = typedArray.getColorStateList(R.styleable.LoaderView_loader_color);
        loaderController = new LoaderController(this);
        loaderController.setUseGradient(typedArray.getBoolean(R.styleable.LoaderView_use_gradient, LoaderConstant.USE_GRADIENT_DEFAULT));
        typedArray.recycle();
    }

    public void resetLoader() {
        if (getDrawable() != null) {
            super.setImageDrawable(null);
            loaderController.startLoading();
        }
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        loaderController.onSizeChanged();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        loaderController.onDraw(canvas);
    }

    @Override
    public void setRectColor(Paint rectPaint) {
        rectPaint.setColor(color != null ? color.getDefaultColor() : LoaderConstant.COLOR_DEFAULT_GREY);
    }

    @Override
    public boolean valueSet() {
        return (getDrawable() != null);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        loaderController.stopLoading();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        loaderController.stopLoading();
    }

    @Override
    public void setImageIcon(Icon icon) {
        super.setImageIcon(icon);
        loaderController.stopLoading();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        loaderController.stopLoading();
    }
}

