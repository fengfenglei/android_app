package com.mhl.shop.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.callback.StringDialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.login.been.Body;
import com.mhl.shop.login.been.Null;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.me.adapter.ImageEvaluatePickerAdapter;
import com.mhl.shop.me.imageloader.GlideImageLoader;
import com.mhl.shop.utils.GsonUtils;
import com.mhl.shop.utils.JBitmapUtils;
import com.mhl.shop.utils.StringUtils;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-12-19 20:46
 * 描述：去评价
 */
public class OrderEvaluateActivity extends MyBaseActivity implements ImageEvaluatePickerAdapter.OnRecyclerViewItemClickListener {


    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.item_image)
    ImageView itemImage;
    @Bind(R.id.shop_name)
    TextView shopName;
    @Bind(R.id.big_money)
    TextView bigMoney;
    @Bind(R.id.guige)
    TextView guiGe;
    @Bind(R.id.lit_money)
    TextView litMoney;
    @Bind(R.id.shop_count)
    TextView shopCount;
    @Bind(R.id.content_evaluation)
    EditText contentEvaluation;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.ratingbarId1)
    RatingBar ratingbarId1;
    @Bind(R.id.ratingbarId2)
    RatingBar ratingbarId2;
    @Bind(R.id.ratingbarId3)
    RatingBar ratingbarId3;
    @Bind(R.id.top_title)
    LinearLayout topTitle;
    @Bind(R.id.chose)
    CheckBox chose;
    @Bind(R.id.sumbit)
    Button sumbit;
    private String item_image, shop_name, shop_count, guige,money,orderDetailId,goodsId;
    private int mRatingBarLevel1=5;
    private int mRatingBarLevel2=5;
    private int mRatingBarLevel3=5;
    private ImageEvaluatePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 4;               //允许选择图片最大数
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    List<File> list= new ArrayList<>();//存放图片的集合
    List<String> picList =new ArrayList<>();//存放图片返回成功后的集合
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_evaluate);
        ButterKnife.bind(this);

        initView();

        initImagePicker();
        initWidget();
        ratingbarId1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                float i =  ratingbarId1.getRating();
                mRatingBarLevel1= (int) i;
            }
        });
        ratingbarId2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                float i =  ratingbarId2.getRating();
                mRatingBarLevel2=(int) i;
            }
        });
        ratingbarId3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                float i =  ratingbarId3.getRating();
                mRatingBarLevel3=(int) i;
            }
        });
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    private void initView() {
        Intent intent = getIntent();
        item_image = intent.getStringExtra("item_image");
        shop_name = intent.getStringExtra("shop_name");
        shop_count = intent.getStringExtra("shop_count");
        guige = intent.getStringExtra("guige");
        money= intent.getStringExtra("money");
        orderDetailId= intent.getStringExtra("orderDetailId");
        goodsId= intent.getStringExtra("goodsId");
        titleCenterTextview.setText("评价");
        Glide.with(this).load(Urls.URL_PHOTO+"/file/v3/download-"+item_image+"-200-200.jpg"
        ).into(itemImage);
        shopName.setText(shop_name);
        shopCount.setText("x"+shop_count);
        guiGe.setText(guige);
        bigMoney.setText("¥"+money);
    }
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    private void initWidget() {

        selImageList = new ArrayList<>();
        adapter = new ImageEvaluatePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(OrderEvaluateActivity.this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
    @OnClick({R.id.title_left_imageview, R.id.chose, R.id.sumbit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.chose:
                break;
            case R.id.sumbit:
                sumnit();
                break;
        }
    }
    Body body;
    private void sumnit() {//提交图片

        for (int j = 0; j < selImageList.size(); j++) {
            try {
                list.add( new File(JBitmapUtils.saveBitmap2File(JBitmapUtils.revitionImageSize(selImageList.get(j).path),selImageList.get(j).path)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (list.size()>0){
            OkGo.post(Urls.URL_UPLOAD)//
                    .tag(this)
                    .addFileParams("file", list) // 这里支持一个key传多个文件
//                    .params("docType", "10")   // 可以添加文件上传
                    .execute(new StringDialogCallback(OrderEvaluateActivity.this, true) {
                                 @Override
                                 public void onSuccess(String s, Call call, Response response) {
                                     body=(Body) GsonUtils.fromJson(s,
                                             Body.class);
                                     if(body.getCode()==200){
                                         picList= body.getDatas();
                                         post();

                                     }else{
                                         T.showShort(OrderEvaluateActivity.this, body.getMessage());
                                     }
                                 }
                                 @Override
                                 public void onError(Call call, Response response, Exception e) {
                                     super.onError(call, response, e);
                                     handleError(call, response, e);
                                 }
                             }
                    );}else {
            post();
        }

    }

    private void post() {
        String is_hidden_username;
        String  pic;
        if(chose.isChecked()){
            is_hidden_username="1";
        }else {
            is_hidden_username="2";
        }
        if (null != picList&& picList.size()>0 ){
            pic=  StringUtils.listToString(picList);
        }else {
            pic=  "";
        }
        if(mRatingBarLevel1==0 || mRatingBarLevel2==0 || mRatingBarLevel3==0){
            T.showShort(OrderEvaluateActivity.this,  "请评分");
            return;
        }
        // 会员去评价
        OkGo.post(Urls.URL_ORDER_EVALUATE)//
                .tag(this)
                .params("orderDetailId",orderDetailId)
                .params("goods_score",mRatingBarLevel1)//商品评分(1-5)
                .params("service_score",mRatingBarLevel2)//服务评分(1-5)
                .params("express_score",mRatingBarLevel3)//物流评分(1-5)
                .params("eval_content",contentEvaluation.getText().toString())//内容
                .params("is_hidden_username",is_hidden_username)// 是否匿名-1是--2否
                .params("eval_picture",pic)
                .execute(new DialogCallback<LzyResponse<Null>>(OrderEvaluateActivity.this, true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse, call, response);
                                 if(lzyResponse.code==200){
                                     finish();
                                     Intent intent = new Intent(OrderEvaluateActivity.this,EvaluationSuccessActivity.class);
                                     intent.putExtra("pkId", orderDetailId);
                                     startActivity(intent);
                                 }else{
                                     T.showShort(OrderEvaluateActivity.this, lzyResponse.info);
                                     if(selImageList!=null){
                                         selImageList.clear();
                                     }
                                 }
                             }
                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                                 handleError(call, response, e);
                                 if(selImageList!=null){
                                     selImageList.clear();
                                 }
                             }
                         }
                );
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                selImageList.addAll(images);
                adapter.setImages(selImageList);
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                selImageList.clear();
                selImageList.addAll(images);
                adapter.setImages(selImageList);
            }
        }
    }
}
