package com.mhl.shop.me.myview;

/**
 * 作者：lff
 * 时间；2016-11-18 18:11
 * 描述：
 */
public enum KeyboardEnum {

    one(ActionEnum.add,"1"),
    two(ActionEnum.add,"2"),
    three(ActionEnum.add,"3"),
    four(ActionEnum.add,"4"),
    five(ActionEnum.add,"5"),
    sex(ActionEnum.add,"6"),
    seven(ActionEnum.add,"7"),
    eight(ActionEnum.add,"8"),
    nine(ActionEnum.add,"9"),
    zero(ActionEnum.add,"0"),
    del(ActionEnum.delete,"del"),
    keyboard_gone(ActionEnum.gone,"keyboard_gone"),
    box1(ActionEnum.box1,"box1"),

    longdel(ActionEnum.longClick,"longclick"),
    cancel(ActionEnum.cancel,"cancel"),
    sure(ActionEnum.sure,"sure");
    public enum ActionEnum{
        add,delete,longClick,cancel,sure,gone,box1
    }
    private ActionEnum type;
    private String value;
    private KeyboardEnum(ActionEnum type,String value){
        this.type=type;
        this.value=value;
    }
    public ActionEnum getType() {
        return type;
    }
    public void setType(ActionEnum type) {
        this.type = type;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }


}
