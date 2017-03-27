package com.mhl.shop.homepage;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mhl.shop.R;
import com.mhl.shop.homepage.bean.HomeBean;
import com.mhl.shop.utils.UIUtils;

import java.util.List;

/**
 * 作者：Administrator
 * 时间；2016-11-11 19:26
 * 描述：
 */
public class ShortCutHolder extends BaseHolder<List<HomeBean.DataEntity.HomeConfigEntity.ShortcutListEntity>> implements View.OnClickListener
{

    private LinearLayout	mHome_ll_mycollection;	// 我的收藏
    private LinearLayout mHome_ll_phone_charge;	// 话费充值
    private LinearLayout	mHome_ll_logistics;	// 话费充值
    private LinearLayout	mHome_ll_want_promote;	// 我要推广
    private LinearLayout	mHome_group_area;		// 集采专区

    private ImageView mIv_my_collect;
    private ImageView		mIv_phone_charge;
    private ImageView		mIv__groupbuy_area;
    private ImageView		mIv_want_promote;

    private TextView mTv_my_collect;
    private TextView		mTv_phone_charge;
    private TextView		mTv__groupbuy_area;
    private TextView		mTv_want_promote;

    private Context mContext;

    public ShortCutHolder() {
    }

    public ShortCutHolder(Context context) {
        this.mContext = context;
    }

    @Override
    protected View initView()
    {
        View view = View.inflate(UIUtils.getContext(), R.layout.home_layout_short_cut, null);
        // 找到快捷方式的内部控件---1
        // 快捷方式_轮播图下面的4个栏目
        mHome_ll_mycollection = (LinearLayout) view.findViewById(R.id.home_ll_mycollection);
        mHome_ll_phone_charge = (LinearLayout) view.findViewById(R.id.home_ll_phone_charge);
        // mHome_ll_logistics = (LinearLayout) headerView
        // .findViewById(R.id.home_ll_logistics);
        mHome_ll_want_promote = (LinearLayout) view.findViewById(R.id.home_ll_want_promote);
        mHome_group_area = (LinearLayout) view.findViewById(R.id.home_ll_groupbuy_area);

        // 快捷方式
        mIv_my_collect = (ImageView) view.findViewById(R.id.home_iv_my_collect);
        mIv_phone_charge = (ImageView) view.findViewById(R.id.home_iv_phone_charge);
        mIv__groupbuy_area = (ImageView) view.findViewById(R.id.home_iv_groupbuy_area);
        mIv_want_promote = (ImageView) view.findViewById(R.id.home_iv_want_promote);

        // 文字部分
        mTv_my_collect = (TextView) view.findViewById(R.id.home_tv_my_collect);
        mTv_phone_charge = (TextView) view.findViewById(R.id.home_tv_phone_charge);
        mTv__groupbuy_area = (TextView) view.findViewById(R.id.home_tv_groupbuy_area);
        mTv_want_promote = (TextView) view.findViewById(R.id.home_tv_want_promote);

        mHome_ll_mycollection.setOnClickListener(this);
        mHome_ll_phone_charge.setOnClickListener(this);
        // mHome_ll_logistics.setOnClickListener(this);
        mHome_ll_want_promote.setOnClickListener(this);
        mHome_group_area.setOnClickListener(this);

        return view;
    }

    @Override
    protected void refreshUI(List<HomeBean.DataEntity.HomeConfigEntity.ShortcutListEntity> data)
    {

        display(data.get(0).getPicPath(), mIv_my_collect);
        display(data.get(1).getPicPath(), mIv_phone_charge);
        display(data.get(2).getPicPath(), mIv__groupbuy_area);
        display(data.get(3).getPicPath(), mIv_want_promote);

        mTv_my_collect.setText(data.get(0).getTitle());
        mTv_phone_charge.setText(data.get(1).getTitle());
        mTv__groupbuy_area.setText(data.get(2).getTitle());
        mTv_want_promote.setText(data.get(3).getTitle());

    }

    @Override
    public void onClick(View v)
    {
        int tag = (Integer) v.getId();
        switch (tag)
        {
            case R.id.home_ll_mycollection:

                break;

            case R.id.home_ll_phone_charge:

                break;
            case R.id.home_ll_want_promote:

                break;
		/*	case R.id.home_ll_logistics:
				IntentUtils.startActivity(getActivity(), WaitReceiveActivity.class);
				break;*/
            case R.id.home_ll_groupbuy_area:


                break;
        }
    }

    // 加载图片
    private void display(String uri, ImageView iv)
    {
//		UIUtils.getImageLoader().displayImage(Common.URL_PIC + uri, iv,BaseApplication.mOptions_home);
        Glide.with(UIUtils.getContext()).load(uri).placeholder(R.drawable.icon_bg).into(iv);

    }

}

