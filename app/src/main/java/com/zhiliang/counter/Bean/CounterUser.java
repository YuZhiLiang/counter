package com.zhiliang.counter.Bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by YZL19 on 2018/4/7 0007.
 */

public class CounterUser extends BmobUser {
    private BmobUser binduser;

    public BmobUser getBinduser() {
        return binduser;
    }

    public void setBinduser(BmobUser binduser) {
        this.binduser = binduser;
    }

    public static class ColumnName {
        public static String MOBILE_PHONE_NUMBER = "mobilePhoneNumber";
    }
}
