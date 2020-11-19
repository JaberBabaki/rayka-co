package com.raykaco.android.customcontrol;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.ImageView;


public class CustomLoadingIndicator extends ImageView {

    private Paint            circleOn;
    private Paint            circleOff;
    private Paint            textPaint;

    private static final int STROKE_WIDTH     = 2;

    private int              currentPageIndex = 0;


    public CustomLoadingIndicator(Context context) {
        super(context);
        initialize(context);
    }


    public CustomLoadingIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }


    public CustomLoadingIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }


    public void setProgress(int value) {
        postInvalidate();
    }


    public void setCurrentPage(int value) {
        currentPageIndex = value;
        postInvalidate();
    }


    private void initialize(Context context) {
        circleOn = new Paint();
        circleOn.setColor(Color.parseColor("#CB4D1F"));
        circleOn.setAntiAlias(true);
        circleOn.setStrokeWidth(STROKE_WIDTH);
        circleOn.setStyle(Style.FILL);

        circleOff = new Paint();
        circleOff.setColor(Color.parseColor("#CB4D1F"));
        circleOff.setAntiAlias(true);
        circleOff.setStrokeWidth(STROKE_WIDTH);
        circleOff.setStyle(Style.STROKE);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Align.CENTER);
        textPaint.setTextSize(30);
        textPaint.setStyle(Style.FILL_AND_STROKE);

        // ser();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (currentPageIndex == 0) {
            canvas.drawCircle((getWidth() / 2) - 45, getHeight() / 2, 8, circleOn);
            canvas.drawCircle((getWidth() / 2) - 10, getHeight() / 2, 8, circleOff);
            canvas.drawCircle((getWidth() / 2) + 25, getHeight() / 2, 8, circleOff);
            canvas.drawCircle((getWidth() / 2) + 60, getHeight() / 2, 8, circleOff);
        } else if (currentPageIndex == 1) {
            canvas.drawCircle((getWidth() / 2) - 45, getHeight() / 2, 8, circleOff);
            canvas.drawCircle((getWidth() / 2) - 10, getHeight() / 2, 8, circleOn);
            canvas.drawCircle((getWidth() / 2) + 25, getHeight() / 2, 8, circleOff);
            canvas.drawCircle((getWidth() / 2) + 60, getHeight() / 2, 8, circleOff);
        } else if (currentPageIndex == 2) {
            canvas.drawCircle((getWidth() / 2) - 45, getHeight() / 2, 8, circleOff);
            canvas.drawCircle((getWidth() / 2) - 10, getHeight() / 2, 8, circleOff);
            canvas.drawCircle((getWidth() / 2) + 25, getHeight() / 2, 8, circleOn);
            canvas.drawCircle((getWidth() / 2) + 60, getHeight() / 2, 8, circleOff);
        } else if (currentPageIndex == 3) {
            canvas.drawCircle((getWidth() / 2) - 45, getHeight() / 2, 8, circleOff);
            canvas.drawCircle((getWidth() / 2) - 10, getHeight() / 2, 8, circleOff);
            canvas.drawCircle((getWidth() / 2) + 25, getHeight() / 2, 8, circleOff);
            canvas.drawCircle((getWidth() / 2) + 60, getHeight() / 2, 8, circleOn);
        }

    }

}
