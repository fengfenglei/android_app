package com.mhl.shop.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.me.adapter.AdressManageAdapter;
import com.mhl.shop.me.been.Address;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;


/**
 * 作者：lff
 * 时间；2016-11-17 17:57
 * 描述：地址管理
 */
public class ManageAdressActivity extends MyBaseActivity implements MeInterface.OnAddressRefreshListener,MeInterface.OnAddressCheckedListener{
    @Bind(R.id.title_left_textview)
    TextView titleLeftTextview;
    @Bind(R.id.title_left_imageview)
    ImageView titleLeftImageview;
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.title_center_imageview)
    ImageView titleCenterImageview;
    @Bind(R.id.title_right_imageview)
    ImageView titleRightImageview;
    @Bind(R.id.title_parentlayout)
    RelativeLayout titleParentlayout;
    @Bind(R.id.my_account_img)
    ImageView myAccountImg;
    @Bind(R.id.my_account_tv)
    TextView myAccountTv;
    @Bind(R.id.tvadd)
    TextView tvadd;
    @Bind(R.id.my_account_zero_adress)
    RelativeLayout myAccountZeroAdress;
    @Bind(R.id.my_account_adress_listview)
    ListView myAccountAdressListview;
    @Bind(R.id.title_right_textview)
    TextView titleRightTextview;
    private List<Address.RowsBean> list;
    private AdressManageAdapter adapter;
    private String					str_address		= "";		// 判断是哪个界面调用的收货地址
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_address);
        ButterKnife.bind(this);
        initViews();
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        initDatas();

    }


    private void initViews() {
        titleCenterTextview.setText("管理地址");
        titleRightTextview.setVisibility(View.VISIBLE);
        titleRightTextview.setText("新增");
        myAccountZeroAdress.setVisibility(View.GONE);
        myAccountAdressListview.setVisibility(View.VISIBLE);
        myAccountAdressListview.setFocusable(false);
        str_address = getIntent().getStringExtra("adress_type"); // 接受判断是哪个界面跳转到管理收货地址

        MeInterface.setOnAddressRefreshListener(ManageAdressActivity.this);// 添加和修改地址回调
        MeInterface.setOnCheckedAddressResult(this); // 回调

    }
    private void initDatas() {
        {// 获取地址列表
            OkGo.post(Urls.ADDRESS_LIST)//
                    .tag(this)
                    .execute(new DialogCallback<LzyResponse<Address>>(this, true) {
                                 @Override
                                 public void onSuccess(LzyResponse<Address> responseData, Call call, Response response) {
                                     handleResponse(responseData.data, call, response);
                                     list=responseData.data.getRows();
                                     if(list!=null){
                                         if(list.size()<1){
                                             myAccountZeroAdress.setVisibility(View.VISIBLE);
                                             myAccountAdressListview.setVisibility(View.GONE);
                                         }else{
                                             adapter = new AdressManageAdapter(ManageAdressActivity.this, list);
                                             myAccountAdressListview.setAdapter(adapter);
                                             myAccountZeroAdress.setVisibility(View.GONE);
                                             myAccountAdressListview.setVisibility(View.VISIBLE);
                                         }
                                     }
                                 }
                                 @Override
                                 public void onError(Call call, Response response, Exception e) {
                                     super.onError(call, response, e);
                                     handleError(call, response, e);
                                 }
                             }
                    );
        }
    }
    @OnClick({R.id.title_left_imageview, R.id.title_right_textview,R.id.tvadd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                this.finish();
                break;
            case R.id.title_right_textview:
                if(list.size()<20){
                    startActivity(new Intent(ManageAdressActivity.this, AddAdressActivity.class));

                }else {
                    T.showShort(ManageAdressActivity.this,"收货地址最多为20条");
                }
                break;
            case R.id.tvadd:
                startActivity(new Intent(ManageAdressActivity.this, AddAdressActivity.class));
                break;
        }
    }
    public void OnAddressRefresh() {
        initDatas();
    }

    @Override
    public void CheckedOnSelectListener(String addressId) {

        Intent intent = new Intent();
        intent.putExtra("addressId", addressId);
        setResult(10, intent);
        if (str_address.equals("order"))
        {
            finish();
        } else if (str_address.equals("userInfo")) {

        }

    }
}
