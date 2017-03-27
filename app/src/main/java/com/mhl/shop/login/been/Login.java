package com.mhl.shop.login.been;

public class Login {

    /**
     store_name	string	供应商名称
     store_logo	string	供应商logo
     order_pay	long	待买家付款数量
     order_delivery	long	待发货数量
     be_received	long	待收货数量
     be_evaluated	long	待评价订单数量
     */

    private int be_evaluated;
    private int be_received;
    private int order_delivery;
    private int order_pay;
    private String store_logo;
    private String store_name;

    public int getBe_evaluated() {
        return be_evaluated;
    }

    public void setBe_evaluated(int be_evaluated) {
        this.be_evaluated = be_evaluated;
    }

    public int getBe_received() {
        return be_received;
    }

    public void setBe_received(int be_received) {
        this.be_received = be_received;
    }

    public int getOrder_delivery() {
        return order_delivery;
    }

    public void setOrder_delivery(int order_delivery) {
        this.order_delivery = order_delivery;
    }

    public int getOrder_pay() {
        return order_pay;
    }

    public void setOrder_pay(int order_pay) {
        this.order_pay = order_pay;
    }

    public String getStore_logo() {
        return store_logo;
    }

    public void setStore_logo(String store_logo) {
        this.store_logo = store_logo;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }
}
