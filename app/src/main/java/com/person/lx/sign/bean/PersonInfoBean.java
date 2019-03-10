package com.person.lx.sign.bean;

import java.time.LocalDateTime;

public class PersonInfoBean {

    private Integer id;

    private Integer sex;

    private String img;
    /**
     * 员工ID
     */
    private String workId;

    /**
     * 员工名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 部门ID
     */
    private Integer departmentId;

    /**
     * 密码
     */
    private String password;

    /**
     * token
     */
    private String token;

    /**
     * 公司ID
     */
    private Integer companyId;

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

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
    public String getPassword() {
        return password;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getIsactive() {
        return isactive;
    }

    public void setIsactive(Integer isactive) {
        this.isactive = isactive;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "CompanyStaff{" +
                "workId=" + workId +
                ", username=" + username +
                ", email=" + email +
                ", phone=" + phone +
                ", departmentId=" + departmentId +
                ", password=" + password +
                ", token=" + token +
                ", companyId=" + companyId +
                ", isactive=" + isactive +
                ", inserttime=" + inserttime +
                ", updatetime=" + updatetime +
                "}";
    }
}
