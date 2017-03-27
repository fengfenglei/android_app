package com.mhl.shop.me.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.login.been.Null;
import com.mhl.shop.main.BottomMenuDialog;
import com.mhl.shop.main.MyBaseAdpter;
import com.mhl.shop.me.MeInterface;
import com.mhl.shop.me.been.Article;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;

import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2017-1-18 18:17
 * 描述：推荐文章列表
 */
public class NoArticleAdapter extends MyBaseAdpter {

    private final Context context;
    private final List<Article.DiscoverListBean> alist;
    BottomMenuDialog d5;

    public NoArticleAdapter(Context context, List<Article.DiscoverListBean> list) {
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
        ViewHolder holder = null;
        if (convertView == null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.collect_no_article_item, null);
            holder.item = (LinearLayout) convertView.findViewById(R.id.item);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.icon =(CircleImageView) convertView.findViewById(R.id.icon);
            holder.article_icon=(ImageView) convertView.findViewById(R.id.article_icon);
            holder.item_title = (TextView) convertView.findViewById(R.id.item_title);
            holder.item_label = (TextView) convertView.findViewById(R.id.item_label);
            holder.go= (Button) convertView.findViewById(R.id.go);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(Urls.URL_PHOTO+"/file/v4/download-"+alist.get(position).getAvator()+"-80-80.jpg").placeholder(R.drawable.icon_bg).into(holder.icon);
        holder.name.setText(alist.get(position).getAuthor());
        holder.time.setText(ToolsUtils.dateToStamp(alist.get(position).getEditTime()));
        holder.item_title.setText(alist.get(position).getDiscSummary());
        holder.item_label.setText(alist.get(position).getDiscTags());
        if(alist.get(position).getDiscCovers()==null || TextUtils.isEmpty(alist.get(position).getDiscCovers())){
            holder.article_icon.setVisibility(View.GONE);
        }else {

            List<String> list = Arrays.asList(alist.get(position).getDiscCovers().split(","));
        Glide.with(context).load(Urls.URL_PHOTO+"/file/v4/download-"+list.get(0)+"-180-180.jpg").placeholder(R.drawable.icon_bg).into(holder.article_icon);
        }
        holder.go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collection(alist.get(position).getPkId());
            }
        });


        return convertView;
    }

    class ViewHolder
    {
        ImageView article_icon;
        TextView name,time,item_label;
        TextView item_title;
        LinearLayout item;
        CircleImageView icon;
        Button go;
    }
    /**
     * 收藏商品
     *
     */
    private void collection(String pk_id) {

        OkGo.post(Urls.URL_DISCOVER_COLLECTION)//
                .tag(this)
                .params("pkId",pk_id)
                .params("collection",1)
                .execute(new DialogCallback<LzyResponse<Null>>((Activity) context,true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 if (lzyResponse.code==200){
                                     //刷新
                                     if (MeInterface.onMyAllCollectListener!=null) {
                                         MeInterface.onMyAllCollectListener.OnMyAllCollectRefresh("",0);
                                     }
                                 }else {

                                         T.showShort(context,"收藏失败");
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
}

