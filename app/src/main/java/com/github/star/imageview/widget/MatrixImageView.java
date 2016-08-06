package com.github.star.imageview.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by xiongxingxing on 16/8/6.
 * @desc support scale imageView
 */

public class MatrixImageView extends ImageView {
    private Matrix mTempMatrix = new Matrix();
    private Matrix mCurrentMatrix = new Matrix();
    private float[] mTempValues = new float[9];
    private ImageView.ScaleType mScaleType = ScaleType.MATRIX;

    public MatrixImageView(Context context) {
        this(context, null);
    }

    public MatrixImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MatrixImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        mScaleType = getScaleType();
        if (mScaleType != getScaleType()) {
            setScaleType(mScaleType);
        }
    }

    public void update() {
        if (!hasDrawable()) {
            return;
        }
        mCurrentMatrix.postTranslate((getImageWidth() - getDrawableWidth()) / 2, (getImageHeight() - getDrawableHeight()) / 2);
        mTempMatrix.set(mCurrentMatrix);
        setImageMatrix(mCurrentMatrix);
    }

    private boolean hasDrawable() {
        if (getDrawable() != null && getDrawable() instanceof BitmapDrawable) {
            return true;
        }
        return false;
    }

    private float getImageWidth() {
        return getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    private float getImageHeight() {
        return getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
    }

    private float getDrawableWidth() {
        return getDrawable().getIntrinsicWidth();
    }

    private float getDrawableHeight() {
        return getDrawable().getIntrinsicHeight();
    }

}
