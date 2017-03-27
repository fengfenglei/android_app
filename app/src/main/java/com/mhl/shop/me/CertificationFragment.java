package com.mhl.shop.me;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.callback.StringDialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.login.been.Body;
import com.mhl.shop.login.been.Null;
import com.mhl.shop.main.WebActivity;
import com.mhl.shop.utils.Constant;
import com.mhl.shop.utils.GsonUtils;
import com.mhl.shop.utils.IdcardValidatorUtils;
import com.mhl.shop.utils.IntentUtils;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class CertificationFragment extends PictureSelectFragment {


    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.title_right_imageview)
    ImageView titleRightImageview;
    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.user_card)
    EditText userCard;
    @Bind(R.id.icon_01)
    ImageView icon01;
    @Bind(R.id.icon_02)
    ImageView icon02;
    String imagePath01="";//正面身份证的路劲
    String imagePath02="";//反面身份证的路劲
    String idCardPic="";//身份证图片(正面)
    String idCardPicObverse="";//身份证图片(反面)
    String is ="";
    public static CertificationFragment newInstance() {
        return new CertificationFragment();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_certification;
    }

    @Override
    public void initViews(View view) {
        titleCenterTextview.setText("实名认证");
        titleRightImageview.setVisibility(View.VISIBLE);
        titleRightImageview.setBackgroundResource( R.drawable.icon_problem);
//        name.clearFocus();
//        name.setSelected(false);
//        userCard.clearFocus();
//        userCard.setSelected(false);
    }

    @Override
    public void initEvents() {

        // 设置身份证正面裁剪图片结果监听
        setOnPictureSelectedListener(new OnPictureSelectedListener() {
            @Override
            public void onPictureSelected(Uri fileUri, Bitmap bitmap) {
                icon01.setImageBitmap(bitmap);

                String filePath = fileUri.getEncodedPath();
                 imagePath01 = Uri.decode(filePath);
                go01(1);//默认上传
            }
        });

        // 设置份证反面裁剪图片结果监听
        setOnPictureSelectedListener1(new OnPictureSelectedListener1() {
            @Override
            public void onPictureSelected1(Uri fileUri, Bitmap bitmap) {
                icon02.setImageBitmap(bitmap);

                String filePath = fileUri.getEncodedPath();
                 imagePath02 = Uri.decode(filePath);
                go01(2);//默认上传
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.title_left_imageview, R.id.title_right_imageview, R.id.icon_01, R.id.go01, R.id.icon_02, R.id.go02, R.id.all_ly, R.id.go_ly})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                getActivity().finish();
                break;
            case R.id.title_right_imageview:
                Intent intent4 = new Intent(getActivity(),WebActivity.class);
                intent4.putExtra(Constant.TITLE, "实名认证说明");
                intent4.putExtra(Constant.LBONCLICKURL, Urls.URL_BASE+"/cus-real-detail.html");
                startActivity(intent4);
                break;
            case R.id.icon_01:

                break;
            case R.id.go01:
                selectPicture(1);
                break;
            case R.id.icon_02:

                break;
            case R.id.go02:
                selectPicture(2);

                break;
            case R.id.all_ly:
                break;
            case R.id.go_ly:
                infoAdd();
                break;
        }
    }

    private void infoAdd() {
        if(TextUtils.isEmpty(name.getText().toString())||TextUtils.isEmpty(userCard.getText().toString())
                ||TextUtils.isEmpty(idCardPic)
                ||TextUtils.isEmpty(idCardPicObverse)
               ){
            T.showShort(getActivity(),"请完善信息");
            return;
        }
        if(!IdcardValidatorUtils.isValidatedAllIdcard(userCard.getText().toString())){
            T.showShort(getActivity(),"身份证号码格式错误");
            return;
        }
        OkGo.post(Urls.URL_AUTHENTICATION_INFO)
                .tag(this)
                .params("realName",name.getText().toString())
                .params("idCardCode",userCard.getText().toString())
                .params("idCardPic",idCardPic)
                .params("idCardPicObverse",idCardPicObverse)
                .execute(new DialogCallback<LzyResponse<Null>>(getActivity(), true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> responseData, Call call, Response response) {
//                                 handleResponse(responseData.data, call, response);
                                 if(responseData.code==200){
                                     IntentUtils.startActivity( getActivity(), CertificationResultActivity.class);
                                     getActivity().finish();
                                 }else {
                                     T.showShort(getActivity(),responseData.info);
                                 }
                             }
                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
//                                 handleError(call, response, e);
                             }
                         }
                );
    }

    //上传单张图片换取图片值
    Body body;
    private void go01(final int i) {
        String file="";
        if(i==1){
            file=imagePath01;
        }else if(i==2){
            file=imagePath02;
        }
        OkGo.post(Urls.URL_UPLOAD)
                .tag(this)
                .params("file",new File(file))
                .execute(new StringDialogCallback(getActivity(), true) {
                             @Override
                             public void onSuccess(String s, Call call, Response response) {
                                 body=(Body) GsonUtils.fromJson(s,
                                         Body.class);
                                 if(body.getCode()==200){
                                     if(i==1){
                                         idCardPic=body.getData();
                                     }else if(i==2){
                                         idCardPicObverse=body.getData();
                                     }

                                 }else{
                                     T.showShort(getActivity(), body.getMessage());
                                 }
                             }
                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                             }
                         }
                );
    }
}
