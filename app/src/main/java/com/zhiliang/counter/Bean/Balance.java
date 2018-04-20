package com.zhiliang.counter.Bean;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

/**
 * Created by 04816381 on 2018-04-19.
 */

public class Balance extends BmobObject {
    private ArrayList<CounterUser> counterUsers;

    public ArrayList<CounterUser> getCounterUsers() {
        return counterUsers;
    }

    public void setCounterUsers(ArrayList<CounterUser> counterUsers) {
        this.counterUsers = counterUsers;
    }
}
