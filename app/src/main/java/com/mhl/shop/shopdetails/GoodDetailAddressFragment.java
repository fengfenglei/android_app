package com.mhl.shop.shopdetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.MyBaseAddressFragment;
import com.mhl.shop.me.MeInterface;
import com.mhl.shop.me.adapter.ChooseAddressAdapter;
import com.mhl.shop.me.been.ProvinceAdressManage;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @项目名: 51mhl_androidapp
 * @包名: com.mhl.shop.fragment
 * @类名: GoodDetailAddressFragment
 * @创建者: lff
 * @创建时间: 2015-10-13 上午11:56:58
 * @描述: 商品详情选择地址,添加地址fragment
 * @svn版本: $Rev: 15021 $
 * @更新人: $Author: lq $
 * @更新时间: $Date: 2015-11-18 14:46:52 +0800 (星期三, 18 十一月 2015) $
 * @更新描述:TODO
 */
public class GoodDetailAddressFragment extends MyBaseAddressFragment {

	private ProvinceAdressManage provinceAdressManage;
	private List<String>			provincesNameList;				// 省级地址集合
	private List<String>			provincesIdList;			// 省级地址集id
	private ListView addressListView;
	private ChooseAddressAdapter provincesAdapter;
	private String					goodAddress;
	private String					goodAddressId;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_good_address, null);
		addressListView = (ListView) view.findViewById(R.id.good_address_choose_item);
		initArearAddress();  //获取全国的省份  
		return view;
	}
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(getActivity());
	}
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(getActivity());

	}
	
	/**
	 * 获取服务器省级地址
	 */
	protected void initArearAddress(){
//		hideAdressInfo();  
		final String addresss = getArguments().getString("address");
		final String id = getArguments().getString("id"); 
		// final boolean wu=getArguments().getBoolean("wu");
		final int tag = getArguments().getInt("tag");
		final int selectTag = getArguments().getInt("selectTag");
		
		String common = "";
		common = Urls.URL_CHINA_ADRESS + Urls.JSON;
		OkGo.post(common)//
				.tag(this)
				.params("parentId",id)
				.execute(new DialogCallback<LzyResponse<ProvinceAdressManage>>(getActivity(), true) {
					@Override
					public void onSuccess(LzyResponse<ProvinceAdressManage> provinceAdressManageLzyResponse, Call call, Response response) {
						provincesNameList = new ArrayList<String>();
						provincesIdList = new ArrayList<String>();
						provinceAdressManage=provinceAdressManageLzyResponse.data;


							if (provinceAdressManage.getRows().size()> 0)
							{

								for (int i = 0; i < provinceAdressManage.getRows().size(); i++)
								{
									String province = provinceAdressManage.getRows().get(i).getAreaName();
									String provinceId = provinceAdressManage.getRows().get(i).getPkId() + "";
									provincesNameList.add(province);
									provincesIdList.add(provinceId);
								}
//							if (tag >= 3)// 省市县级地址不添加无
//							{
//								provincesNameList.add("无");
//							}

							provincesAdapter = new ChooseAddressAdapter(getActivity(), provincesNameList);
							addressListView.setAdapter(provincesAdapter);

							addressListView.setOnItemClickListener(new OnItemClickListener() {
								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
//									goodAddress = provincesNameList.get(arg2).toString()+" >";

									goodAddress = addresss + provincesNameList.get(arg2) + " >";
									String idCurrent = provincesIdList.get(arg2);
									String[] a = goodAddress.split(">");
									String c = "";
//									if (provincesNameList.get(arg2).equals("无"))// 如果选择无则返回
//									{
									if(a.length>=selectTag){
										for (int i = 0; i < a.length; i++)
										{
											c = c + a[i] + ""; //
										}
										a = c.split(" ");
										goodAddressId = id;  //
										if (MeInterface.onGoodAddressResultLister != null) {
											MeInterface.onGoodAddressResultLister.OnGoodAddressResultInfo(c,idCurrent);
											getActivity().finish();
											return;  //选择无后不能再执行后面的方法
										}
									}
									else
									{
										goodAddressId = provincesIdList.get(arg2);
									}

									if (MeInterface.onGoodsChiceAddressListener != null) {
										MeInterface.onGoodsChiceAddressListener.OnGoodsChoiceAddressResultInfo(a,goodAddressId);
									}
								}
							});
						}else {
							String tureAddress;
							tureAddress = addresss.replace(">", "");// 把>转为空组装地址

							if (MeInterface.onGoodAddressResultLister != null) {
								MeInterface.onGoodAddressResultLister.OnGoodAddressResultInfo(tureAddress,id);
								getActivity().finish();
							}
						}

					}

					@Override
					public void onError(Call call, Response response, Exception e) {
						super.onError(call, response, e);
						handleError(call, response, e);
					}
				});
//		CSendHttp.sendHttp_post(getActivity(), common, null, "post", true, "正在加载...", new ResultListener() {
//			@Override
//			public void handleResult(String result) {
//				System.out.println(result);
//				if (!result.equals("doPostError")&&result!=null&&!result.equals("")) {
//					try {
//						provinceAdressManage = JSON.parseObject(result, ProvinceAdressManage.class);
//						provincesNameList = new ArrayList<String>();
//						provincesIdList = new ArrayList<String>();
//						if (provinceAdressManage!=null&&provinceAdressManage.getArea().size()>0) {
//							for (int i = 0; i < provinceAdressManage.getArea().size(); i++) {
//								String areaName = provinceAdressManage.getArea().get(i).getName();  //地址名称
//								String areaId = provinceAdressManage.getArea().get(i).getId()+"";   //地址的id
//								provincesNameList.add(areaName);
//								provincesIdList.add(areaId);
//							}
////							if (tag >= 3)// 省市县级地址不添加无
////							{
////								provincesNameList.add("无");
////							}
//
//							provincesAdapter = new ChooseAddressAdapter(getActivity(), provincesNameList);
//							addressListView.setAdapter(provincesAdapter);
//
//							addressListView.setOnItemClickListener(new OnItemClickListener() {
//								@Override
//								public void onItemClick(AdapterView<?> arg0,
//										View arg1, int arg2, long arg3) {
////									goodAddress = provincesNameList.get(arg2).toString()+" >";
//
//									goodAddress = addresss + provincesNameList.get(arg2) + " >";
//									String idCurrent = provincesIdList.get(arg2);
//									String[] a = goodAddress.split(">");
//									String c = "";
////									if (provincesNameList.get(arg2).equals("无"))// 如果选择无则返回
////									{
//									if(a.length>=selectTag){
//										for (int i = 0; i < a.length; i++)
//										{
//											c = c + a[i] + ""; //
//										}
//										a = c.split(" ");
//										goodAddressId = id;  //
//										if (CartInterface.onGoodAddressResultLister != null) {
//											CartInterface.onGoodAddressResultLister.OnGoodAddressResultInfo(c,idCurrent);
//											getActivity().finish();
//											return;  //选择无后不能再执行后面的方法
//										}
//									}
//									else
//									{
//										goodAddressId = provincesIdList.get(arg2);
//									}
//
//									if (CartInterface.onGoodsChiceAddressListener != null) {
//										CartInterface.onGoodsChiceAddressListener.OnGoodsChoiceAddressResultInfo(a,goodAddressId);
//									}
//								}
//							});
//						}else {
//							String tureAddress;
//							tureAddress = addresss.replace(">", "");// 把>转为空组装地址
//
//							if (CartInterface.onGoodAddressResultLister != null) {
//								CartInterface.onGoodAddressResultLister.OnGoodAddressResultInfo(tureAddress,id);
//								getActivity().finish();
//							}
//						}
//
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//
//				}else {
////					TipstUtils.show(GoodsActivity.this, "请求地址出错,可能是网络的原因");
//				}
//			}
//		});
	}
	
}
