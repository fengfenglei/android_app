package com.mhl.shop.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.mhl.shop.me.adapter.WriteImagePickerAdapter;
import com.mhl.shop.me.been.Company;
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
 * 时间；2016-12-22 14:42
 * 描述：填写物流信息
 */
public class WriteLogisticsActivity extends MyBaseActivity  implements WriteImagePickerAdapter.OnRecyclerViewItemClickListener {

    @Bind(R.id.title_left_imageview)
    ImageView titleLeftImageview;
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.order_number)
    TextView orderNumber;
    @Bind(R.id.spinner)
    Spinner spinner;
    @Bind(R.id.shipcode)
    EditText shipcode;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.info)
    EditText info;
    @Bind(R.id.ok_button)
    Button okButton;
    private String ReturnNo,PkId;//退款编号，退款id
    private String expressCompanyId,expressCompany;//物流公司ID，物流公司
    private WriteImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 1;               //允许选择图片最大数
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    List<File> list= new ArrayList<>();//存放图片的集合
    List<String> picList =new ArrayList<>();//存放图片返回成功后的集合

    private ArrayAdapter<String> sadapter;
    private List<Company> company;
    private  Body body;//头像信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_logistics);
        ButterKnife.bind(this);
        initView();
        initData();
        initImagePicker();
        initWidget();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                TextView tv = (TextView)view;
                tv.setTextColor(getResources().getColor(R.color.main_text_tow_color));    //设置颜色
                tv.setTextSize(14.0f);    //设置大小
                expressCompanyId =company.get(pos).getPkId()+"";
                expressCompany =company.get(pos).getCompanyName();
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
    private void initData() {
        OkGo.post(Urls.URL_ORDER_RETURN_COMPANY)//
                .tag(this)
                .execute(new DialogCallback<LzyResponse<List<Company>>>(WriteLogisticsActivity.this, true) {
                             @Override
                             public void onSuccess(LzyResponse<List<Company>> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse, call, response);
                                 if (lzyResponse.code == 200) {
                                     company =lzyResponse.data;
//                                     setData(lzyResponse.data);
//                                     all_ly.setVisibility(View.VISIBLE);
                                     sadapter = new ArrayAdapter<String>(WriteLogisticsActivity.this,
                                             android.R.layout.simple_spinner_item, getEditSource());
                                     spinner.setAdapter(sadapter);
                                 } else {
                                     T.showShort(WriteLogisticsActivity.this, lzyResponse.info);
                                 }
                             }

                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                                 handleError(call, response, e);
                             }
                         }
                );
    }
    public List<String> getEditSource() {
        List<String> list = new ArrayList<String>();
        for(int    i=0;    i<company.size();    i++)    {
            list.add(company.get(i).getCompanyName());
        }
        return list;
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
        adapter = new WriteImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(WriteLogisticsActivity.this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void initView() {
        Intent intent = getIntent();
        ReturnNo = intent.getStringExtra("ReturnNo");
        PkId= intent.getStringExtra("PkId");
        titleCenterTextview.setText("会员退货");
        orderNumber.setText(ReturnNo);
    }

    @OnClick({R.id.title_left_imageview, R.id.ok_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.ok_button:
                  sumbit();
                break;
        }
    }

    private void sumbit() {
        if(TextUtils.isEmpty(shipcode.getText().toString())){
            T.showShort(WriteLogisticsActivity.this,"请填写物流单号");
            return;
        }
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
                    .execute(new StringDialogCallback(WriteLogisticsActivity.this, true) {
                                 @Override
                                 public void onSuccess(String s, Call call, Response response) {
                                     body=(Body) GsonUtils.fromJson(s,
                                             Body.class);
                                     if(body.getCode()==200){
                                         picList= body.getDatas();
                                         post();
                                     }else{
                                         T.showShort(WriteLogisticsActivity.this, body.getMessage());
                                     }
                                 }
                                 @Override
                                 public void onError(Call call, Response response, Exception e) {
                                     super.onError(call, response, e);
                                     handleError(call, response, e);
                                 }
                             }
                    );
        }else {
            post();
        }

    }

    private void post() {

        String  pic;
        if (null != picList&& picList.size()>0 ){
            pic=  StringUtils.listToString(picList);
        }else {
            pic=  "";
        }
        OkGo.post(Urls.URL_ORDER_RETURN_DELIVER)//
                .tag(this)
                .params("returnInfoId",PkId)//退款/退货退款ID
                .params("expressNo",shipcode.getText().toString())//物流单号
                .params("expressCompanyId",expressCompanyId)//物流公司ID
                .params("expressCompany",expressCompany)//expressCompany
                .params("expressPic",pic)//物流附件(多个以逗号分隔)
                .params("expressRemark",info.getText().toString())//备注
                .execute(new DialogCallback<LzyResponse<Null>>(WriteLogisticsActivity.this, true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse, call, response);
                                 if (lzyResponse.code == 200) {
                                        finish();
                                 } else {
                                     T.showShort(WriteLogisticsActivity.this, lzyResponse.info);
                                 }
                             }

                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                                 handleError(call, response, e);
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
