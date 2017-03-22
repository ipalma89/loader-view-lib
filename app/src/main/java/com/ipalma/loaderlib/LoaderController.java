package com.ipalma.loaderlib;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.animation.LinearInterpolator;


class LoaderController {

    private LoaderView loaderView;
    private Paint rectPaint;
    private LinearGradient linearGradient;
    private float progress;
    private ValueAnimator valueAnimator;
    private float widthWeight = LoaderConstant.MAX_WEIGHT;
    private float heightWeight = LoaderConstant.MAX_WEIGHT;
    private boolean useGradient = LoaderConstant.USE_GRADIENT_DEFAULT;

    private final static int MAX_COLOR_CONSTANT_VALUE = 255;
    private final static int ANIMATION_CYCLE_DURATION = 750;
    private final static int ANIMATION_CANCEL_DURATION = 250;

    public LoaderController(LoaderView view) {
        loaderView = view;
        init();
    }

    private void init() {
        rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        loaderView.setRectColor(rectPaint);
        setValueAnimator(0.5f, 1, ObjectAnimator.INFINITE, false);
    }

    public void onDraw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        float margin_height = height * (1 - heightWeight) / 2;
        rectPaint.setAlpha((int) (progress * MAX_COLOR_CONSTANT_VALUE));

        if (useGradient) {
            prepareGradient(width * widthWeight);
        }

        if (loaderView instanceof LoaderCircleImageView) {
            float circleSize = width / 2;
            canvas.drawCircle(circleSize, circleSize, circleSize, rectPaint);
        } else {
            canvas.drawRect(0, margin_height, width * widthWeight, height - margin_height, rectPaint);
        }
    }

    public void onSizeChanged() {
        linearGradient = null;
        startLoading();
    }

    private void prepareGradient(float width) {
        if (linearGradient == null) {
            linearGradient = new LinearGradient(0, 0, width, 0, rectPaint.getColor(),
                    LoaderConstant.COLOR_DEFAULT_GRADIENT, Shader.TileMode.MIRROR);
        }
        rectPaint.setShader(linearGradient);
    }

    public void startLoading() {
        if (valueAnimator != null && !loaderView.valueSet()) {
            valueAnimator.cancel();
            init();
            valueAnimator.start();
        }
    }

    public void setHeightWeight(float heightWeight) {
        this.heightWeight = validateWeight(heightWeight);
    }

    public void setWidthWeight(float widthWeight) {
        this.widthWeight = validateWeight(widthWeight);
    }

    public void setUseGradient(boolean useGradient) {
        this.useGradient = useGradient;
    }

    private float validateWeight(float weight) {
        return weight > LoaderConstant.MAX_WEIGHT ? LoaderConstant.MAX_WEIGHT :
                weight < LoaderConstant.MIN_WEIGHT ? LoaderConstant.MIN_WEIGHT : weight;
    }

    public void stopLoading() {
        valueAnimator.cancel();
        setValueAnimator(progress, 0, 0, true);
        valueAnimator.start();
    }

    private void setValueAnimator(float begin, float end, int repeatCount, boolean isCancel) {
        valueAnimator = ValueAnimator.ofFloat(begin, end);
        valueAnimator.setRepeatCount(repeatCount);
        valueAnimator.setDuration(isCancel ? ANIMATION_CANCEL_DURATION : ANIMATION_CYCLE_DURATION);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (float) animation.getAnimatedValue();
                loaderView.invalidate();
            }
        });
    }
}


