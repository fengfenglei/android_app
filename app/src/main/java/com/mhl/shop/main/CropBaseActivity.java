package com.mhl.shop.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ViewConfiguration;

import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Field;

import butterknife.ButterKnife;

public abstract class CropBaseActivity extends ActionBarActivity {

	protected Context mContext			= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		mContext = this;
		setOverflowShowingAlways();
        super.onCreate(savedInstanceState);

		initContentView();
		ButterKnife.bind(this);
		init();
		initViews();
		initEvents();
    }
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	/**
	 * 设置总是显示溢出菜单

	 */
	private void setOverflowShowingAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@Override
	protected void onDestroy() {
		super.onDestroy();
		ButterKnife.unbind(this);
	}

	public void finish() {
		super.finish();
	}

	/**
	 * 初始化布局
	 */
	protected abstract void initContentView();

	/**
	 * 初始化
	 */
	protected void init() {}

	/**
	 * 初始化View
	 */
	protected abstract void initViews();

	/**
	 * 初始化事件
	 */
	protected abstract void initEvents();
}
