package com.example.building.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.example.building.R;

public class FourRoundView extends View {
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private float mStrokeWidth;
    private float mLineWidth;
    private int mLineColor;
    private float rx;
    private float ry;
    private RectF rectF;

    public FourRoundView(Context context) {
        this(context, null);
    }

    public FourRoundView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FourRoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FourRoundView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FourRoundView);
        mLineWidth = ta.getDimension(R.styleable.FourRoundView_lineWidth, dip2px(10));
        mStrokeWidth = ta.getDimension(R.styleable.FourRoundView_strokeWidth, dip2px(10));
        mLineColor = ta.getColor(R.styleable.FourRoundView_lineColor, Color.BLACK);
        ta.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mLineColor);
        mPaint.setStrokeWidth(mStrokeWidth);
        rx = 10;
        ry = 10;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        rectF = new RectF(mWidth / 2F - 50F, mHeight / 2F - 50F, mWidth / 2F + 50F, mHeight / 2F + 50F);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 左上角
        canvas.drawLine(0, mStrokeWidth / 2F, mLineWidth, mStrokeWidth / 2F, mPaint);
        canvas.drawLine(mStrokeWidth / 2F, mStrokeWidth / 2F, mStrokeWidth / 2F, mLineWidth, mPaint);
        // 左下角
        canvas.drawLine(mStrokeWidth / 2F, mHeight - mLineWidth, mStrokeWidth / 2F, mHeight, mPaint);
        canvas.drawLine(mStrokeWidth / 2F, mHeight - mStrokeWidth / 2F, mLineWidth, mHeight - mStrokeWidth / 2F, mPaint);
        // 右上角
        canvas.drawLine(mWidth - mLineWidth, mStrokeWidth / 2F, mWidth, mStrokeWidth / 2F, mPaint);
        canvas.drawLine(mWidth - mStrokeWidth / 2F, mStrokeWidth / 2F, mWidth - mStrokeWidth / 2F, mLineWidth, mPaint);
        // 右下角
        canvas.drawLine(mWidth - mStrokeWidth / 2F, mHeight - mLineWidth, mWidth - mStrokeWidth / 2F, mHeight, mPaint);
        canvas.drawLine(mWidth - mLineWidth, mHeight - mStrokeWidth / 2F, mWidth, mHeight - mStrokeWidth / 2F, mPaint);
    }

    public void setHeight(int height){
        this.setHeight(height);
    }

    public int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
