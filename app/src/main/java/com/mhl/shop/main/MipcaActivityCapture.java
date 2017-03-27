package com.mhl.shop.main;

import android.Manifest;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.mhl.shop.R;
import com.mhl.shop.shopdetails.GoodsDetailsActivity;
import com.mhl.shop.shopdetails.SupplierActivity;
import com.mhl.shop.wheel.AlertDialog;
import com.mhl.zxing.camera.CameraManager;
import com.mhl.zxing.decoding.CaptureActivityHandler;
import com.mhl.zxing.decoding.InactivityTimer;
import com.mhl.zxing.view.ViewfinderView;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @项目名: 51mhl_androidapp
 * @包名: com.mhl.shop.activity
 * @类名: MipcaActivityCapture
 * @创建者: 雷峰峰
 * @创建时间: 2015-7-21 上午9:47:13
 * @描述: 扫一扫界面
 * @svn版本: $Rev: 16487 $
 * @更新人: $Author: zf $
 * @更新时间: $Date: 2015-12-30 16:12:46 +0800 (星期三, 30 十二月 2015) $
 * @更新描述:TODO
 */
public class MipcaActivityCapture extends FragmentActivity implements Callback {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
//	private boolean vibrate;
	private Camera m_Camera;
	private Button deng_off,deng_on;
	private Button mButtonBack;
	// 等待窗口
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture);
		//ViewUtil.addTopView(getApplicationContext(), this, R.string.scan_card);
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		
		 mButtonBack = (Button) findViewById(R.id.remove);
		 deng_off = (Button) findViewById(R.id.deng_off);
		 deng_on = (Button) findViewById(R.id.deng_on);
		mButtonBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MipcaActivityCapture.this.finish();

			}
		});
		deng_off.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//打开闪关灯
				CameraManager.get().turnOn();
				deng_off.setVisibility(View.GONE);
				deng_on.setVisibility(View.VISIBLE);

			 }
		});
		deng_on.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//关闭闪光灯
				CameraManager.get().turnOff();
				deng_on.setVisibility(View.GONE);
				deng_off.setVisibility(View.VISIBLE);

			}
		});
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
//				final String d=		"http://www.51mdx.net/mall/good-detail-822389421956534272.html";
////				final String d= "http://www.51mdx.net/mall/shop-detail-816489255806308352.html";
//		Log.d("resultString","goodsid="+d.substring(d.indexOf("good-detail-")+12,d.indexOf(".html")));
		ActivityCompat.requestPermissions(this,
				new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
				1);
	}

	 
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);

		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
//		vibrate = true;
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);

		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}
	

	public void handleDecode(Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		final String resultString = result.getText();
		Log.d("resultString","resultString="+resultString);
		if (resultString.equals("")) {
			Toast.makeText(MipcaActivityCapture.this, "Scan failed!", Toast.LENGTH_SHORT).show();
		}else {

			if(resultString.equals("")||resultString==null){
				Toast.makeText(MipcaActivityCapture.this, "Scan failed!", Toast.LENGTH_SHORT).show();
				MipcaActivityCapture.this.finish();
			}else{
				if(resultString.indexOf("good-detail")!=-1){//包含商品
					Intent intent1 = new Intent(MipcaActivityCapture.this, GoodsDetailsActivity.class);
					intent1.putExtra("goodsid",resultString.substring(resultString.indexOf("good-detail-")+12,resultString.indexOf(".html")));
					startActivity(intent1);
					MipcaActivityCapture.this.finish();
					Log.d("resultString","goodsid="+resultString.substring(resultString.indexOf("good-detail-")+12,resultString.indexOf(".html")));

				}else if(resultString.indexOf("shop-detail")!=-1){//包含供应商
					Intent intent1 = new Intent(MipcaActivityCapture.this,SupplierActivity.class);
					intent1.putExtra("supplierId", resultString.substring(resultString.indexOf("shop-detail-")+12,resultString.indexOf(".html")));
					startActivity(intent1);
					MipcaActivityCapture.this.finish();
					Log.d("resultString","supplierId="+resultString.substring(resultString.indexOf("shop-detail-")+12,resultString.indexOf(".html")));
				}else{
					new AlertDialog(MipcaActivityCapture.this).builder().setTitle("温馨提示")
							.setMsg("能存在风险，是否继续访问？")
							.setPositiveButton("确认", new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									Uri uri = Uri.parse(resultString);
									Intent intent = new Intent(Intent.ACTION_VIEW, uri);
									startActivity(intent);
									finish();						}
							}).setNegativeButton("取消", new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							finish();

						}
					}).show();
				}




		}
//		MipcaActivityCapture.this.finish();
	}}
	public boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
//		if (vibrate) {
//			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//			vibrator.vibrate(VIBRATE_DURATION);
//		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};
}