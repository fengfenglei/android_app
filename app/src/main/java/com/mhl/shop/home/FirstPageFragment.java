package com.mhl.shop.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.callback.StringDialogCallback;
import com.mhl.shop.homepage.MessageActivity;
import com.mhl.shop.login.LoginActivity;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.MipcaActivityCapture;
import com.mhl.shop.main.MyApplication;
import com.mhl.shop.main.MyBaseFragment;
import com.mhl.shop.main.StringDate;
import com.mhl.shop.main.WebActivity;
import com.mhl.shop.me.ExtensionCenterActivity;
import com.mhl.shop.search.SearchActivity;
import com.mhl.shop.search.ShopSearchResultActivity;
import com.mhl.shop.shopdetails.GoodsDetailsActivity;
import com.mhl.shop.shopdetails.SupplierActivity;
import com.mhl.shop.utils.Constant;
import com.mhl.shop.utils.GsonUtils;
import com.mhl.shop.utils.IntentUtils;
import com.mhl.shop.utils.SharePreferenceUtil;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2017-1-5 15:28
 * 描述：首页
 */
public class FirstPageFragment extends MyBaseFragment  implements HomeListView.setOnRefreshListener , View.OnClickListener {
    private Context mContext;
    private LayoutInflater mInflater;
    private TextView mHome_search_bar;
    private LinearLayout mHome_ll_scan;
    private RelativeLayout home_news;
    private View mRootView; // 根布局
    private View vHead;//头布局
    private List<String> imageUrlsOne =new ArrayList<>();//第一个轮播图
    private List<String> imageUrlsTow =new ArrayList<>();
    private HomeListView home_lv;
    private GridView gridview;
    private Banner banner;//第一个轮播图
    private Banner banner02;//品牌推荐轮播图
    private  Home homeData;//首页的数据对象
    private RecyclerView  mRecyclerView;
    private ImageView iv_msg_point,home_quick_iv_01,home_quick_iv_02,home_quick_iv_03,home_quick_iv_04;//快捷方式的图片
    private TextView home_quick_tv_01,home_quick_tv_02,home_quick_tv_03,home_quick_tv_04;//快捷方式的文字
    private  HomeListAdapter  adapter;
    private ImageView classified01_iv,classified02_iv,classified03_iv,classified04_iv,classified05_iv,classified06_iv;//分类精选
    private ImageView home_iv_hot_01,home_iv_hot_02,home_iv_hot_03,home_iv_hot_04;//快捷方式
    private GalleryAdapter mAdapter;//小郎精选
    private FrameLayout net_connecte_fail;//重新加载
    private FrameLayout loading_layout;//加载框
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        this.mInflater = LayoutInflater.from(mContext);// 从上下文获取构造器
        mRootView = (ViewGroup) inflater.inflate(R.layout.first_home, container, false);
        initView();
        initData(false);
        home_lv.setOnRefreshListener(this);
        return mRootView;

    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }
    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.getApplication().isLogin())//登录之后才去获取消息
        {
            isNews(false);
        }
//        Log.d("abbott", "result水水水水==onResume");
        MobclickAgent.onResume(getActivity());

    }


    StringDate stringDate;
    private void isNews(boolean b) {
       // 获取我的未读消息
        OkGo.post(Urls.URL_INFO_NOTREADNOTICE)//
                .tag(this)
                .execute(new StringDialogCallback(getActivity(), true) {
                             @Override
                             public void onSuccess(String s, Call call, Response response) {
                                 handleResponse(s, call, response);
                                 stringDate=(StringDate) GsonUtils.fromJson(s,
                                         StringDate.class);
                                 if(stringDate.code==200){
                            if(Integer.parseInt(stringDate.data)>0) {
                                iv_msg_point.setVisibility(View.VISIBLE);
                            }else {
                                iv_msg_point.setVisibility(View.INVISIBLE);
                            }
                                 }else{
                                     T.showShort(getActivity(),  stringDate.info);
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

    private void initView() {
        home_lv = (HomeListView)mRootView.findViewById(R.id.home_lv);
        net_connecte_fail = (FrameLayout)mRootView.findViewById(R.id.net_connecte_fail);
        loading_layout= (FrameLayout)mRootView.findViewById(R.id.loading_layout);
        mHome_search_bar = (TextView) mRootView.findViewById(R.id.home_search_tv);
        mHome_ll_scan = (LinearLayout) mRootView.findViewById(R.id.home_ll_scan);
        home_news= (RelativeLayout) mRootView.findViewById(R.id.home_news);
        iv_msg_point= (ImageView) mRootView.findViewById(R.id.iv_msg_point);
        mHome_search_bar.setOnClickListener(this);
        mHome_ll_scan.setOnClickListener(this);
        home_news.setOnClickListener(this);
        net_connecte_fail.setOnClickListener(this);
//头布局
        vHead = mInflater.inflate(R.layout.first_home_head, null);
//头布局放入listView中
        home_lv.addHeaderView(vHead);
//轮播图
        banner = (Banner)vHead.findViewById(R.id.banner);
//快捷方式
        home_quick_iv_01 = (ImageView)vHead.findViewById(R.id.home_quick_iv_01);
        home_quick_iv_02 = (ImageView)vHead.findViewById(R.id.home_quick_iv_02);
        home_quick_iv_03 = (ImageView)vHead.findViewById(R.id.home_quick_iv_03);
        home_quick_iv_04 = (ImageView)vHead.findViewById(R.id.home_quick_iv_04);
        home_quick_iv_01.setOnClickListener(this);   home_quick_iv_02.setOnClickListener(this);   home_quick_iv_03.setOnClickListener(this);   home_quick_iv_04.setOnClickListener(this);
        home_quick_tv_01 = (TextView)vHead.findViewById(R.id.home_quick_tv_01);
        home_quick_tv_02 = (TextView)vHead.findViewById(R.id.home_quick_tv_02);
        home_quick_tv_03 = (TextView)vHead.findViewById(R.id.home_quick_tv_03);
        home_quick_tv_04 = (TextView)vHead.findViewById(R.id.home_quick_tv_04);

//热门
        home_iv_hot_01 = (ImageView)vHead.findViewById(R.id.home_iv_hot_01);
        home_iv_hot_02 = (ImageView)vHead.findViewById(R.id.home_iv_hot_02);
        home_iv_hot_03 = (ImageView)vHead.findViewById(R.id.home_iv_hot_03);
        home_iv_hot_04 = (ImageView)vHead.findViewById(R.id.home_iv_hot_04);
        home_iv_hot_01.setOnClickListener(this);   home_iv_hot_02.setOnClickListener(this);   home_iv_hot_03.setOnClickListener(this);   home_iv_hot_04.setOnClickListener(this);

//分类精选
        classified01_iv = (ImageView)vHead.findViewById(R.id.classified01_iv);
        classified02_iv = (ImageView)vHead.findViewById(R.id.classified02_iv);
        classified03_iv = (ImageView)vHead.findViewById(R.id.classified03_iv);
        classified04_iv = (ImageView)vHead.findViewById(R.id.classified04_iv);
        classified05_iv = (ImageView)vHead.findViewById(R.id.classified05_iv);
        classified06_iv = (ImageView)vHead.findViewById(R.id.classified06_iv);
        classified01_iv.setOnClickListener(this);   classified02_iv.setOnClickListener(this);   classified03_iv.setOnClickListener(this);
        classified04_iv.setOnClickListener(this);classified05_iv.setOnClickListener(this);   classified06_iv.setOnClickListener(this);


        mRecyclerView = (RecyclerView)vHead.findViewById(R.id.id_recyclerview_horizontal);
       //小狼精选绑定适配器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
//品牌热卖轮播图
         banner02 = (Banner)vHead.findViewById(R.id.banner02);
      //轮播图点击事件
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                classIntent(homeData.getCarousel().get(position-1).getKeyType(),homeData.getCarousel().get(position-1).getKeyValue(),homeData.getCarousel().get(position-1).getTitle());
            }
        });
        //品牌热卖轮播图点击事件
        banner02.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
//                classIntent(homeData.getBrand().get(position-1).getKeyType(),homeData.getBrand().get(position-1).getKeyValue(),homeData.getBrand().get(position-1).getTitle());
//                classShopIntent(homeData.getBrand().get(position-1).getTitle(),homeData.getBrand().get(position-1).getKeyValue());
                Intent intent = new Intent(getActivity(), ShopSearchResultActivity.class);
                intent.putExtra("shopkey",homeData.getBrand().get(position-1).getTitle());
                intent.putExtra("classid","");
                intent.putExtra("type", "");
                startActivity(intent);
            }
        });
    }
    //首页跳转的判断分类
    private void classIntent(String keyType, String keyValue, String Title) {
        //-1商品--2供应商--4活动
        if(keyType.equals("1")){
            Intent intent = new Intent();
            intent.putExtra("goodsid",keyValue);
            intent.setClass(getActivity(), GoodsDetailsActivity.class);
            startActivity(intent);
        }else if(keyType.equals("2")){
            Intent intent1 = new Intent(getActivity(),SupplierActivity.class);
            intent1.putExtra("supplierId", keyValue);
            startActivity(intent1);
        }else if(keyType.equals("4")){
            Intent intent1 = new Intent(getActivity(),WebActivity.class);
            if(keyValue.indexOf("?")!=-1){
                intent1.putExtra(Constant.LBONCLICKURL, keyValue+"&access_token="+new SharePreferenceUtil(MyApplication.getContext()).getAccess_token());
            }else{
                intent1.putExtra(Constant.LBONCLICKURL, keyValue+"?access_token="+new SharePreferenceUtil(MyApplication.getContext()).getAccess_token());
            }
            intent1.putExtra(Constant.TITLE, Title);
            startActivity(intent1);
        }

    }

    private void initData(boolean b) {
        loading_layout.setVisibility(View.VISIBLE);
// 获取我的首页
        OkGo.post(Urls.APP_HOME_PAGE)//
                .tag(this)
                .cacheKey("home")    //
                .cacheTime(5000)         // 缓存的过期时间,单位毫秒
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new DialogCallback<LzyResponse<Home>>(getActivity(), b) {
                             @Override
                             public void onSuccess(LzyResponse<Home> responseData, Call call, Response response) {
                                 handleResponse(responseData.data, call, response);
                                 if(homeData!=null){
                                     homeData=null;
                                 }
                                 if(responseData.code==200){
                                     if(responseData.data!=null){
                                         homeData= responseData.data;
                                         setData();
                                         net_connecte_fail.setVisibility(View.GONE);

                                     } else {
                                         net_connecte_fail.setVisibility(View.VISIBLE);

                                     }
                                 }else {
                                     net_connecte_fail.setVisibility(View.VISIBLE);
                                 }
                                 loading_layout.setVisibility(View.GONE);
                             }
                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                                 handleError(call, response, e);
                                 net_connecte_fail.setVisibility(View.VISIBLE);
                                 loading_layout.setVisibility(View.GONE);
                             }
                         }

                );
    }
    private void setData() {
        //轮播图
        if (imageUrlsOne!=null){
            imageUrlsOne.clear();
        }
               for (int j = 0; j < homeData.getCarousel().size(); j++) {
            imageUrlsOne.add(Urls.URL_BASE+"/file/v4/download-"+homeData.getCarousel().get(j).getImg()+"-1400-800.jpg");
        }

        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(imageUrlsOne);
        banner.setDelayTime(3000);
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.start();
        //轮播图
        if (imageUrlsTow!=null){
            imageUrlsTow.clear();
        }
        for (int i = 0; i < homeData.getBrand().size(); i++) {
            imageUrlsTow.add(Urls.URL_BASE+"/file/v4/download-"+homeData.getBrand().get(i).getImg()+"-1400-480.jpg");
        }
        banner02.setImageLoader(new GlideImageLoader());
        banner02.setImages(imageUrlsTow);
        banner02.setDelayTime(3000);
        banner02.setIndicatorGravity(BannerConfig.RIGHT);
        banner02.start();

        //四个快捷方式
        Glide.with(getActivity()).load(Urls.URL_PHOTO+"/file/v1/download-"+homeData.getShortcut().get(0).getImg()+".png").placeholder(R.drawable.icon_bg).into(home_quick_iv_01);
        Glide.with(getActivity()).load(Urls.URL_PHOTO+"/file/v1/download-"+homeData.getShortcut().get(1).getImg()+".png").placeholder(R.drawable.icon_bg).into(home_quick_iv_02);
        Glide.with(getActivity()).load(Urls.URL_PHOTO+"/file/v1/download-"+homeData.getShortcut().get(2).getImg()+".png").placeholder(R.drawable.icon_bg).into(home_quick_iv_03);
        Glide.with(getActivity()).load(Urls.URL_PHOTO+"/file/v1/download-"+homeData.getShortcut().get(3).getImg()+".png").placeholder(R.drawable.icon_bg).into(home_quick_iv_04);

        home_quick_tv_01.setText(homeData.getShortcut().get(0).getTitle());
        home_quick_tv_02.setText(homeData.getShortcut().get(1).getTitle());
        home_quick_tv_03.setText(homeData.getShortcut().get(2).getTitle());
        home_quick_tv_04.setText(homeData.getShortcut().get(3).getTitle());
        //小郎精选设置适配器
        mAdapter = new GalleryAdapter(mContext, homeData.getLang());
        mRecyclerView.setAdapter(mAdapter);

        //为你推荐
        adapter = new HomeListAdapter(getActivity(), homeData.getRecommend());
        home_lv.setAdapter(adapter);
        home_lv.setOnRefreshComplete();
        //热门精选https://cdn.51mhl.com/file/v3/download-8590b10d6de44d08b8f99e85583d2055-1080-1080.jpg
        Glide.with(getActivity()).load(Urls.URL_PHOTO+"/file/v3/download-"+homeData.getHot().get(0).getImg()+"-1080-1080.jpg").placeholder(R.drawable.icon_bg).into(home_iv_hot_01);
        Glide.with(getActivity()).load(Urls.URL_PHOTO+"/file/v3/download-"+homeData.getHot().get(1).getImg()+"-1080-840.jpg").placeholder(R.drawable.icon_bg).into(home_iv_hot_02);
        Glide.with(getActivity()).load(Urls.URL_PHOTO+"/file/v3/download-"+homeData.getHot().get(2).getImg()+"-900-900.jpg").placeholder(R.drawable.icon_bg).into(home_iv_hot_03);
        Glide.with(getActivity()).load(Urls.URL_PHOTO+"/file/v3/download-"+homeData.getHot().get(3).getImg()+"-900-900.jpg").placeholder(R.drawable.icon_bg).into(home_iv_hot_04);
        //分类精选
        Glide.with(getActivity()).load(Urls.URL_PHOTO+"/file/v3/download-"+homeData.getClassified().get(0).getImg()+"-1270-500.jpg").placeholder(R.drawable.icon_bg).into(classified01_iv);
        Glide.with(getActivity()).load(Urls.URL_PHOTO+"/file/v3/download-"+homeData.getClassified().get(1).getImg()+"-1270-500.jpg").placeholder(R.drawable.icon_bg).into(classified02_iv);
        Glide.with(getActivity()).load(Urls.URL_PHOTO+"/file/v3/download-"+homeData.getClassified().get(2).getImg()+"-1270-500.jpg").placeholder(R.drawable.icon_bg).into(classified03_iv);
        Glide.with(getActivity()).load(Urls.URL_PHOTO+"/file/v3/download-"+homeData.getClassified().get(3).getImg()+"-1270-500.jpg").placeholder(R.drawable.icon_bg).into(classified04_iv);
        Glide.with(getActivity()).load(Urls.URL_PHOTO+"/file/v3/download-"+homeData.getClassified().get(4).getImg()+"-1270-500.jpg").placeholder(R.drawable.icon_bg).into(classified05_iv);
        Glide.with(getActivity()).load(Urls.URL_PHOTO+"/file/v3/download-"+homeData.getClassified().get(5).getImg()+"-1270-500.jpg").placeholder(R.drawable.icon_bg).into(classified06_iv);

    }

    @Override
    public void onRefresh() {
        initData(false);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
                /*点击重新加载*/
            case R.id.net_connecte_fail:
                if (MyApplication.getApplication().isLogin())//登录之后才去获取消息
                {
                    isNews(false);
                }
                initData(false);

                break;
            /*顶部栏的操作*/
            case R.id.home_search_tv:
                //
                IntentUtils.startActivity(getActivity(), SearchActivity.class);
                break;
            case R.id.home_ll_scan:
                //
//                IntentUtils.startActivity(getActivity(), MipcaActivityCapture.class);
                Intent mipca = new Intent(getActivity(), MipcaActivityCapture.class);
                getActivity().startActivity(mipca);
                break;
            case R.id.home_news:
                //货郎消息

                if (!MyApplication.getApplication().isLogin())
                {
                    Intent login = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(login);
                }
                else
                {
                    IntentUtils.startActivity(getActivity(), MessageActivity.class);
                }
                break;
            /*快捷方式*/
            case R.id.home_quick_iv_01:

                quick(homeData.getShortcut().get(0).getKeyType(),0);
                break;
            case R.id.home_quick_iv_02:
                quick(homeData.getShortcut().get(1).getKeyType(), 1);


                break;
            case R.id.home_quick_iv_03:
                quick(homeData.getShortcut().get(2).getKeyType(), 2);
                break;
            case R.id.home_quick_iv_04:
                quick(homeData.getShortcut().get(3).getKeyType(), 3);

                break;
             /*热门*/
            case R.id.home_iv_hot_01:
                //
                classIntent(homeData.getHot().get(0).getKeyType(),homeData.getHot().get(0).getKeyValue(),homeData.getHot().get(0).getTitle());
                break;
            case R.id.home_iv_hot_02:
                //
                classIntent(homeData.getHot().get(1).getKeyType(),homeData.getHot().get(1).getKeyValue(),homeData.getHot().get(1).getTitle());
                break;
            case R.id.home_iv_hot_03:
                //
                classIntent(homeData.getHot().get(2).getKeyType(),homeData.getHot().get(2).getKeyValue(),homeData.getHot().get(2).getTitle());
                break;
            case R.id.home_iv_hot_04:
                //
                classIntent(homeData.getHot().get(3).getKeyType(),homeData.getHot().get(3).getKeyValue(),homeData.getHot().get(3).getTitle());
                break;
            /*分类精选*/
            case R.id.classified01_iv:
                //
                classShopIntent(homeData.getClassified().get(0).getTitle(),homeData.getClassified().get(0).getClassId()+"");
                    break;
            case R.id.classified02_iv:
                //
                classShopIntent(homeData.getClassified().get(1).getTitle(),homeData.getClassified().get(1).getClassId()+"");
                 break;
            case R.id.classified03_iv:
                //
                classShopIntent(homeData.getClassified().get(2).getTitle(),homeData.getClassified().get(2).getClassId()+"");
                break;
            case R.id.classified04_iv:
                //
                classShopIntent(homeData.getClassified().get(3).getTitle(),homeData.getClassified().get(3).getClassId()+"");
                break;
            case R.id.classified05_iv:
                //
                classShopIntent(homeData.getClassified().get(4).getTitle(),homeData.getClassified().get(4).getClassId()+"");
                break;
            case R.id.classified06_iv:
                //
                classShopIntent(homeData.getClassified().get(5).getTitle(),homeData.getClassified().get(5).getClassId()+"");
                break;
        }
    }

    private void quick(String keyType, int i) {
        if(keyType.equals("1")){
            //特色馆
            Intent intent = new Intent(getActivity(), FeatureActivity.class);
            intent.putExtra("title",homeData.getShortcut().get(0).getTitle());
            startActivity(intent);
        }else  if(keyType.equals("2")){
            //话费充值


            if (!MyApplication.getApplication().isLogin())
            {
                Intent login = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(login);
            }
            else
            {
                IntentUtils.startActivity(getActivity(), PrepaidrechargeActivity.class);
            }
        }else  if(keyType.equals("3")){
            //签到

            if (!MyApplication.getApplication().isLogin())
            {
                Intent login = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(login);
            }
            else
            {
                Intent intent4 = new Intent(getActivity(),WebActivity.class);
                intent4.putExtra(Constant.TITLE, "每日签到");
                intent4.putExtra(Constant.LBONCLICKURL, Urls.URL_BASE+"/sign-in.html"+"?access_token="+new SharePreferenceUtil(MyApplication.getContext()).getAccess_token());
                startActivity(intent4);
            }
        }    else  if(keyType.equals("4")){      //推广中心
        if (!MyApplication.getApplication().isLogin())
        {
            Intent login = new Intent(getActivity(), LoginActivity.class);
            getActivity().startActivity(login);
        }
        else
        {
            Intent activeIntent = new Intent(getActivity(),ExtensionCenterActivity.class);
            startActivity(activeIntent);
        }}   else  if(keyType.equals("5")){
            //活动
            Intent intent1 = new Intent(getActivity(),WebActivity.class);
            if(homeData.getShortcut().get(i).getUrl().indexOf("?")!=-1){
                intent1.putExtra(Constant.LBONCLICKURL, homeData.getShortcut().get(i).getUrl()+"&access_token="+new SharePreferenceUtil(MyApplication.getContext()).getAccess_token());
            }else{
                intent1.putExtra(Constant.LBONCLICKURL, homeData.getShortcut().get(i).getUrl()+"?access_token="+new SharePreferenceUtil(MyApplication.getContext()).getAccess_token());
            }
            intent1.putExtra(Constant.TITLE, homeData.getShortcut().get(i).getTitle());
            startActivity(intent1);
        }

    }

    private void classShopIntent(String title, String classid) {
        Intent intent = new Intent(getActivity(), ShopSearchResultActivity.class);
        intent.putExtra("shopkey",title);
        intent.putExtra("classid",classid);
        intent.putExtra("type", "class");
        startActivity(intent);
    }
}
