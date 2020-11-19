package com.raykaco.android.customcontrol;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.ImageView;


public class Loading extends ImageView {

    private Paint            sandPaint;
    private Paint            LinePaint;
    private int              xFrist        = 120;
    private int              yFrist        = 190;
    private int              tRows         = 250;
    private int              tColumn       = 270;
    private int              Cascade       = 100;
    private int              eachSand      = 10;
    private int              bRows         = 0;
    private int              Roof          = 150;
    private int              finishCascade = 330;
    private int              countEachRow  = 0;
    private int              yu            = 445;
    private boolean          tru           = true;
    private static final int STROKE_WIDTH  = 8;


    /*
     * Designed by J. Babaki
     * 1394/04/16
     */

    public Loading(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }


    public Loading(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }


    public Loading(Context context) {
        super(context);
        initialize(context);
    }


    private void initialize(Context context) {

        sandPaint = new Paint();
        // sandPaint.setColor(Color.MAGENTA);
        sandPaint.setColor(Color.parseColor("#689E7C"));
        sandPaint.setAntiAlias(true);
        sandPaint.setStrokeWidth(STROKE_WIDTH);
        sandPaint.setStyle(Style.STROKE);

        LinePaint = new Paint();
        LinePaint.setColor(Color.parseColor("#626F80"));
        LinePaint.setAntiAlias(true);
        LinePaint.setStrokeWidth(STROKE_WIDTH);
        LinePaint.setStyle(Style.STROKE);

    }

}
