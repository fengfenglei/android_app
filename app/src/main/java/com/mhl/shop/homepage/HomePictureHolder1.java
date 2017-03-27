package com.mhl.shop.homepage;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.mhl.shop.R;
import com.mhl.shop.homepage.bean.HomeBean;
import com.mhl.shop.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Administrator
 * 时间；2016-11-11 18:20
 * 描述：
 */
public class HomePictureHolder1 extends BaseHolder<List<HomeBean.DataEntity.HomeConfigEntity.HomePagePicListEntity>> implements ViewPager.OnPageChangeListener
{
    private Context mContext;
    private TouchedViewPager	mPager;

    private LinearLayout mPointContainer;

//	private List<String>		mPictureDatas;

    private AutoSwitchTask		mSwitchTask;
    private List<HomeBean.DataEntity.HomeConfigEntity.HomePagePicListEntity> 	mData;
    private HomeBean.DataEntity.HomeConfigEntity.HomePagePicListEntity lbData;
    private String				lbClickUrl	= "";
    private String				lbClickId	= "";
    private String				lbTitle		= "";	// 跳转的标题
    private List<String>		lbPicUrlList;		// 轮播图显示加载图片的url
    private List<String>		lbClickUrlList;	// 点击轮播图片要跳转的url
    private List<Boolean>		goodsOrNotList;	// 轮播图 判断是不是商品
    private List<Integer>		lbClickIdList;		// 轮播图 是商品的时候的Id
    private	List<String>		lbTitleList;		// 跳转的标题集合
    private	List<String>		lbRuleList;		// 跳转的标题集合
    ImageView mIvAdvertise;		// 轮播图控件
    private String				activityRulePic		= "nourl";
    private String				activityName;
    public HomePictureHolder1() {
        super();
    }

    public HomePictureHolder1(Context context) {
        this.mContext = context;
    }

    @Override
    protected View initView()
    {
        View view = View.inflate(UIUtils.getContext(), R.layout.advertisement_board, null);
        mPager = (TouchedViewPager) view.findViewById(R.id.vp_advertise);
        mPointContainer = (LinearLayout) view.findViewById(R.id.ll_mPointContainer);
        // new集合对象
        lbPicUrlList = new ArrayList<String>();
        lbClickUrlList = new ArrayList<String>();
        lbTitleList = new ArrayList<String>();// 不是商品的title
        lbRuleList = new ArrayList<String>();// 活动规则
        lbClickIdList = new ArrayList<Integer>();
        goodsOrNotList = new ArrayList<Boolean>();
        // 新建轮播任务
        mSwitchTask = new AutoSwitchTask();
        return view;
    }

    @Override
    protected void refreshUI(List<HomeBean.DataEntity.HomeConfigEntity.HomePagePicListEntity> data)
    {
        // 铺数据
        mData = data;
        for (int i = 0; i < mData.size(); i++)
        {
            int size = mData.size();
            HomeBean.DataEntity.HomeConfigEntity.HomePagePicListEntity lbDataEntity = mData.get(i);
            boolean goodsOrNot = lbDataEntity.isGoodsOrNot();
            if (!goodsOrNot)// 不是商品 的时候进来
            {
                String picUrl = lbDataEntity.getPicPath();// 拿到轮播图图片的url
                String lbClickUrl = lbDataEntity.getUrl();// 拿到轮播图点击事件的url
                String lbTitlekUrl = lbDataEntity.getTitle();// 拿到轮播图点击跳转的标题
                lbPicUrlList.add("http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg");
                lbClickUrlList.add(lbClickUrl);
                lbTitleList.add(lbTitlekUrl);
                lbClickIdList.add(null);
            }
            else
            {// 是商品
                String picUrl = lbDataEntity.getPicPath();// 拿到轮播图图片的url
                int goodsId = lbDataEntity.getGoodsId();
                lbPicUrlList.add("http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg");
                lbClickIdList.add(goodsId);
                lbClickUrlList.add(null);
                lbTitleList.add(null);
            }
            goodsOrNotList.add(goodsOrNot);// 把是不是商品的判断加入集合
        }

        // 加载点
        mPointContainer.removeAllViews();
        if (mData != null)
        {
            for (int i = 0; i < mData.size(); i++)
            {
                View point = new View(UIUtils.getContext());
                point.setBackgroundResource(R.drawable.point_yellow_normal);

                //设置点到底部的距离
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIUtils.dip2px(8), UIUtils.dip2px(8));
                params.leftMargin = UIUtils.dip2px(8);
                params.bottomMargin = UIUtils.dip2px(4);
                mPointContainer.addView(point, params);

                if (i == 0)
                {
                    point.setBackgroundResource(R.drawable.point_red_lighted);
                }
            }
        }

        // 给viewpager设置数据 Adapter ---> list
        mPager.setAdapter(new HomePictureAdapter());

        // 设置ViewPager的监听
        mPager.setOnPageChangeListener(this);

        mPager.setCurrentItem(mData.size() * 1000);

        // 开启轮播任务
        mSwitchTask.start();

        mPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                int action = event.getAction();
                switch (action)
                {
                    case MotionEvent.ACTION_DOWN:
                        mSwitchTask.stop();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        mSwitchTask.start();
                        break;
                    default:
                        break;
                }

                return false;
            }
        });
    }

    class AutoSwitchTask implements Runnable
    {
        public static final long	DELAY	= 3500;

        public void start()
        {
            stop();
            // 执行延时操作
            UIUtils.postDelayed(this, DELAY);
        }

        public void stop()
        {
            UIUtils.removeCallbacks(this);
        }

        @Override
        public void run()
        {
            // 选中下一个
            int item = mPager.getCurrentItem();
            mPager.setCurrentItem(++item);

            UIUtils.postDelayed(this, DELAY);
        }

    }

    class HomePictureAdapter extends PagerAdapter
    {

        @Override
        public int getCount()
        {
            // if (mPictureDatas != null) { return mPictureDatas.size(); }
            if (mData != null) { return Integer.MAX_VALUE; }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {


            ImageView iv = new ImageView(UIUtils.getContext());

            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            // 设置默认图标
            //	iv.setImageResource(R.drawable.home_lb_default);

            // 加载图片
            position = position % mData.size();

            final int POSITION = position;

            final HomeBean.DataEntity.HomeConfigEntity.HomePagePicListEntity lbDataEntity = mData.get(POSITION);
            // //多次调用-->>多次解析
            try
            {
                String advertise_img_url = lbPicUrlList.get(POSITION).toString();
                /**
                 * 显示图片 参数1：图片url 参数2：显示图片的控件 参数3：显示图片的设置 参数4：监听器
                 */
                Glide.with(UIUtils.getContext()).load(advertise_img_url).placeholder(R.drawable.icon_bg).into(iv);

                container.addView(iv);

                iv.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v)
                    {
                        if (UIUtils.isFastClick())//防止连续点击
                        {
                            return;
                        }else {
                            boolean goodsOrNot = goodsOrNotList.get(POSITION).equals(true);// 如果是商品
                            if (goodsOrNot)
                            {// 是商品
                                lbClickId = lbClickIdList.get(POSITION).toString();// 拿商品Id
//                                Intent intent = new Intent((MainActivity) mContext, GoodsDetailsActivity.class);  //GoodsDetailsActivity
//                                intent.putExtra(ConstantBean.CONSTAN_PRODUCT_GOODSID, lbClickId);
//                                mContext.startActivity(intent);
                            }
                            else
                            {// 不是商品的时候
//                                lbClickUrl = lbClickUrlList.get(POSITION).toString();// 网页的url
//                                lbTitle = lbTitleList.get(POSITION).toString();// 网页的标题
//                                mData.get(POSITION).isActivityRuleOrNot();
//                                Intent intent = new Intent((MainActivity) mContext, PiDanActivity.class);
//                                intent.putExtra(Common.LBONCLICKURL, lbClickUrl);
//                                intent.putExtra("activityName", lbTitle);
//                                intent.putExtra("activityUrl", activityRulePic);
//                                mContext.startActivity(intent);
                            }
                        }

                    }
                });
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View) object);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int position)
    {
        // 选中时切换点
        int count = mPointContainer.getChildCount();
        for (int i = 0; i < count; i++)
        {
            // // 设置默认

            mPointContainer.getChildAt(i).setBackgroundResource((i == position % count) ? R.drawable.point_red_lighted : R.drawable.point_yellow_normal);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {
        // TODO Auto-generated method stub

    }
}

