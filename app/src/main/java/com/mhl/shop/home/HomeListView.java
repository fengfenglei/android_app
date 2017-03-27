package com.mhl.shop.home;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mhl.shop.R;
import com.mhl.shop.main.FirstSetpView;
import com.mhl.shop.main.SecondStepView;

import java.text.SimpleDateFormat;

/**
 * 作者：Administrator
 * 时间；2016-11-28 11:34
 * 描述：
 */
public class HomeListView extends ListView implements AbsListView.OnScrollListener{
    private static final int DONE = 0;
    private static final int PULL_TO_REFRESH = 1;
    private static final int RELEASE_TO_REFRESH = 2;
    private static final int REFRESHING = 3;
    private static final int RATIO = 3;
    private LinearLayout headerView;
    private int headerViewHeight;
    private float startY;
    private float offsetY;
    private TextView tv_pull_to_refresh;
    private setOnRefreshListener mOnRefreshListener;

    private int state;
    private int mFirstVisibleItem;
    private int mTwoVisibleItem;
    private boolean isLoadingMore = false; // 是否正在加载更多中

    private boolean isRecord;
    private boolean isEnd;
    private boolean isRefreable;
    private FrameLayout mAnimContainer;
    private Animation animation;
    private SimpleDateFormat format;
    private FirstSetpView firstSetpView;
    private SecondStepView secondStepView;
    private AnimationDrawable secondAnimation;
    private boolean isScrollToBottom; // 是否滑动到底部
    private int footerViewHeight; // 脚布局的高度

    public HomeListView(Context context) {
        super(context);
        init(context);
    }

    public HomeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HomeListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }




    public interface setOnRefreshListener{
        void onRefresh();
    }
    public void setOnRefreshListener(setOnRefreshListener onRefreshListener){
        mOnRefreshListener = onRefreshListener;
        isRefreable = true;
    }
    public void setOnRefreshComplete(){
        isEnd = true;
        state = DONE;

        changeHeaderByState(state);
    }

    private void init(Context context) {
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        setOnScrollListener(this);

        headerView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.my_item, null, false);
        tv_pull_to_refresh = (TextView) headerView.findViewById(R.id.tv_pull_to_refresh);
        firstSetpView = (FirstSetpView) headerView.findViewById(R.id.first_step_view);
        secondStepView = (SecondStepView) headerView.findViewById(R.id.second_step_view);
        secondStepView.setBackgroundResource(R.drawable.second_step_animation);
        secondAnimation = (AnimationDrawable) secondStepView.getBackground();
        measureView(headerView);
        addHeaderView(headerView);
        headerViewHeight = headerView.getMeasuredHeight();

        headerView.setPadding(0, -headerViewHeight, 0, 0);

        state = DONE;
        isEnd = true;
        isRefreable = false;
    }




    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING) {
            // 判断当前是否已经到了底部
            if (isScrollToBottom && !isLoadingMore) {
                isLoadingMore = true;
                // 当前到底部
                this.setSelection(this.getCount());


            }
        }
    }
    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mFirstVisibleItem = firstVisibleItem;
//        mFirstVisibleItem = firstVisibleItem + visibleItemCount - 1;
        if (getLastVisiblePosition() == (totalItemCount - 1)) {
            isScrollToBottom = true;
        } else {
            isScrollToBottom = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isEnd) {
            if (isRefreable) {
                switch (ev.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if (mFirstVisibleItem == 0 && !isRecord) {
                            isRecord = true;
                            startY = ev.getY();
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float tempY = ev.getY();
                        if (mFirstVisibleItem == 0 && !isRecord) {
                            isRecord = true;
                            startY = tempY;
                        }
                        if (state!=REFRESHING && isRecord ) {
                            offsetY = tempY - startY;
                            float currentHeight = (-headerViewHeight+offsetY/3);
                            float currentProgress = 1+currentHeight/headerViewHeight;
                            if (currentProgress>=1) {
                                currentProgress = 1;
                            }
                            if (state == RELEASE_TO_REFRESH && isRecord) {
//                                setSelection(0);
                                if (-headerViewHeight+offsetY/RATIO<0) {
                                    state = PULL_TO_REFRESH;
                                    changeHeaderByState(state);
                                }else if (offsetY<=0) {
                                    state = DONE;
                                    changeHeaderByState(state);
                                }
                            }
                            if (state == PULL_TO_REFRESH && isRecord) {
//                                setSelection(0);
                                if (-headerViewHeight+offsetY/RATIO>=0) {
                                    state = RELEASE_TO_REFRESH;
                                    changeHeaderByState(state);
                                }else if (offsetY<=0) {
                                    state = DONE;
                                    changeHeaderByState(state);
                                }
                            }
                            if (state == DONE && isRecord) {
                                if (offsetY>=0) {
                                    state = PULL_TO_REFRESH;
                                }
                            }
                            if (state == PULL_TO_REFRESH) {
                                headerView.setPadding(0,(int)(-headerViewHeight+offsetY/RATIO) ,0,0);
                                firstSetpView.setCurrentProgress(currentProgress);
                                firstSetpView.postInvalidate();
                            }
                            if (state == RELEASE_TO_REFRESH) {
                                headerView.setPadding(0,(int)(-headerViewHeight+offsetY/RATIO) ,0, 0);
                                firstSetpView.setCurrentProgress(currentProgress);
                                firstSetpView.postInvalidate();
                            }
                        }


                        break;
                    case MotionEvent.ACTION_UP:
                        if (state == PULL_TO_REFRESH) {
                            this.smoothScrollBy((int)(-headerViewHeight+offsetY/RATIO)+headerViewHeight, 500);
                            changeHeaderByState(state);
                        }
                        if (state == RELEASE_TO_REFRESH) {
                            this.smoothScrollBy((int)(-headerViewHeight+offsetY/RATIO), 500);
                            state = REFRESHING;
                            mOnRefreshListener.onRefresh();
                            changeHeaderByState(state);
                        }
                        isRecord = false;
                        break;
                }

            }
        }
        return super.onTouchEvent(ev);
    }

    private void changeHeaderByState(int state){
        switch (state) {
            case DONE:
                headerView.setPadding(0, -headerViewHeight, 0, 0);
                firstSetpView.setVisibility(View.VISIBLE);
                secondAnimation.stop();
                secondStepView.setVisibility(View.GONE);
                break;
            case RELEASE_TO_REFRESH:
                tv_pull_to_refresh.setText("松开刷新");

                break;
            case PULL_TO_REFRESH:
                tv_pull_to_refresh.setText("下拉刷新");
                state = DONE;
                firstSetpView.setVisibility(View.VISIBLE);
                secondAnimation.stop();
                secondStepView.setVisibility(View.GONE);
                break;
            case REFRESHING:
                tv_pull_to_refresh.setText("正在刷新");
                firstSetpView.setVisibility(View.GONE);
                secondStepView.setVisibility(View.VISIBLE);
                secondAnimation.stop();
                secondAnimation.start();
                break;
            default:
                break;
        }
    }


    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }


}
