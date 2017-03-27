package com.mhl.shop.homepage;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.home.adpter.MessageAdapter;
import com.mhl.shop.homepage.bean.Message;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.MyBaseActivity;
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
 * 时间；2017-1-19 15:38
 * 描述：货郎消息
 */
public class MessageActivity extends MyBaseActivity {
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.lv_data)
    ListView lvData;
    @Bind(R.id.me_my_zero)
    LinearLayout meMyZero;
    MessageAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_message);
        ButterKnife.bind(this);
        initView();

    }
    public void onResume() {
        super.onResume();
        initData(true);
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initView() {
        titleCenterTextview.setText("消息中心");
    }

    private void initData(boolean b) {
        // 获取我的未读消息
        OkGo.post(Urls.URL_INFO_NOTICE_GROUP)//
                .tag(this)
                .execute(new DialogCallback<LzyResponse<List<Message>>>(MessageActivity.this, b) {
                             @Override
                             public void onSuccess(LzyResponse<List<Message>> responseData, Call call, Response response) {
                                 handleResponse(responseData.data, call, response);
                                 if(responseData.data!=null){
                                     if (responseData.data.size() < 1) {
                                         meMyZero.setVisibility(View.VISIBLE);
                                         lvData.setVisibility(View.GONE);
                                     } else {
                                         adapter = new MessageAdapter(MessageActivity.this, responseData.data);
                                         lvData.setAdapter(adapter);
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


    @OnClick(R.id.title_left_imageview)
    public void onClick() {
        finish();
    }
}
