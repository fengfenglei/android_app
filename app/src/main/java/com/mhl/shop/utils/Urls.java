package com.mhl.shop.utils;

/**
 * 作者：lff
 * 时间；2016-11-15 15:26
 * 描述：接口
 */
public class Urls {
//    public static final String	URL_BASE_HTTP						= "http://api.51mhl.com";
    public static final String	URL_BASE						= "https://api.51mhl.com";
    public static final String	URL_PHOTO						= "https://cdn.51mhl.com";
    public static final String	URL_SHARE						= "https://wx.51mhl.com";
    public static final String	URL_BASE_HTTP						= "http://api.51mhl.com";

//    public static final String	URL_BASE						= "http://192.168.0.46:38001";

    public static String	JSON						=  ".json";//后缀（token登录接口不需要）
//
    //登录（刷新TOKEN）
    public static final String	TOKEN						= "https://ssl.51mhl.com/oauth/token";
    //首页
    public static final String	APP_HOME_PAGE						= URL_BASE+"/api/mall/app-home-page-conf"+JSON;
    //广告页面
    public static final String	APP_WELCOME						= URL_BASE+"/api/mall/app-welcome-list"+JSON;
    //特色馆一级列表
    public static final String	APP_FEATURE_LIST						= URL_BASE+"/api/mall/app-feature-list"+JSON;
    //特色馆二级列表
    public static final String	APP_FEATURE_DETAIL_LIST						= URL_BASE+"/api/mall/app-feature-detail-list"+JSON;
    //APP提交意见反馈
    public static final String	APP_FEEDBACK						= URL_BASE+"/api/mall/app-feedback"+JSON;
    //获取验证码
    public static final String	SEND_CODE						= URL_BASE+"/api/ucenter/mem-app-user-send-code"+JSON;
    //下一步
    public static final String	SEND_NEXT						= URL_BASE+"/api/ucenter/mem-app-user-register-next"+JSON;
    //会员注册(App完成注册)
    public static final String	REGISTER						= URL_BASE+"/api/ucenter/mem-app-user-register"+JSON;
    //地址列表
    public static final String	ADDRESS_LIST					= URL_BASE+"/api/ucenter/mem-app-address-list"+JSON;
    //地址列表
    public static final String URL_DEL_ADDRESS                  = URL_BASE+"/api/ucenter/mem-address-remove"+JSON;
    //新增地址
    public static final String URL_ADD_ADDRESS                 = URL_BASE+"/api/ucenter/mem-address-add"+JSON;
    //编辑地址
    public static final String URL_EDIT_ADDRESS                 = URL_BASE+"/api/ucenter/mem-address-edit"+JSON;
    //设置默认地址
    public static final String URL_DEFAULT_ADDRESS                 = URL_BASE+"/api/ucenter/mem-address-default"+JSON;
    //地区选择
    public static final String URL_CHINA_ADRESS =                 URL_BASE+"/api/ucenter/sys-app-address-list";
    //我的账户
    public static final String URL_USER_INFO                     = URL_BASE+"/api/ucenter/mem-app-user-base-info"+JSON;
    //修改登录密码
    public static final String URL_RESET_LOGIN_PWD                = URL_BASE+"/api/ucenter/mem-app-login-pwd-reset"+JSON;
    //找回密码发送验证码
    public static final String URL_PWD_SEND_CODE                    = URL_BASE+"/api/ucenter/mem-app-find-pwd-send-code"+JSON;
    //找回密码下一步
    public static final String URL_PWD_SEND_CODE_NEXT                = URL_BASE+"/api/ucenter/mem-app-retrieve-pwd-next"+JSON;
    //找回密码重置密码
    public static final String URL_PWD_FIND_PWD_RESET                = URL_BASE+"/api/ucenter/mem-app-find-pwd-reset"+JSON;
    //设置支付密码下一步
    public static final String URL_PWD_SETTING_NEXT                = URL_BASE+"/api/ucenter/mem-app-pay-pwd-next"+JSON;
    //设置支付密码
    public static final String URL_PWD_SETTING                    = URL_BASE+"/api/ucenter/mem-app-pay-pwd-reset"+JSON;
    //编辑支付密码
    public static final String URL_PWD_EDIT                    = URL_BASE+"/api/ucenter/mem-app-pay-pwd-edit"+JSON;
    //发送手机验证码到登录用户注册手机(APP)
    public static final String URL_PWD_SEND_LOGIN_CODE              = URL_BASE+"/api/ucenter/mem-app-login-send-code"+JSON;
    //获取不同分类的第一条消息
    public static final String URL_INFO_NOTICE_GROUP               = URL_BASE+"/api/ucenter/mem-first-notice-group-by-type"+JSON;
    //是否有新消息
    public static final String URL_INFO_NOTREADNOTICE               = URL_BASE+"/api/ucenter/mem-user-notReadNotice-num-all"+JSON;
    //货郎通知列表
    public static final String URL_INFO_NOTICE_LIST               = URL_BASE+"/api/ucenter/mem-user-notice-list-app"+JSON;
    //修改会员基本信息
    public static final String URL_INFO_EDIT               = URL_BASE+"/api/ucenter/mem-app-user-info-edit"+JSON;
    //上传头像
    public static final String URL_UPLOAD               = URL_BASE+"/api/upload/upload"+JSON;
    //我的首页
    public static final String URL_MINE              = URL_BASE+"/api/ucenter/mem-app-mine-index"+JSON;
    //实名认证
    public static final String URL_AUTHENTICATION_INFO              = URL_BASE+"/api/ucenter/mem-authentication-Info-add"+JSON;
    //获取认证信息审核日志
    public static final String URL_AUTHENTICATION_LOG              = URL_BASE+"/api/ucenter/mem-last-authentication-Info"+JSON;
    //收藏商品列表
    public static final String URL_COLLECTION_GOODS              = URL_BASE+"/api/ucenter/mem-app-user-collection-goods-list"+JSON;
    //收藏供应商列表
    public static final String URL_GONG_GOODS              = URL_BASE+"/api/ucenter/mem-app-user-collection-supplier-list"+JSON;
    //收藏文章列表
    public static final String URL_ARTICLE_GOODS              = URL_BASE+"/api/mall/app-discover-collection-list"+JSON;
    //收藏文章列表
    public static final String URL_GONG_WEN             = URL_BASE+"/api/ucenter/mem-app-user-collection-supplier-list"+JSON;
    //取消商品收藏
    public static final String URL_DELETE_COLLECTSHOP             = URL_BASE+"/api/ucenter/mem-cancel-collection-by-goods"+JSON;
    //取消供应商收藏
    public static final String URL_DELETE_SUPPLIER             = URL_BASE+"/api/ucenter/mem-cancel-collection-by-supplier"+JSON;
    //优惠券列表
    public static final String URL_COUPON_LIST              = URL_BASE+"/api/ucenter/mem-app-user-coupon-list"+JSON;
    //分类统计用户未使用过-已使用过-已过期优惠券数量
    public static final String URL_COUPON_NUM              = URL_BASE+"/api/ucenter/mem-user-coupon-classify-count"+JSON;
    //浏览记录
    public static final String URL_HISTORY_LIST              = URL_BASE+"/api/ucenter/mem-app-user-browsing-history"+JSON;
    //清空浏览记录
    public static final String URL_HISTORY_CLEAR              = URL_BASE+"/api/ucenter/mem-app-user-browsing-history-clear"+JSON;
    //购物车列表
    public static final String URL_CART_LIST              = URL_BASE+"/api/ucenter/mem-cart-list-app"+JSON;
    //  21号替换为/api/ucenter/mem-cart-list-app
    //购物车删除商品
    public static final String URL_GOODS_SHOP              = URL_BASE+"/api/ucenter/mem-user-collection-goods-add-batch"+JSON;
    //购物车删除商品
    public static final String URL_DELETE_SHOP              = URL_BASE+"/api/ucenter/mem-cart-remove-batch"+JSON;
    //商品详情
    public static final String URL_DETAILS_SHOP              = URL_BASE+"/api/operative/goods-detail-app"+JSON;
    //商品评论接口
    public static final String URL_COMMENT_SHOP              = URL_BASE+"/api/operative/goods-evaluate-list"+JSON;
    //商品运费接口
    public static final String URL_POSTAGE_SHOP              = URL_BASE+"/api/operative/goods-postage-by-city"+JSON;
    //商品规格
    public static final String URL_INFO_SHOP              = URL_BASE+"/api/operative/goods-spec-info-app"+JSON;
    //收藏商品
    public static final String URL_COLLECTION_SHOP              = URL_BASE+"/api/ucenter/mem-user-collection-goods-add"+JSON;
    //收藏商品
    public static final String URL_COLLECTION_CANCEL_SHOP              = URL_BASE+"/api/ucenter/mem-user-cancel-goods-collection"+JSON;
    //收藏供应商
    public static final String URL_SUPPLIER_SHOP              = URL_BASE+"/api/ucenter/mem-user-collection-supplier-add"+JSON;
    //取消收藏供应商
    public static final String URL_SUPPLIER_SHOP_CANCEL              = URL_BASE+"/api/ucenter/mem-user-cancel-store-collection"+JSON;
    //改版购物车单个商品的数量
    public static final String URL_CART_CHANGE_GOODS              = URL_BASE+"/api/ucenter/mem-change-goods-cart-num"+JSON;
    //加入购物车
    public static final String URL_CART_ADD              = URL_BASE+"/api/ucenter/mem-cart-add"+JSON;
    //立即购买
    public static final String URL_CART_BUY              = URL_BASE+"/api/ucenter/mem-cart-add-buy-now"+JSON;
    //查看评价
    public static final String URL_VIEW_EVALYATE              = URL_BASE+"/api/operative/goods-evaluate-list"+JSON;
    //查看个人评价
    public static final String URL_USER_EVALYATE              = URL_BASE+"/api/order/order-user-evaluate-app"+JSON;
    //查看供应商列表
    public static final String URL_VIEW_SUPPLIER              = URL_BASE+"/api/operative/goods-info-supplier-list-app"+JSON;
    //查看供应商评分
    public static final String URL_INDEX_SUPPLIER              = URL_BASE+"/api/operative/supplier-index-info-app"+JSON;
    //购物车去结算订单
    public static final String URL_SUBMIT_ORDER             = URL_BASE+"/api/order/order-user-submit-order-general-new"+JSON;
    //提交订单
    public static final String URL_VIEW_CONFIRM              = URL_BASE+"/api/order/order-confirm-app"+JSON;
    //订单列表
    public static final String URL_VIEW_ORDER             = URL_BASE+"/api/order/order-list-app"+JSON;
    //会员申请退款
    public static final String URL_RETURN_ORDER             = URL_BASE+"/api/order/order-user-apply-return-goods-money"+JSON;
    //会员申请退货退款
    public static final String URL_RETURN_GOODS             = URL_BASE+"/api/order/order-user-apply-return-goods"+JSON;
    //订单详情
    public static final String URL_ORDER_DETAIL             = URL_BASE+"/api/order/order-user-order-detail-app"+JSON;
    //订单详情(11.80,99)
    public static final String URL_ORDER_VIR_DETAIL             = URL_BASE+"/api/order/order-detail-vir-app"+JSON;
    //订单日志
    public static final String URL_ORDER_LOG             = URL_BASE+"/api/order/order-log-list-app"+JSON;
    //虚拟订单日志
    public static final String URL_ORDERVIRTUAL_LOG             = URL_BASE+"/api/order/order-virtual-log-list-app"+JSON;
    //物流日志
    public static final String URL_PROGRESS_FOLLOWING             = URL_BASE+"/api/order/order-express-progress-following-by-order-id"+JSON;
    //退货物流日志
    public static final String URL_RETURN_FOLLOWING             = URL_BASE+"/api/order/order-express-progress-following-return-by-order-id"+JSON;
    //手机号码归属地查询
    public static final String URL_MOBILE_ATTRIBUTION             = URL_BASE+"/api/order/order-mobile-attribution"+JSON;
    //获取话费可用充值金额和实付金额
    public static final String URL_MOBILE_BILLINFO             = URL_BASE+"/api/order/order-mobile-bill-info"+JSON;
    //话费充值
    public static final String URL_MOBILE_BILL             = URL_BASE+"/api/order/order-mobile-bill"+JSON;
    //再次购买
    public static final String URL_ORDER_AGAIN             = URL_BASE+"/api/order/order-user-buy-again"+JSON;
    //立即付款
    public static final String URL_GO_PAYMENT             = URL_BASE+"/api/order/order-user-go-payment"+JSON;
    //评价订单
    public static final String URL_ORDER_EVALUATE            = URL_BASE+"/api/order/order-user-order-evaluate"+JSON;
    //确认收货
    public static final String URL_ORDER_CONSIGN            = URL_BASE+"/api/order/order-user-consign-goods"+JSON;
    //退货详情
    public static final String URL_ORDER_RETURN_DETAIL          = URL_BASE+"/api/order/order-return-detail-app"+JSON;
    //取消订单
    public static final String URL_ORDER_RETURN_ANCEL         = URL_BASE+"/api/order/order-user-cancel-order-vir"+JSON;
    //撤销退款
    public static final String URL_ORDER_RETURN_CANCEL         = URL_BASE+"/api/order/order-user-cancel-return-money"+JSON;
    //撤销退货退款
    public static final String URL_ORDER_RETURN_CANCEL_GOODS         = URL_BASE+"/api/order/order-user-cancel-return-goods"+JSON;
    //获取快递公司列表
    public static final String URL_ORDER_RETURN_COMPANY       = URL_BASE+"/api/ucenter/sys-express-company-list"+JSON;
    //会员发货
    public static final String URL_ORDER_RETURN_DELIVER       = URL_BASE+"/api/order/order-user-deliver-goods"+JSON;
    //支付宝支付
    public static final String URL_ORDER_ALIPAY       = URL_BASE+"/api/finance/plypay/appalipay"+JSON;
    //微信支付
    public static final String URL_ORDER_WXPAY       = URL_BASE+"/api/finance/plypay/appwxpay"+JSON;
    //貨郎豆
    public static final String URL_LIST_GOLD       = URL_BASE+"/api/finance/finance-gold-record-list-app"+JSON;
    //交易記錄
    public static final String URL_RECORD_FUND      = URL_BASE+"/api/finance/finance-fund-record-list-app"+JSON;
    //货郎豆交易详情
    public static final String URL_RECORD_DETAIL      = URL_BASE+"/api/finance/finance-gold-record-detail-app"+JSON;
    //待激活交易详情
    public static final String URL_ACTIVATION_DETAIL      = URL_BASE+"/api/finance/finance-wait-activation-detail-app"+JSON;
    //交易明细交易详情
    public static final String URL_RECORD_FUND_DETAIL      = URL_BASE+"/api/finance/finance-fund-record-detail-app"+JSON;
    //获取待激活金额明细记录列表
    public static final String URL_WAIT_ACTIVATION_LIST      = URL_BASE+"/api/finance/finance-wait-activation-list-app"+JSON;
    //APP获取用户认证状态
    public static final String URL_AUTHENTICATION_STATUS      = URL_BASE+"/api/ucenter/mem-authentication-status"+JSON;
    //获取用户绑定银行卡信息
    public static final String URL_BANK_INFO      = URL_BASE+"/api/finance/finance-user-withdrawal-init-app"+JSON;
    //会员提现申请
    public static final String URL_WITHDRAWAL      = URL_BASE+"/api/finance/finance-app-withdrawal-user-apply"+JSON;
    //获取用户中心推荐会员列表
    public static final String URL_RECOMMEND_LIST      = URL_BASE+"/api/ucenter/mem-user-recommend-list-api"+JSON;
    //获取用户安全中心状态
    public static final String URL_SECURITY_INFO      = URL_BASE+"/api/ucenter/mem-security-info"+JSON;
    //获取会员用户资金账户
    public static final String URL_USER_BALANCE      = URL_BASE+"/api/ucenter/mem-user-balance"+JSON;
    //获取认证信息真是姓名，身份证号（认证成功后）
    public static final String URL_REALNAME_IDCARD     = URL_BASE+"/api/ucenter/mem-get-realname-and-idcard"+JSON;
    //会员绑定银行卡信息（只能绑定认证信息的银行卡）
    public static final String URL_REALNAME_IDCARD_INFO     = URL_BASE+"/api/ucenter/mem-auth-bind-bank-info"+JSON;
    //获取用户绑定银行卡信息详细
    public static final String URL_REALNAME_IDCARD_INFO_FULL     = URL_BASE+"/api/ucenter/mem-user-bank-info-full"+JSON;
    //修改绑定银行卡信息
    public static final String URL_REALNAME_EDIT_INFO_FULL     = URL_BASE+"/api/ucenter/mem-edit-bank-info"+JSON;
    //查询发现列表
    public static final String URL_DISCOVER_LIST      = URL_BASE+"/api/mall/app-discover-list"+JSON;
    //赞或者取消赞
    public static final String URL_DISCOVER_FAVOR      = URL_BASE+"/api/mall/app-discover-favor"+JSON;
    //发现详情
    public static final String URL_DISCOVER_DETAIL      = URL_BASE+"/api/mall/app-discover-detail"+JSON;
    //文章收藏(取消收藏)
    public static final String URL_DISCOVER_COLLECTION      = URL_BASE+"/api/mall/app-discover-collection"+JSON;
    //商品联想搜索
    public static final String URL_SEARCH_NAME     = URL_BASE+"/api/operative/goods-name-search-list-app"+JSON;
    //商品热搜
    public static final String URL_HOT_KEYWORD     = URL_BASE+"/api/operative/goods-hot-keyword"+JSON;
    //供应商搜索
    public static final String URL_UPPLIER_NAME    = URL_BASE+"/api/operative/supplier-get-by-name"+JSON;
    //商品
    public static final String URL_SHOP_LIST    = URL_BASE+"/api/operative/goods-info-search-list-app"+JSON;
    //APP会员充值
    public static final String URL_RECHARGE_APP    = URL_BASE+"/api/finance/finance-user-recharge"+JSON;
    //检查更新
    public static final String URL_APP_VERSION    = URL_BASE+"/api/mall/app-version"+JSON;
}
