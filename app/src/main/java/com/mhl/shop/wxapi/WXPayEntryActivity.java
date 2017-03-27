package com.mhl.shop.wxapi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mhl.shop.R;
import com.mhl.shop.homepage.RechargeSuccessActivity;
import com.mhl.shop.main.PayType;
import com.mhl.shop.me.OrderMainActivity;
import com.mhl.shop.shopdetails.MyCheckStandActivity;
import com.mhl.shop.utils.Constant;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	

    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	api = WXAPIFactory.createWXAPI(this, MyCheckStandActivity.APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
//		Toast.makeText(this, "openid = " + resp.openId, Toast.LENGTH_SHORT).show();
//		Toast.makeText(this, "transaction = " + resp.transaction, Toast.LENGTH_SHORT).show();
//		Toast.makeText(this, "errStr = " + resp.errStr, Toast.LENGTH_SHORT).show();
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage(getString(R.string.menu_lock, String.valueOf(resp.errCode)));
			builder.show();
		}
		Log.d("123456", "errCode====" + resp.errCode);
		Log.d("123456", "errStr====" + resp.errStr);
		if (resp.errCode==0){
			if(PayType.getT().equals("1")){
				Intent intent = new Intent();
				intent.putExtra(Constant.ORDER_STATE, Constant.ALL_ORDER);
				intent.setClass(WXPayEntryActivity.this, OrderMainActivity.class);
				startActivity(intent);
				Toast.makeText(WXPayEntryActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
			}else if(PayType.getT().equals("4")){
				Intent mIntent = new Intent(WXPayEntryActivity.this,RechargeSuccessActivity.class);
				startActivity(mIntent);
			}else if(PayType.getT().equals("3")){
				Toast.makeText(WXPayEntryActivity.this, "余额充值成功", Toast.LENGTH_SHORT).show();
			}
			finish();
		}else {
			Toast.makeText(WXPayEntryActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
			finish();
		}
//		Intent mIntent = new Intent("wx");
//		mIntent.putExtra("wxpayre", String.valueOf(resp.errCode));
//
//		//发送广播
//		sendBroadcast(mIntent);

//		finish();
	}
}