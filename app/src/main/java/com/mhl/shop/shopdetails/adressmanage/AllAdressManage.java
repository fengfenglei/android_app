package com.mhl.shop.shopdetails.adressmanage;

public class AllAdressManage
{

	/**
	 * 地址
	 * 
	 */
	private String area;
	/**
	 * 地址总条数
	 * 
	 */
	private int pageCount;
	/**
	 * 当前页
	 * 
	 */
	private int pageNum;
	/**
	 * 地址总页数
	 * 
	 */
	private int pageTotal;
	
	/**
	 * 地址每页条数
	 * 
	 */
	private int size;

	public String getArea()
	{
		return area;
	}

	public void setArea(String area)
	{
		this.area = area;
	}

	public int getPageCount()
	{
		return pageCount;
	}

	public void setPageCount(int pageCount)
	{
		this.pageCount = pageCount;
	}

	public int getPageNum()
	{
		return pageNum;
	}

	public void setPageNum(int pageNum)
	{
		this.pageNum = pageNum;
	}

	public int getPageTotal()
	{
		return pageTotal;
	}

	public void setPageTotal(int pageTotal)
	{
		this.pageTotal = pageTotal;
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}

	
	
}
