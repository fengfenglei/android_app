package com.mhl.shop.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.MyBaseAddressFragment;
import com.mhl.shop.me.adapter.ChooseAddressAdapter;
import com.mhl.shop.me.been.ProvinceAdressManage;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：Administrator
 * 时间；2016-11-24 14:21
 * 描述：
 */
public class AddAddressFragment extends MyBaseAddressFragment {
    private ProvinceAdressManage	provinceAdressManage;
    private List<String> provinces;				// 省级地址集合
    private List<String>			provincesId;			// 省级地址集id
    private ListView address_choose_item;
    private String					address;
    private ChooseAddressAdapter adapter;
    private String					addressId;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_add_address, null);
        address_choose_item = (ListView) view.findViewById(R.id.address_choose_item);
        getChinaAdress();// 获取全国的省份
        return view;
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }
    private void getChinaAdress()
    {
        String common;
        final String addresss = getArguments().getString("address");
        final String id = getArguments().getString("id");
        final int tag = getArguments().getInt("tag");
//        if (id != null && !id.equals(""))
//        {
//            common = Urls.URL_CHINA_ADRESS + "/" + id +  Urls.JSON;
//        }
//        else
//        {
            common = Urls.URL_CHINA_ADRESS + Urls.JSON;
//        }

        OkGo.post(common)//
                .tag(this)
                .params("parentId",id)
                .execute(new DialogCallback<LzyResponse<ProvinceAdressManage>>(getActivity(), true) {
                    @Override
                    public void onSuccess(LzyResponse<ProvinceAdressManage> provinceAdressManageLzyResponse, Call call, Response response) {
                        provinces = new ArrayList<String>();
                        provincesId = new ArrayList<String>();
                        provinceAdressManage=provinceAdressManageLzyResponse.data;
                        if (provinceAdressManageLzyResponse != null)
                        {
                            if (provinceAdressManage.getRows().size()> 0)
                            {

                                for (int i = 0; i < provinceAdressManage.getRows().size(); i++)
                            {
                                String province = provinceAdressManage.getRows().get(i).getAreaName();
                                String provinceId = provinceAdressManage.getRows().get(i).getPkId() + "";
                                provinces.add(province);
                                provincesId.add(provinceId);
                            }
                                if (tag >= 3)// 省市县级地址不添加无
                                {
                                    provinces.add("无");
                                }
                                adapter = new ChooseAddressAdapter(getActivity(), provinces);
                                address_choose_item.setAdapter(adapter);
                                address_choose_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
                                    {
                                        address = addresss + provinces.get(arg2) + " >";
                                        String[] a = address.split(">");
                                        String c = "";
                                        if (provinces.get(arg2).equals("无"))// 如果选择无则返回
                                        {
                                            for (int i = 0; i < a.length - 1; i++)
                                            {
                                                c = c + a[i] + ""; //
                                            }
                                            a = c.split(" ");
                                            addressId = id;  //
                                            if (MeInterface.onAddressResultListener != null)
                                            {
                                                MeInterface.onAddressResultListener.OnAddressResultRefresh(c, id);
                                                getActivity().finish();
                                                return;// 选择无后不能再执行后面的方法
                                            }

                                        }
                                        else
                                        {

                                            addressId = provincesId.get(arg2);
                                        }
                                        if (MeInterface.onChooseAddressListener != null)
                                        {
                                            MeInterface.onChooseAddressListener.OnChooseAddressRefresh(a, addressId);
                                        }
                                    }

                                });
                            }
                            else
                            {
                                String tureAddress;
                                tureAddress = addresss.replace(">", "");// 把>转为空组装地址
                                if (MeInterface.onAddressResultListener != null)
                                {
                                    MeInterface.onAddressResultListener.OnAddressResultRefresh(tureAddress, id);
                                    getActivity().finish();
                                }
                            }
                        }
                        //

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        handleError(call, response, e);
                    }
                });

    }

}
