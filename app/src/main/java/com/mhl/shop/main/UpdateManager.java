package com.mhl.shop.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.mhl.shop.R;
import com.mhl.shop.main.wight.UpAlertDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 作者：lff
 * 时间；2017-2-14 14:27
 * 描述：更新版本
 */
public class UpdateManager {

    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;
    //判断是否更新中
    public static String tag = "1";//未点击更新

    /* 下载保存路径 */
    private String mSavePath;
    /* 记录进度条数量 */
    private int progress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;

    private String ver;//版本号
    private String urlapk;//apk地址
    private String notes;//升级说明
    private int mupgradeFocus;//是否强制更新
    private Context mContext;
    private int len;
    private NotificationManager manager;
    private Notification notif;
    /* 更新进度条 */
    private ProgressBar mProgress;
    private Dialog mDownloadDialog;
    //    RemoteViews contentView;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 正在下载
                case DOWNLOAD:
                    // 设置进度条位置
                    mProgress.setProgress(progress);
//                    contentView.setTextViewText(R.id.notificationPercent, 46 + "");
//                    contentView.setProgressBar(R.id.notificationProgress, 100, progress, false);
                    notif.contentView.setTextViewText(R.id.content_view_text1, "正在下载...  "+progress+"%");
                    notif.contentView.setProgressBar(R.id.content_view_progress, 100, progress, false);
                    manager.notify(0, notif);
                    break;
                case DOWNLOAD_FINISH:
                    // 安装文件
                    notif.contentView.setTextViewText(R.id.content_view_text1, "下载完成！");
                    notif.contentView.setProgressBar(R.id.content_view_progress, 100, 100, false);
                    manager.notify(0, notif);
                    tag = "2";//更新完成开始安装
                    installApk();
                    break;
                default:
                    break;
            }
        }
        ;
    };

    public UpdateManager(String ver, String url, String notes, int upgradeFocus, Context context) {
        this.mContext = context;
        this.ver = ver;
        this.urlapk = url;
        this.notes = notes;
        this.mupgradeFocus = upgradeFocus;


    }

    public boolean isShowToast = true;

    /**
     * 检测软件更新
     */
    public void checkUpdate() {
        if (isUpdate()) {
            // 显示提示对话框
            if(tag.equals("1")){
                showNoticeDialog();
            }else{
            }

        } else {
            if(isShowToast){
                Toast.makeText(mContext, R.string.soft_update_no, Toast.LENGTH_LONG).show();
            }
        }
    }
    /**
     * 检查软件是否有更新版本
     *
     * @return
     */
    private boolean isUpdate() {
        // 获取当前软件版本
        int versionCode = getVersionCode(mContext);

        int serviceCode = Integer.valueOf(ver);
        Log.d("abbott", "versionCode=="+versionCode);

        Log.d("abbott", "serviceCode=="+serviceCode);
        // 版本判断
        if (serviceCode > versionCode) {
            return true;
        }

        return false;
    }

    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    private int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo("com.mhl.shop", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 显示软件更新对话框
     */
    private void showNoticeDialog() {
//        AndroidDialogView dialogView2 = new AndroidDialogView(mContext, R.style.dialog,notes, new AndroidDialogView.OnYesButtonListener() {
//
//            @Override
//            public void YesButtonListener() {
//                Toast.makeText(mContext, "后台更新中,请稍后...", Toast.LENGTH_LONG).show();
//                tag="3";//开始更新
//                // 显示下载对话框
//                showDownloadDialog();
//            }
//
//
//        }, null);
//        dialogView2.show();
        if(mupgradeFocus==1){ //强制更新
            UpAlertDialog builder = new UpAlertDialog(mContext).builder().setTitle("发现新版本！")
                    .setMsg(notes)
                    .setPositiveButton("马上更新", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mContext, "后台更新中,请稍后...", Toast.LENGTH_LONG).show();
                            tag="3";//开始更新
                            // 显示下载对话框
                            showDownloadDialog();
                        }
                    });
            builder.setCancelable(false);
            builder.show();
        }else { //非强制更新
            UpAlertDialog builder = new UpAlertDialog(mContext).builder().setTitle("发现新版本！")
                    .setMsg(notes)
                    .setPositiveButton("马上更新", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mContext, "后台更新中,请稍后...", Toast.LENGTH_LONG).show();
                            tag="3";//开始更新
                            // 显示下载对话框
                            showDownloadDialog();
                        }
                    });
            builder.show() ;
        }



    }

    /**
     * 显示软件下载对话框
     */
    private void showDownloadDialog() {

        // 构造软件下载对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.soft_updating);
        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.softupdate_progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
        builder.setView(v);
        // 取消更新
        builder.setNegativeButton(R.string.soft_update_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 设置取消状态
                cancelUpdate = true;
            }
        });
        mDownloadDialog = builder.create();
//        mDownloadDialog.show();   //不让下载进度条显示



//        if(progress<100&&tag.equals("1")){
        //点击通知栏后打开的activity
        Intent intent = new Intent(mContext,MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
        manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notif = new Notification();
        notif.icon = R.drawable.icon16;
        notif.tickerText = "更新通知";
        //通知栏显示所用到的布局文件
        notif.contentView = new RemoteViews(mContext.getPackageName(), R.layout.notif);
        notif.contentIntent = pIntent;
        manager.notify(0, notif);
//        }else{


//        }






        // 下载文件
        downloadApk();
    }

    /**
     * 下载apk文件
     */
    private void downloadApk() {
        // 启动新线程下载软件

        new downloadApkThread().start();
    }
    int downloadCount = 0;
    /**
     * 下载文件线程
     *
     * @author coolszy
     * @date 2012-4-26
     * @blog http://blog.92coding.com
     */
    private class downloadApkThread extends Thread {
        @Override
        public void run() {
            try {

                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    mSavePath = sdpath + "download";
                    URL url = new URL(urlapk);
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    InputStream is = conn.getInputStream();

                    File file = new File(mSavePath);
                    // 判断文件目录是否存在
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    File apkFile = new File(mSavePath, "mhl_manage.apk");
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件中


                    do {
                        int numread = is.read(buf);
                        count += numread;

                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        Log.i("progress","progress==="+progress);
                        Log.i("progress","count==="+count+numread);
                        // 更新进度
                        //为了防止频繁的通知导致应用吃紧，百分比增加10才通知一次
                        if(downloadCount == 0 || progress-5>downloadCount){
                            downloadCount+=5;
                            mHandler.sendEmptyMessage(DOWNLOAD);
                        }

//                        try {
//                            Thread.sleep(100);// 休息1秒后再读取下载进度
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }

                        if (numread <= 0) {
                            // 下载完成
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numread);
                    } while (!cancelUpdate);// 点击取消就停止下载.
                    fos.close();
                    is.close();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 取消下载对话框显示
//            mDownloadDialog.dismiss();
        }
    }

    ;

    /**
     * 安装APK文件
     */
    private void installApk() {

        File apkfile = new File(mSavePath, "mhl_manage.apk");
        if (!apkfile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        ;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(intent);

    }


}
