package com.github.star.imageview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private ImageView mImageView;
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;
    @NonNull
    private GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.image_view);
        mScaleGestureDetector = new ScaleGestureDetector(this, mSimpleOnScaleGestureListener);
        mGestureDetector = new GestureDetector(this, mSimpleOnGestureListener);
        mImageView.setScaleType(ImageView.ScaleType.MATRIX);
        mTempMatrix = mImageView.getImageMatrix();
        mImageView.setOnTouchListener(this);
        Picasso.with(this)
                .load("http://pic4.zhimg.com/70/f3bfac899b2e83f2f7c5aaa9814814f3_b.jpg")
                .fit().centerInside()
                .into(mImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        update();
                    }

                    @Override
                    public void onError() {
                    }
                });
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        boolean flag = mGestureDetector.onTouchEvent(event);
        flag |= mScaleGestureDetector.onTouchEvent(event);
        return flag;
    }
    public void update() {
        if (!hasDrawable()) {
            return;
        }
        mCurrentMatrix.postTranslate((getImageWidth() - getDrawableWidth()) / 2, (getImageHeight() - getDrawableHeight()) / 2);
        mTempMatrix.set(mCurrentMatrix);
        mImageView.setImageMatrix(mCurrentMatrix);
    }

    private boolean hasDrawable() {
        if (mImageView != null && mImageView.getDrawable() != null && mImageView.getDrawable() instanceof BitmapDrawable) {
            return true;
        }
        return false;
    }

    private float getImageWidth() {
        return mImageView.getMeasuredWidth() - mImageView.getPaddingLeft() - mImageView.getPaddingRight();
    }

    private float getImageHeight() {
        return mImageView.getMeasuredHeight() - mImageView.getPaddingTop() - mImageView.getPaddingBottom();
    }

    private float getDrawableWidth() {
        return mImageView.getDrawable().getIntrinsicWidth();
    }

    private float getDrawableHeight() {
        return mImageView.getDrawable().getIntrinsicHeight();
    }

}
