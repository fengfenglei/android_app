package com.mhl.shop.me;

/**
 * 作者：lff
 * 时间；2016-11-24 14:16
 * 描述：回调
 */
public class MeInterface {
    /**
     * 刷新是否设置密码
     * @author Administrator
     *参数不需要时为空0
     */
    public interface OnMyPasswordListener
    {
        void OnMyPasswordRefresh(String order_stutas,int pageNum);
    }

    public static OnMyPasswordListener onMyPasswordListener	= null;

    public static void setOnMyPasswordListener(OnMyPasswordListener mMyPasswordListener)
    {
        onMyPasswordListener = mMyPasswordListener;
    }
    /**
     * 商品详情收藏刷新
     * @author Administrator
     *参数不需要时为空0
     */
    public interface OnMyGoodsListener
    {
        void OnMyGoodsRefresh(String s,int i);
    }

    public static OnMyGoodsListener onMyGoodsListener	= null;

    public static void setOnMyGoodsListener(OnMyGoodsListener mMyGoodsListener)
    {
        onMyGoodsListener = mMyGoodsListener;
    }
    /**
     * 我的界面刷新
     * @author Administrator
     *参数不需要时为空0
     */
    public interface OnMyMeListener
    {
        void OnMyMeRefresh(String order_stutas,int pageNum);
    }

    public static OnMyMeListener onMyMeListener	= null;

    public static void setOnMyMeListener(OnMyMeListener mMyMeListener)
    {
        onMyMeListener = mMyMeListener;
    }
    /**
     * 购物车刷新
     * @author Administrator
     *参数不需要时为空0
     */
    public interface OnMyCartListener
    {
        void OnMyCartRefresh(String order_stutas,int pageNum);
    }

    public static OnMyCartListener onMyCartListener	= null;

    public static void setOnMyCartListener(OnMyCartListener mMyCartListener)
    {
        onMyCartListener = mMyCartListener;
    }
    /**
     * 发现点赞
     * @author Administrator
     *参数不需要时为空0
     */
    public interface OnMyFindListener
    {
        void OnMyFindRefresh(String order_stutas,int pageNum);
    }

    public static OnMyFindListener onMyFindListener	= null;

    public static void setOnMyFindListener(OnMyFindListener mMyFindListener)
    {
        onMyFindListener = mMyFindListener;
    }
    /**
     * 刷新收藏
     * @author Administrator
     *参数不需要时为空0
     */
    public interface OnMyAllCollectListener
    {
        void OnMyAllCollectRefresh(String order_stutas,int pageNum);
    }

    public static OnMyAllCollectListener	onMyAllCollectListener	= null;

    public static void setOnMyAllCollectListener(OnMyAllCollectListener mMyAllCollectListener)
    {
        onMyAllCollectListener = mMyAllCollectListener;
    }
    /**
     * 刷新收藏供应商
     * @author Administrator
     *参数不需要时为空0
     */
    public interface OnMyAllCollectSupplierListener
    {
        void OnMyAllCollectSupplierRefresh(String order_stutas,int pageNum);
    }

    public static OnMyAllCollectSupplierListener onMyAllCollectSupplierListener	= null;

    public static void setOnMyAllCollectSupplierListener(OnMyAllCollectSupplierListener mMyAllCollectSupplierListener)
    {
        onMyAllCollectSupplierListener = mMyAllCollectSupplierListener;
    }
    /**
     * 通过选中的id来判断选择的常用地址
     */
    public interface OnAddressCheckedListener
    {
        public void CheckedOnSelectListener(String addressId);
    }

    public static OnAddressCheckedListener	m_addressCheckedListener	= null;

    public static void setOnCheckedAddressResult(OnAddressCheckedListener s_checkedListener)
    {
        m_addressCheckedListener = s_checkedListener;
    }
    /**
     * 刷新地址
     */
    public interface OnAddressRefreshListener
    {
        void OnAddressRefresh();
    }

    public static OnAddressRefreshListener	onAddressRefreshListener;

    public static OnAddressRefreshListener getOnAddressRefreshListener()
    {
        return onAddressRefreshListener;
    }

    public static void setOnAddressRefreshListener(OnAddressRefreshListener monAddressRefreshListener)
    {
        onAddressRefreshListener = monAddressRefreshListener;
    }
    /**
     * 地址结果回调
     */
    public interface OnAddressResultListener
    {
        void OnAddressResultRefresh(String address, String id);
    }
    public static OnAddressResultListener onAddressResultListener=null;



    public static OnAddressResultListener getOnAddressResultListener()
    {
        return onAddressResultListener;
    }

    public static void setOnAddressResultListener(OnAddressResultListener monAddressResultListener)
    {
        onAddressResultListener = monAddressResultListener;
    }
    /**
     * 商品详情返回的地址
     */
    public interface OnGoodAddressResultLister{
        public void OnGoodAddressResultInfo(String goodAddress, String id);
    }
    public static OnGoodAddressResultLister onGoodAddressResultLister = null;

    public static void setOnGoodAddressResultLister(OnGoodAddressResultLister o_lisener){
        onGoodAddressResultLister = o_lisener;
    }
    /**
     * 商品详情选择地址回调
     */
    public interface OnGoodsChoiceAddressLister{
        public void OnGoodsChoiceAddressResultInfo(String goodsChoiceAddress[],String id);
    }

    public static OnGoodsChoiceAddressLister onGoodsChiceAddressListener = null;
    public static void setOnGoodsChoiceAddress(OnGoodsChoiceAddressLister goodsChoiceAddressLister){
        onGoodsChiceAddressListener = goodsChoiceAddressLister;
    }

    /**
     * 选择地址回调
     */
    public interface OnChooseAddressListener
    {
        void OnChooseAddressRefresh(String[] address, String id);
    }

    public static OnChooseAddressListener	onChooseAddressListener	= null;
    public static OnChooseAddressListener getOnChooseAddressListener()
    {
        return onChooseAddressListener;
    }

    public static void setOnChooseAddressListener(OnChooseAddressListener monChooseAddressListener)
    {
        onChooseAddressListener = monChooseAddressListener;
    }
}
