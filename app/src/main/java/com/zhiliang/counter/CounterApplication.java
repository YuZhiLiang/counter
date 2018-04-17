package com.zhiliang.counter;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.zhiliang.counter.features.ActivityCollectionManager;

import cn.bmob.v3.Bmob;

/**
 * Created by YZL19 on 2018/4/7 0007.
 */

public class CounterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        Bmob.initialize(this.getApplicationContext(), "8ccf3c71bbd851998e80e5de0c1d5de0");
        ActivityCollectionManager.getInstance().init(this);
    }
}
