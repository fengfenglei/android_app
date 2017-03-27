package com.mhl.shop.shopdetails;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mhl.shop.R;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.me.MeInterface;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GoodsAddressActivity extends MyBaseActivity implements OnClickListener, MeInterface.OnGoodsChoiceAddressLister{
	@Bind(R.id.title_left_imageview)
	ImageView titleLeftImageview;

	@Bind(R.id.title_center_textview)
	TextView titleCenterTextview;
	private TextView								goods_address_choose;								//选择的地址
	private LinearLayout							goods_address_return;                             // 
	private String[]								goods_b;                                          //
	private int										goods_c		= 1;
	private GoodDetailAddressFragment				goods_aaf;
	private SparseArray<GoodDetailAddressFragment>	mGoodsCaches	= new SparseArray<GoodDetailAddressFragment>();
	private int selectTag = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_good_choice_address);
		ButterKnife.bind(this);
		String m_tag = getIntent().getStringExtra("selectTag");
		selectTag = Integer.parseInt(m_tag);
	    goodsInit();
	}
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	private void goodsInit() { 
       	initTitle();
		MeInterface.setOnGoodsChoiceAddress(this);
       	goods_b = new String[0];
       	goods_address_choose = (TextView) findViewById(R.id.goods_address_choose);
       	goods_address_choose.setText("请选择省/市");
       	goods_address_return = (LinearLayout) findViewById(R.id.goods_address_return);
       	goods_address_return.setOnClickListener(this);
       	FragmentTransaction ft = getSupportFragmentManager().beginTransaction(); 
       	goods_aaf = new GoodDetailAddressFragment();   //添加不同的framgent
		Bundle bundle = new Bundle();  
		bundle.putString("address", "");
		bundle.putString("id", ""); 
		bundle.putInt("tag", 1);   //
		bundle.putInt("selectTag", selectTag);   //
		ft.add(R.id.goods_address_choose_framelayout, goods_aaf);
		goods_aaf.setArguments(bundle);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);  //专场动画
		ft.commitAllowingStateLoss();
		mGoodsCaches.put(0, goods_aaf);
	}
	
	//初始话标题
	private void initTitle()
	{

		titleCenterTextview.setText("地区选择");
	}
	@OnClick(R.id.title_left_imageview)
	public void onClick() {
		this.finish();
		mGoodsCaches.clear();  //退出是清缓存
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.goods_address_return:
			getSupportFragmentManager().popBackStack();
			String address = "";
			if (goods_b.length > 0)
			{
				for (int i = 0; i < goods_b.length - goods_c; i++)
				{
					address = address + goods_b[i] + ">";
				}
				goods_c = goods_c + 1;  //点击一次返回地址要减少一级
				goods_address_choose.setText(address);
				if (goods_address_choose.getText().equals("") || goods_address_choose.getText() == null)
				{
					goods_address_choose.setText("请选择省/市");
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void OnGoodsChoiceAddressResultInfo(String[] goodsChoiceAddress,String id) {
		goods_c = 1;
		String a = "";
		goods_b = goodsChoiceAddress;  //
		for (int i = 0; i < goodsChoiceAddress.length; i++)
		{
			a = a + goodsChoiceAddress[i] + ">";  //为显示在头部的地址添加">"
		}
		goods_address_choose.setText(a);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.hide(goods_aaf);
		GoodDetailAddressFragment fragment = mGoodsCaches.get(goods_b.length);
		if (fragment != null)
		{
			for (int i = goods_b.length - 1; i < mGoodsCaches.size(); i++)
			{
				fragment = mGoodsCaches.get(i);
				ft.hide(fragment);
			}
		}
		goods_aaf = new GoodDetailAddressFragment();
		Bundle bundle = new Bundle();
		bundle.putString("address", a);
		bundle.putString("id", id);
		bundle.putInt("tag", goods_b.length);// 地址是否添加无的标记,如果大于等于3则添加无反之不添加(也就是说省市县不添加无,是必选的地址)
		bundle.putInt("selectTag", selectTag);   //
		ft.add(R.id.goods_address_choose_framelayout, goods_aaf);
		goods_aaf.setArguments(bundle);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commitAllowingStateLoss();
		mGoodsCaches.put(goods_b.length, goods_aaf);
	}
}
