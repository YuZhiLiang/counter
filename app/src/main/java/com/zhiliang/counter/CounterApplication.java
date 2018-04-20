package com.zhiliang.counter;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.zhiliang.counter.features.ActivityCollectionManager;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * Created by YZL19 on 2018/4/7 0007.
 */

public class CounterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
//        Bmob.initialize(this.getApplicationContext(), "8ccf3c71bbd851998e80e5de0c1d5de0");
        //设置BmobConfig
        BmobConfig config =new BmobConfig.Builder(this)
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                .setApplicationId("8ccf3c71bbd851998e80e5de0c1d5de0")
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(500*1024)
                .build();
        Bmob.initialize(config);
        ActivityCollectionManager.getInstance().init(this);
    }
}
