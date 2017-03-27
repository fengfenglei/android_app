package com.mhl.shop.me;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.login.been.Null;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：LFF
 * 时间；2016-11-21 15:50
 * 描述：新增地址
 */
public class AddAdressActivity extends MyBaseActivity implements View.OnClickListener,MeInterface.OnAddressResultListener{

    private EditText my_account_adress_username;
    private EditText		my_account_adress_phone;
    private EditText		my_account_adress_postalcode;
    private EditText		my_account_adress_detail;
    private TextView my_account_adress_where_adress; // 所在地区拼接地址
    private Button my_account_add_adress_btn;
    private RelativeLayout my_account_choose_address;		// 选择地址
    private  boolean isChoiseaddress = false;//是否去选择过地址
    // 编辑模式标志
    private boolean			EDIT	= false;
    private String			adressId;
    private String defaults;
    private  List<String> list;//地区拆分之后集合
    private Intent intent;
    @Bind(R.id.title_left_imageview)
    ImageView titleLeftImageview;
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_adress);
        ButterKnife.bind(this);
        //初始化控件
        init();
        isEdit();//判断是否是编辑
    }
    @OnClick(R.id.title_left_imageview)
    public void onClick() {
        this.finish();
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    private void init()
    {

        my_account_adress_username = (EditText) findViewById(R.id.my_account_adress_username);
        my_account_adress_phone = (EditText) findViewById(R.id.my_account_adress_phone);
        my_account_adress_postalcode = (EditText) findViewById(R.id.my_account_adress_postalcode);
        my_account_adress_where_adress = (TextView) findViewById(R.id.my_account_adress_where_adress);
        my_account_choose_address = (RelativeLayout) findViewById(R.id.my_account_choose_address);
        my_account_choose_address.setOnClickListener(this);
        my_account_adress_detail = (EditText) findViewById(R.id.my_account_adress_detail);
        my_account_add_adress_btn = (Button) findViewById(R.id.my_account_add_adress_btn);
        my_account_add_adress_btn.setOnClickListener(this);
        MeInterface.setOnAddressResultListener(this);
    }

    /**
     * 判断是否是编辑
     */

    private void isEdit()
    {
        intent = getIntent();
        adressId = intent.getStringExtra("adressId");
        // 如果adressId值不为空，则进入编辑模式
        if (adressId != null)
        {
            EDIT = true; // 修改模式
            titleCenterTextview.setText("修改收货地址");
        }
        else
        {// 添加模式
            EDIT = false;
            titleCenterTextview.setText("新增收货地址");
        }
        if (EDIT)
        {
            my_account_adress_username.setText(intent.getStringExtra("TrueName"));
            my_account_adress_phone.setText(intent.getStringExtra("Mobile"));
            my_account_adress_postalcode.setText(intent.getStringExtra("Zip"));
            my_account_adress_where_adress.setText(intent.getStringExtra("WhereAdress"));
            my_account_adress_detail.setText(intent.getStringExtra("getArea_info"));
            defaults=intent.getStringExtra("defaults");
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.my_account_add_adress_btn:
                saveAdress();
                break;
            case R.id.my_account_choose_address:
                startActivity(new Intent(AddAdressActivity.this, ChooseAddressActivity.class));
                break;
        }
    }

    /**
     * 保存收货地址
     */
    private void saveAdress()
    {
        // 获得输入的内容
        String userName = my_account_adress_username.getText().toString().trim();
        String userPhone = my_account_adress_phone.getText().toString().trim();
        String postalcodet = my_account_adress_postalcode.getText().toString().trim(); // 邮政编码
        String deatilAdress = my_account_adress_detail.getText().toString().trim(); // 详细地址
        String adressWhere = my_account_adress_where_adress.getText().toString().trim(); // 地址
        if (TextUtils.isEmpty(userName))
        {
            T.showShort(this, "收货人姓名不能为空");
            return;
        }
        if (TextUtils.isEmpty(userPhone))
        {
            T.showShort(this, "手机号码不能为空");
            return;
        }
        if (!ToolsUtils.isMobileNO(userPhone))
        {
            T.showShort(this, "手机号格式错误");
            return;
        }
        if (TextUtils.isEmpty(adressWhere))
        {
            T.showShort(this, "请完善所在地区");
            return;
        }
        if (TextUtils.isEmpty(deatilAdress))
        {
            T.showShort(this, "详细地址不能为空");
            return;
        }
        if(userName.length()<2 || userName.length()>18 ){
            T.showShort(this, "收货人姓名的长度为2~18位");
            return;
        }
        if(deatilAdress.length()<2 || deatilAdress.length()>50 ){
            T.showShort(this, "详情地址的长度为2~50位");
            return;
        }
        if (!isChoiseaddress){//没有选择过地址
            list = Arrays.asList(intent.getStringExtra("WhereAdress").split(" "));
        }
        String province = "";
        String city = "";
        String district = "";
        String street = "";
        String village = "";

        if (list.size()==3){
            province=list.get(0);
            city=list.get(1);
            district=list.get(2);
            street="";
            village="";
        }else   if (list.size()==4){
            province=list.get(0);
            city=list.get(1);
            district=list.get(2);
            street=list.get(3);
            village="";
        }
        else   if (list.size()==5){
            province=list.get(0);
            city=list.get(1);
            district=list.get(2);
            street=list.get(3);
            village=list.get(4);
        }

        if (EDIT)// 修改模式
        {

            OkGo.post(Urls.URL_EDIT_ADDRESS)//
                    .tag(this)
                    .params("addressId", adressId)//收货人
                    .params("consignee", userName)//收货人
                    .params("province", province)//省
                    .params("city", city)//市
                    .params("district", district)//区
                    .params("street", street)//街道
                    .params("village", village)//村/居委会
                    .params("addressInfo", deatilAdress)//详细地址
                    .params("zip", postalcodet)//邮政编码
                    .params("telephone", "")//联系电话
                    .params("mobile", userPhone)//手机号码
                    .params("defaultFlag", defaults)//是否默认(--1默认--2非默认)
                    .execute( new DialogCallback<LzyResponse<Null>>(this, true) {
                        @Override
                        public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                            handleResponse(lzyResponse, call, response);
                            if(lzyResponse.code==200){
                                if (MeInterface.onAddressRefreshListener != null)
                                {
                                    MeInterface.onAddressRefreshListener.OnAddressRefresh();// 回调刷新地址
                                }
                                T.showShort(AddAdressActivity.this,  "更新保存成功");
//                                finish();
                            }else{
                                T.showShort(AddAdressActivity.this,  lzyResponse.info);
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            handleError(call, response,e);
                        }
                    });

        }
        else
        {// 添加模式

            OkGo.post(Urls.URL_ADD_ADDRESS)//
                    .tag(this)
                    .params("consignee", userName)//收货人
                    .params("province", province)//省
                    .params("city", city)//市
                    .params("district", district)//区
                    .params("street", street)//街道
                    .params("village", village)//村/居委会
                    .params("addressInfo", deatilAdress)//详细地址
                    .params("zip", postalcodet)//邮政编码
                    .params("telephone", "")//联系电话
                    .params("mobile", userPhone)//手机号码
                    .params("defaultFlag", "")//是否默认(--1默认--2非默认)
                    .execute( new DialogCallback<LzyResponse<Null>>(this, true) {
                        @Override
                        public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                            handleResponse(lzyResponse, call, response);
                            if(lzyResponse.code==200){
                                if (MeInterface.onAddressRefreshListener != null)
                                {
                                    MeInterface.onAddressRefreshListener.OnAddressRefresh();// 回调刷新地址
                                }
                                T.showShort(AddAdressActivity.this,  "地址保存成功");
                            }else{
                                T.showShort(AddAdressActivity.this,  lzyResponse.info);
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            handleError(call, response,e);
                        }
                    });
        }

        finish();
    }
    /**
     * 选择地址回调
     */
    @Override
    public void OnAddressResultRefresh(String address, String id)
    {
//        area_id = Long.parseLong(id);
         isChoiseaddress=true;
         my_account_adress_where_adress.setText(address);
         list = Arrays.asList(address.split(" "));

    }
}
