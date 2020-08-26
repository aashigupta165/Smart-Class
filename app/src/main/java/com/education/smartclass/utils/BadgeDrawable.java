package com.education.smartclass.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.education.smartclass.R;

public class BadgeDrawable extends Drawable {

    private Paint mBadgePaint;
    private Paint mBadgePaint1;

    private Boolean mCount = false;
    private boolean mWillDraw;

    public BadgeDrawable(Context context) {

        mBadgePaint = new Paint();
        mBadgePaint.setColor(Color.RED);
        mBadgePaint.setAntiAlias(true);
        mBadgePaint.setStyle(Paint.Style.FILL);
        mBadgePaint1 = new Paint();
        mBadgePaint1.setColor(ContextCompat.getColor(context.getApplicationContext(), R.color.grey));
        mBadgePaint1.setAntiAlias(true);
        mBadgePaint1.setStyle(Paint.Style.FILL);
    }

    @Override
    public void draw(Canvas canvas) {

        if (!mWillDraw) {
            return;
        }

        Rect bounds = getBounds();

        float width = bounds.right - bounds.left;
        float height = bounds.bottom - bounds.top;
        float radius = ((Math.max(width, height) / 2)) / 2;
        float centerX = (width - radius - 1) + 5;
        float centerY = radius - 5;

        canvas.drawCircle(centerX, centerY, (int) (radius), mBadgePaint1);
        canvas.drawCircle(centerX, centerY, (int) (radius), mBadgePaint);

    }

    public void setCount(Boolean count) {
        mCount = count;
        mWillDraw = mCount;
        invalidateSelf();
    }

    @Override
    public void setAlpha(int alpha) {
        // do nothing
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        // do nothing
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}