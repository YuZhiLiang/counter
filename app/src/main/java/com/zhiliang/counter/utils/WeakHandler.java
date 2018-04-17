package com.zhiliang.counter.utils;

import android.os.Handler;

import java.lang.ref.WeakReference;

/**
 * Created by 80151689 on 2017-04-17.
 */
public class WeakHandler<T> extends Handler {
    private final WeakReference<T> mReference;

    public WeakHandler(T reference) {
        mReference = new WeakReference<T>(reference);
    }

    public T getReference() {
        return mReference.get();
    }

    public void clear() {
        mReference.clear();
    }
}
