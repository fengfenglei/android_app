package com.mhl.shop.search.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mhl.shop.R;
import com.mhl.shop.main.MyBaseAdpter;
import com.mhl.shop.search.been.SupplierMain;
import com.mhl.shop.shopdetails.SupplierActivity;
import com.mhl.shop.utils.Urls;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者：lff
 * 时间；2016-11-17 18:04
 * 描述：供应商搜索
 */
public class SupplierSearchAdapter extends MyBaseAdpter {

    private final Context context;
    private final List<SupplierMain> alist;
    public SupplierSearchAdapter(Context context, List<SupplierMain> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.supplier_item, null);
               holder = new ViewHolder();
            holder.icon = (CircleImageView) convertView.findViewById(R.id.icon);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.go= (Button) convertView.findViewById(R.id.go);
            //得到控件
            holder. mRecyclerView = (RecyclerView) convertView.findViewById(R.id.id_recyclerview_horizontal);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent1 = new Intent(context,SupplierActivity.class);
                intent1.putExtra("supplierId", alist.get(position).getPkId());
                context.startActivity(intent1);
            }
        });

        Glide.with(context).load(Urls.URL_PHOTO+"/file/v4/download-"+alist.get(position).getIdentificationId()+"-80-80.jpg").into(holder.icon);

           holder.name.setText(alist.get(position).getSupplierName());



        holder.time.setText("共"+alist.get(position).getGoodsQuantity()+""+"件商品");
//if(alist.get(position).getGoodsList()==null || alist.get(position).getGoodsList().size()<=0){
//    holder.mRecyclerView.setVisibility(View.GONE);
//}else {
    //设置布局管理器
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
    holder.mRecyclerView.setLayoutManager(linearLayoutManager);
    //设置适配器
        SupplierPhotoAdapter mAdapter = new SupplierPhotoAdapter(context,alist.get(position).getGoodsList());
    holder.mRecyclerView.setAdapter(mAdapter);

//}
        return convertView;
    }
    class ViewHolder
    {
        CircleImageView icon;
        TextView name;
        TextView time;
        RecyclerView mRecyclerView;
        Button go;
    }

}
