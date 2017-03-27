package com.mhl.shop.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.main.WebActivity;
import com.mhl.shop.me.been.CertificationResult;
import com.mhl.shop.utils.Constant;
import com.mhl.shop.utils.IntentUtils;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：LFF
 * 时间；2017-1-20 18:18
 * 描述：实名认证结果
 */
public class CertificationResultActivity extends MyBaseActivity {
    @Bind(R.id.title_right_imageview)
    ImageView titleRightImageview;
    @Bind(R.id.applicationTime)
    TextView applicationTime;
    @Bind(R.id.auditTime)
    TextView auditTime;
    @Bind(R.id.auditStatus)
    TextView auditStatus;
    @Bind(R.id.auditOpinion)
    TextView auditOpinion;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.user_card)
    TextView userCard;
    @Bind(R.id.certification_bt)
    Button certificationBt;
    @Bind(R.id.all_ly)
    LinearLayout allLy;
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.conent)
    TextView conent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification_result);
        ButterKnife.bind(this);
        //初始化控件
        initViews();
        initData();
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

    private void initViews() {
        titleCenterTextview.setText("实名认证");
        titleRightImageview.setVisibility(View.VISIBLE);
        titleRightImageview.setBackgroundResource( R.drawable.icon_problem);
    }

    private void initData() {
        OkGo.post(Urls.URL_AUTHENTICATION_LOG)
                .tag(this)

                .execute(new DialogCallback<LzyResponse<CertificationResult>>(CertificationResultActivity.this, true) {
                             @Override
                             public void onSuccess(LzyResponse<CertificationResult> responseData, Call call, Response response) {
                                 handleResponse(responseData.data, call, response);
                                 if (responseData.code == 200) {
                                     setData(responseData.data);
                                     allLy.setVisibility(View.VISIBLE);
                                 } else {
                                     T.showShort(CertificationResultActivity.this, responseData.info);
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

    //绑定数据
    private void setData(CertificationResult data) {
        if (data.getAuditStatus() == 1) {  //是否认证(--1已认证----3审核中--2审核失败) ,
            conent.setText("您已经通过实名认证，现在您可以正常进行资金提现并享受更多的保障");
            applicationTime.setText(ToolsUtils.dateToStampLit(data.getApplicationTime()));
            auditTime.setText(ToolsUtils.dateToStampLit(data.getAuditTime()));
            auditStatus.setText("已认证");
            auditOpinion.setText(data.getAuditOpinion());
            userCard.setText(data.getIdCardCode());
            name.setText(data.getApplicant());
        }else if(data.getAuditStatus() == 3){
            conent.setText("您已经提交实名信息，我们将尽快为您审核，预计时间1-3个工作日，请您耐心等待审核结果，谢谢！");
            applicationTime.setText(ToolsUtils.dateToStampLit(data.getApplicationTime()));
            auditTime.setText(ToolsUtils.dateToStampLit(data.getAuditTime()));
            auditStatus.setText("审核中");
            auditOpinion.setText(data.getAuditOpinion());
            userCard.setText(data.getIdCardCode());
            name.setText(data.getApplicant());
        }else if(data.getAuditStatus() == 2){
            conent.setText("很抱歉，您未能通过实名认证，请您参考一下审核意见并重新申请认证，谢谢！");
            applicationTime.setText(ToolsUtils.dateToStampLit(data.getApplicationTime()));
            auditTime.setText(ToolsUtils.dateToStampLit(data.getAuditTime()));
            auditStatus.setText("审核失败");
            auditOpinion.setText(data.getAuditOpinion());
            userCard.setText(data.getIdCardCode());
            name.setText(data.getApplicant());
            certificationBt.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.title_left_imageview, R.id.title_center_textview, R.id.title_right_imageview, R.id.certification_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.title_center_textview:
                break;
            case R.id.title_right_imageview:
                Intent intent4 = new Intent(this,WebActivity.class);
                intent4.putExtra(Constant.TITLE, "实名认证说明");
                intent4.putExtra(Constant.LBONCLICKURL, Urls.URL_BASE+"/cus-real-detail.html");
                startActivity(intent4);
                break;
            case R.id.certification_bt:
                IntentUtils.startActivity(this, CertificationActivity.class);
                break;
        }
    }
}
