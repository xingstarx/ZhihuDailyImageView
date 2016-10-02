package com.github.star.imageview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.star.imageview.widget.MatrixImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private MatrixImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (MatrixImageView) findViewById(R.id.image_view);
        Picasso.with(this)
                .load("http://pic4.zhimg.com/70/f3bfac899b2e83f2f7c5aaa9814814f3_b.jpg")
                .placeholder(R.drawable.bg_default)
                .fit().centerInside()
                .into(mImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        mImageView.update();
                    }

                    @Override
                    public void onError() {
                    }
                });
    }
}
