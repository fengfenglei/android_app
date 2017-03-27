package com.mhl.shop.shopdetails.adressmanage;

import java.util.List;


public class ProvinceAdressManage
{
	private List<ProvinceAdressList>	area;

	public List<ProvinceAdressList> getArea()
	{
		return area;
	}

	public void setArea(List<ProvinceAdressList> area)
	{
		this.area = area;
	}

	public ProvinceAdressManage(List<ProvinceAdressList> area) {
		super();
		this.area = area;
	}

	public ProvinceAdressManage() {
		super();
	}
	
	
}
