package com.zhiliang.counter.utils;

import android.app.Activity;

/**
 * Created by 04816381 on 2018-04-09.
 */

public class WeakActivityHandler<T extends Activity> extends WeakHandler<T> {
    public WeakActivityHandler(T activity) {
        super(activity);
    }

    @Override
    public T getReference() {
        return super.getReference();
    }
}
