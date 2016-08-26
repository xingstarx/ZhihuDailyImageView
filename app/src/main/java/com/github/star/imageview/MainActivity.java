package com.github.star.imageview;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

        init();
    }

    private void init() {
        Matrix matrix = new Matrix();
        Matrix matrix2 = new Matrix();

        float[] values = new float[]{1.2f, 0, 30, 0, 1.2f, 30, 0, 0, 1};
        float[] values2 = new float[]{1.2f, 0, 0, 0, 1.2f, 0, 0, 0, 1};

        matrix.setValues(values);
        matrix2.setValues(values2);

        printValues(matrix);

        boolean flag = matrix.preScale(1.2f, 1.2f);

        Log.e(TAG, "***************** flag == " + flag);
        printValues(matrix);

    }


    public void printValues(Matrix matrix) {
        float[] values = new float[9];
        matrix.getValues(values);
        StringBuilder sbf = new StringBuilder();
        sbf.append("{ ");
        for (int i = 0; i < values.length; i++) {
            sbf.append(values[i]);
            if (i != values.length -1) {
                sbf.append(", ");
            }
        }
        sbf.append(" }");
        Log.e(TAG, "matrix == " + sbf.toString());
    }

}
