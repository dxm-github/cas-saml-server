package com.uyun.cas.server.credential;

import org.apereo.cas.authentication.UsernamePasswordCredential;

/**
 * @author dongxiaoming
 * @date 2022/11/23
 */
public class UyunUserCredential extends UsernamePasswordCredential {
    private String userId;
    private String tenantId;
    private String realname;
    private String account;
    private String email;
    private String mobile;

    public UyunUserCredential(String account, String password) {
        super(account,password);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
