package com.mhl.shop.homepage;

/**
 * 作者：lff
 * 时间；2016-11-10 14:12
 * 描述：首页
 */


public class HomeFragment {
//    @Bind(R.id.header)
//    RecyclerViewHeader mHeader;
//
//    @Bind(R.id.recycler)
//    RecyclerView mRecyclerView;
//    @Bind(R.id.rotate_header_list_view_frame)
//    PtrClassicFrameLayout mPtrFrame ;
//    MyAdapter mAdapter;
//    private List<ImageView> views = new ArrayList<ImageView>();
//    private CycleViewPager cycleViewPager;
//    private List<ADInfo> infos = new ArrayList<ADInfo>();
//    private View mRootView; // 根布局
//    private LinearLayout mLinearLayout;//顶部
//    private ImageView iv_msg_point;//消息
//    private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
//            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
//            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
//            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
//            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};
//
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        mRootView = inflater.inflate(R.layout.fragment_home, container,
//                false);
//        ButterKnife.bind(this, mRootView);
//        configImageLoader();
//        initialize();
//        initViews();
//        return mRootView;
//    }
//
//    private void initViews() {
//
//        mAdapter = new MyAdapter(getActivity(),getList());
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mRecyclerView.setAdapter(mAdapter);
//
//        //设置布局管理器 , 将布局设置成纵向
//        LinearLayoutManager linerLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        mRecyclerView.setLayoutManager(linerLayoutManager);
//          //参数含义显而易见，2就是2列，第二个参数是垂直方向
//        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        // 将顶部视图与RecyclerView关联即可
//        mHeader.attachTo(mRecyclerView, true);
//
//        //绑定头部搜索框
//        mLinearLayout = (LinearLayout)mRootView.findViewById(R.id.ll_home_bg);
//        mLinearLayout.getBackground().setAlpha(0);
//
//        iv_msg_point = (ImageView) mRootView.findViewById(R.id.iv_msg_point);
//    }
//    private List<String> getList() {
//        List<String> list = new ArrayList<>();
//        for (int i = 0;i < 50;i++) {
//            list.add("Item" + i);
//        }
//        return list;
//    }
//    @SuppressLint("NewApi")
//    private void initialize() {
//        cycleViewPager = (CycleViewPager) getChildFragmentManager()
//                .findFragmentById(R.id.fragment_cycle_viewpager_content);
//        for(int i = 0; i < imageUrls.length; i ++){
//            ADInfo info = new ADInfo();
//            info.setUrl(imageUrls[i]);
//            info.setContent("图片-->" + i );
//            infos.add(info);
//        }
//        // 将最后一个ImageView添加进来
//        views.add(ViewFactory.getImageView(getActivity(), infos.get(infos.size() - 1).getUrl()));
//        for (int i = 0; i < infos.size(); i++) {
//            views.add(ViewFactory.getImageView(getActivity(), infos.get(i).getUrl()));
//        }
//        // 将第一个ImageView添加进来
//        views.add(ViewFactory.getImageView(getActivity(), infos.get(0).getUrl()));
//
//        // 设置循环，在调用setData方法前调用
//        cycleViewPager.setCycle(true);
//
//        // 在加载数据前设置是否循环
//        cycleViewPager.setData(views, infos, mAdCycleViewListener);
//        //设置轮播
//        cycleViewPager.setWheel(true);
//
//        // 设置轮播时间，默认5000ms
//        cycleViewPager.setTime(2000);
//        //设置圆点指示图标组居中显示，默认靠右
////        cycleViewPager.setIndicatorCenter();
//    }
//    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {
//
//        @Override
//        public void onImageClick(ADInfo info, int position, View imageView) {
//            if (cycleViewPager.isCycle()) {
//                position = position - 1;
//                Toast.makeText(getActivity(),
//                        "position-->" + info.getContent(), Toast.LENGTH_SHORT)
//                        .show();
//            }
//
//        }
//
//    };
//    /**
//     * 控制首页上滑的时候顶部透明度的变化
//     */
//    public void onHomeScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//        // 第一个item布局
//        if (firstVisibleItem == 1) {
//            View view = mRecyclerView.getChildAt(0);
//            int top = -view.getTop();
//            // int height = view.getHeight();
//            int height = 500;
//            // 满足这个条件的时候，是头布局在XListview的最上面第一个控件的时候，只有这个时候，我们才调整透明度
//            if (top <= height && top >= 0) {
//                // 获取当前位置占头布局高度的百分比
//                float f = (float) top / (float) height;
//                mLinearLayout.getBackground().setAlpha((int) (f * 255));
//                // 通知标题栏刷新显示
//                mLinearLayout.invalidate();
//                iv_msg_point.setImageResource(R.drawable.red_point);
//            } else {
//                mLinearLayout.getBackground().setAlpha(255);
//                iv_msg_point.setImageResource(R.drawable.white_point);
//            }
//        } else if (firstVisibleItem > 0) {
//            mLinearLayout.getBackground().setAlpha(255);
//            if (mLinearLayout == null) {
//                mLinearLayout.setVisibility(View.VISIBLE);
//            }
//            // 显示小火箭
////            mToTop.setVisibility(View.VISIBLE);
//        } else {
////            mToTop.setVisibility(View.INVISIBLE);
//            mLinearLayout.getBackground().setAlpha(0);
//        }
//
//    }
//
//    /**
//     * 配置ImageLoder
//     */
//    private void configImageLoader() {
//        // 初始化ImageLoader
//        @SuppressWarnings("deprecation")
//        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.ic_launcher) // 设置图片下载期间显示的图片
//                .showImageForEmptyUri(R.drawable.ic_launcher) // 设置图片Uri为空或是错误的时候显示的图片
//                .showImageOnFail(R.drawable.ic_launcher) // 设置图片加载或解码过程中发生错误显示的图片
//                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
//                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
//                // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
//                .build(); // 创建配置过得DisplayImageOption对象
//
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity()).defaultDisplayImageOptions(options)
//                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
//                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
//        ImageLoader.getInstance().init(config);
//    }
//
//    @Override
//    public void onClick(View view) {
//
//    }
}
