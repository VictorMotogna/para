package com.example.victormotogna.para.utils.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.example.victormotogna.para.R;

/**
 * Created by victormotogna on 11/8/17.
 */

public class ImageViewRoundCorners extends android.support.v7.widget.AppCompatImageView {

    public ImageViewRoundCorners(Context context) {
        super(context);
    }

    public ImageViewRoundCorners(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewRoundCorners(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int radius = (int) getResources().getDimension(R.dimen.corner_radius_small);

        Path path = new Path();
        path.addRoundRect(new RectF(0, 0, getWidth(), getHeight()), radius, radius, Path.Direction.CW);

        canvas.clipPath(path);

        super.onDraw(canvas);
    }
}
