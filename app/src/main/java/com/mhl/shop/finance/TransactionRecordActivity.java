package com.mhl.shop.finance;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ihidea.multilinechooselib.MultiLineChooseLayout;
import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.finance.adapter.TransactionRecordAdapter;
import com.mhl.shop.finance.been.TransactionRecord;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.AllListView;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2017-1-3 16:47
 * 描述：交易記錄
 */
public class TransactionRecordActivity extends MyBaseActivity implements AllListView.setOnRefreshListener,AllListView.setLOnRefreshListener {
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.title_right_textview)
    TextView titleRightTextview;
    @Bind(R.id.lv_data)
    AllListView lvData;
    @Bind(R.id.me_my_zero)
    LinearLayout meMyZero;
    @Bind(R.id.right_drawer)
    RelativeLayout right_drawer;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.flowLayout_time)
    MultiLineChooseLayout flowLayoutTime;
    @Bind(R.id.flowLayout_money)
    MultiLineChooseLayout flowLayoutMoney;
    @Bind(R.id.flowLayout_type)
    MultiLineChooseLayout flowLayoutType;
    @Bind(R.id.bt_cancle)
    Button bt_cancle;
    @Bind(R.id.bt_ok)
    Button bt_ok;
    private int page=1;//加载的页数
    private List<TransactionRecord.RowsBean> list;
    private TransactionRecordAdapter adapter;
    /** 右边栏打开/关闭状态 */
    private boolean isDirection_right = false;

    private String period="";
    private String fundType="";
    private String fundFlows="";
    private List<String> mDataListTime = new ArrayList<>();
    private List<String> mDataListMoney = new ArrayList<>();
    private List<String> mDataListType = new ArrayList<>();
    List<String> flowLayoutTimeResult = new ArrayList<>();
    List<String> flowLayoutTypeResult = new ArrayList<>();
    List<String> flowLayoutMoneyResult = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_record);
        ButterKnife.bind(this);
        initView();
      initData(true,page);
        initDataRight();
        lvData.setOnRefreshListener(this);
        lvData.setLOnRefreshListener(this);
        //流式布局
        flowLayoutTime.setOnItemClickListener(new MultiLineChooseLayout.onItemClickListener() {
            @Override
            public void onItemClick(int position, String text) {
                            if(text.equals("全部")){
                                period="";
                            }else if(text.equals("最近一周")){
                             period="7";
                             }else if(text.equals("最近一月")){
                                period="30";
                            }else if(text.equals("最近一年")){
                                period="365";
                            }else {
                                period="";
                            }

            }
        });
        flowLayoutMoney.setOnItemClickListener(new MultiLineChooseLayout.onItemClickListener() {
            @Override
            public void onItemClick(int position, String text) {
                    if(text.equals("全部")){
                        fundFlows="";
                    }else if(text.equals("支出")){
                        fundFlows="0";
                    }else if(text.equals("收入")){
                        fundFlows="1";
                    }else {
                        fundType="";

                }
            }
        });
        flowLayoutType.setOnItemClickListener(new MultiLineChooseLayout.onItemClickListener() {
            @Override
            public void onItemClick(int position, String text) {
                    if(text.equals("全部")){
                        fundType="";
                    }else if(text.equals("赠送金额")){
                        fundType="4";
                    }else if(text.equals("激活金额")){
                        fundType="6";
                    }else if(text.equals("可提现金额")){
                        fundType="1";
                    }else if(text.equals("其他")){
                        fundType="100";
                    }else {
                        fundType="";
                    }
                }
        });
    }

    private void initView() {
        titleCenterTextview.setText("交易记录");
        titleRightTextview.setVisibility(View.VISIBLE);
        titleRightTextview.setText("筛选");
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    public void onRefresh() {
        initData(false,1);
        page=1;
    }
    @Override
    public void onLoadingMore() {
        initData(false, ++page);
    }
    private void initData(boolean b, final int page) {
        OkGo.post(Urls.URL_RECORD_FUND)//
                .tag(this)
                .params("page",page)
                .params("rows", 15)
                .params("fundFlows", fundFlows)//0-支出 1-收入
                .params("fundType", fundType)//1-可提现余额 4-赠送金额 6-激活金额
                .params("period", period)//7-最近一周 30-最近一个月 365-最近一年
                .params("userRole", "1")//1-会员 2-服务站 3-运营中心 4-供应商
                .execute(new DialogCallback<LzyResponse<TransactionRecord>>(TransactionRecordActivity.this,b) {
                             @Override
                             public void onSuccess(LzyResponse<TransactionRecord> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 if (page==1){
                                     if (list!=null){
                                         list.clear();
                                     }
                                     list = lzyResponse.data.getRows();
                                     if (list != null) {
                                         if (list.size() < 1) {
                                             meMyZero.setVisibility(View.VISIBLE);
                                             lvData.setVisibility(View.GONE);
                                         } else {
                                             adapter = new TransactionRecordAdapter(TransactionRecordActivity.this, list);
                                             lvData.setAdapter(adapter);
                                             lvData.setOnRefreshComplete();
                                             lvData.setSelection(0);
                                         }
                                     }
                                 }else {
                                     if (lzyResponse.data.getRows().size()>0){
                                         list.addAll(lzyResponse.data.getRows());
                                         adapter.notifyDataSetChanged();
                                     }else {
                                         T.showShort(TransactionRecordActivity.this,"没有更多数据了");
                                     }
                                     lvData.hideFooterView();

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
    @OnClick({R.id.title_left_imageview, R.id.title_right_textview, R.id.bt_cancle, R.id.bt_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.title_right_textview:
                if (!isDirection_right) {// 右边栏关闭时，打开

                    mDrawerLayout.openDrawer(right_drawer);
                } else {// 右边栏打开时，关闭
                    mDrawerLayout.closeDrawer(right_drawer);
                }
                break;
            case R.id.bt_cancle:
                mDrawerLayout.closeDrawer(right_drawer);
                break;
            case R.id.bt_ok:
                mDrawerLayout.closeDrawer(right_drawer);
                initData(true,1);
                break;
        }
    }
    private void initDataRight() {
         if(mDataListTime!=null){
             mDataListTime.clear();
         }
        if(mDataListType!=null){
            mDataListType.clear();
        }
        if(mDataListMoney!=null){
            mDataListMoney.clear();
        }
        mDataListTime.add("全部");
        mDataListTime.add("最近一周");
        mDataListTime.add("最近一月");
        mDataListTime.add("最近一年");
        flowLayoutTime.setList(mDataListTime);
        flowLayoutTime.setIndexItemSelected(0);
        mDataListType.add("全部");
        mDataListType.add("赠送金额");
        mDataListType.add("激活金额");
        mDataListType.add("可提现金额");
        mDataListType.add("其他");
        flowLayoutType.setList(mDataListType);
        flowLayoutType.setIndexItemSelected(0);
        mDataListMoney.add("全部");
        mDataListMoney.add("收入");
        mDataListMoney.add("支出");
        flowLayoutMoney.setList(mDataListMoney);
        flowLayoutMoney.setIndexItemSelected(0);
    }
}
