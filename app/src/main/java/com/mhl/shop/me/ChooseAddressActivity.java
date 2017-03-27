package com.mhl.shop.me;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mhl.shop.R;
import com.mhl.shop.main.MyBaseaddressActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：Administrator
 * 时间；2016-11-24 10:16
 * 描述：
 */
public class ChooseAddressActivity extends MyBaseaddressActivity implements View.OnClickListener,MeInterface.OnChooseAddressListener
{
    @Bind(R.id.title_left_imageview)
    ImageView titleLeftImageview;

    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    private TextView address_choose;								// 选择的地址
    private LinearLayout address_return;                             //
    private String[]								b;                                          //
    private int										c		= 1;
    private AddAddressFragment						aaf;

    private static SparseArray<AddAddressFragment> mCaches	= new
            SparseArray<AddAddressFragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_address);  //布局文件
        ButterKnife.bind(this);
        init(); //
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

    }
    private void init() {
        titleCenterTextview.setText("地区选择");
        MeInterface.setOnChooseAddressListener(this);
        b = new String[0];
        address_choose = (TextView) findViewById(R.id.address_choose);
        address_choose.setText("请选择省/市");
        address_return = (LinearLayout) findViewById(R.id.address_return);
        address_return.setOnClickListener(this);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        aaf = new AddAddressFragment();   //添加不同的framgent
        Bundle bundle = new Bundle();     //
        bundle.putString("address", "");  //
        bundle.putString("id", "");       //
        bundle.putInt("tag", 1);   //
        ft.add(R.id.address_choose_framelayout, aaf);
        aaf.setArguments(bundle);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);  //专场动画
        ft.commitAllowingStateLoss();
        mCaches.put(0, aaf);

    }
    @OnClick(R.id.title_left_imageview)
    public void onClick() {
        this.finish();
        mCaches.clear();  //退出是清缓存
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            //点击地址返回上一级
            case R.id.address_return:
                getSupportFragmentManager().popBackStack(); //返回上一个fragment
                String address = "";
                System.out.println(b);
                if (b.length > 0)
                {
                    for (int i = 0; i < b.length - c; i++)
                    {
                        address = address + b[i] + ">";
                    }
                    c = c + 1;  //点击一次返回地址要减少一级
                    address_choose.setText(address);
                    if (address_choose.getText().equals("") || address_choose.getText() == null)
                    {
                        address_choose.setText("请选择省/市");
                    }
                }
                break;

        }

    }

    @Override
    public void OnChooseAddressRefresh(String[] address, String id)
    {
        c = 1;
        String a = "";
        b = address;  //
        for (int i = 0; i < address.length; i++)
        {
            a = a + address[i] + ">";  //为显示在头部的地址添加">"
        }
        address_choose.setText(a);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(aaf);
        AddAddressFragment fragment = mCaches.get(b.length);
        if (fragment != null)
        {
            for (int i = b.length - 1; i < mCaches.size(); i++)
            {
                fragment = mCaches.get(i);
                ft.hide(fragment);
            }
        }
        aaf = new AddAddressFragment();
        Bundle bundle = new Bundle();
        bundle.putString("address", a);
        bundle.putString("id", id);
        bundle.putInt("tag", b.length);//地址是否添加无的标记,如果大于等于3则添加无反之不添加(也就是说省市县不添加无,是必选的地址)
        ft.add(R.id.address_choose_framelayout, aaf);
        aaf.setArguments(bundle);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
        mCaches.put(b.length, aaf);

    }

    /**
     * 重写物理按键返回键动态改变地址
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        String address = "";
        if (b.length > 0)
        {
            for (int i = 0; i < b.length - c; i++)
            {
                address = address + b[i] + ">";
            }
            c = c + 1;
            address_choose.setText(address);
            if (address_choose.getText().equals("") || address_choose.getText() == null)
            {
                address_choose.setText("请选择省/市");
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onDestroy()
    {
        //TODO Auto-generated method stub
        super.onDestroy();
//        mCaches.clear();
    }
}
