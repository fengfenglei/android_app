package com.mhl.shop.me;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.kevin.crop.UCrop;
import com.kevin.crop.util.BitmapLoadUtils;
import com.kevin.crop.view.CropImageView;
import com.kevin.crop.view.GestureCropImageView;
import com.kevin.crop.view.OverlayView;
import com.kevin.crop.view.TransformImageView;
import com.kevin.crop.view.UCropView;
import com.mhl.shop.R;
import com.mhl.shop.main.CropBaseActivity;

import java.io.OutputStream;

import butterknife.Bind;


public class CropActivity extends CropBaseActivity {

    private static final String TAG = "CropActivity";

    @Bind(R.id.toolbar)
    Toolbar mToolBar;
    @Bind(R.id.weixin_act_ucrop)
    UCropView mUCropView;
    GestureCropImageView mGestureCropImageView;
    OverlayView mOverlayView;

    @Bind(R.id.crop_act_save_fab)
    FloatingActionButton mSaveFab;

    private Uri mOutputUri;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_crop);
    }

    @Override
    protected void initViews() {
        initToolBar();

        mGestureCropImageView = mUCropView.getCropImageView();
        mOverlayView = mUCropView.getOverlayView();

        initCropView();

    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        mToolBar.setTitle("裁剪图片");
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.drawable.ic_header_left);
    }

    /**
     * 初始化裁剪View
     */
    private void initCropView() {
        // 设置允许缩放
        mGestureCropImageView.setScaleEnabled(true);
        // 设置禁止旋转
        mGestureCropImageView.setRotateEnabled(false);

        // 设置外部阴影颜色
        mOverlayView.setDimmedColor(Color.parseColor("#AA000000"));
        // 设置周围阴影是否为椭圆(如果false则为矩形)
        mOverlayView.setOvalDimmedLayer(false);
        // 设置显示裁剪边框
        mOverlayView.setShowCropFrame(true);
        // 设置不显示裁剪网格
        mOverlayView.setShowCropGrid(true);

        final Intent intent = getIntent();
        setImageData(intent);
    }

    private void setImageData(Intent intent) {
        Uri inputUri = intent.getParcelableExtra(UCrop.EXTRA_INPUT_URI);
        mOutputUri = intent.getParcelableExtra(UCrop.EXTRA_OUTPUT_URI);

        if (inputUri != null && mOutputUri != null) {
            try {
                mGestureCropImageView.setImageUri(inputUri);
            } catch (Exception e) {
                setResultException(e);
                finish();
            }
        } else {
            setResultException(new NullPointerException("Both input and output Uri must be specified"));
            finish();
        }

        // 设置裁剪宽高比
        if (intent.getBooleanExtra(UCrop.EXTRA_ASPECT_RATIO_SET, false)) {
            float aspectRatioX = intent.getFloatExtra(UCrop.EXTRA_ASPECT_RATIO_X, 0);
            float aspectRatioY = intent.getFloatExtra(UCrop.EXTRA_ASPECT_RATIO_Y, 0);
            if (aspectRatioX > 0 && aspectRatioY > 0) {
                mGestureCropImageView.setTargetAspectRatio(aspectRatioX / aspectRatioY);
            } else {
                mGestureCropImageView.setTargetAspectRatio(CropImageView.SOURCE_IMAGE_ASPECT_RATIO);
            }
        }

        // 设置裁剪的最大宽高
        if (intent.getBooleanExtra(UCrop.EXTRA_MAX_SIZE_SET, false)) {
            int maxSizeX = intent.getIntExtra(UCrop.EXTRA_MAX_SIZE_X, 0);
            int maxSizeY = intent.getIntExtra(UCrop.EXTRA_MAX_SIZE_Y, 0);

            if (maxSizeX > 0 && maxSizeY > 0) {
                mGestureCropImageView.setMaxResultImageSizeX(maxSizeX);
                mGestureCropImageView.setMaxResultImageSizeY(maxSizeY);
            } else {
                Log.w(TAG, "EXTRA_MAX_SIZE_X and EXTRA_MAX_SIZE_Y must be greater than 0");
            }
        }
    }

    @Override
    protected void initEvents() {
        mGestureCropImageView.setTransformImageListener(mImageListener);
        mSaveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropAndSaveImage();
            }
        });
    }
    private void cropAndSaveImage() {
        OutputStream outputStream = null;
        try {
            final Bitmap croppedBitmap = mGestureCropImageView.cropImage();
            if (croppedBitmap != null) {
                outputStream = getContentResolver().openOutputStream(mOutputUri);
                croppedBitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream);
                croppedBitmap.recycle();

                setResultUri(mOutputUri, mGestureCropImageView.getTargetAspectRatio());
                finish();
            } else {
                setResultException(new NullPointerException("CropImageView.cropImage() returned null."));
            }
        } catch (Exception e) {
            setResultException(e);
            finish();
        } finally {
            BitmapLoadUtils.close(outputStream);
        }
    }

    private TransformImageView.TransformImageListener mImageListener = new TransformImageView.TransformImageListener() {
        @Override
        public void onRotate(float currentAngle) {
//            setAngleText(currentAngle);
        }

        @Override
        public void onScale(float currentScale) {
//            setScaleText(currentScale);
        }

        @Override
        public void onLoadComplete() {
            Animation fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.crop_fade_in);
            fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    mUCropView.setVisibility(View.VISIBLE);
                    mGestureCropImageView.setImageToWrapCropBounds();
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            mUCropView.startAnimation(fadeInAnimation);
        }

        @Override
        public void onLoadFailure(Exception e) {
            setResultException(e);
            finish();
        }

    };

    private void setResultUri(Uri uri, float resultAspectRatio) {
        setResult(RESULT_OK, new Intent()
                .putExtra(UCrop.EXTRA_OUTPUT_URI, uri)
                .putExtra(UCrop.EXTRA_OUTPUT_CROP_ASPECT_RATIO, resultAspectRatio));
    }

    private void setResultException(Throwable throwable) {
        setResult(UCrop.RESULT_ERROR, new Intent().putExtra(UCrop.EXTRA_ERROR, throwable));
    }
}
