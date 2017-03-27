package com.mhl.shop.me.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.callback.StringDialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.login.been.Null;
import com.mhl.shop.main.MyBaseAdpter;
import com.mhl.shop.main.StringDate;
import com.mhl.shop.me.AddAdressActivity;
import com.mhl.shop.me.ManageAdressActivity;
import com.mhl.shop.me.MeInterface;
import com.mhl.shop.me.been.Address;
import com.mhl.shop.utils.GsonUtils;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.Urls;
import com.mhl.shop.wheel.AlertDialog;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-11-17 18:04
 * 描述：地址列表
 */
public class AdressManageAdapter extends MyBaseAdpter {

    private final ManageAdressActivity context;
    private final List<Address.RowsBean> alist;

    public AdressManageAdapter(ManageAdressActivity context, List<Address.RowsBean> list) {
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
//        final ReceiveAdress adress = list.get(position);
        View view = convertView;
        final ViewHolder holder;
        if (view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_adress_item, null);
            holder = new ViewHolder();
            holder.addressLayout = (LinearLayout) view.findViewById(R.id.address_layout);
            holder.big_address_layout = (LinearLayout) view.findViewById(R.id.big_address_layout);
            holder.userName = (TextView) view.findViewById(R.id.my_account_adress_manage_item_username);
            holder.userHpone = (TextView) view.findViewById(R.id.my_account_adress_manage_item_phone);
            holder.deatilAdress = (TextView) view.findViewById(R.id.my_account_adress_manage_item_deatil);
            holder.settingDefault = (CheckBox) view.findViewById(R.id.my_account_adress_manage_setting_default);
            holder.edit = (ImageView) view.findViewById(R.id.my_account_adress_manage_item_edit);
            holder.delete = (ImageView) view.findViewById(R.id.my_account_adress_manage_item_delete);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        holder.userName.setText(alist.get(position).getConsignee());
        holder.userHpone.setText(alist.get(position).getMobile());
        holder.deatilAdress.setText(alist.get(position).getProvince()+" "+alist.get(position).getCity()+" "+alist.get(position).getDistrict()+" "+alist.get(position).getStreet()+" "+alist.get(position).getVillage()+" "+alist.get(position).getAddressInfo());
        //设置默认地址
       if(alist.get(position).getDefaultFlag()==1){
           holder.settingDefault.setChecked(true);
       }else {
           holder.settingDefault.setChecked(false);
       }
        // 删除
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                post(alist.get(position).getPkId()+"",position);
            }
        });
        final int pos = position; // pos必须声明为final

        // 点击设置按钮
        holder.settingDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                defaults(alist.get(position).getPkId()+"",position);
//                listener.onItemClick(alist.get(position).getId()+"");
//                if (CartInterface.m_addressDefaultCheckedListener != null)
//                {
//                    CartInterface.m_addressDefaultCheckedListener.DefaultCheckedOnSelectListener(holder.settingDefault, alist.get(position).getId()+"");
//                }
//                for (int i = 0; i < checks.length; i++)
//                {
//                    if (i == position)
//                    {
//                        checks[pos] = true;
//                    }
//                    else
//                    {
//                        checks[i] = false;
//                    }
//
//                }
//                holder.settingDefault.setChecked(checks[position]);

            }
        });
        // 点击设置按钮
        holder.big_address_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
//                listener.onItemClick(alist.get(position).getId()+"");
//                if (CartInterface.m_addressDefaultCheckedListener != null)
//                {
//                    CartInterface.m_addressDefaultCheckedListener.DefaultCheckedOnSelectListener(holder.settingDefault, alist.get(position).getId()+"");
//                }
//                for (int i = 0; i < checks.length; i++)
//                {
//                    if (i == position)
//                    {
//                        checks[pos] = true;
//                    }
//                    else
//                    {
//                        checks[i] = false;
//                    }
//
//                }
//                holder.settingDefault.setChecked(checks[position]);

            }
        });
        // 编辑
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.putExtra("adressId", String.valueOf(alist.get(position).getPkId()));// 指定地址id
                intent.putExtra("TrueName", alist.get(position).getConsignee());
                intent.putExtra("Mobile", alist.get(position).getMobile());
                intent.putExtra("Zip", alist.get(position).getZip());
                intent.putExtra("WhereAdress", alist.get(position).getProvince()+" "+alist.get(position).getCity()+" "+alist.get(position).getDistrict()+" "+alist.get(position).getStreet()+" "+alist.get(position).getVillage());
                intent.putExtra("getArea_info", alist.get(position).getAddressInfo());
                intent.putExtra("defaults", alist.get(position).getDefaultFlag()+"");
                intent.setClass(context, AddAdressActivity.class);
                context.startActivity(intent);
            }
        });
        // 点击选择地址
        holder.addressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String addressId = String.valueOf( alist.get(position).getPkId());

                if (MeInterface.m_addressCheckedListener != null)
                {
                    MeInterface.m_addressCheckedListener.CheckedOnSelectListener(addressId);
                }
            }
        });

        return view;
    }

    private void defaults(String adressId, int position) {


        OkGo.post(Urls.URL_DEFAULT_ADDRESS)//
                .tag(this)
                .params("addressId", adressId)
                .execute( new DialogCallback<LzyResponse<Null>>(context, true) {
                    @Override
                    public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                        handleResponse(lzyResponse, call, response);
                        if(lzyResponse.code==200){
                               if (MeInterface.onAddressRefreshListener != null)
                                {
                                    MeInterface.onAddressRefreshListener.OnAddressRefresh();// 回调刷新地址
                                }
                            T.showShort(context,  "设置默认地址成功");
//                                finish();
                        }else{
                            T.showShort(context, lzyResponse.info);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        handleError(call, response,e);
                    }
                });

    }
    StringDate stringDate;
    private void post(final String id, final int position) {
        new AlertDialog(context).builder().setTitle("温馨提示")
                .setMsg("确定要删除该地址吗？")
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OkGo.post(Urls.URL_DEL_ADDRESS)//
                                .tag(this)
                                .params("addressId",id)
                                .execute( new StringDialogCallback(context, true) {
                                              @Override
                                              public void onSuccess(String s, Call call, Response response) {
                                                  handleResponse(s, call, response);
                                                  stringDate=(StringDate) GsonUtils.fromJson(s,
                                                          StringDate.class);
                                                  if(stringDate.code==200){
                                                      if(alist.size()==1){
                                                          if (MeInterface.onAddressRefreshListener != null)
                                                          {
                                                              MeInterface.onAddressRefreshListener.OnAddressRefresh();// 回调刷新地址
                                                          }
                                                      }else {
                                                          alist.remove(position);
                                                          notifyDataSetChanged();
                                                      }
                                                      T.showShort(context,  "删除成功！");

                                                  }else{
                                                      T.showShort(context,  stringDate.info);
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
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }
    public interface OnItemClickListener {
        void onItemClick(String newPrice);

    }
    class ViewHolder
    {
        LinearLayout addressLayout,big_address_layout;
        TextView		userName;
        TextView		userHpone;
        TextView deatilAdress;
        CheckBox settingDefault;
        ImageView		edit;
        ImageView delete;
    }

}
