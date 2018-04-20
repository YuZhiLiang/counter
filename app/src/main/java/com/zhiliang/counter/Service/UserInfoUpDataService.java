package com.zhiliang.counter.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by 04816381 on 2018-04-20.
 */

public class UserInfoUpDataService extends Service {
    private final UIUBind mUIUBind = new UIUBind();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mUIUBind;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public class UIUBind extends Binder {
        UserInfoUpDataService getService() {
            return UserInfoUpDataService.this;
        }
    }
}
