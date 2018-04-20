package com.zhiliang.counter.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by YZL19 on 2018/4/16 0016.
 */

public class Rule extends BmobObject {
    private CounterUser theCounterUser;
    private CounterUser otherCounterUser;
    private String cause;

    public CounterUser getTheCounterUser() {
        return theCounterUser;
    }

    public void setTheCounterUser(CounterUser counterUser) {
        this.theCounterUser = counterUser;
    }

    public CounterUser getOtherCounterUser() {
        return otherCounterUser;
    }

    public void setOtherCounterUser(CounterUser otherCounterUser) {
        this.otherCounterUser = otherCounterUser;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public static class ColumnName {
        public static String INITIATE_COUNTER_USER = "theCounterUser";
        public static String THE_OTHER_ONE_COUNTERUSER = "otherCounterUser";
    }
}
