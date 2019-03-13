package com.xqlh.beatandshout.Entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class UserInfo implements Serializable {
    private Integer id;

    private String realname;

    private String username;

    private String openid;

    private String nickname;

    private String headimgurl;

    private String sex;

    private Boolean isadmin;

    private Boolean ishighestadmin;

    private Boolean issystemadmin;

    private Date birthdate;

    private String telephone;

    private String email;

    private Integer educationlevel;

    private String userpassword;

    private Date createtime;

    private String residence;

    private String province;

    private String city;

    private Integer groupid;

    private Integer consultanttitle;

    private String personalprofile;

    private String idnum;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Boolean getIsadmin() {
        return isadmin;
    }

    public void setIsadmin(Boolean isadmin) {
        this.isadmin = isadmin;
    }

    public Boolean getIshighestadmin() {
        return ishighestadmin;
    }

    public void setIshighestadmin(Boolean ishighestadmin) {
        this.ishighestadmin = ishighestadmin;
    }

    public Boolean getIssystemadmin() {
        return issystemadmin;
    }

    public void setIssystemadmin(Boolean issystemadmin) {
        this.issystemadmin = issystemadmin;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEducationlevel() {
        return educationlevel;
    }

    public void setEducationlevel(Integer educationlevel) {
        this.educationlevel = educationlevel;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public Integer getConsultanttitle() {
        return consultanttitle;
    }

    public void setConsultanttitle(Integer consultanttitle) {
        this.consultanttitle = consultanttitle;
    }

    public String getPersonalprofile() {
        return personalprofile;
    }

    public void setPersonalprofile(String personalprofile) {
        this.personalprofile = personalprofile;
    }

    public String getIdnum() {
        return idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        UserInfo other = (UserInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRealname() == null ? other.getRealname() == null : this.getRealname().equals(other.getRealname()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getOpenid() == null ? other.getOpenid() == null : this.getOpenid().equals(other.getOpenid()))
            && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
            && (this.getHeadimgurl() == null ? other.getHeadimgurl() == null : this.getHeadimgurl().equals(other.getHeadimgurl()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getIsadmin() == null ? other.getIsadmin() == null : this.getIsadmin().equals(other.getIsadmin()))
            && (this.getIshighestadmin() == null ? other.getIshighestadmin() == null : this.getIshighestadmin().equals(other.getIshighestadmin()))
            && (this.getIssystemadmin() == null ? other.getIssystemadmin() == null : this.getIssystemadmin().equals(other.getIssystemadmin()))
            && (this.getBirthdate() == null ? other.getBirthdate() == null : this.getBirthdate().equals(other.getBirthdate()))
            && (this.getTelephone() == null ? other.getTelephone() == null : this.getTelephone().equals(other.getTelephone()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getEducationlevel() == null ? other.getEducationlevel() == null : this.getEducationlevel().equals(other.getEducationlevel()))
            && (this.getUserpassword() == null ? other.getUserpassword() == null : this.getUserpassword().equals(other.getUserpassword()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getResidence() == null ? other.getResidence() == null : this.getResidence().equals(other.getResidence()))
            && (this.getProvince() == null ? other.getProvince() == null : this.getProvince().equals(other.getProvince()))
            && (this.getCity() == null ? other.getCity() == null : this.getCity().equals(other.getCity()))
            && (this.getGroupid() == null ? other.getGroupid() == null : this.getGroupid().equals(other.getGroupid()))
            && (this.getConsultanttitle() == null ? other.getConsultanttitle() == null : this.getConsultanttitle().equals(other.getConsultanttitle()))
            && (this.getPersonalprofile() == null ? other.getPersonalprofile() == null : this.getPersonalprofile().equals(other.getPersonalprofile()))
            && (this.getIdnum() == null ? other.getIdnum() == null : this.getIdnum().equals(other.getIdnum()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRealname() == null) ? 0 : getRealname().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getOpenid() == null) ? 0 : getOpenid().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getHeadimgurl() == null) ? 0 : getHeadimgurl().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getIsadmin() == null) ? 0 : getIsadmin().hashCode());
        result = prime * result + ((getIshighestadmin() == null) ? 0 : getIshighestadmin().hashCode());
        result = prime * result + ((getIssystemadmin() == null) ? 0 : getIssystemadmin().hashCode());
        result = prime * result + ((getBirthdate() == null) ? 0 : getBirthdate().hashCode());
        result = prime * result + ((getTelephone() == null) ? 0 : getTelephone().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getEducationlevel() == null) ? 0 : getEducationlevel().hashCode());
        result = prime * result + ((getUserpassword() == null) ? 0 : getUserpassword().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getResidence() == null) ? 0 : getResidence().hashCode());
        result = prime * result + ((getProvince() == null) ? 0 : getProvince().hashCode());
        result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
        result = prime * result + ((getGroupid() == null) ? 0 : getGroupid().hashCode());
        result = prime * result + ((getConsultanttitle() == null) ? 0 : getConsultanttitle().hashCode());
        result = prime * result + ((getPersonalprofile() == null) ? 0 : getPersonalprofile().hashCode());
        result = prime * result + ((getIdnum() == null) ? 0 : getIdnum().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", realname=").append(realname);
        sb.append(", username=").append(username);
        sb.append(", openid=").append(openid);
        sb.append(", nickname=").append(nickname);
        sb.append(", headimgurl=").append(headimgurl);
        sb.append(", sex=").append(sex);
        sb.append(", isadmin=").append(isadmin);
        sb.append(", ishighestadmin=").append(ishighestadmin);
        sb.append(", issystemadmin=").append(issystemadmin);
        sb.append(", birthdate=").append(birthdate);
        sb.append(", telephone=").append(telephone);
        sb.append(", email=").append(email);
        sb.append(", educationlevel=").append(educationlevel);
        sb.append(", userpassword=").append(userpassword);
        sb.append(", createtime=").append(createtime);
        sb.append(", residence=").append(residence);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", groupid=").append(groupid);
        sb.append(", consultanttitle=").append(consultanttitle);
        sb.append(", personalprofile=").append(personalprofile);
        sb.append(", idnum=").append(idnum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}