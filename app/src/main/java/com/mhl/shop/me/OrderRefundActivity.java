package com.mhl.shop.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.StringDialogCallback;
import com.mhl.shop.login.been.Body;
import com.mhl.shop.main.Data;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.me.adapter.ImagePickerAdapter;
import com.mhl.shop.me.imageloader.GlideImageLoader;
import com.mhl.shop.utils.GsonUtils;
import com.mhl.shop.utils.JBitmapUtils;
import com.mhl.shop.utils.StringUtils;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-12-15 20:16
 * 描述：申请退款
 */
public class OrderRefundActivity extends MyBaseActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener {
    private TextView titleCenterTextview;
    private Spinner spinner;
    private TextView money;
    private EditText reason;
    private RecyclerView recyclerView;
    private Button sumbit;
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 3;               //允许选择图片最大数
    private String pkId, allmoney, type, supplierId, goodsId;
    String mStr;
    List<String> picList = new ArrayList<>();//存放图片返回成功后的集合
    List<File> list = new ArrayList<>();//存放图片的集合

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_refund);
        ButterKnife.bind(this);
        initView();
        initImagePicker();
        initWidget();
//        initData();
        sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sumnit();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                TextView tv = (TextView) view;
                tv.setTextColor(getResources().getColor(R.color.main_text_tow_color));    //设置颜色
                tv.setTextSize(14.0f);    //设置大小
                String[] mReason = getResources().getStringArray(R.array.mReason);
//                Toast.makeText(OrderRefundActivity.this, "你点击的是:"+mReason[pos],Toast.LENGTH_SHORT).show();
                mStr = mReason[pos];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
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
        pkId = intent.getStringExtra("pkId");
        allmoney = intent.getStringExtra("money");
        type = intent.getStringExtra("type");
        supplierId = intent.getStringExtra("supplierId");
        goodsId = intent.getStringExtra("goodsId");
        titleCenterTextview = (TextView) findViewById(R.id.title_center_textview);
        if (type.equals("1")) {
            titleCenterTextview.setText("申请退款");
        } else {
            titleCenterTextview.setText("申请退货退款");
        }

        spinner = (Spinner) findViewById(R.id.spinner);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        sumbit = (Button) findViewById(R.id.sumbit);
        money = (TextView) findViewById(R.id.money);
        reason = (EditText) findViewById(R.id.reason);
        money.setText("¥" + ToolsUtils.Tow(allmoney));
        if (selImageList != null) {
            selImageList.clear();
        }

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
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    Body body;

    private void sumnit() {//提交图片
        if (mStr.equals("请选择原因")) {
            T.showShort(OrderRefundActivity.this, "请选择原因");
            return;
        } else if (mStr.equals("价格问题")) {
            mStr = 1 + "";

        } else if (mStr.equals("服务问题")) {
            mStr = 2 + "";

        } else if (mStr.equals("质量问题")) {
            mStr = 3 + "";

        } else if (mStr.equals("物流问题")) {
            mStr = 4 + "";

        } else if (mStr.equals("其他")) {
            mStr = 5 + "";

        }

        for (int j = 0; j < selImageList.size(); j++) {
            try {
                list.add(new File(JBitmapUtils.saveBitmap2File(JBitmapUtils.revitionImageSize(selImageList.get(j).path), selImageList.get(j).path)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (list.size() > 0) {

            OkGo.post(Urls.URL_UPLOAD)//
                    .tag(this)
                    .addFileParams("file", list) // 这里支持一个key传多个文件
//                    .params("docType", "10")   // 可以添加文件上传
                    .execute(new StringDialogCallback(OrderRefundActivity.this, true) {
                                 @Override
                                 public void onSuccess(String s, Call call, Response response) {
                                     body = (Body) GsonUtils.fromJson(s,
                                             Body.class);
                                     if (body.getCode() == 200) {
                                         picList = body.getDatas();
                                         post();

                                     } else {
                                         T.showShort(OrderRefundActivity.this, body.getMessage());
                                     }
                                 }

                                 @Override
                                 public void onError(Call call, Response response, Exception e) {
                                     super.onError(call, response, e);
                                     handleError(call, response, e);
                                 }
                             }
                    );
        } else {
            post();
        }

    }

    Data data;

    private void post() {

        String pic;

        if (null != picList && picList.size() > 0) {
            pic = StringUtils.listToString(picList);
        } else {
            pic = "";
        }
        // 会员申请退款
        OkGo.post(Urls.URL_RETURN_ORDER)//
                .tag(this)
                .params("orderDetailId", pkId)
                .params("reasonType", mStr)
                .params("returnType", type)
                .params("returnExplain", reason.getText().toString())
                .params("returnPic", pic)
                .execute(
                        new StringDialogCallback(OrderRefundActivity.this, true) {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                data = (Data) GsonUtils.fromJson(s,
                                        Data.class);
                                if (data.getCode() == 200) {
                                    Intent intent = new Intent(OrderRefundActivity.this, ReturnDetailActivity.class);
                                    intent.putExtra("pkId", pkId);
                                    intent.putExtra("supplierId", supplierId);
                                    intent.putExtra("goodsId", goodsId);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    if(!TextUtils.isEmpty(data.getInfo())){
                                        T.showShort(OrderRefundActivity.this,data.getInfo());

                                    }
                                    if (selImageList != null) {
                                        selImageList.clear();
                                    }
                                }
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                handleError(call, response, e);
                                if (selImageList != null) {
                                    selImageList.clear();
                                }
                            }
                        }
//                        new DialogCallback<LzyResponse<Null>>(OrderRefundActivity.this, true) {
//                             @Override
//                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
//                                 handleResponse(lzyResponse, call, response);
//                                 if(lzyResponse.code==200){
//                                     Intent intent = new Intent(OrderRefundActivity.this,ReturnDetailActivity.class);
//                                     intent.putExtra("pkId", pkId);
//                                     intent.putExtra("supplierId", supplierId);
//                                     intent.putExtra("goodsId", goodsId);
//                                     startActivity(intent);
//                                     finish();
//                                 }else{
//                                     T.showShort(OrderRefundActivity.this, lzyResponse.info);
//                                     if(selImageList!=null){
//                                         selImageList.clear();
//                                     }
//                                 }
//                             }
//                             @Override
//                             public void onError(Call call, Response response, Exception e) {
//                                 super.onError(call, response, e);
//                                 handleError(call, response, e);
//                                 if(selImageList!=null){
//                                     selImageList.clear();
//                                 }
//                             }
//                         }
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

    @OnClick(R.id.title_left_imageview)
    public void onClick() {
        finish();
    }
}
