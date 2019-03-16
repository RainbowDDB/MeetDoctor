package com.example.meetdoctor.model.bean;

public class PersonBean {

    private String name;
    private int gender;
    private int age;
    private String birthday;
    private int height;
    private int weight;
    private String note; // 备注

    public PersonBean(String name, int gender, int age, String birthday, int height, int weight, String note) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
        this.note = note;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
