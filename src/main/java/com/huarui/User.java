package com.huarui;

import java.awt.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lihui on 2018/10/20.
 * 用于放入缓存的对象
 */
public class User implements Serializable{

    private String id;
    private String name;
    private String mobile;
    private Integer height;
    private Date birthday;

    public User() {
    }

    public User(String id, String name, String mobile, Integer height, Date birthday) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.height = height;
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", height=" + height +
                ", birthday=" + birthday +
                '}';
    }
}
