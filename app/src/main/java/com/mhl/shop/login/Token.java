package com.mhl.shop.login;

/**
 * 作者：lff
 * 时间；2016-11-15 15:31
 * 描述：Token
 */
public class Token  {


    /**
     * access_token : 4a55cdab-ba58-4264-bbe5-0495c3ce9556
     * token_type : bearer
     * refresh_token : 20182ffa-926f-4856-ae13-59619554e3dc
     * expires_in : 520964
     * scope : openid
     * "error": "invalid_client",
     *"error_description": "Bad client credentials"
     */
    private  String error;
    private  String error_description;
    private  String access_token;
    private  String token_type;
    private  String refresh_token;
    private  int expires_in;
    private  String scope;

    public  String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public  String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public  String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public  String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public  int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public  String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
