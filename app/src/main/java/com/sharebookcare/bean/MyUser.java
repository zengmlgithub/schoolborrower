package com.sharebookcare.bean;

import cn.bmob.v3.BmobUser;


public class MyUser extends BmobUser{
    private Integer money;

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "money=" + money +
                ",name=" + getUsername() +
                '}';
    }
}
