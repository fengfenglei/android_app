package com.mhl.shop.shopdetails.adressmanage;


public class ProvinceAdressList
{
	/**
	 * 省id
	 */
	private long	id;
	/**
	 * 省名称
	 */
	private String	name;
	/**
	 * 省名称
	 */
	private String	nameStr;
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getNameStr()
	{
		return nameStr;
	}
	public void setNameStr(String nameStr)
	{
		this.nameStr = nameStr;
	}
	public ProvinceAdressList(long id, String name, String nameStr) {
		super();
		this.id = id;
		this.name = name;
		this.nameStr = nameStr;
	}
	public ProvinceAdressList() {
		super();
	}
	
}
