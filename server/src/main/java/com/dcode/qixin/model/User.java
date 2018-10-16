package com.dcode.qixin.model;

import java.io.Serializable;

public class User implements Serializable {


    private Integer sysId;

    private String name;

    private String nickname;

    private String password;

    private Short pwErrCount;

    private Integer roleId;

    private String token;

    private Long tokenExpire;

    private Short level;

    private Integer createUser;

    private Integer creditLimit;
    private Integer creditLimitLeft;

    private Short eneabled;

    private Short deleted;

    public Integer getSysId() {
        return sysId;
    }

    public void setSysId(Integer sysId) {
        this.sysId = sysId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Short getPwErrCount() {
        return pwErrCount;
    }

    public void setPwErrCount(Short pwErrCount) {
        this.pwErrCount = pwErrCount;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public Long getTokenExpire() {
        return tokenExpire;
    }

    public void setTokenExpire(Long tokenExpire) {
        this.tokenExpire = tokenExpire;
    }

    public Short getLevel() {
        return level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Integer creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Integer getCreditLimitLeft() {
        return creditLimitLeft;
    }

    public void setCreditLimitLeft(Integer creditLimitLeft) {
        this.creditLimitLeft = creditLimitLeft;
    }

    public Short getDeleted() {
        return deleted;
    }

    public void setDeleted(Short deleted) {
        this.deleted = deleted;
    }

    public Short getEneabled() {
        return eneabled;
    }

    public void setEneabled(Short eneabled) {
        this.eneabled = eneabled;
    }
}