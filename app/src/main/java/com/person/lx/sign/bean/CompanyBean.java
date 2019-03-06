package com.person.lx.sign.bean;

import java.time.LocalDateTime;

public class CompanyBean {
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    private  Integer id;
    /**
     * 公司图标base64
     */
    private String icon;

    /**
     * 公司名
     */
    private String companyName;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 公司电话
     */
    private String phone;

    /**
     * 公司地址
     */
    private String address;

    /**
     * 公司描述
     */
    private String description;

    /**
     * 删除标志
     */
    private Boolean isactive;

    /**
     * 创建时间
     */
    private LocalDateTime inserttime;

    /**
     * 修改时间
     */
    private LocalDateTime updatetime;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getDesc() {
        return description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDesc(String desc) {
        this.description = desc;
    }
    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }
    public LocalDateTime getInserttime() {
        return inserttime;
    }

    public void setInserttime(LocalDateTime inserttime) {
        this.inserttime = inserttime;
    }
    public LocalDateTime getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(LocalDateTime updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public String toString() {
        return "Company{" +
                "icon=" + icon +
                ", companyName=" + companyName +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", phone=" + phone +
                ", address=" + address +
                ", desc=" + description +
                ", isactive=" + isactive +
                ", inserttime=" + inserttime +
                ", updatetime=" + updatetime +
                "}";
    }
}
