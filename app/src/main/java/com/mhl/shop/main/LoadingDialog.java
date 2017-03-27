package com.mhl.shop.main;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.TextView;

import com.mhl.shop.R;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import static com.mhl.shop.utils.UIUtils.getResources;

/**
 * 作者：Administrator
 * 时间；2016-11-16 11:49
 * 描述：
 */
public class LoadingDialog extends Dialog {
    private static final int CHANGE_TITLE_WHAT = 1;
    private static final int CHNAGE_TITLE_DELAYMILLIS = 300;
    private static final int MAX_SUFFIX_NUMBER = 3;
    private static final char SUFFIX = '.';


    private GifImageView iv_route;
    private TextView detail_tv;
    private TextView tv_point;


    private Handler handler = new Handler() {
        private int num = 0;


        public void handleMessage(android.os.Message msg) {
            if (msg.what == CHANGE_TITLE_WHAT) {
                StringBuilder builder = new StringBuilder();
                if (num >= MAX_SUFFIX_NUMBER) {
                    num = 0;
                }
                num++;
                for (int i = 0; i < num; i++) {
                    builder.append(SUFFIX);
                }
                tv_point.setText(builder.toString());
                if (isShowing()) {
                    handler.sendEmptyMessageDelayed(CHANGE_TITLE_WHAT, CHNAGE_TITLE_DELAYMILLIS);
                }
                else {
                    num = 0;
                }
            }
        };
    };


    public LoadingDialog(Context context) {
        super(context, R.style.LoadDialog);
        init();
    }


//    public LoadingDialog(Context context, boolean isTrans) {
//            super(context, isTrans ? R.style.LoadDialog : R.style.LoadDialog);
//        init();
//    }

    @SuppressWarnings("ResourceType")
    private void init() {
        setContentView(R.layout.common_dialog_loading_layout);
        iv_route = (GifImageView) findViewById(R.id.iv_route);
        detail_tv = (TextView) findViewById(R.id.detail_tv);
        tv_point = (TextView) findViewById(R.id.tv_point);
        try {
            GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.k);

        // gif1加载一个动态图gif
        iv_route.setImageDrawable(gifDrawable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getWindow().setWindowAnimations(R.anim.bottom_in);
    }





    @Override
    public void show() {//在要用到的地方调用这个方法
        handler.sendEmptyMessage(CHANGE_TITLE_WHAT);
        super.show();
    }


    @Override
    public void dismiss() {
        super.dismiss();
    }


    @Override
    public void setTitle(CharSequence title) {
        if (TextUtils.isEmpty(title)) {
            detail_tv.setText("正在加载");
        }
        else {
            detail_tv.setText(title);
        }
    }


    @Override
    public void setTitle(int titleId) {
        setTitle(getContext().getString(titleId));
    }


    public static void dismissDialog(LoadingDialog loadingDialog) {
        if (null == loadingDialog) { return; }
        loadingDialog.dismiss();
    }
}

