package com.github.star.imageview.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by xiongxingxing on 16/8/6.
 * @desc support scale imageView
 */

public class MatrixImageView extends ImageView implements View.OnTouchListener{
    private Matrix mTempMatrix = new Matrix();
    private Matrix mCurrentMatrix = new Matrix();
    private float[] mTempValues = new float[9];
    private ImageView.ScaleType mScaleType = ScaleType.MATRIX;
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;
    private static final float MIN_SCALE = 0.5f;
    private static final float DEFAULT_SCALE = 1f;
    private static final float MID_SCALE = 1.75f;
    private static final float MAX_SCALE = 3f;
    private float mMinScale = MIN_SCALE;
    private float mDefaultScale = DEFAULT_SCALE;
    private float mMidScale = MID_SCALE;
    private float mMaxScale = MAX_SCALE;
    private static final String TAG = "MatrixImageView";

    @NonNull
    private GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            float scale = getScale(mCurrentMatrix);
            float scaleFactor = 0;
            if (scale < mMidScale) {
                scaleFactor = mMidScale / scale;
            } else if (scale < mMaxScale) {
                scaleFactor = mMaxScale / scale;
            } else if (scale >= mMaxScale) {
                scaleFactor = mDefaultScale / scale;
            }
            mCurrentMatrix.postScale(scaleFactor, scaleFactor, getImageWidth() / 2, getImageHeight() / 2);
            setImageMatrix(mCurrentMatrix);
            return true;
        }
    };
    @NonNull
    private ScaleGestureDetector.SimpleOnScaleGestureListener mSimpleOnScaleGestureListener = new ScaleGestureDetector.SimpleOnScaleGestureListener() {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            return true;
        }
    };

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
        if (mScaleType != getScaleType()) {
            setScaleType(mScaleType);
        }
        mScaleGestureDetector = new ScaleGestureDetector(context, mSimpleOnScaleGestureListener);
        mGestureDetector = new GestureDetector(context, mSimpleOnGestureListener);
        setOnTouchListener(this);
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

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        boolean flag = mGestureDetector.onTouchEvent(event);
        flag |= mScaleGestureDetector.onTouchEvent(event);
        return flag;
    }

    private float getScale(Matrix matrix) {
        return (float) Math.hypot(getValue(matrix, Matrix.MSCALE_X), getValue(matrix, Matrix.MSCALE_Y));
    }

    private float getValue(Matrix matrix, int whichValue) {
        matrix.getValues(mTempValues);
        return mTempValues[whichValue];
    }
}
