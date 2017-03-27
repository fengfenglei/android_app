package com.mhl.shop.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 作者：lff
 * 时间；2016-11-17 11:30
 * 描述：倒计时工具类
 */
public class CountDownTimerUtils extends CountDownTimer
{
    public static final int	TIME_COUNT	= 60000;	// 倒计时60s
    private TextView btn;
    private String			endStrRid;

    /***
     * 参数 millisInFuture 倒计时总时间（如60S，120s等）* 参数 countDownInterval 渐变时间（每次倒计1s） *
     * 参数 btn 点击的按钮(因为Button是TextView子类，为了通用我的参数设置为TextView） * 参数 endStrRid
     * 倒计时结束后，按钮对应显示的文字
     */
    public CountDownTimerUtils(long millisInFuture, long countDownInterval, TextView btn, String endStrRid)
    {
        super(millisInFuture, countDownInterval);
        this.btn = btn;
        this.endStrRid = endStrRid;
    }

    public CountDownTimerUtils(TextView btn, String endStrRid)
    {
        super(TIME_COUNT, 1000);
        this.btn = btn;
        this.endStrRid = endStrRid;
    }

    // 计时完毕时触发
    @Override
    public void onFinish()
    {
        btn.setText(endStrRid);
        btn.setEnabled(true);
    }

    // 计时过程显示
    @Override
    public void onTick(long millisUntilFinished)
    {

        btn.setEnabled(false);
        btn.setText(millisUntilFinished / 1000 + "s后重新获取");
    }

    public void countDownTimeFinish(String endCode){
        btn.setText(endCode);
        btn.setEnabled(true);
    }

}

