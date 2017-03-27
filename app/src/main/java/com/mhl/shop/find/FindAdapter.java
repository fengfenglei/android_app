package com.mhl.shop.find;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LoginActivity;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.login.been.Null;
import com.mhl.shop.main.MyApplication;
import com.mhl.shop.main.MyBaseAdpter;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;

import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-11-17 18:04
 * 描述：发现列表
 */
public class FindAdapter extends MyBaseAdpter {

    private final Context context;
    private final List<Find> alist;
    private boolean isZan =false;
    private List<String> lists;

    public FindAdapter(Context context, List<Find> list) {
        super();
        this.context = context;
        this.alist = list;
    }




    public int getCount() {
        return alist.size();
    }

    public Object getItem(int position) {
        return alist.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder ;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.find_item, null);
               holder = new ViewHolder();
            holder.icon = (CircleImageView) convertView.findViewById(R.id.icon);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.comment = (TextView) convertView.findViewById(R.id.comment);
            holder.discTags = (TextView) convertView.findViewById(R.id.discTags);
            holder.favors = (TextView) convertView.findViewById(R.id.favors);
            holder.zan= (ImageView) convertView.findViewById(R.id.zan);
            holder.detail_ly= (LinearLayout) convertView.findViewById(R.id.detail_ly);
            //得到控件
            holder. mRecyclerView = (RecyclerView) convertView.findViewById(R.id.id_recyclerview_horizontal);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        //如果账号登陆了
        if (MyApplication.getApplication().isLogin())
        {
            if(alist.get(position).getFavored()==1){//已赞
                holder.zan.setImageResource(R.drawable.found_like_click);
            }else {//没有赞
                holder.zan.setImageResource(R.drawable.found_like_native);
            }
        }

        //点赞
        holder.zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (!MyApplication.getApplication().isLogin())
                {
                    Intent login = new Intent(context, LoginActivity.class);
                    context.startActivity(login);

                }
                else
                {
                    if(alist.get(position).getFavored()==1){ //取消赞
//                        alist.get(position).setFavored(2);
//                        holder.zan.setImageResource(R.drawable.found_like_native);
//                        zan(alist.get(position).getPkId(),2);
                    }else { //去赞
                        alist.get(position).setFavored(1);
                       zan(alist.get(position).getPkId(),1);
                        holder.zan.setImageResource(R.drawable.found_like_click);
                        holder.favors.setText( (Integer.parseInt(holder.favors.getText().toString())+1)+"");
                        holder.zan.startAnimation(AnimationUtils.loadAnimation(
                                context, R.anim.dianzan_anim));
                    }

                }
            }
        });
        Glide.with(context).load(Urls.URL_PHOTO+"/file/v3/download-"+alist.get(position).getAvator()+"-120-120.jpg").into(holder.icon);

           holder.name.setText(alist.get(position).getAuthor());
           holder.discTags.setText(alist.get(position).getDiscTags());
           holder.favors.setText(alist.get(position).getFavors()+"");
        if(TextUtils.isEmpty(alist.get(position).getDiscTitle())){
            holder.comment.setVisibility(View.GONE);
        }else{
            holder.comment.setText(alist.get(position).getDiscTitle());
            holder.comment.setVisibility(View.VISIBLE);
        }



        holder.time.setText(ToolsUtils.dateToStampLit(alist.get(position).getEditTime()));
        if(alist.get(position).getDiscCovers()==null || TextUtils.isEmpty(alist.get(position).getDiscCovers())){
    holder.mRecyclerView.setVisibility(View.GONE);
}else {

     lists = Arrays.asList(alist.get(position).getDiscCovers().split(","));

    //设置布局管理器
    if(lists.size()>0){
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
    holder.mRecyclerView.setLayoutManager(linearLayoutManager);
    //设置适配器
        FindPhotoAdapter mAdapter = new FindPhotoAdapter(context, lists,alist.get(position).getPkId(), Urls.URL_BASE_HTTP+"/file/v3/download-"+lists.get(0)+"-100-100.jpg"
        ,alist.get(position).getDiscTitle());
    holder.mRecyclerView.setAdapter(mAdapter);}else {
        holder.mRecyclerView.setVisibility(View.GONE);
    }
}
        //详情
        holder.mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.putExtra("pkId", alist.get(position).getPkId());
                intent.putExtra("title", alist.get(position).getDiscTitle());
                intent.putExtra("icon", Urls.URL_BASE+"/file/v3/download-"+lists.get(0)+"-100-100.jpg");

                intent.setClass(context, FindDetailActivity.class);
                context.startActivity(intent);
            }
        });
        holder.detail_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.putExtra("pkId", alist.get(position).getPkId());
                intent.putExtra("title", alist.get(position).getDiscTitle());
                intent.putExtra("icon", Urls.URL_BASE+"/file/v3/download-"+lists.get(0)+"-100-100.jpg");
                intent.setClass(context, FindDetailActivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    private void zan(String pkId, final int i) {
            OkGo.post(Urls.URL_DISCOVER_FAVOR)//
                    .tag(this)
                    .params("pkId",pkId)
                    .params("favor",i)
                    .execute(new DialogCallback<LzyResponse<Null>>((Activity) context,false) {
                                 @Override
                                 public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                     handleResponse(lzyResponse.data, call, response);
                                     if (lzyResponse.code==200){
//                                    if(i==1){
//                                        holder.zan.setImageResource(R.drawable.found_like_click);
//                                     }else if(i==2){
//                                        holder.zan.setImageResource(R.drawable.found_like_native);
//                                    }
                                     }else {

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

    class ViewHolder
    {
        CircleImageView icon;
        TextView name;
        TextView time;
        TextView comment;
        TextView discTags;
        TextView favors;
        ImageView zan;
        RecyclerView mRecyclerView;
        LinearLayout detail_ly;
    }

}
