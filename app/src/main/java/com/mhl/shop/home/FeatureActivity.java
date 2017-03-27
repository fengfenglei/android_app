package com.mhl.shop.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.home.adpter.FeatureAdapter;
import com.mhl.shop.home.been.Feature;
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
 * 时间；2017-1-22 10:34
 * 描述：特色馆
 */
public class FeatureActivity extends MyBaseActivity implements
        AdapterView.OnItemClickListener {

    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.listview)
    ListView listView;
    FragmentTransaction fragmentTransaction;
    private FeatureAdapter adapter;
    private FeatureFragment myFragment;
    public static int mPosition;
    private String name,id;
    private List<Feature.RowsBean> featureData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        titleCenterTextview.setText(intent.getStringExtra("title"));

        initData(true);
        //创建MyFragment对象
        myFragment = new FeatureFragment();
         fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, myFragment);
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
        private void initData(boolean b) {
        // 获取特色馆一级列表
        OkGo.post(Urls.APP_FEATURE_LIST)
                .tag(this)
                .execute(new DialogCallback<LzyResponse<Feature>>(FeatureActivity.this, b) {
                             @Override
                             public void onSuccess(LzyResponse<Feature> responseData, Call call, Response response) {
                                 handleResponse(responseData.data, call, response);
                                 if(featureData!=null){
                                     featureData=null;
                                 }
                                 if(responseData.data!=null){
                                     featureData= responseData.data.getRows();
                                        adapter = new FeatureAdapter(FeatureActivity.this, featureData);
                                     listView.setAdapter(adapter);

                                     listView.setOnItemClickListener(FeatureActivity.this);

                                     //通过bundle传值给MyFragment
                                     Bundle bundle = new Bundle();
                                     bundle.putString("name", featureData.get(mPosition).getTitle1());
                                     bundle.putString("id", featureData.get(mPosition).getPkId()+"");
                                     bundle.putString("img", featureData.get(mPosition).getImg2()+"");
                                     myFragment.setArguments(bundle);
                                     fragmentTransaction.commit();
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
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO Auto-generated method stub
        //拿到当前位置
        mPosition = position;
        //即使刷新adapter
        adapter.notifyDataSetChanged();
            myFragment = new FeatureFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, myFragment);
            Bundle bundle = new Bundle();
            bundle.putString("name", featureData.get(position).getTitle1());
            bundle.putString("id", featureData.get(position).getPkId()+"");
            bundle.putString("img", featureData.get(position).getImg2()+"");
            myFragment.setArguments(bundle);
            fragmentTransaction.commit();

    }

    @OnClick(R.id.title_left_imageview)
    public void onClick() {
        finish();
    }
}

