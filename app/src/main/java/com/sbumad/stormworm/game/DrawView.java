package com.sbumad.stormworm.game;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * Created by John on 2/22/14.
 */
public class DrawView extends View {

    //for zooming in and out
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;

    // for moving the map
    float lastX;
    float lastY;
    float transX;
    float transY;

    public DrawView(Context context) {
        super(context);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        // Scale the picture
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);

        // Update the sprite manager
        MainActivity.getMain().getSpriteManager().update(canvas, transX, transY);

        // Restore scaling
        canvas.restore();

        // Paint it again!
        this.invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.
        mScaleDetector.onTouchEvent(ev);
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            lastX = ev.getX();
            lastY = ev.getY();
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE){
            float newX = ev.getX();
            float newY = ev.getY();
            transX += (newX - lastX);
            transY += (newY - lastY);
            lastX = newX;
            lastY = newY;
        }
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

            invalidate();
            return true;
        }
    }
}

