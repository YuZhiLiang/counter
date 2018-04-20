package com.zhiliang.counter.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by 04816381 on 2018-04-19.
 */

public class BindRequest extends BmobObject {
    private CounterUser requestUser;
    private CounterUser receiverUser;

    public CounterUser getRequestUser() {
        return requestUser;
    }

    public void setRequestUser(CounterUser requestUser) {
        this.requestUser = requestUser;
    }

    public CounterUser getReceiverUser() {
        return receiverUser;
    }

    public void setReceiverUser(CounterUser receiverUser) {
        this.receiverUser = receiverUser;
    }
}
