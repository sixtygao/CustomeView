package com.murphy.china.testannonation.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.murphy.china.testannonation.R;

/**
 * Created by hope on 2016/11/7.
 */

public class MyView extends View {


    private int width;
    private int height;
    private Paint mPaint;
    private Paint mCpaint;
    private int mRadius = 150;
    private Paint mHpaint;
    private Paint mMpaint;
    private int mgap = 20;
    private Rect mrect = new Rect();

    private int mSdegree = 0;//秒钟起始角度为0
    private int mMdegree = 0;//分钟起始角度为0

    private Paint.FontMetrics mFontMetrics = new Paint.FontMetrics();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mSdegree = (mSdegree + 6) % 360;
            if(mSdegree ==0) {
                mMdegree = (mMdegree + 6)%360;
            }
            invalidate();
            mHandler.sendEmptyMessageDelayed(0, 1000);
        }
    };

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyView);
        array.recycle();
        init(context);
    }

    public void init(Context context) {
        mPaint = new Paint();
        mPaint.setStrokeWidth(dpTopx(context, 2));
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#ff0000"));

        mCpaint = new Paint();
        mCpaint.setStrokeWidth(dpTopx(context, 5));
        mCpaint.setAntiAlias(true);
        mCpaint.setStyle(Paint.Style.FILL);
        mCpaint.setColor(Color.parseColor("#ff0000"));


        mHpaint = new Paint();
        mHpaint.setStrokeWidth(dpTopx(context, 2));
        mHpaint.setAntiAlias(true);
        mHpaint.setTextSize(dpTopx(context,16));
        mHpaint.setStyle(Paint.Style.FILL);
        mHpaint.setColor(Color.parseColor("#000000"));
        mHpaint.getFontMetrics(mFontMetrics);


        mMpaint = new Paint();
        mMpaint.setStrokeWidth(dpTopx(context, 1));
        mMpaint.setAntiAlias(true);
        mMpaint.setStyle(Paint.Style.FILL);
        mMpaint.setColor(Color.parseColor("#e0e0e0"));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mRadius * 2 + 10, mRadius * 2 + 10);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("TAG", "w=" + w + "  h=" + h);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(width / 2, height / 2);
        canvas.drawCircle(0, 0, 10, mCpaint);
        canvas.drawCircle(0, 0, mRadius, mPaint);
        drawLine(canvas);
        drawSeccondPoint(canvas);
        drawMinutePoint(canvas);
        canvas.restore();
    }


    private void drawLine(Canvas canvas) {
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) { //时钟刻度
                canvas.drawLine(0, -mRadius, 0, -mRadius + 25, mHpaint);
                //画时间值
                if(i%15==0) {
                    drawTime(canvas,i/5==0?"12":String.valueOf(i/5),-i/5*30);
                }
            } else { //分钟刻度
                canvas.drawLine(0, -mRadius, 0, -mRadius + 10, mMpaint);
            }
            canvas.rotate(6);
        }
    }

    private void drawTime(Canvas canvas, String i ,int degree) {
        canvas.save();
        canvas.translate(0, -mRadius + 30 + mgap);
        canvas.rotate(degree);
        mHpaint.getTextBounds(i, 0, i.length(), mrect);
        switch (degree) {
            case 0:
                canvas.drawText("" + i, 0 - mrect.width() / 2 , 0, mHpaint);
                break;
            case -90:
                canvas.drawText("" + i, 0 ,mrect.width() / 2, mHpaint);
                break;
            case -180 :
                canvas.drawText("" + i, 0 - mrect.width() / 2 , mrect.height() / 2, mHpaint);
                break;
            case -270 :
                canvas.drawText("" + i,-mrect.width() / 2 , mrect.width() / 2, mHpaint);
                break;
        }
        canvas.restore();
    }

    /**
     * 画秒钟指针
     *
     * @param canvas
     */
    private void drawSeccondPoint(Canvas canvas) {
        canvas.save();
        canvas.rotate(mSdegree);
        canvas.drawLine(0, 30, 0, -mRadius + 30 + mgap + 5, mPaint);
        canvas.restore();
    }

    /**
     * 画分钟指针
     *
     * @param canvas
     */
    private void drawMinutePoint(Canvas canvas) {
        canvas.save();
        canvas.rotate(mMdegree);
        canvas.drawLine(0, 30, 0, -mRadius + 30 + mgap + 30, mHpaint);
        canvas.restore();
    }

    public static int dpTopx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int pxTodp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public void start() {
        mHandler.sendEmptyMessage(0);
    }

    public void stop() {
        mHandler.removeMessages(0);
    }
}
