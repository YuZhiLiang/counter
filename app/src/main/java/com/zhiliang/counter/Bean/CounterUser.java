package com.zhiliang.counter.Bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by YZL19 on 2018/4/7 0007.
 */

public class CounterUser extends BmobUser {
    private CounterUser binduser;
    private Balance balance;
    private String avatat;

    public CounterUser getBinduser() {
        return binduser;
    }

    public void setBinduser(CounterUser binduser) {
        this.binduser = binduser;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public String getAvatat() {
        return avatat;
    }

    public void setAvatat(String avatat) {
        this.avatat = avatat;
    }

    public static CounterUser getCurrentUser() {
        return getCurrentUser(CounterUser.class);
    }

    public static class ColumnName {
        public static String MOBILE_PHONE_NUMBER = "mobilePhoneNumber";
        public static String BALANCE = "balance";
        public static String AVATAT = "avatat";
    }
}
