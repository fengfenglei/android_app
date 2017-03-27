package com.mhl.shop.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mhl.shop.R;
import com.mhl.shop.utils.Constant;

/**
 * 作者：Administrator
 * 时间；2016-11-16 10:30
 * 描述：
 */
public class CDialog_load extends Dialog{
    private String 		m_strMessage 	= "正在加载...";
    private Context		m_context 		= null;
    // =======================================================================================
    public CDialog_load(Context v_context,String msg){
        super(v_context, R.style.LoadDialog);
        m_context = v_context;
        if (!msg.equals("")) {
            m_strMessage = msg;
        }
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        setContentView(getContentView());
    }
    private View getContentView(){
        // ======设置主界面  ======
        RelativeLayout layout_main = new RelativeLayout(m_context);
        RelativeLayout.LayoutParams lp_mainView = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp_mainView.leftMargin = (int)(20* Constant.REDIO_SCREEN);
        lp_mainView.rightMargin = (int)(20*Constant.REDIO_SCREEN);
        lp_mainView.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout_main.setGravity(Gravity.CENTER);
        layout_main.setLayoutParams(lp_mainView);
        //---- 加载框 ----
        LinearLayout layout_loadView = new LinearLayout(m_context);
        int padding_loadView = (int)(30.0f*Constant.REDIO_SCREEN);
        layout_loadView.setBackgroundResource(R.drawable.bg_exit);
        layout_loadView.getBackground().setAlpha(100);
        layout_loadView.setGravity(Gravity.CENTER);

        RelativeLayout.LayoutParams lp_loadView = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp_loadView.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout_loadView.setLayoutParams(lp_loadView);
        layout_loadView.setPadding(padding_loadView, padding_loadView/2, padding_loadView, padding_loadView/2);
        layout_main.addView(layout_loadView);
        layout_main.setGravity(Gravity.CENTER);

        // ----转动圈----
        int width_bar = (int)(70*Constant.REDIO_SCREEN);
        ProgressBar progressBar = new ProgressBar(m_context);
        progressBar.setBackgroundColor(Color.TRANSPARENT);
        progressBar.setIndeterminateDrawable(((Activity)m_context).getResources().getDrawable(R.drawable.mhl_loading_anim));  //动画
        LinearLayout.LayoutParams lp_bar = new LinearLayout.LayoutParams(width_bar, width_bar);
        layout_loadView.addView(progressBar, lp_bar);

        // ---- 文字 ----
        int margin_left = (int)(10*Constant.REDIO_SCREEN);
        float fFontSize_message = 20.0f*Constant.REDIO_SCREEN;
        TextView textView_message = new TextView(m_context);
        textView_message.setTextSize(TypedValue.COMPLEX_UNIT_PX, fFontSize_message);
        textView_message.setGravity(Gravity.LEFT);
        textView_message.setBackgroundColor(Color.TRANSPARENT);
        textView_message.setTextColor(Color.rgb(97, 166, 146));
        textView_message.setSingleLine(false);
        textView_message.setText(m_strMessage);
        LinearLayout.LayoutParams lp_tv = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp_tv.leftMargin = margin_left;
        layout_loadView.addView( textView_message, lp_tv);
        return layout_main;
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
//        if (!hasFocus) {
//            dismiss();
//        }
    }
}

