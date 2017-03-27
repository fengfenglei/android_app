package com.mhl.shop.homepage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.mhl.shop.R;

/**
 * 作者：Administrator
 * 时间；2016-11-11 17:42
 * 描述：
 */
public class FirstSetpView extends  View{

    //	private Bitmap goods;
    private Bitmap people;
    private Bitmap peopleWithGoods;
    private int measuredWidth;
    private int measuredHeight;
    private float mCurrentProgress;
    private int mCurrentAlpha;
    private Paint mPaint;
    private Bitmap scaledPeople;
    private Bitmap scaledGoods;
    public FirstSetpView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FirstSetpView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FirstSetpView(Context context) {
        super(context);
        init();
    }
    private void init(){
        //快递小哥bitmap
        people = BitmapFactory.decodeResource(getResources(), R.drawable.loading_animation_01);
        //这是后面动画中的最后一张图片，拿这张图片的作用是用它的宽高来测量
        //我们这个自定义View的宽高
        peopleWithGoods = BitmapFactory.decodeResource(getResources(), R.drawable.loading_animation_01);
        //来个画笔，我们注意到快递小哥和包裹都有一个渐变效果的，我们用
        //mPaint.setAlpha来实现这个渐变的效果
        mPaint = new Paint();
        //首先设置为完全透明
        mPaint.setAlpha(0);
    }

    /**
     * 测量方法
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }
    //测量宽度
    private int measureWidth(int widthMeasureSpec){
        int result = 0;
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if (MeasureSpec.EXACTLY == mode) {
            result = size;
        }else {
            result = peopleWithGoods.getWidth();
            if (MeasureSpec.AT_MOST == mode) {
                result = Math.min(result, size);
            }
        }
        return result;
    }
    //测量高度
    private int measureHeight(int heightMeasureSpec){
        int result = 0;
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        if (MeasureSpec.EXACTLY == mode) {
            result = size;
        }else {
            result = peopleWithGoods.getHeight();
            if (MeasureSpec.AT_MOST == mode) {
                result = Math.min(result, size);
            }
        }
        return result;
    }
    //在这里面拿到测量后的宽和高，w就是测量后的宽，h是测量后的高
    //
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        measuredWidth = w;
        measuredHeight = h;
        //根据测量后的宽高来对快递小哥做一个缩放
        scaledPeople = Bitmap.createScaledBitmap(people,measuredWidth,measuredHeight,true);
    }

    /**
     * 绘制方法
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //save
        //画小哥
        //restore
        canvas.save();
        canvas.scale(mCurrentProgress, mCurrentProgress , 0 , measuredHeight/2);
        mPaint.setAlpha(mCurrentAlpha);
        canvas.drawBitmap(scaledPeople, 0,0,mPaint);
        canvas.restore();
    }

    /**
     * 根据进度来对小哥和包裹进行缩放
     * @param currentProgress
     */
    public void setCurrentProgress(float currentProgress){
        this.mCurrentProgress = currentProgress;
        this.mCurrentAlpha = (int) (currentProgress*255);
    }
}

