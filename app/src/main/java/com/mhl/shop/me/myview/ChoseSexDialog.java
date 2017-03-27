package com.mhl.shop.me.myview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.mhl.shop.R;

/**
 * 作者：lff
 * 时间；2016-11-15 14:55
 * 描述：
 */
public class ChoseSexDialog extends Dialog implements OnClickListener
{

    private RadioButton	chose_sex_man;			// 男
    private RadioButton	chose_sex_girl;		// 女
    private RadioButton	chose_sex_privary;		// 保密
    private Button		chose_sex_cancel;		// 取消
    private Button      chose_sex_ok;           //确定
    private RadioGroup	chose_sex_radiogroup;

    private Context context = null;
    private int num;
    public ChoseSexDialog(Context context, int theme,int num) {
        super(context, theme);
        this.context = context;
        this.num = num;
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_chose_sex);
        chose_sex_man = (RadioButton) findViewById(R.id.chose_sex_man);
        chose_sex_girl = (RadioButton) findViewById(R.id.chose_sex_girl);
        chose_sex_privary = (RadioButton) findViewById(R.id.chose_sex_privary);
        chose_sex_cancel = (Button) findViewById(R.id.chose_sex_cancel);  //取消
        chose_sex_ok   = (Button) findViewById(R.id.chose_sex_ok);   //确定
        chose_sex_radiogroup = (RadioGroup) findViewById(R.id.chose_sex_radiogroup);
        chose_sex_cancel.setOnClickListener(this);
        chose_sex_ok.setOnClickListener(this);
        chose_sex_radiogroup.setOnCheckedChangeListener(new listener());
        if (num==1) {
            chose_sex_girl.setChecked(false);
            chose_sex_man.setChecked(true);
            chose_sex_privary.setChecked(false);
        }
        else if (num==2) {
            chose_sex_girl.setChecked(true);
            chose_sex_man.setChecked(false);
            chose_sex_privary.setChecked(false);
        }else {
            chose_sex_girl.setChecked(false);
            chose_sex_man.setChecked(false);
            chose_sex_privary.setChecked(true);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.chose_sex_cancel:
                dismiss();
                break;
            case R.id.chose_sex_ok:
                if (num==1) {
                    onSetSexListener.onComplete("男",1);
                }
                else if (num==2) {
                    onSetSexListener.onComplete("女",2);
                }else {
                    onSetSexListener.onComplete("保密",3);
                }
                dismiss();
                break;

        }

    }

    class listener implements OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId)
        {
            int id = checkedId;   //2131427922
            System.out.println(id);
            if (checkedId == chose_sex_man.getId())
            {
//				onSetSexListener.onComplete("男");
//				dismiss();
                num = 1;
            }
            if (checkedId == chose_sex_girl.getId())
            {
//				onSetSexListener.onComplete("女");
//				dismiss();
                num = 2;
            }
            if (checkedId == chose_sex_privary.getId())
            {
//				onSetSexListener.onComplete("保密");
//				dismiss();
                num = 3;
            }

        }

    }

    private OnSetSexListener	onSetSexListener;

    public OnSetSexListener getOnSetSexListener()
    {
        return onSetSexListener;
    }

    public void setOnSetSexListener(OnSetSexListener onSetSexListener)
    {
        this.onSetSexListener = onSetSexListener;
    }

    /**
     * 返回结果监听器
     */
    public interface OnSetSexListener
    {
        void onComplete(String sex, int index);
    }

}
