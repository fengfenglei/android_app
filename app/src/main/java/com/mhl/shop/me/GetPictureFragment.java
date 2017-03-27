package com.mhl.shop.me;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mhl.shop.R;
import com.mhl.shop.utils.FileUtils;
import com.umeng.analytics.MobclickAgent;

import java.io.File;

/**
 * 作者：lff
 * 时间；2016-11-15 11:06
 * 描述：照相
 */
public class GetPictureFragment extends DialogFragment implements
        OnClickListener{
    // 头像（1:1的裁剪）
    public static final int		TYPE_REGIST			= 1;
    // 其他（不裁剪）
    public static final int		TYPE_OTHER			= 2;
    // 拍照之后的地址
    private Uri					picUri;
    // 裁减之后的地址
    private Uri					imageUri;
    private static final int	CHOOSE_BIG_PICTURE	= 1;
    private static final int	TACK_PICTURE		= 2;

    public static GetPictureFragment newInstance(int type)
    {
        GetPictureFragment fragment = new GetPictureFragment();
        Bundle b = new Bundle();
        b.putInt("type", type);
        fragment.setArguments(b);
        return fragment;
    }

    /**
     * 当前获取的类型
     *
     * @return
     */
    private int getPicType()
    {
        return getArguments().getInt("type");
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setCancelable(true);
        setStyle(STYLE_NO_TITLE, 0);
        initCacheFolder();

    }

    private void initCacheFolder()
    {

//		if (Common.checkCacheFolder(Common.CACHE_PATH))
//		{
//			picUri = Uri.parse("file://" + Common.CACHE_PATH + "/t001.jpg");
//		}
        if((new File(FileUtils.getDir("cache"),"t001.jpg"))!=null)
        {
            picUri = Uri.parse("file://"+(new File(FileUtils.getDir("cache"),"t001.jpg")));
        }
        else
        {
            Toast.makeText(getActivity(),"本地文件不可写",Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        File file = new File(FileUtils.getDir("cache"),System.currentTimeMillis() + ".jpg");
        String absolutePath = file.getAbsolutePath();
        imageUri = Uri.parse("file://" +file);
        View layout = inflater.inflate(R.layout.fragment_get_picture, null);
        layout.findViewById(R.id.btn_get_from_camera).setOnClickListener(this);
        layout.findViewById(R.id.btn_get_from_local).setOnClickListener(this);
        layout.findViewById(R.id.my_account_icon_delete).setOnClickListener(this);
        return layout;
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());

    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        switch (v.getId())
        {
            case R.id.btn_get_from_local:
                getFromPictureHome();
                break;
            case R.id.btn_get_from_camera:
                startCamera();
                break;
            case R.id.my_account_icon_delete:
                dismiss();
                break;
        }
    }

    private void startCamera()
    {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
        // 调用系统的拍照功能
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
        intent.putExtra("noFaceDetection", false);
        startActivityForResult(intent, TACK_PICTURE);

    }

    // 从图库获取并裁减
    private void getFromPictureHome()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", "JPEG");
        // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, CHOOSE_BIG_PICTURE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub
        if (resultCode == Activity.RESULT_OK)
        {
            switch (requestCode)
            {
                case TACK_PICTURE:
                    // 将拍照之后的图像进行裁剪，这里启动裁剪的Activity
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(picUri, "image/*");
                    intent.putExtra("crop", "true");
                    if (getPicType() == TYPE_REGIST)
                    {
                        // // aspectX aspectY 是宽高的比例
                        intent.putExtra("aspectX", 1);
                        intent.putExtra("aspectY", 1);
                    }
                    // // outputX outputY 是裁剪图片宽高
                    // intent.putExtra("outputX", 64);
                    // intent.putExtra("outputY", 64);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    intent.putExtra("outputFormat", "JPEG");
                    intent.putExtra("return-data", false);
                    startActivityForResult(intent, CHOOSE_BIG_PICTURE);
                    break;
                case CHOOSE_BIG_PICTURE:
                    //Toast.makeText(getActivity(), imageUri+"", Toast.LENGTH_SHORT).show();
                    if (imageUri != null)
                    {
                        if (null != onGetPictureListener)
                        {
                            onGetPictureListener.onComplete(imageUri);
                        }
                    }
                    dismiss();
                    break;
            }
        }
        else
        {
            if (null != onGetPictureListener)
            {
                onGetPictureListener.onCancel();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private OnGetPictureListener	onGetPictureListener;

    public OnGetPictureListener getOnGetPictureListener()
    {
        return onGetPictureListener;
    }

    public void setOnGetPictureListener(
            OnGetPictureListener onGetPictureListener)
    {
        this.onGetPictureListener = onGetPictureListener;
    }

    /**
     * 返回结果的监听器
     *
     *
     *
     */
    public interface OnGetPictureListener
    {
        void onComplete(Uri result);

        void onCancel();
    }


}
