package com.mhl.shop.me.myview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mhl.shop.R;
import com.mhl.shop.login.EditPayPwdActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnLongClick;

/**
 * 作者：lff
 * 时间；2016-11-18 18:05
 * 描述：支付密码框以及键盘
 */
public class PayPasswordView implements View.OnClickListener {
    @Bind(R.id.keyboard)
    public LinearLayout keyboard;
    @Bind(R.id.keyboard_gone)
    public LinearLayout keyboard_gone;
    @Bind(R.id.pay_keyboard_del)
    public ImageView del;
    @Bind(R.id.pay_keyboard_zero)
    public ImageView zero;
    @Bind(R.id.pay_keyboard_one)
    public ImageView one;
    @Bind(R.id.pay_keyboard_two)
    public ImageView two;
    @Bind(R.id.pay_keyboard_three)
    public ImageView three;
    @Bind(R.id.pay_keyboard_four)
    public ImageView four;
    @Bind(R.id.pay_keyboard_five)
    public ImageView five;
    @Bind(R.id.pay_keyboard_sex)
    public ImageView sex;
    @Bind(R.id.pay_keyboard_seven)
    public ImageView seven;
    @Bind(R.id.pay_keyboard_eight)
    public ImageView eight;
    @Bind(R.id.pay_keyboard_nine)
    public ImageView nine;
    @Bind(R.id.pay_cancel)
    public TextView cancel;
    @Bind(R.id.pay_sure)
    public TextView sure;
    @Bind(R.id.pay_box1)
    public TextView box1;
    @Bind(R.id.pay_box2)
    public TextView box2;
    @Bind(R.id.pay_box3)
    public TextView box3;
    @Bind(R.id.pay_box4)
    public TextView box4;
    @Bind(R.id.pay_box5)
    public TextView box5;
    @Bind(R.id.pay_box6)
    public TextView box6;
    @Bind(R.id.pay_title)
    public TextView pay_title;
    @Bind(R.id.xiu)
    public TextView xiu;
    private ArrayList<String> mList = new ArrayList<>();
    private View mView;
    private OnPayListener listener;
    private Context mContext;

    private String password = "";
    private String cash_payment,cash_amount;
    /*提交订单*/
    public PayPasswordView(Context mContext, OnPayListener listener) {
        getDecorView( mContext, listener	);
    }
    public static PayPasswordView getInstance(Context mContext, OnPayListener listener) {
        return new PayPasswordView( mContext,listener);
    }

    public void getDecorView( Context mContext, OnPayListener listener) {

        this.listener = listener;
        this.mContext = mContext;

        mView = LayoutInflater.from(mContext).inflate(R.layout.item_paypassword, null);
        ButterKnife.bind(this, mView);
        xiu.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // 下划线

        zero.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        sex.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        del.setOnClickListener(this);
        keyboard_gone.setOnClickListener(this);
        box1.setOnClickListener(this);
        cancel.setOnClickListener(this);
        xiu.setOnClickListener(this);
        sure.setBackgroundResource(R.drawable.button_bg_selector);
        sure.setOnClickListener(null);
        keyboard.setVisibility(View.VISIBLE);
    }

    public void onClick(View v) {
        if (v == zero) {
            parseActionType(KeyboardEnum.zero);
        } else if (v == one) {
            parseActionType(KeyboardEnum.one);
        } else if (v == two) {
            parseActionType(KeyboardEnum.two);
        } else if (v == three) {
            parseActionType(KeyboardEnum.three);
        } else if (v == four) {
            parseActionType(KeyboardEnum.four);
        } else if (v == five) {
            parseActionType(KeyboardEnum.five);
        } else if (v == sex) {
            parseActionType(KeyboardEnum.sex);
        } else if (v == seven) {
            parseActionType(KeyboardEnum.seven);
        } else if (v == eight) {
            parseActionType(KeyboardEnum.eight);
        } else if (v == nine) {
            parseActionType(KeyboardEnum.nine);
        } else
        if (v == cancel) {
            parseActionType(KeyboardEnum.cancel);
        } else
        if (v == sure) {
            parseActionType(KeyboardEnum.sure);
        } else if (v == del) {
            parseActionType(KeyboardEnum.del);
        } else if (v == xiu) {
            Intent intent1 = new Intent();
            intent1.setClass(mContext, EditPayPwdActivity.class);
            mContext.startActivity(intent1);
        }else if (v == keyboard_gone) {
            parseActionType(KeyboardEnum.keyboard_gone);
        }else if (v == box1) {
            parseActionType(KeyboardEnum.box1);
        }
    }

    @OnLongClick(R.id.pay_keyboard_del)
    public boolean onLongClick(View v) {
        // TODO Auto-generated method stub
        parseActionType(KeyboardEnum.longdel);
        return false;
    }

    private void parseActionType(KeyboardEnum type) {
        // TODO Auto-generated method stub
        if (type.getType() == KeyboardEnum.ActionEnum.add) {
            if (mList.size() < 6) {
                mList.add(type.getValue().toString());
                updateUi();
            }
        } else if (type.getType() == KeyboardEnum.ActionEnum.delete) {
            if (mList.size() > 0) {
//                password="";
                mList.remove(mList.size() - 1);
                updateUi();
            }
        } else if (type.getType() == KeyboardEnum.ActionEnum.cancel) {
            listener.onCancelPay();
            // mList.clear();
        } else if (type.getType() == KeyboardEnum.ActionEnum.sure) {
            if (mList.size() < 6) {
                Toast.makeText(mContext, "支付密码必须6位", Toast.LENGTH_SHORT).show();
            } else {
                if(!TextUtils.isEmpty(password)){
                    password="";
                }
                for (int i = 0; i < mList.size(); i++) {
                    password += mList.get(i);
                }
                listener.onSurePay(password);

            }
            Log.d("123456", "password111====" + password);

        } else if (type.getType() == KeyboardEnum.ActionEnum.longClick) {
            updateUi();
        }else if (type.getType() == KeyboardEnum.ActionEnum.gone) {
            keyboard.setVisibility(View.INVISIBLE);
        }else if (type.getType() == KeyboardEnum.ActionEnum.box1) {
            keyboard.setVisibility(View.VISIBLE);
        }
    }


    private void updateUi() {
        // TODO Auto-generated method stub
        if (mList.size() == 0) {
            box1.setText("");
            box2.setText("");
            box3.setText("");
            box4.setText("");
            box5.setText("");
            box6.setText("");
            sure.setBackgroundResource(R.drawable.btn_grey);
            sure.setOnClickListener(null);
        } else if (mList.size() == 1) {
            box1.setText(mList.get(0));
            box2.setText("");
            box3.setText("");
            box4.setText("");
            box5.setText("");
            box6.setText("");
//			cancel.setBackgroundResource(R.drawable.btn_red_pressed);
            sure.setBackgroundResource(R.drawable.btn_grey);
            sure.setOnClickListener(null);
        } else if (mList.size() == 2) {
            box1.setText(mList.get(0));
            box2.setText(mList.get(1));
            box3.setText("");
            box4.setText("");
            box5.setText("");
            box6.setText("");
            sure.setBackgroundResource(R.drawable.btn_grey);
            sure.setOnClickListener(null);
        } else if (mList.size() == 3) {
            box1.setText(mList.get(0));
            box2.setText(mList.get(1));
            box3.setText(mList.get(2));
            box4.setText("");
            box5.setText("");
            box6.setText("");
            sure.setBackgroundResource(R.drawable.btn_grey);
            sure.setOnClickListener(null);
        } else if (mList.size() == 4) {
            box1.setText(mList.get(0));
            box2.setText(mList.get(1));
            box3.setText(mList.get(2));
            box4.setText(mList.get(3));
            box5.setText("");
            box6.setText("");
            sure.setBackgroundResource(R.drawable.btn_grey);
            sure.setOnClickListener(null);
        } else if (mList.size() == 5) {
            box1.setText(mList.get(0));
            box2.setText(mList.get(1));
            box3.setText(mList.get(2));
            box4.setText(mList.get(3));
            box5.setText(mList.get(4));
            box6.setText("");
            sure.setBackgroundResource(R.drawable.btn_grey);
            sure.setOnClickListener(null);
        } else if (mList.size() == 6) {
            box1.setText(mList.get(0));
            box2.setText(mList.get(1));
            box3.setText(mList.get(2));
            box4.setText(mList.get(3));
            box5.setText(mList.get(4));
            box6.setText(mList.get(5));
            sure.setBackgroundResource(R.drawable.button_bg_selector);
            sure.setOnClickListener(this);
        }
    }

    public interface OnPayListener {
        void onCancelPay();

        void onSurePay(String password);

        void onBackPay();
    }

    public View getView() {
        return mView;
    }
}


