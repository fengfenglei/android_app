package com.mhl.shop.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mhl.shop.R;
import com.mhl.shop.main.photoView.HackyViewPager;
import com.mhl.shop.main.photoView.PhotoView;
import com.mhl.shop.main.photoView.PhotoViewAttacher;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：LFF
 * 时间；2016-12-12 18:18
 * 描述：大图显示
 */
public class ViewPagerActivity extends MyBaseActivity {

    private static final String ISLOCKED_ARG = "isLocked";

    private ViewPager mViewPager;
    //	private LoadingCircleView loading_cirle_view;
    private MenuItem menuLockItem;
    private int position = 0;
    private ArrayList<String> mainList = null;
    private int pageSize = 0;
    private TextView txtNumber = null;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

        };
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainList = new ArrayList<>();
        setContentView(R.layout.activity_view_pager);
        mViewPager = (HackyViewPager) findViewById(R.id.view_pager);
        txtNumber = (TextView) findViewById(R.id.num_view_frme_txt);

        position = getIntent().getIntExtra("position", 0); //你点击了是哪个图片
        mainList = getIntent().getStringArrayListExtra("mainImg");
//        mainList = new ArrayList<>();
//        mainList.add("http://img.ivsky.com/img/bizhi/pre/201601/27/february_2016-001.jpg");
//        mainList.add("http://img.ivsky.com/img/bizhi/pre/201601/27/february_2016-002.jpg");
//        mainList.add("http://img.ivsky.com/img/bizhi/pre/201601/27/february_2016-003.jpg");
//        mainList.add("http://img.ivsky.com/img/bizhi/pre/201601/27/february_2016-004.jpg");
//        mainList.add("http://img.ivsky.com/img/tupian/pre/201511/16/chongwugou.jpg");
        System.out.println(mainList);

        txtNumber.setVisibility(View.VISIBLE);  //显示
        txtNumber.setText((position+1)+"/"+mainList.size());  //默认显示1

        mViewPager.setAdapter(new SamplePagerAdapter(position,mainList));

        if (savedInstanceState != null) {
            boolean isLocked = savedInstanceState.getBoolean(ISLOCKED_ARG, false);
            ((HackyViewPager) mViewPager).setLocked(isLocked);
        }
        mViewPager.setCurrentItem(position);


        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
//				System.out.println("当前的页数:"+arg0);
                txtNumber.setText((arg0+1)+"/"+mainList.size());
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }
            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }
    /**
     * 商品详情顶部图片展示ViewPager的适配器
     * @author lq
     *
     */
    class SamplePagerAdapter extends PagerAdapter {

        private int mIndex = 0;
        private List<String> mainImageList =  new ArrayList<>();

        public SamplePagerAdapter(int index,ArrayList<String> m_mainImgList){
            this.mIndex = index;
//            this.mainImageList = new ArrayList(Arrays.asList(m_mainImgList));
            this.mainImageList = m_mainImgList;
        }

        @Override
        public int getCount() {
            pageSize = mainImageList.size();
            return pageSize;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            final PhotoView photoView = new PhotoView(container.getContext());
            String bmpImg = mainImageList.get(position);  //加载图片
            if (bmpImg.equals("")) {
                T.showShort(ViewPagerActivity.this, "加载图片失败");
            }else {
                Glide.with(MyApplication.getContext()).load(Urls.URL_PHOTO+"/file/v2/download-"+bmpImg+".jpg").placeholder(R.drawable.icon_bg).into(photoView);

            }

            photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    finish();
                    overridePendingTransition(0, R.anim.activity_zoomout);
                }
            });

            container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

            return photoView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.viewpager_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menuLockItem = menu.findItem(R.id.menu_lock);
        toggleLockBtnTitle();
        menuLockItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                toggleViewPagerScrolling();
                toggleLockBtnTitle();
                return true;
            }
        });

        return super.onPrepareOptionsMenu(menu);
    }

    private void toggleViewPagerScrolling() {
        if (isViewPagerActive()) {
            ((HackyViewPager) mViewPager).toggleLock();
        }
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    private void toggleLockBtnTitle() {
        boolean isLocked = false;
        if (isViewPagerActive()) {
            isLocked = ((HackyViewPager) mViewPager).isLocked();
        }
        String title = (isLocked) ? getString(R.string.menu_unlock) : getString(R.string.menu_lock);
        if (menuLockItem != null) {
            menuLockItem.setTitle(title);
        }
    }

    private boolean isViewPagerActive() {
        return (mViewPager != null && mViewPager instanceof HackyViewPager);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (isViewPagerActive()) {
            outState.putBoolean(ISLOCKED_ARG, ((HackyViewPager) mViewPager).isLocked());
        }
        super.onSaveInstanceState(outState);
    }

    private void loadingImageView() {
        Thread t = new Thread() {
            @Override
            public void run() {
                int i = 0;
                while (i <= 100) {
                    try {
                        i++;
                        handler.sendEmptyMessage(i);
                        this.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                super.run();
            }
        };
        t.start();
    }
}

