package com.mhl.shop.me;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.login.been.Null;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2017-1-22 15:35
 * 描述：帮助与 反馈
 */
public class HelpActivity extends MyBaseActivity {
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.feedContent)
    EditText feedContent;
    @Bind(R.id.iphone)
    EditText iphone;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
        titleCenterTextview.setText("帮助与反馈");
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
    private void save() {
        if (TextUtils.isEmpty(feedContent.getText().toString())) {
            T.showShort(HelpActivity.this, "请完善信息");
            return;
        }
        Log.d("dddd",feedContent.getText().toString());
        Log.d("dddd", android.os.Build.MODEL+"@"+android.os.Build.VERSION.RELEASE);
        Log.d("dddd",iphone.getText().toString());

        OkGo.post(Urls.APP_FEEDBACK)
                .tag(this)
                .params("feedContent", feedContent.getText().toString())
                .params("clientType",2)
                .params("clientDetail", android.os.Build.MODEL+"_"+android.os.Build.VERSION.RELEASE)
                .params("feedContant", iphone.getText().toString())
                .execute(new DialogCallback<LzyResponse<Null>>(HelpActivity.this, true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> responseData, Call call, Response response) {
                                 handleResponse(responseData.data, call, response);
                                 if (responseData.code == 200) {
//                                     getActivity().finish();
                                     T.showShort(HelpActivity.this,"感谢您的宝贵意见，反馈成功！");
                                     finish();
                                 } else {
                                     T.showShort(HelpActivity.this, responseData.info);
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


    @OnClick({R.id.go_ly, R.id.title_left_imageview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_ly:
                save();
                break;
            case R.id.title_left_imageview:
                finish();
                break;
        }
    }
}
