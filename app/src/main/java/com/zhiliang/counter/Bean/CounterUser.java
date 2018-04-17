package com.zhiliang.counter.Bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by YZL19 on 2018/4/7 0007.
 */

public class CounterUser extends BmobUser {
    private CounterUser binduser;

    public CounterUser getBinduser() {
        return binduser;
    }

    public void setBinduser(CounterUser binduser) {
        this.binduser = binduser;
    }
}
