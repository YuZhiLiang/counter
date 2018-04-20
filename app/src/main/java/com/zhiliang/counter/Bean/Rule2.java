package com.zhiliang.counter.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by YZL19 on 2018/4/16 0016.
 */

public class Rule2 extends BmobObject {
    private CounterUser initiateCounterUser;
    private CounterUser theOtherOneCounterUser;
    private String cause;

    public CounterUser getInitiateCounterUser() {
        return initiateCounterUser;
    }

    public void setInitiateCounterUser(CounterUser counterUser) {
        this.initiateCounterUser = counterUser;
    }

    public CounterUser getTheOtherOneCounterUser() {
        return theOtherOneCounterUser;
    }

    public void setTheOtherOneCounterUser(CounterUser theOtherOneCounterUser) {
        this.theOtherOneCounterUser = theOtherOneCounterUser;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public static class ColumnName {
        public static String INITIATE_COUNTER_USER = "initiateCounterUser";
        public static String THE_OTHER_ONE_COUNTERUSER = "theOtherOneCounterUser";
    }
}
