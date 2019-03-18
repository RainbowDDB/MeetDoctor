package com.example.meetdoctor.model.bean;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class PersonBean {

    private int id;

    @SerializedName("user_id")
    private int userId;

    private String name;

    @SerializedName("sex")
    private int gender;

    private String birthday;
    private Double height;
    private Double weight;

    @SerializedName("alias_name")
    private String alias; // 备注

    public PersonBean(String name,
                      int gender,
                      String birthday,
                      @Nullable Double height,
                      @Nullable Double weight,
                      String alias) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Double getHeight() {
        return height;
    }

    public Double getWeight() {
        return weight;
    }
}
