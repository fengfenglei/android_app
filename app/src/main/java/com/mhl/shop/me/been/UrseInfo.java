package com.mhl.shop.me.been;

/**
 * 作者：lff
 * 时间；2016-11-24 18:13
 * 描述：我的账户
 */
public class UrseInfo {

//    activationBalance (number, optional): 激活金额 ,
//    activationBalanceDueIn (number, optional): 待激活金额 ,
//    availableBalance (number, optional): 可提现余额 ,
//    avatar (string, optional): 头像 ,
//    browseNum (integer, optional): 浏览数量 ,
//    collectionArticleNum (integer, optional): 收藏文章数量 ,
//    collectionGoodsNum (integer, optional): 收藏商品数量 ,
//    collectionStoreNum (integer, optional): 收藏供应商数量 ,
//    couponsNum (integer, optional): 优惠券数量 ,
//    giftBalance (number, optional): 赠送金额 ,
//    gold (integer, optional): 货郎金币 ,
//    goodsCartNum (integer, optional): 购物车数量 ,
//    loginName (string, optional): 用户名 ,
//    notReceivedNum (integer, optional): 待收货订单数量 ,
//    pendingPaymentNum (integer, optional): 待支付订单数量 ,
//    receivedNum (integer, optional): 已收货订单数量 ,
//    returnNum (integer, optional): 退货订单数量 ,
//    waitDeliveryNum (integer, optional): 待发货订单数量
    /**
     * realName : **喜
     * loginName : 13510273932
     * sex : 3
     * dateOfBirth : -28800000
     * avatar : fbf4adefec654855bd508f2f141689e1
     * payPwd : true
     * authenticationFlag : 1
     * idCardCode : 440301***********6
     */

    private String realName;
    private String loginName;
    private int sex;
    private long dateOfBirth;
    private String avatar;
    private String payPwd;
    private int authenticationFlag;
    private String idCardCode;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(int dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    public int getAuthenticationFlag() {
        return authenticationFlag;
    }

    public void setAuthenticationFlag(int authenticationFlag) {
        this.authenticationFlag = authenticationFlag;
    }

    public String getIdCardCode() {
        return idCardCode;
    }

    public void setIdCardCode(String idCardCode) {
        this.idCardCode = idCardCode;
    }
}
