package com.mhl.shop.wheel;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mhl.shop.R;

/**
 * 作者：Administrator
 * 时间；2016-11-16 18:15
 * 描述：
 */
public class DialogView extends Dialog implements View.OnClickListener
{
    private String				msg;
    private TextView tv;
    private Button cancel;
    private Button				confirm;
    private OnYesButtonListener	onYesButtonListener;
    private OnNoButtonListener	onNoButtonListener;
    public OnYesButtonListener getOnYesButtonListener()
    {
        return onYesButtonListener;
    }
    public OnNoButtonListener getOnNoButtonListener()
    {
        return onNoButtonListener;
    }
    public void setOnYesButtonListener(OnYesButtonListener onYesButtonListener)
    {
        this.onYesButtonListener = onYesButtonListener;
    }
    public void setOnNoButtonListener(OnNoButtonListener onNoButtonListener)
    {
        this.onNoButtonListener = onNoButtonListener;
    }
    public DialogView(Context context, int theme, String msg, OnYesButtonListener onYesButtonListener, OnNoButtonListener onNoButtonListener) {
        super(context, theme);
        this.msg = msg;
        this.onYesButtonListener = onYesButtonListener;
        this.onNoButtonListener = onNoButtonListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setCancelable(true);
        // setStyle(STYLE_NO_TITLE, 0);
        setContentView(R.layout.delete_img_dialog);
        // getDialog().getWindow().setBackgroundDrawable(new
        // ColorDrawable(Color.TRANSPARENT));
        tv = (TextView) findViewById(R.id.dialog_tv);
        cancel = (Button) findViewById(R.id.bt_cancle);
        confirm = (Button) findViewById(R.id.bt_ok);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
        tv.setText(msg);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bt_cancle:

                if (onNoButtonListener != null)
                {
                    onNoButtonListener.NoButtonListener();
                    dismiss();
                }
                else
                {
                    dismiss();
                }
                break;
            case R.id.bt_ok:
                if (onYesButtonListener != null)
                {
                    onYesButtonListener.YesButtonListener();
                    dismiss();
                }
                break;

        }

    }

    /**
     * 确定按钮监听器
     */

    public interface OnYesButtonListener
    {
        void YesButtonListener();
    }
    public interface OnNoButtonListener
    {
        void NoButtonListener();
    }
}


