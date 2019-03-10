package com.person.lx.sign.bean;

import java.time.LocalDateTime;

public class SignLogBean {
    private Integer id;

    /**
     * 员工手机号
     */
    private String phone;

    private Integer companyId;

    /**
     * 签到时间
     */
    private String signIn;

    /**
     * 签出时间
     */
    private String signOut;

    /**
     * 删除标志
     */
    private Integer isactive;

    /**
     * 创建时间
     */
    private String inserttime;

    /**
     * 修改时间
     */
    private String updatetime;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSignIn() {
        return signIn;
    }

    public void setSignIn(String signIn) {
        this.signIn = signIn;
    }

    public String getSignOut() {
        return signOut;
    }

    public void setSignOut(String signOut) {
        this.signOut = signOut;
    }

    public Integer getIsactive() {
        return isactive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setIsactive(Integer isactive) {
        this.isactive = isactive;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getInserttime() {
        return inserttime;
    }

    public void setInserttime(String inserttime) {
        this.inserttime = inserttime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
