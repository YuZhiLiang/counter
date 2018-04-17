package com.zhiliang.counter.features;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.zhiliang.counter.interfaceadapter.ActivityLifecycleCallbacksImp;

import java.util.ArrayList;

/**
 * Activity的收集管理器，主要是利用Application注册ActivityLifecycleCallbacks。
 * 在这个ActivityLifecycleCallbacks回调中，对Activity进行收集，即收集应用内部的所有Activity进入集合。
 * 然后对集合内的Activity进行统一管理，比如：统一关闭，获取集合内Activity的数量等等。
 */
public class ActivityCollectionManager {
    // 用于记录Activity的列表集合
    private ArrayList<Activity> mActivities = new ArrayList<Activity>(5);
    private static ActivityCollectionManager instance= new ActivityCollectionManager();
    public static ActivityCollectionManager getInstance() {
        return instance;
    }

    public void init(Application application) {
        application.registerActivityLifecycleCallbacks(getActivityLifecycleCallbacks());
    }

    private Application.ActivityLifecycleCallbacks getActivityLifecycleCallbacks() {
        return new ActivityLifecycleCallbacksImp() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                super.onActivityCreated(activity, savedInstanceState);
                if (!mActivities.contains(activity)) {
                    mActivities.add(activity);
                }
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                super.onActivityDestroyed(activity);
                mActivities.remove(activity);
            }
        };
    }

    /**
     * 获取当前应用启动的Activity的数量
     *
     * @return
     */
    public int getAliveActivitiesNumber() {
        return mActivities.size();
    }

    /**
     * 是不是有活动的Activity
     *
     * @return
     */
    public boolean hasAliveActivity() {
        return getAliveActivitiesNumber() > 0;
    }

    /**
     * 关闭应用内所有的Activity。
     * 解决：处理安装完第一次进入弹出引导层时出现强制升级Dialog，点退出App会重启的问题
     */
    public void finishAllActivities() {
        for (Activity activity : mActivities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * Finish所有的activity，除了参数指定的activity
     * @param exceptActivityName
     */
    public void finishAllActivitiesExceptActivity(String exceptActivityName){
        Log.i("Jiaxing", "finishAllActivitiesExceptActivity: " + exceptActivityName);
        if (TextUtils.isEmpty(exceptActivityName)){
            return;
        }
        for (Activity activity : mActivities) {
            if (!activity.isFinishing() && !activity.getClass().getName().equals(exceptActivityName)) {
                activity.finish();
            }
        }
    }
}
