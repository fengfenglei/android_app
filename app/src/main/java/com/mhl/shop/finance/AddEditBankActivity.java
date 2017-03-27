package com.mhl.shop.finance;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.finance.been.AddName;
import com.mhl.shop.finance.been.EditName;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.login.myview.ClearEditText;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.me.MeInterface;
import com.mhl.shop.shopdetails.GoodsAddressActivity;
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
 * 作者：lff
 * 时间；2017-1-5 14:30
 * 描述：编辑或者添加银行卡
 */
public class AddEditBankActivity extends MyBaseActivity implements MeInterface.OnGoodAddressResultLister{

    @Bind(R.id.title_left_textview)
    TextView titleLeftTextview;
    @Bind(R.id.title_left_imageview)
    ImageView titleLeftImageview;
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.title_center_imageview)
    ImageView titleCenterImageview;
    @Bind(R.id.title_right_textview)
    TextView titleRightTextview;
    @Bind(R.id.title_right_imageview)
    ImageView titleRightImageview;
    @Bind(R.id.title_parentlayout)
    RelativeLayout titleParentlayout;
    @Bind(R.id.bank_type)
    TextView bank_type;
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.bank_user_card)
    TextView bankUserCard;
    @Bind(R.id.textView4)
    TextView textView4;
    @Bind(R.id.bank_user_phone)
    ClearEditText bankUserPhone;
    @Bind(R.id.bank_user_bank_address)
    TextView bankUserBankAddress;
    @Bind(R.id.bank_user_bank_name)
    ClearEditText bankUserBankName;
    @Bind(R.id.bank_user_bank_wang)
    ClearEditText bankUserBankWang;
    @Bind(R.id.bank_user_bank_num)
    ClearEditText bankUserBankNum;
    @Bind(R.id.save_btn)
    Button saveBtn;
    private String type;
    private Intent intent;
    // 编辑模式标志
    private boolean EDIT = false;
    private List<String> list;//地区拆分之后集合
    private String userBankCardId;
    private  boolean isChoiseaddress = false;//是否去选择过地址
    String sbankProvince,sbankCity;
    protected void onCreate(Bundle savedInstanceState) {
        //TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank);
        ButterKnife.bind(this);
        //初始化控件
        isEdit();//判断是否是编辑

    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    private void isEdit() {
        intent = getIntent();
        type = intent.getStringExtra("type");
        // 如果adressId值不为空，则进入编辑模式
        if (type.equals("edit")) {
            EDIT = true; // 修改模式
            titleCenterTextview.setText("修改银行卡信息");
            initaEditUser();

        } else if (type.equals("add")) {// 添加模式
            EDIT = false;
            titleCenterTextview.setText("新增银行卡");
            initaAddUser();
        }
    }

    private void initaEditUser() {
        OkGo.post(Urls.URL_REALNAME_IDCARD_INFO_FULL)//
                .tag(this)
                .execute(new DialogCallback<LzyResponse<EditName>>(AddEditBankActivity.this, false) {
                             @Override
                             public void onSuccess(LzyResponse<EditName> responseData, Call call, Response response) {
                                 handleResponse(responseData.data, call, response);
                                 if(responseData.code==200){
                                     userName.setText(responseData.data.getBankUser());
                                     bankUserCard.setText(responseData.data.getBankUserIdCard());
                                     bankUserPhone.setText(responseData.data.getBankUserMobile()+"");
                                     bankUserBankAddress.setText(responseData.data.getBankProvince()+" "+responseData.data.getBankCity());
                                     bankUserBankName.setText(responseData.data.getBankName());
                                     bankUserBankWang.setText(responseData.data.getBankAddress());
                                     bankUserBankNum.setText(responseData.data.getBankCardCode());
                                     userBankCardId=responseData.data.getPkId()+"";
                                     sbankProvince=responseData.data.getBankProvince();
                                     sbankCity=responseData.data.getBankCity();



                                 }else {
                                     T.showShort(AddEditBankActivity.this,responseData.info);
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

    private void initaAddUser() {
        OkGo.post(Urls.URL_REALNAME_IDCARD)//
                .tag(this)
                .execute(new DialogCallback<LzyResponse<AddName>>(AddEditBankActivity.this, false) {
                             @Override
                             public void onSuccess(LzyResponse<AddName> responseData, Call call, Response response) {
                                 handleResponse(responseData.data, call, response);
                                 if(responseData.code==200){

                                     userName.setText(responseData.data.getRealName());
                                     bankUserCard.setText(responseData.data.getIdCardCode());
                                 }else {
                                     T.showShort(AddEditBankActivity.this,responseData.info);
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

    private void initaAddSave() {

        String bankUserMobile,bankProvince = "",bankCity = "",bankName,bankCardCode,bankAddress;

        bankUserMobile=bankUserPhone.getText().toString();

        if(list==null){
            T.showShort(AddEditBankActivity.this,"请完善地址信息信息");
            return;
        }else {
            if(list.size()<2){
                T.showShort(AddEditBankActivity.this,"请完善地址信息信息");
                      return;
            }else {
                bankProvince=list.get(0).toString();
                bankCity=list.get(1).toString();
            }

        }
        bankName=bankUserBankName.getText().toString();
        bankCardCode=bankUserBankNum.getText().toString();
        bankAddress=bankUserBankWang.getText().toString();
//        if(TextUtils.isEmpty(bankUserMobile)
//                ||TextUtils.isEmpty(bankProvince)
//                ||TextUtils.isEmpty(bankCity)
//                ||TextUtils.isEmpty(bankName)
//                ||TextUtils.isEmpty(bankCardCode)
//                ||TextUtils.isEmpty(bankAddress)){
//            T.showShort(AddEditBankActivity.this,"请完善信息");
//            return;
//        }
        if(TextUtils.isEmpty(bankUserMobile)){
            T.showShort(AddEditBankActivity.this,"开户人手机号不能为空");
            return;
        }
        if(TextUtils.isEmpty(bankUserMobile)||TextUtils.isEmpty(bankCity)){
            T.showShort(AddEditBankActivity.this,"所在地不能为空");
            return;
        }
        if(TextUtils.isEmpty(bankName)){
            T.showShort(AddEditBankActivity.this,"银行名称不能为空");
            return;
        }
        if(TextUtils.isEmpty(bankAddress)){
            T.showShort(AddEditBankActivity.this,"开户网点不能为空");
            return;
        }
        if(TextUtils.isEmpty(bankCardCode)){
            T.showShort(AddEditBankActivity.this,"银行卡号不能为空");
            return;
        }
        if (!ToolsUtils.isMobileNO(bankUserMobile))
        {
            T.showShort(this, R.string.toast_phone_error);
            return;
        }
        if(bankName.length()<2 || bankName.length()>20){
            T.showShort(AddEditBankActivity.this,"开户行在2~20字之间");
            return;
        }
        if(bankAddress.length()<2 || bankAddress.length()>50){
            T.showShort(AddEditBankActivity.this,"开户网点在2~50字之间");
            return;
        }
            OkGo.post(Urls.URL_REALNAME_IDCARD_INFO)//
                .tag(this)
                .params("bankCardType","1")//银行卡类型(--1借记卡--2信用卡)
                .params("bankCardAccountType","2")//银行账户类型(--1对公--2对私)
                .params("bankUserMobile",bankUserMobile)//开户人手机号
                .params("bankProvince",bankProvince)//开户行所在省
                .params("bankCity",bankCity)//开户行所在市
                .params("bankName",bankName)//银行名称
                .params("bankCardCode",bankCardCode)//银行卡号
                .params("bankAddress",bankAddress)//开户网点
                .execute(new DialogCallback<LzyResponse<AddName>>(AddEditBankActivity.this, false) {
                             @Override
                             public void onSuccess(LzyResponse<AddName> responseData, Call call, Response response) {
                                 handleResponse(responseData.data, call, response);
                                 if(responseData.code==200){
                                          finish();
                                 }else {
                                     T.showShort(AddEditBankActivity.this,responseData.info);
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
    @OnClick({R.id.title_left_imageview, R.id.bank_user_bank_address, R.id.save_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.bank_user_bank_address:
                MeInterface.setOnGoodAddressResultLister(this);
                Intent spinIntent = new Intent(AddEditBankActivity.this, GoodsAddressActivity.class);
                spinIntent.putExtra("selectTag", "2"); // 两级联动
                startActivity(spinIntent);
                break;
            case R.id.save_btn:
                if (EDIT) {
                    initaEditSave();
                }else {
                    initaAddSave();
                }
                break;
        }
    }

    private void initaEditSave() {
        String bankUserMobile,bankProvince,bankCity,bankName,bankCardCode,bankAddress;


        bankUserMobile=bankUserPhone.getText().toString();

        if(isChoiseaddress){
            bankProvince=list.get(0).toString();
            bankCity=list.get(1).toString();
        }else {
            bankProvince=sbankProvince;
            bankCity=sbankCity;
        }

        bankName=bankUserBankName.getText().toString();
        bankCardCode=bankUserBankNum.getText().toString();
        bankAddress=bankUserBankWang.getText().toString();

//        if(TextUtils.isEmpty(bankUserMobile)
//                ||TextUtils.isEmpty(bankProvince)
//                ||TextUtils.isEmpty(bankCity)
//                ||TextUtils.isEmpty(bankName)
//                ||TextUtils.isEmpty(bankCardCode)
//                ||TextUtils.isEmpty(bankAddress)){
//            T.showShort(AddEditBankActivity.this,"请完善信息");
//            return;
//        }
        if(TextUtils.isEmpty(bankUserMobile)){
            T.showShort(AddEditBankActivity.this,"开户人手机号不能为空");
            return;
        }
        if(TextUtils.isEmpty(bankUserMobile)||TextUtils.isEmpty(bankCity)){
            T.showShort(AddEditBankActivity.this,"所在地不能为空");
            return;
        }
        if(TextUtils.isEmpty(bankName)){
            T.showShort(AddEditBankActivity.this,"银行名称不能为空");
            return;
        }
        if(TextUtils.isEmpty(bankAddress)){
            T.showShort(AddEditBankActivity.this,"开户网点不能为空");
            return;
        }
        if(TextUtils.isEmpty(bankCardCode)){
            T.showShort(AddEditBankActivity.this,"银行卡号不能为空");
            return;
        }
        if (!ToolsUtils.isMobileNO(bankUserMobile))
        {
            T.showShort(this, R.string.toast_phone_error);
            return;
        }
        if(bankName.length()<2 || bankName.length()>20){
            T.showShort(AddEditBankActivity.this,"银行名称在2~20字之间");
            return;
        }
        if(bankAddress.length()<2 || bankAddress.length()>50){
            T.showShort(AddEditBankActivity.this,"开户网点在2~20字之间");
            return;
        }
        OkGo.post(Urls.URL_REALNAME_EDIT_INFO_FULL)//
                .tag(this)
                .params("userBankCardId",userBankCardId)//绑定银行卡信息ID
                .params("bankCardType","1")//银行卡类型(--1借记卡--2信用卡)
                .params("bankCardAccountType","2")//银行账户类型(--1对公--2对私)
                .params("bankUserMobile",bankUserMobile)//开户人手机号
                .params("bankProvince",bankProvince)//开户行所在省
                .params("bankCity",bankCity)//开户行所在市
                .params("bankName",bankName)//银行名称
                .params("bankCardCode",bankCardCode)//银行卡号
                .params("bankAddress",bankAddress)//开户网点
                .execute(new DialogCallback<LzyResponse<AddName>>(AddEditBankActivity.this, false) {
                             @Override
                             public void onSuccess(LzyResponse<AddName> responseData, Call call, Response response) {
                                 handleResponse(responseData.data, call, response);
                                 if(responseData.code==200){
                                     finish();
                                 }else {
                                     T.showShort(AddEditBankActivity.this,responseData.info);
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

    private void verification() {


    }

    @Override
    public void OnGoodAddressResultInfo(String goodAddress, String id) {
        list = Arrays.asList(goodAddress.split(" "));
        if(list.size()>=2){
            bankUserBankAddress.setText(goodAddress);
            isChoiseaddress=true;

        }
    }
}
